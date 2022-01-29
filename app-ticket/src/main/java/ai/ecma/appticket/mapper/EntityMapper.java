package ai.ecma.appticket.mapper;

import ai.ecma.appticket.entity.Order;
import ai.ecma.appticket.entity.Speaker;
import ai.ecma.appticket.entity.Specialization;
import ai.ecma.appticket.payload.OrderResDto;
import ai.ecma.appticket.payload.SpeakerResDto;
import ai.ecma.appticket.payload.SpecializationDto;
import org.mapstruct.Mapper;

/**
 * @author Muhammad Mo'minov
 * 07.10.2021
 */
@Mapper(componentModel = "spring")
public interface EntityMapper {
    SpeakerResDto speakerToDto(Speaker speaker);

    SpecializationDto specializationToDto(Specialization specialization);
}
