package ru.user.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "Информация о количестве товара в позиции")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemCountDto {
    Long orderDetailId;
    Integer count;
}
