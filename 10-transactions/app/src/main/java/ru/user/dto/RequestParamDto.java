package ru.user.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import ru.user.enums.OrderStateEnum;

@ApiModel(value = "Информация о количестве товара в позиции")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestParamDto {

    /**
     * тут конечно надо использовать библиотеку, но для упрощения реализации будет без проверок
     */
    private OrderStateEnum orderStateFilter;

    private String sortField;
    private Sort.Direction sortDirection;
}
