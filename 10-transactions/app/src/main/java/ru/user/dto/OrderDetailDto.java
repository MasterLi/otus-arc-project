package ru.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "Информация о заказе")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {

    @ApiModelProperty(value = "id записи")
    private Long id;

    @ApiModelProperty(value = "id заказа")
    private Long orderId;

    @ApiModelProperty(value = "id товара")
    private Long orderProductId;

    @ApiModelProperty(value = "количество")
    private Integer itemCount;

    @ApiModelProperty(value = "наименование")
    private String name;

    @ApiModelProperty(value = "цена")
    private Integer price;

    @ApiModelProperty(value = "Стоимость по позиции")
    private Integer totalPrice;

}
