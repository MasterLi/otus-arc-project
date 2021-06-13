package ru.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@ApiModel(value = "Информация о заказе")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateDto {
    @ApiModelProperty(value = "товары")
    @JsonProperty("orderItems")
    private List<OrderItemDto> orderItems;

    @ApiModelProperty(value = "idempotentMarker")
    @JsonProperty("idempotentMarker")
    private Long idempotentMarker;
}
