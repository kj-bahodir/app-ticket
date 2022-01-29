package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.BronTariffReqDto;
import ai.ecma.appticket.payload.BronTariffResDto;
import ai.ecma.appticket.service.BronTariffService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class BronTariffControllerImpl implements BronTariffController {
    private final BronTariffService bronTariffService;

    public BronTariffControllerImpl(BronTariffService bronTariffService) {
        this.bronTariffService = bronTariffService;
    }

    @PreAuthorize(value = "hasAuthority('ADD_BRON_TARIFF')")
    @Override
    public ApiResult<BronTariffResDto> add(BronTariffReqDto bronTariffReqDto) {
        return bronTariffService.add(bronTariffReqDto);
    }

    @Override
    public ApiResult<BronTariffResDto> getOne(UUID id) {
        return bronTariffService.getOne(id);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_BRON_TARIFF')")
    @Override
    public ApiResult<?> delete(UUID id) {
        return bronTariffService.delete(id);
    }
}
