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

import java.util.List;

@Api(value = OrderUserReadController.BASE_URL, tags = {Tags.ORDER})
@RestController
@RequestMapping(path = OrderUserReadController.BASE_URL)
@RequiredArgsConstructor
public class OrderUserReadController {
    public final static String BASE_URL = "/private/user/order";
    private final UserRepository userRepository;
    private final OrderUserService orderUserService;

    @AccessCheck(value = {"ROLE_USER", "ROLE_ADMIN"})
    @ApiOperation(value = "Получение информации о заказах пользователя", tags = {Tags.ORDER})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderInfoDto>> getAllUserOrders() {
        return ResponseEntity.ok(orderUserService.getAllUserOrders(findCurrentUser()));
    }

    @AccessCheck(value = {"ROLE_USER", "ROLE_ADMIN"})
    @ApiOperation(value = "Получение информации о товаре в заказе пользователя", tags = {Tags.ORDER})
    @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderDetailDto>> getUserOrderDetailInfo(@RequestParam Long orderId) {
        return ResponseEntity.ok(orderUserService.getUserOrderDetailInfo(findCurrentUser(), orderId));
    }

    private User findCurrentUser() {
        String login = SecurityUtils.getLogin();
        return userRepository.findByUsernameLike(login).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
