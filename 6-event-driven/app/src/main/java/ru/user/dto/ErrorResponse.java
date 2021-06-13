package ru.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Ответ с ошибкой")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    @ApiModelProperty(value = "Текст ошибки", required = true)
    @JsonProperty("message")
    private String message;

    @ApiModelProperty(value = "Идентификатор ошибки в логе", required = false)
    @JsonProperty("uuid")
    private String uuid;

    @ApiModelProperty(value = "Коллекция с ошибками (если ошибок больше 1)", required = false)
    @JsonProperty("details")
    private List<ru.user.dto.ErrorDetailItem> details;

    @Builder.Default
    @ApiModelProperty(value = "Время сервера", required = true)
    @JsonProperty("timestamp")
    private ZonedDateTime timestamp = ZonedDateTime.now();

    @ApiModelProperty(value = "Время сервера", required = true)
    @JsonProperty("status")
    private Integer status;

    @ApiModelProperty(value = "Описание HTTP ошибки", required = true)
    @JsonProperty("error")
    private String error;

}
