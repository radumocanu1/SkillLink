package unibuc.SkillLink.mappers.bookings;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import unibuc.SkillLink.DTOs.booking.BookingDetailsDTO;
import unibuc.SkillLink.models.BookingDetails;

@Mapper(componentModel = "spring")
public interface BookingDetailsMapper {
    @Mapping(source = "date", target = "date")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "description", target = "description")
    @Mapping(target = "ratePercent", constant = "0.0")
    BookingDetails toEntity(BookingDetailsDTO dto);

    @Mapping(source = "date", target = "date")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "description", target = "description")
    BookingDetailsDTO fromEntity(BookingDetails entity);
}
