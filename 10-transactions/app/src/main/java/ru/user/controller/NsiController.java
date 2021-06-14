package ru.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.user.entity.OrderProduct;
import ru.user.service.NsiService;

import java.util.List;

@Api(value = NsiController.BASE_URL, tags = {Tags.NSI})
@RequestMapping(path = NsiController.BASE_URL)
@RestController
@RequiredArgsConstructor
public class NsiController {
    public final static String BASE_URL = "/private/nsi";
    final private NsiService nsiService;

    @ApiOperation(value = "Справочник товаров", tags = {Tags.NSI})
    @GetMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderProduct>> getAllProducts() {
        return ResponseEntity.ok(nsiService.getAllOrderProducts());
    }
}
