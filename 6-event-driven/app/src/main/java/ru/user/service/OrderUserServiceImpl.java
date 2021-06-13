package ru.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.user.dto.OrderCreateDto;
import ru.user.dto.OrderDetailDto;
import ru.user.dto.OrderInfoDto;
import ru.user.dto.OrderItemDto;
import ru.user.entity.Order;
import ru.user.entity.OrderDetailLog;
import ru.user.entity.OrderProduct;
import ru.user.entity.User;
import ru.user.enums.EventTypeEnum;
import ru.user.enums.OrderStateEnum;
import ru.user.exception.BusinessException;
import ru.user.repository.OrderDetailLogRepository;
import ru.user.repository.OrderProductRepository;
import ru.user.repository.OrderRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderUserServiceImpl implements OrderUserService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderDetailLogRepository orderDetailLogRepository;
    private final EventLogService eventLogService;

    @Override
    public List<OrderInfoDto> getAllUserOrders(User user) {
        List<OrderRepository.OrderInfo> orderInfo = orderRepository.getAllUserOrders(user.getId());
        return orderInfo.stream().map(i -> OrderInfoDto.builder()
                .id(i.getId())
                .createDate(ZonedDateTime.ofInstant(i.getDateCreated(), ZoneId.systemDefault()))
                .itemCount(i.getItemCount())
                .price(i.getPrice())
                .username(i.getUsername())
                .state(OrderStateEnum.valueOf(i.getState()))
                .build()).collect(Collectors.toList());
    }

    @Override
    public List<OrderDetailDto> getUserOrderDetailInfo(User user, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BusinessException("Заказ не найден"));
        checkUserOrderAccess(user, order);
        return orderDetailLogRepository.getUserOrderDetailInfo(orderId);
    }

    @Override
    public Long createOrder(User user, OrderCreateDto dto) {
        //проверяем заказов с маркером идемпотентности
        List<Order> draftOrders = orderRepository.findAllByUserAndIdempotentMarker(user, dto.getIdempotentMarker());
        if (!draftOrders.isEmpty()) {
            throw new BusinessException("You've got orders with marker " + dto.getIdempotentMarker());
        }

        Order order = Order.builder()
                .dateCreated(ZonedDateTime.now())
                .state(OrderStateEnum.DRAFT)
                .user(user)
                .idempotentMarker(dto.getIdempotentMarker())
                .build();
        order = orderRepository.save(order);

        eventLogService.createEvent(order.getId(), user.getUsername(), EventTypeEnum.ORDER_CREATE, OrderStateEnum.DRAFT.name());
        for (OrderItemDto item : dto.getOrderItems()) {
            item.setOrderId(order.getId());
            addOrderItem(user, item);
        }
        return order.getId();
    }

    @Override
    public Long addOrderItem(User user, OrderItemDto dto) {
        Order order = orderRepository.findById(dto.getOrderId()).orElseThrow(() -> new BusinessException("Заказ не найден"));
        checkUserOrderAccess(user, order);
        if (!order.getState().equals(OrderStateEnum.DRAFT)) {
            throw new BusinessException("Заказ не в стадии выбора товара");
        }
        OrderProduct orderProduct = orderProductRepository.findById(dto.getItemId()).orElseThrow(() -> new BusinessException("Товар не найден"));
        OrderDetailLog orderDetailLog = OrderDetailLog.builder()
                .isDeleted(false)
                .itemCount(dto.getCount())
                .order(order)
                .orderProduct(orderProduct)
                .build();
        orderDetailLog = orderDetailLogRepository.save(orderDetailLog);

        eventLogService.createEvent(orderDetailLog.getId(), user.getUsername(), EventTypeEnum.ORDER_PRODUCT_INSERT, dto.getCount().toString());

        return orderDetailLog.getId();
    }

    @Override
    public void cancelOrder(User user, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BusinessException("Заказ не найден"));
        checkUserOrderAccess(user, order);
        if (!order.getState().equals(OrderStateEnum.DRAFT)) {
            throw new BusinessException("Заказ не в стадии выбора товара");
        }
        order.setState(OrderStateEnum.REJECTED);
        orderRepository.save(order);

        eventLogService.createEvent(order.getId(), user.getUsername(), EventTypeEnum.ORDER_CHANGE_STATE, OrderStateEnum.REJECTED.name());
    }

    @Transactional
    @Override
    public void changeOrderItemCount(User user, Long orderDetailId, Integer count) {
        OrderDetailLog orderDetailLog = orderDetailLogRepository.findById(orderDetailId).orElseThrow(() -> new BusinessException("Товар не найден в корзине"));
        Order order = orderRepository.findById(orderDetailLog.getOrder().getId()).orElseThrow(() -> new BusinessException("Заказ не найден"));
        checkUserOrderAccess(user, order);
        if (!order.getState().equals(OrderStateEnum.DRAFT)) {
            throw new BusinessException("Заказ не в стадии выбора товара");
        }
        orderDetailLog.setItemCount(count);
        orderDetailLog = orderDetailLogRepository.save(orderDetailLog);

        eventLogService.createEvent(orderDetailLog.getId(), user.getUsername(), EventTypeEnum.ORDER_PRODUCT_UPDATE, count.toString());
    }

    @Override
    public void deleteOrderItem(User user, Long orderDetailId) {
        OrderDetailLog orderDetailLog = orderDetailLogRepository.findById(orderDetailId).orElseThrow(() -> new BusinessException("Товар не найден в корзине"));
        Order order = orderRepository.findById(orderDetailLog.getOrder().getId()).orElseThrow(() -> new BusinessException("Заказ не найден"));
        checkUserOrderAccess(user, order);
        if (!order.getState().equals(OrderStateEnum.DRAFT)) {
            throw new BusinessException("Заказ не в стадии выбора товара");
        }
        orderDetailLog.setIsDeleted(true);
        orderDetailLog = orderDetailLogRepository.save(orderDetailLog);

        eventLogService.createEvent(orderDetailLog.getId(), user.getUsername(), EventTypeEnum.ORDER_PRODUCT_DELETE, null);
    }

    @Override
    public void payOrder(User user, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BusinessException("Заказ не найден"));
        checkUserOrderAccess(user, order);
        order.setState(OrderStateEnum.PAYMENT);
        orderRepository.save(order);

        eventLogService.createEvent(order.getId(), user.getUsername(), EventTypeEnum.ORDER_CHANGE_STATE, OrderStateEnum.PAYMENT.name());
    }

    private void checkUserOrderAccess(User user, Order order) {
        if (!order.getUser().getId().equals(user.getId())) {
            throw new BusinessException("Нет доступа к заказу");
        }
    }
}
