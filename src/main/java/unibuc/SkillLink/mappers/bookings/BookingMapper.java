package unibuc.SkillLink.mappers.bookings;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import unibuc.SkillLink.DTOs.booking.BookingResponse;
import unibuc.SkillLink.DTOs.booking.CreateBookingRequest;
import unibuc.SkillLink.models.Booking;


@Mapper(componentModel = "spring", uses = {BookingDetailsMapper.class})
public interface BookingMapper {

    @Mapping(target = "status", constant = "PENDING")
    @Mapping(source = "bookingDetails", target = "bookingDetails")
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "provider", ignore = true)
    Booking toEntity(CreateBookingRequest request);

    @Mapping(source = "client.username", target = "clientUsername")
    @Mapping(source = "provider.username", target = "providerUsername")
    @Mapping(source = "bookingDetails", target = "bookingDetails")
    @Mapping(source = "client.profilePicture", target = "clientProfilePicture")
    @Mapping(source = "provider.profilePicture", target = "providerProfilePicture")
    BookingResponse fromEntity(Booking booking);
}