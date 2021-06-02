package ru.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.user.dto.ErrorResponse;
import ru.user.dto.UserDto;
import ru.user.dto.UserInfoDto;
import ru.user.service.UserService;

import java.net.HttpURLConnection;
import java.util.List;

@Api(value = ru.user.controller.UserController.BASE_URL, tags = {Tags.USER})
@RestController
@RequestMapping(path = ru.user.controller.UserController.BASE_URL)
@RequiredArgsConstructor
public class UserController {
    public final static String BASE_URL = "/public/user";
    private final UserService userService;

    @ApiOperation(value = "Получение информации о пользователе", tags = {Tags.USER})
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK", response = UserInfoDto.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Business exception", response = ErrorResponse.class),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden exception", response = ErrorResponse.class)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoDto> getUserInfo(@PathVariable(value = "userId", required = true) Long userId) {
        return ResponseEntity.ok(userService.getUserInfo(userId));
    }


    @ApiOperation(value = "Создание пользователе", tags = {Tags.USER})
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK", response = Long.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Business exception", response = ErrorResponse.class),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden exception", response = ErrorResponse.class)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @ApiOperation(value = "Изменение пользователя", tags = {Tags.USER})
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Business exception", response = ErrorResponse.class),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden exception", response = ErrorResponse.class)
    })
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@PathVariable(value = "userId", required = true) Long userId, @RequestBody UserDto userDto) {
        userService.updateUser(userId, userDto);
    }

    @ApiOperation(value = "Удаление пользователя", tags = {Tags.USER})
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Business exception", response = ErrorResponse.class),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden exception", response = ErrorResponse.class)
    })
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUser(@PathVariable(value = "userId", required = true) Long userId) {
        userService.deleteUser(userId);
    }

}
