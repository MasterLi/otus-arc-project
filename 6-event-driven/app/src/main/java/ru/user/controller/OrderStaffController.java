package ru.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.user.aspect.AccessCheck;
import ru.user.dto.OrderInfoDto;
import ru.user.dto.RequestParamDto;
import ru.user.service.OrderAdminService;

import java.util.List;

@Api(value = OrderStaffController.BASE_URL, tags = {Tags.ORDER})
@RestController
@RequestMapping(path = OrderStaffController.BASE_URL)
@RequiredArgsConstructor
public class OrderStaffController {
    public final static String BASE_URL = "/private/staff/order";
    private final OrderAdminService orderAdminService;

    @AccessCheck(value = "ROLE_ADMIN")
    @ApiOperation(value = "Получение информации о заказах пользователей", tags = {Tags.ORDER})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderInfoDto>> getAllUserOrders(@RequestBody RequestParamDto requestParamDto) {
        return ResponseEntity.ok(orderAdminService.getAllActiveOrders(requestParamDto));
    }

}
