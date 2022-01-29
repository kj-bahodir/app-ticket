package ai.ecma.appticket.controller;

import ai.ecma.appticket.entity.User;
import ai.ecma.appticket.payload.*;
import ai.ecma.appticket.security.CurrentUser;
import ai.ecma.appticket.utils.RestConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static ai.ecma.appticket.utils.AppConstant.DEFAULT_PAGE_NUMBER;
import static ai.ecma.appticket.utils.AppConstant.DEFAULT_PAGE_SIZE;

@RequestMapping(RestConstant.ORDER_CONTROLLER)
@Tag(name = "Order operatsiyalar", description = "Order")
public interface OrderController {

    @Operation(summary = "Orderlarning listini sahifalab olish")
    @GetMapping
    ApiResult<CustomPage<OrderResDto>> getAll(@RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int page,
                                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size);

    @Operation(summary = "Bitta orderni olish")
    @GetMapping("/{id}")
    ApiResult<OrderResDto> getOne(@PathVariable UUID id);

    @Operation(summary = "Oxirgi qilingan orderni birinchi qilib (sort) listini sahifalab olish")
    @GetMapping("/created-at")
    ApiResult<CustomPage<OrderResDto>> getByCreatedAt(@RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int page,
                                                      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size);


    @Operation(summary = "Order yaratish")
    @PostMapping
    ApiResult<PayDto> add(@RequestBody @Valid OrderReqDto orderReqDto, @Parameter(hidden = true) @CurrentUser User user);


    @Operation(summary = "To'lov qilish")
    @PostMapping("/payment")
    ApiResult<?> paymentProcess(@RequestBody @Valid PayDto payDto, @Parameter(hidden = true) @CurrentUser User user);

    @Operation(summary = "Orderni o'chirish")
    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable UUID id);

    @Operation(summary = "Bron qilish uchun bron yaratish")
    @PostMapping("/order-for-bron")
    ApiResult<?> orderForBron(@RequestBody @Valid OrderBronDto orderBronDto, @Parameter(hidden = true) @CurrentUser User user);
}
