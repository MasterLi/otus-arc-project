package ru.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(value = "Информация о позиции заказе")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    @ApiModelProperty(value = "id товара")
    private Long itemId;

    @ApiModelProperty(value = "id заказа")
    private Long orderId;

    @ApiModelProperty(value = "количество товара")
    private Integer count;

}
