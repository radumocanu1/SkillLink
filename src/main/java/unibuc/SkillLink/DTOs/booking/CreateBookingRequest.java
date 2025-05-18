package unibuc.SkillLink.DTOs.booking;

import unibuc.SkillLink.models.enums.BookingType;

public class CreateBookingRequest {
    private String providerUsername;
    private BookingType bookingType;
    private BookingDetailsDTO bookingDetails;

    public CreateBookingRequest() {}

    public String getProviderUsername() {
        return providerUsername;
    }

    public void setProviderUsername(String providerUsername) {
        this.providerUsername = providerUsername;
    }

    public BookingType getBookingType() {
        return bookingType;
    }

    public void setBookingType(BookingType bookingType) {
        this.bookingType = bookingType;
    }

    public BookingDetailsDTO getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(BookingDetailsDTO bookingDetails) {
        this.bookingDetails = bookingDetails;
    }
}
