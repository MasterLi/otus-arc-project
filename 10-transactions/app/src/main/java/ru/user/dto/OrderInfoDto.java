package ru.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.user.enums.OrderStateEnum;

import java.time.ZonedDateTime;

@ApiModel(value = "Информация о заказе")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoDto {
    @ApiModelProperty(value = "id договора")
    private Long id;

    @ApiModelProperty(value = "Имя входа пользователя")
    private String username;

    @ApiModelProperty(value = "дата и время заказа")
    private ZonedDateTime createDate;

    @ApiModelProperty(value = "Состояние заказа")
    private OrderStateEnum state;

    @ApiModelProperty(value = "цена заказа")
    private Integer price;

    @ApiModelProperty(value = "позиций товара")
    private Integer itemCount;

}
