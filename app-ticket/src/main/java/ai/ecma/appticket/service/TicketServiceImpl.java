package ai.ecma.appticket.service;

import ai.ecma.appticket.entity.Ticket;
import ai.ecma.appticket.entity.User;
import ai.ecma.appticket.exception.RestException;
import ai.ecma.appticket.mapper.CustomMapper;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.ticket.TicketEditReqDto;
import ai.ecma.appticket.payload.ticket.TicketReqDto;
import ai.ecma.appticket.payload.TicketResDto;
import ai.ecma.appticket.repository.EventSessionRepository;
import ai.ecma.appticket.repository.TicketRepository;
import ai.ecma.appticket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final EventSessionRepository eventSessionRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResult<TicketResDto> add(TicketReqDto ticketReqDto) {
        boolean uniqueTicket = ticketRepository.existsBySectionAndRowAndNameAndEventSessionId(
                ticketReqDto.getSection(),
                ticketReqDto.getRow(),
                ticketReqDto.getName(),
                ticketReqDto.getEventSessionId()
        );
        if (uniqueTicket)
            throw new RestException(HttpStatus.CONFLICT, "Bu ticket yaratilgan");

        Ticket ticket = new Ticket(
               ticketReqDto.getUserId()!=null
                       ?userRepository.findById(ticketReqDto.getUserId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND,"User is not found!"))
                       :null,
               eventSessionRepository.findByIdAndActiveTrue(ticketReqDto.getEventSessionId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND,"Tadbir topilmadi")),
               ticketReqDto.getSection(),
               ticketReqDto.getRow(),
               ticketReqDto.getName(),
               ticketReqDto.getStatus(),
               ticketReqDto.getPrice()
        );
        ticketRepository.save(ticket);
        return ApiResult.successResponse(CustomMapper.ticketResDto(ticket));
    }

    @Override
    public ApiResult<TicketResDto> edit(UUID id, TicketEditReqDto ticketEditReqDto) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Chipta topilmadi"));
        ticket.setStatus(ticketEditReqDto.getStatus());
        ticket.setPrice(ticketEditReqDto.getPrice());
        ticket.setUser(userRepository.findById(ticketEditReqDto.getUserId()).orElseGet((Supplier<? extends User>) ticket.getUser()));
        ticketRepository.save(ticket);
        return ApiResult.successResponse(CustomMapper.ticketResDto(ticket));
    }

    @Override
    public ApiResult<CustomPage<TicketResDto>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "price");
        Page<Ticket> ticketPage = ticketRepository.findAll(pageable);
        CustomPage<TicketResDto> ticketResDtoCustomPage = ticketResDtoCustomPage(ticketPage);
        return ApiResult.successResponse(ticketResDtoCustomPage);
    }

    @Override
    public ApiResult<TicketResDto> getOne(UUID uuid) {
        Ticket ticket = ticketRepository.findById(uuid).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Bunaqa chipta bazadan topilmadi"));
        return ApiResult.successResponse(CustomMapper.ticketResDto(ticket));
    }



    @Override
    public ApiResult<?> delete(UUID uuid) {
        try {
            ticketRepository.deleteById(uuid);
            return ApiResult.successResponse("Bilet O'chirildi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RestException(HttpStatus.NOT_FOUND, "bilet topilmadi");
    }


    public CustomPage<TicketResDto> ticketResDtoCustomPage(Page<Ticket> ticketPage) {
        return new CustomPage<>(
                ticketPage.getContent().stream().map(CustomMapper::ticketResDto).collect(Collectors.toList()),
                ticketPage.getNumberOfElements(),
                ticketPage.getNumber(),
                ticketPage.getTotalElements(),
                ticketPage.getTotalPages(),
                ticketPage.getSize()
        );
    }
}
