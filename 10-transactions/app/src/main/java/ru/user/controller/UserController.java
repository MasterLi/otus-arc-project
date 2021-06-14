package ru.user.controller;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.user.dto.ErrorResponse;
import ru.user.dto.UserDto;
import ru.user.dto.UserInfoDto;
import ru.user.service.UserService;

import java.net.HttpURLConnection;

@Api(value = ru.user.controller.UserController.BASE_URL, tags = {Tags.USER})
@RestController
@RequestMapping(path = ru.user.controller.UserController.BASE_URL)
public class UserController {
    public final static String BASE_URL = "/private/user";
    private final UserService userService;
    private final MeterRegistry registry;
    private final Counter counter_get;
    private final Counter counter_put;
    private final Counter counter_delete;

    public UserController(UserService userService, MeterRegistry registry) {
        this.userService = userService;
        this.registry = registry;
        counter_get = registry.counter("http.requests.user.get.count", "application", "ws-user");
        counter_put = registry.counter("http.requests.user.put.count", "application", "ws-user");
        counter_delete = registry.counter("http.requests.user.delete.count", "application", "ws-user");
    }


    @ApiOperation(value = "Получение информации о текущем пользователе", tags = {Tags.USER})
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK", response = UserInfoDto.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Business exception", response = ErrorResponse.class),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden exception", response = ErrorResponse.class)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed(value = "http.requests.user.get.method", description = "Time taken to return info", percentiles = {0.5, 0.95, 0.99})
    public ResponseEntity<UserInfoDto> getUserInfo() {
        counter_get.increment();
        return ResponseEntity.ok(userService.getUserInfo());
    }

    @ApiOperation(value = "Изменение пользователя", tags = {Tags.USER})
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Business exception", response = ErrorResponse.class),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden exception", response = ErrorResponse.class)
    })
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed(value = "http.requests.user.put.method", description = "Time taken to return info", percentiles = {0.5, 0.95, 0.99})
    public void updateUser(@RequestBody UserDto userDto) {
        counter_put.increment();
        userService.updateUser(userDto);
    }

    @ApiOperation(value = "Удаление пользователя", tags = {Tags.USER})
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Business exception", response = ErrorResponse.class),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden exception", response = ErrorResponse.class)
    })
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed(value = "http.requests.user.delete.method", description = "Time taken to return info", percentiles = {0.5, 0.95, 0.99})
    public void deleteUser(@RequestParam(value = "userId", required = true) Long userId) {
        counter_delete.increment();
        userService.deleteUser(userId);
    }

}
