package ru.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@ApiModel(value = "Информация о пользователе")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @ApiModelProperty(value = "Имя входа пользователя")
    private String username;

    @ApiModelProperty(value = "Имя")
    private String firstName;

    @ApiModelProperty(value = "Фамилия")
    private String lastName;

    @ApiModelProperty(value = "email")
    private String email;

    @ApiModelProperty(value = "Телефон")
    private String phone;

    @ApiModelProperty(value = "Пароль")
    private String password;
}
