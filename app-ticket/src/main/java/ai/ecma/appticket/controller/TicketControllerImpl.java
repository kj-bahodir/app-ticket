package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.ticket.TicketEditReqDto;
import ai.ecma.appticket.payload.ticket.TicketReqDto;
import ai.ecma.appticket.payload.TicketResDto;
import ai.ecma.appticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TicketControllerImpl implements TicketController {
    private final TicketService ticketService;

    @PreAuthorize(value = "hasAuthority('ADD_TICKET')")
    @Override
    public ApiResult<TicketResDto> add(TicketReqDto ticketReqDto) {

        return ticketService.add(ticketReqDto);
    }

    @Override
    public ApiResult<CustomPage<TicketResDto>> getAll(int page, int size) {
        return ticketService.getAll(page, size);
    }

    @Override
    public ApiResult<TicketResDto> getOne(UUID id) {
        return ticketService.getOne(id);
    }

    @PreAuthorize(value = "hasAuthority('EDIT_TICKET')")
    @Override
    public ApiResult<TicketResDto> edit(UUID id, @Valid TicketEditReqDto ticketEditReqDto) {
        return ticketService.edit(id, ticketEditReqDto);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_TICKET')")
    @Override
    public ApiResult<?> delete(UUID id) {
        return ticketService.delete(id);
    }
}
