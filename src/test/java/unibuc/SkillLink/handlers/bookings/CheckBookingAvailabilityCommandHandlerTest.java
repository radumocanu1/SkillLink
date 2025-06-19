package unibuc.SkillLink.handlers.bookings;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unibuc.SkillLink.commands.bookings.CheckBookingAvailabilityCommand;
import unibuc.SkillLink.models.Booking;
import unibuc.SkillLink.models.BookingDetails;
import unibuc.SkillLink.models.enums.BookingStatus;
import unibuc.SkillLink.repositories.BookingsRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckBookingAvailabilityCommandHandlerTest {

    @Mock
    private BookingsRepository bookingsRepository;

    @InjectMocks
    private CheckBookingAvailabilityCommandHandler handler;

    @Test
    void shouldReturnTrueWhenNoExistingBookings() {
        String providerUsername = "testprovider";
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(11, 0);

        when(bookingsRepository.findByProviderUsernameAndBookingDetailsDateAndStatus(
            providerUsername, date, BookingStatus.ACCEPTED
        )).thenReturn(Collections.emptyList());

        Boolean result = handler.handle(new CheckBookingAvailabilityCommand(
            providerUsername, date, startTime, endTime
        ));

        assertTrue(result);
    }

    @Test
    void shouldReturnTrueWhenNoOverlappingBookings() {
        String providerUsername = "testprovider";
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(11, 0);

        Booking existingBooking = new Booking();
        BookingDetails details = new BookingDetails();
        details.setDate(date);
        details.setStartTime(LocalTime.of(12, 0));
        details.setEndTime(LocalTime.of(13, 0));
        existingBooking.setBookingDetails(details);

        when(bookingsRepository.findByProviderUsernameAndBookingDetailsDateAndStatus(
            providerUsername, date, BookingStatus.ACCEPTED
        )).thenReturn(List.of(existingBooking));

        Boolean result = handler.handle(new CheckBookingAvailabilityCommand(
            providerUsername, date, startTime, endTime
        ));

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenOverlappingBookingExists() {
        String providerUsername = "testprovider";
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(11, 0);

        Booking existingBooking = new Booking();
        BookingDetails details = new BookingDetails();
        details.setDate(date);
        details.setStartTime(LocalTime.of(10, 30));
        details.setEndTime(LocalTime.of(11, 30));
        existingBooking.setBookingDetails(details);

        when(bookingsRepository.findByProviderUsernameAndBookingDetailsDateAndStatus(
            providerUsername, date, BookingStatus.ACCEPTED
        )).thenReturn(List.of(existingBooking));

        Boolean result = handler.handle(new CheckBookingAvailabilityCommand(
            providerUsername, date, startTime, endTime
        ));

        assertFalse(result);
    }

    @Test
    void shouldReturnFalseWhenBookingStartsDuringExistingBooking() {
        String providerUsername = "testprovider";
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(10, 30);
        LocalTime endTime = LocalTime.of(11, 30);

        Booking existingBooking = new Booking();
        BookingDetails details = new BookingDetails();
        details.setDate(date);
        details.setStartTime(LocalTime.of(10, 0));
        details.setEndTime(LocalTime.of(11, 0));
        existingBooking.setBookingDetails(details);

        when(bookingsRepository.findByProviderUsernameAndBookingDetailsDateAndStatus(
            providerUsername, date, BookingStatus.ACCEPTED
        )).thenReturn(List.of(existingBooking));

        Boolean result = handler.handle(new CheckBookingAvailabilityCommand(
            providerUsername, date, startTime, endTime
        ));

        assertFalse(result);
    }

    @Test
    void shouldReturnFalseWhenBookingEndsDuringExistingBooking() {
        String providerUsername = "testprovider";
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(9, 30);
        LocalTime endTime = LocalTime.of(10, 30);

        Booking existingBooking = new Booking();
        BookingDetails details = new BookingDetails();
        details.setDate(date);
        details.setStartTime(LocalTime.of(10, 0));
        details.setEndTime(LocalTime.of(11, 0));
        existingBooking.setBookingDetails(details);

        when(bookingsRepository.findByProviderUsernameAndBookingDetailsDateAndStatus(
            providerUsername, date, BookingStatus.ACCEPTED
        )).thenReturn(List.of(existingBooking));

        Boolean result = handler.handle(new CheckBookingAvailabilityCommand(
            providerUsername, date, startTime, endTime
        ));

        assertFalse(result);
    }
} 