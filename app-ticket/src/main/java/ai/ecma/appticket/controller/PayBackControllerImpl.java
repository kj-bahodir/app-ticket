package ai.ecma.appticket.controller;

import ai.ecma.appticket.entity.User;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.RefundReqDto;
import ai.ecma.appticket.payload.RefundResDto;
import ai.ecma.appticket.service.PayBackService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayBackControllerImpl implements PayBackController{
    private final PayBackService payBackService;

    public PayBackControllerImpl(PayBackService payBackService) {
        this.payBackService = payBackService;
    }

    @Override
    public ApiResult<RefundResDto> refund(RefundReqDto refundReqDto, User user) {
        return payBackService.refund(refundReqDto,user);
    }
}
