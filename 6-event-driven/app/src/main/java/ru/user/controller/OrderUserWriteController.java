package ru.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.user.aspect.AccessCheck;
import ru.user.dto.*;
import ru.user.entity.User;
import ru.user.repository.UserRepository;
import ru.user.service.OrderUserService;
import ru.user.service.SecurityUtils;


@Api(value = OrderUserWriteController.BASE_URL, tags = {Tags.ORDER})
@RestController
@RequestMapping(path = OrderUserWriteController.BASE_URL)
@RequiredArgsConstructor
public class OrderUserWriteController {
    public final static String BASE_URL = "/private/user/order";
    private final UserRepository userRepository;
    private final OrderUserService orderUserService;

    @AccessCheck(value = {"ROLE_USER", "ROLE_ADMIN"})
    @ApiOperation(value = "Создание заказа пользователя", tags = {Tags.ORDER})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createOrder(@RequestBody OrderCreateDto dto) {
        return ResponseEntity.ok(orderUserService.createOrder(findCurrentUser(), dto));
    }

    @AccessCheck(value = {"ROLE_USER", "ROLE_ADMIN"})
    @ApiOperation(value = "Добавление товара в заказе пользователя", tags = {Tags.ORDER})
    @PostMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> addOrderItem(@RequestBody OrderItemDto dto) {
        return ResponseEntity.ok(orderUserService.addOrderItem(findCurrentUser(), dto));
    }

    @AccessCheck(value = {"ROLE_USER", "ROLE_ADMIN"})
    @ApiOperation(value = "Отмена заказа", tags = {Tags.ORDER})
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void cancelOrder(@RequestParam Long orderId) {
        orderUserService.cancelOrder(findCurrentUser(), orderId);
    }

    @AccessCheck(value = {"ROLE_USER", "ROLE_ADMIN"})
    @ApiOperation(value = "Изменение количества товара в заказе пользователя", tags = {Tags.ORDER})
    @PostMapping(value = "/product/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public void changeOrderItemCount(@RequestBody OrderItemCountDto dto) {
        orderUserService.changeOrderItemCount(findCurrentUser(), dto.getOrderDetailId(), dto.getCount());
    }

    @AccessCheck(value = {"ROLE_USER", "ROLE_ADMIN"})
    @ApiOperation(value = "Удаление товара в заказе пользователя", tags = {Tags.ORDER})
    @DeleteMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteOrderItem(@RequestParam Long orderDetailId) {
        orderUserService.deleteOrderItem(findCurrentUser(), orderDetailId);
    }


    @AccessCheck(value = {"ROLE_USER", "ROLE_ADMIN"})
    @ApiOperation(value = "Оплата заказа", tags = {Tags.ORDER})
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void payOrder(@RequestParam Long orderId) {
        orderUserService.payOrder(findCurrentUser(), orderId);
    }

    private User findCurrentUser() {
        String login = SecurityUtils.getLogin();
        return userRepository.findByUsernameLike(login).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
