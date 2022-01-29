package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.PayTypeResDto;
import ai.ecma.appticket.service.PayTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PayTypeControllerImpl implements PayTypeController {
    private final PayTypeService payTypeService;

    @Override
    public ApiResult<List<PayTypeResDto>> getAll() {
        return payTypeService.getAll();
    }

    @Override
    public ApiResult<PayTypeResDto> get(UUID id) {
        return payTypeService.get(id);
    }

    @Override
    @PreAuthorize("hasAuthority('ADD_PAY_TYPE')")
    public ApiResult<PayTypeResDto> create(PayTypeResDto payTypeResDto) {
        return payTypeService.create(payTypeResDto);
    }

    @Override
    @PreAuthorize("hasAuthority('EDIT_PAY_TYPE')")
    public ApiResult<PayTypeResDto> edit(PayTypeResDto payTypeResDto, UUID id) {
        return payTypeService.edit(payTypeResDto, id);
    }

    @Override
    @PreAuthorize("hasAuthority('DELETE_PAY_TYPE')")
    public ApiResult<?> delete(UUID id) {
        return payTypeService.delete(id);
    }
}
