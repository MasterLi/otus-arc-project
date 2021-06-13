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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.user.dto.ErrorResponse;
import ru.user.dto.UserDto;
import ru.user.service.UserService;

import java.net.HttpURLConnection;

@Api(value = PublicController.BASE_URL, tags = {Tags.USER})
@RestController
@RequestMapping(path = PublicController.BASE_URL)
public class PublicController {
    public final static String BASE_URL = "/public/user";
    private final UserService userService;
    private final MeterRegistry registry;
    private final Counter counter_post;

    public PublicController(UserService userService, MeterRegistry registry) {
        this.userService = userService;
        this.registry = registry;
        counter_post = registry.counter("http.requests.user.post.count", "application", "ws-user");

    }

    @ApiOperation(value = "Создание пользователе", tags = {Tags.USER})
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK", response = Long.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Business exception", response = ErrorResponse.class),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden exception", response = ErrorResponse.class)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed(value = "http.requests.user.post.method", description = "Time taken to return info", percentiles = {0.5, 0.95, 0.99})
    public ResponseEntity<Long> createUser(@RequestBody UserDto userDto) {
        counter_post.increment();
        return ResponseEntity.ok(userService.createUser(userDto));
    }
}
