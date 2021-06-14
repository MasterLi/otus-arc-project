package ru.user.service;

import ru.user.dto.OrderCreateDto;
import ru.user.dto.OrderDetailDto;
import ru.user.dto.OrderInfoDto;
import ru.user.dto.OrderItemDto;
import ru.user.entity.Order;
import ru.user.entity.User;

import java.util.List;

public interface OrderUserService {

    List<OrderInfoDto> getAllUserOrders(User user);

    List<OrderDetailDto> getUserOrderDetailInfo(User user, Long orderId);

    Long createOrder(User user, OrderCreateDto dto);

    Long addOrderItem(User user, OrderItemDto dto);

    void cancelOrder(User user, Long orderId);

    void changeOrderItemCount(User user, Long orderDetailId, Integer count);

    void deleteOrderItem(User user, Long orderDetailId);

    void payOrder(User user, Long orderId);

}
