package ru.user.service;

import ru.user.dto.UserDto;
import ru.user.dto.UserInfoDto;
import ru.user.exception.BusinessException;

public interface UserService {

    Long createUser(UserDto dto) throws BusinessException;

    void updateUser(UserDto dto) throws BusinessException;

    void deleteUser(Long userId) throws BusinessException;

    UserInfoDto getUserInfo() throws BusinessException;
}
