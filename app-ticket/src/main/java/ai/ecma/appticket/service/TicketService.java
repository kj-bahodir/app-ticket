package ai.ecma.appticket.service;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.ticket.TicketEditReqDto;
import ai.ecma.appticket.payload.ticket.TicketReqDto;
import ai.ecma.appticket.payload.TicketResDto;

import java.util.UUID;


public interface TicketService {

    ApiResult<TicketResDto> add(TicketReqDto ticketReqDto);

    ApiResult<CustomPage<TicketResDto>> getAll(int page, int size);

    ApiResult<TicketResDto> getOne(UUID uuid);

    ApiResult<TicketResDto> edit(UUID id, TicketEditReqDto ticketEditReqDto);

    ApiResult<?> delete(UUID uuid);
}
