package ai.ecma.appticket.service;

import ai.ecma.appticket.entity.User;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.RefundReqDto;
import ai.ecma.appticket.payload.RefundResDto;

public interface PayBackService {
    ApiResult<RefundResDto> refund(RefundReqDto refundReqDto, User user);

}
