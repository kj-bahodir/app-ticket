package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.ticket.TicketEditReqDto;
import ai.ecma.appticket.payload.ticket.TicketReqDto;
import ai.ecma.appticket.payload.TicketResDto;
import ai.ecma.appticket.utils.RestConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static ai.ecma.appticket.utils.AppConstant.DEFAULT_PAGE_NUMBER;
import static ai.ecma.appticket.utils.AppConstant.DEFAULT_PAGE_SIZE;

@RequestMapping(RestConstant.TICKET_CONTROLLER)
@Tag(name = "Chipta operatsiyalari",description = "Chipta")
public interface TicketController {

    @Operation(summary = "Chipta qo'shish")
    @PostMapping()
    ApiResult<TicketResDto>add(@RequestBody TicketReqDto ticketReqDto);

    @Operation(summary = "Chiptalarning listini sahifalab olish")
    @GetMapping()
    ApiResult<CustomPage<TicketResDto>>getAll(@RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int page,
                                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size);
    @Operation(summary = "Bitta chiptani olish")
    @GetMapping("/{id}")
    ApiResult<TicketResDto>getOne(@PathVariable UUID id);

    @Operation(summary = "Chiptani edit qilish")
    @PutMapping("/{id}")
    ApiResult<TicketResDto>edit(@PathVariable UUID id, @RequestBody @Valid TicketEditReqDto ticketEditReqDto);

    @Operation(summary = "Chiptani o'chirish")
    @DeleteMapping("/{id}")
    ApiResult<?>delete(@PathVariable UUID id);
}
