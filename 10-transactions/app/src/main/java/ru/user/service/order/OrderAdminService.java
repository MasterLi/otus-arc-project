package ru.user.service;

import ru.user.dto.OrderInfoDto;
import ru.user.dto.RequestParamDto;

import java.util.List;

public interface OrderAdminService {

    List<OrderInfoDto> getAllActiveOrders(RequestParamDto requestParamDto);

}
