package ai.ecma.appticket.controller;

import ai.ecma.appticket.entity.User;
import ai.ecma.appticket.payload.*;
import ai.ecma.appticket.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController{

    private final OrderService orderService;

    @Override
    public ApiResult<CustomPage<OrderResDto>> getAll(int page, int size) {
        return orderService.getAll(page,size);
    }

    @Override
    public ApiResult<OrderResDto> getOne(UUID id) {
        return orderService.getOne(id);
    }

    @Override
    public ApiResult<CustomPage<OrderResDto>> getByCreatedAt(int page, int size) {
        return orderService.getByCreatedAt(page,size);
    }

    @Override
    public ApiResult<PayDto> add(OrderReqDto orderReqDto, User user) {
        return orderService.add(orderReqDto,user);
    }


    @PreAuthorize(value = "hasAuthority('PAYMENT')")
    @Override
    public ApiResult<?> paymentProcess(PayDto payDto, User user) {
        return orderService.paymentProcess(payDto,user);
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        return orderService.delete(id);
    }



    @Override
    public ApiResult<?> orderForBron(OrderBronDto orderBronDto, User user) {
        return orderService.orderForBron(orderBronDto, user);
    }
}
