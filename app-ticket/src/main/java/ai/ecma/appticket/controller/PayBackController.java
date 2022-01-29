package ai.ecma.appticket.controller;

import ai.ecma.appticket.entity.User;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.RefundReqDto;
import ai.ecma.appticket.payload.RefundResDto;
import ai.ecma.appticket.security.CurrentUser;
import ai.ecma.appticket.utils.RestConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping(RestConstant.PAY_BACK_CONTROLLER)
@Tag(name = "Pul qaytarish operatsiyalari",description = "Pul qaytarish")
public interface PayBackController {


    /**
     * /refund yo'li bu pulni qaytaradigan yo'l.Bu yo'lga cardnumber, userId, List<Ticket> kirib keladi
     * @param refundReqDto
     * @param user
     * @return
     */

    @Operation(summary = "Pulni qayatarish")
    @PostMapping("/refund")
    ApiResult<RefundResDto> refund(@RequestBody @Valid RefundReqDto refundReqDto,
                                   @Parameter(hidden = true) @CurrentUser User user);

}
