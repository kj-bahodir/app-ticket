package ai.ecma.appticket.service;

import ai.ecma.appticket.entity.User;
import ai.ecma.appticket.payload.*;

import java.util.UUID;

public interface OrderService {

    ApiResult<CustomPage<OrderResDto>> getAll(int page, int size);

    ApiResult<OrderResDto> getOne(UUID id);

    ApiResult<PayDto> add(OrderReqDto orderReqDto, User user);

    ApiResult<?> paymentProcess(PayDto payDto, User user);

    ApiResult<?> delete(UUID id);

    ApiResult<CustomPage<OrderResDto>> getByCreatedAt(int page, int size);

    ApiResult<?> orderForBron(OrderBronDto orderBronDto, User user);
}
