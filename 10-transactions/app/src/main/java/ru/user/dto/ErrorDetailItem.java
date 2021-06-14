package ru.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Детали ошибки")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetailItem {

    @ApiModelProperty(value = "Код", required = false, example = "1")
    @JsonProperty("code")
    private Long code;

    @ApiModelProperty(value = "Текст ошибки", required = true)
    @JsonProperty("message")
    private String message;

    @ApiModelProperty(value = "Расширенное описание ошибки", required = false)
    @JsonProperty("detail_info")
    private String detailInfo;

}
