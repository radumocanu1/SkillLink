package unibuc.SkillLink.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import unibuc.SkillLink.DTOs.booking.CreateBookingRequest;
import unibuc.SkillLink.DTOs.booking.UpdateBookingRequest;
import unibuc.SkillLink.abstractions.Mediator;
import unibuc.SkillLink.annotations.Authorized;
import unibuc.SkillLink.commands.GetCurrentUserCommand;
import unibuc.SkillLink.commands.bookings.*;
import unibuc.SkillLink.models.enums.BookingStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class BookingsController {
    @Autowired
    Mediator mediator;

    @GetMapping("/booking/check-availability")
    @ResponseBody
    public ResponseEntity<Map<String, Boolean>> checkAvailability(
            @RequestParam String providerUsername,
            @RequestParam String date,
            @RequestParam String startTime,
            @RequestParam String endTime) {
        
        var command = new CheckBookingAvailabilityCommand(
            providerUsername,
            LocalDate.parse(date),
            LocalTime.parse(startTime),
            LocalTime.parse(endTime)
        );
        
        boolean isAvailable = mediator.handle(command);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", isAvailable);
        
        return ResponseEntity.ok(response);
    }

    @Authorized(authority = "CLIENT")
    @PostMapping("/booking")
    public String createBooking(Model model, Authentication authentication, @RequestBody @Validated CreateBookingRequest createBookingRequest) {
        var bookingResponse = mediator.handle(new CreateBookingCommand(createBookingRequest, authentication.getName()));
        model.addAttribute("bookingResponse", bookingResponse);
        model.addAttribute("authentication", authentication);
        return "booking/details";
    }

    @Authorized
    @GetMapping("/bookings")
    public String getBookings(Model model, Authentication authentication) {
        var user = mediator.handle(new GetCurrentUserCommand(authentication));
        var bookingResponse = mediator.handle(new GetBookingsCommand(user));
        model.addAttribute("bookingResponse", bookingResponse);
        return "booking/all-bookings";
    }

    @Authorized
    @GetMapping("/booking/{id}")
    public String getBookingDetails(@PathVariable UUID id, Model model, Authentication authentication) {
        var bookingResponse = mediator.handle(new GetBookingCommand(id));
        model.addAttribute("bookingResponse", bookingResponse);
        model.addAttribute("authentication", authentication);
        return "booking/details";
    }

    @Authorized(authority = "PROVIDER")
    @PostMapping("/booking/{id}/accept")
    @ResponseBody
    public ResponseEntity<?> acceptBooking(@PathVariable UUID id, @RequestBody(required = false) UpdateBookingRequest request, Authentication authentication) {
        var command = new UpdateBookingStatusCommand(id, BookingStatus.ACCEPTED, authentication.getName());
        if (request != null && request.getConnectionLink() != null) {
            command.setConnectionLink(request.getConnectionLink());
        }
        var bookingResponse = mediator.handle(command);
        return ResponseEntity.ok(bookingResponse);
    }

    @Authorized(authority = "PROVIDER")
    @PostMapping("/booking/{id}/reject")
    @ResponseBody
    public ResponseEntity<?> rejectBooking(@PathVariable UUID id, Authentication authentication) {
        var bookingResponse = mediator.handle(new UpdateBookingStatusCommand(id, BookingStatus.REJECTED, authentication.getName()));
        return ResponseEntity.ok(bookingResponse);
    }

    @Authorized(authority = "CLIENT")
    @PostMapping("/booking/{id}/cancel")
    @ResponseBody
    public ResponseEntity<?> cancelBooking(@PathVariable UUID id, Authentication authentication) {
        var bookingResponse = mediator.handle(new UpdateBookingStatusCommand(id, BookingStatus.REJECTED, authentication.getName()));
        return ResponseEntity.ok(bookingResponse);
    }
}
