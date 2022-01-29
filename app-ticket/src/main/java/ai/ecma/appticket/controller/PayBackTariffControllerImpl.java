package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.PayBackTariffDto;
import ai.ecma.appticket.payload.PayBackTariffReqDto;
import ai.ecma.appticket.service.PayBackTariffServise;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PayBackTariffControllerImpl implements PayBackTariffController {

    private final PayBackTariffServise payBackTariffServise;

    @Override
    public ApiResult<CustomPage<PayBackTariffDto>> getAll(int page, int size) {
        return payBackTariffServise.getAll(page, size);
    }

    @Override
    public ApiResult<PayBackTariffDto> getById(UUID payBackTariffId) {
        return payBackTariffServise.getById(payBackTariffId);
    }

    @PreAuthorize(value = "hasAuthority('ADD_PAY_BACK_TARIFF')")
    @Override
    public ApiResult<PayBackTariffDto> save(@Valid PayBackTariffReqDto payBackTariffReqDto) {
        return payBackTariffServise.save(payBackTariffReqDto);
    }

    @PreAuthorize(value = "hasAuthority('EDIT_PAY_BACK_TARIFF')")
    @Override
    public ApiResult<PayBackTariffDto> edit(@Valid PayBackTariffReqDto payBackTariffReqDto, UUID payBackTariffId) {
        return payBackTariffServise.edit(payBackTariffReqDto, payBackTariffId);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_PAY_BACK_TARIFF')")
    @Override
    public ApiResult<?> delete(UUID payBackTariffId) {
        return payBackTariffServise.delete(payBackTariffId);
    }


}
