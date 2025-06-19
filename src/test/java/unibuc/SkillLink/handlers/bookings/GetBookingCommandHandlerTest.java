package unibuc.SkillLink.handlers.bookings;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unibuc.SkillLink.DTOs.booking.BookingResponse;
import unibuc.SkillLink.commands.bookings.GetBookingCommand;
import unibuc.SkillLink.mappers.bookings.BookingMapper;
import unibuc.SkillLink.models.Booking;
import unibuc.SkillLink.models.BookingDetails;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.models.Provider;
import unibuc.SkillLink.models.enums.BookingStatus;
import unibuc.SkillLink.models.enums.BookingType;
import unibuc.SkillLink.repositories.BookingsRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetBookingCommandHandlerTest {

    @Mock
    private BookingsRepository bookingsRepository;

    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    private GetBookingCommandHandler handler;

    @Test
    void shouldGetBookingById() {
        UUID bookingId = UUID.randomUUID();
        String clientUsername = "testclient";
        String providerUsername = "testprovider";

        Provider provider = new Provider();
        provider.setUsername(providerUsername);
        provider.setId(UUID.randomUUID());

        Client client = new Client();
        client.setUsername(clientUsername);
        client.setId(UUID.randomUUID());

        BookingDetails details = new BookingDetails();
        details.setDate(LocalDate.now());
        details.setStartTime(LocalTime.of(10, 0));
        details.setEndTime(LocalTime.of(11, 0));

        Booking booking = new Booking();
        booking.setId(bookingId);
        booking.setBookingType(BookingType.ONLINE);
        booking.setStatus(BookingStatus.PENDING);
        booking.setProvider(provider);
        booking.setClient(client);
        booking.setBookingDetails(details);

        BookingResponse expectedResponse = new BookingResponse();
        expectedResponse.setId(bookingId);
        expectedResponse.setBookingType(BookingType.ONLINE);
        expectedResponse.setStatus(BookingStatus.PENDING);
        expectedResponse.setClientUsername(clientUsername);
        expectedResponse.setProviderUsername(providerUsername);

        when(bookingsRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(bookingMapper.fromEntity(booking)).thenReturn(expectedResponse);

        BookingResponse result = handler.handle(new GetBookingCommand(bookingId));

        assertNotNull(result);
        assertEquals(bookingId, result.getId());
        assertEquals(BookingType.ONLINE, result.getBookingType());
        assertEquals(BookingStatus.PENDING, result.getStatus());
        assertEquals(clientUsername, result.getClientUsername());
        assertEquals(providerUsername, result.getProviderUsername());
    }

    @Test
    void shouldThrowExceptionWhenBookingNotFound() {
        UUID bookingId = UUID.randomUUID();
        when(bookingsRepository.findById(bookingId)).thenReturn(Optional.empty());

        assertThrows(
            RuntimeException.class,
            () -> handler.handle(new GetBookingCommand(bookingId)),
            "Booking not found"
        );
    }
} 