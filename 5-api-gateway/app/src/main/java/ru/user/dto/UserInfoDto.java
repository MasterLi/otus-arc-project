package ru.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@ApiModel(value = "Полная Информация о пользователе")
@Getter
@SuperBuilder
public class UserInfoDto extends UserDto {

    @ApiModelProperty(value = "id")
    private Long id;
}
