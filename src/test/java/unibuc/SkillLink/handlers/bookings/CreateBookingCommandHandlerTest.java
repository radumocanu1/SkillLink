package unibuc.SkillLink.handlers.bookings;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unibuc.SkillLink.DTOs.booking.BookingDetailsDTO;
import unibuc.SkillLink.DTOs.booking.BookingResponse;
import unibuc.SkillLink.DTOs.booking.CreateBookingRequest;
import unibuc.SkillLink.abstractions.Mediator;
import unibuc.SkillLink.commands.bookings.CreateBookingCommand;
import unibuc.SkillLink.commands.clients.GetClientCommand;
import unibuc.SkillLink.commands.providers.GetProviderCommand;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateBookingCommandHandlerTest {

    @Mock
    private BookingsRepository bookingsRepository;

    @Mock
    private BookingMapper bookingMapper;

    @Mock
    private Mediator mediator;

    @InjectMocks
    private CreateBookingCommandHandler handler;

    @Test
    void shouldCreateBooking() {
        String clientUsername = "testclient";
        String providerUsername = "testprovider";
        UUID bookingId = UUID.randomUUID();

        CreateBookingRequest request = new CreateBookingRequest();
        request.setProviderUsername(providerUsername);
        request.setBookingType(BookingType.ONLINE);
        
        BookingDetailsDTO detailsDTO = new BookingDetailsDTO();
        detailsDTO.setDate(LocalDate.now());
        detailsDTO.setStartTime(LocalTime.of(10, 0));
        detailsDTO.setEndTime(LocalTime.of(11, 0));
        request.setBookingDetails(detailsDTO);

        Provider provider = new Provider();
        provider.setUsername(providerUsername);
        provider.setId(UUID.randomUUID());

        Client client = new Client();
        client.setUsername(clientUsername);
        client.setId(UUID.randomUUID());

        Booking booking = new Booking();
        booking.setId(bookingId);
        booking.setBookingType(BookingType.ONLINE);
        booking.setStatus(BookingStatus.PENDING);
        booking.setProvider(provider);
        booking.setClient(client);
        
        BookingDetails details = new BookingDetails();
        details.setDate(detailsDTO.getDate());
        details.setStartTime(detailsDTO.getStartTime());
        details.setEndTime(detailsDTO.getEndTime());
        booking.setBookingDetails(details);

        BookingResponse expectedResponse = new BookingResponse();
        expectedResponse.setId(bookingId);
        expectedResponse.setBookingType(BookingType.ONLINE);
        expectedResponse.setStatus(BookingStatus.PENDING);
        expectedResponse.setClientUsername(clientUsername);
        expectedResponse.setProviderUsername(providerUsername);

        when(mediator.handle(any(GetProviderCommand.class))).thenReturn(provider);
        when(mediator.handle(any(GetClientCommand.class))).thenReturn(client);
        when(bookingMapper.toEntity(request)).thenReturn(booking);
        when(bookingsRepository.save(booking)).thenReturn(booking);
        when(bookingMapper.fromEntity(booking)).thenReturn(expectedResponse);

        BookingResponse result = handler.handle(new CreateBookingCommand(request, clientUsername));

        assertNotNull(result);
        assertEquals(bookingId, result.getId());
        assertEquals(BookingType.ONLINE, result.getBookingType());
        assertEquals(BookingStatus.PENDING, result.getStatus());
        assertEquals(clientUsername, result.getClientUsername());
        assertEquals(providerUsername, result.getProviderUsername());
    }
} 