$(document).ready(function () {
    let bookings = [];
    generateBookingID();

// generate ID for booking
    function generateBookingID() {
        try {
            let lastBookingId = bookings.length > 0 ? bookings[bookings.length - 1].bookingId : "CB-000";
            let newBookingId = parseInt(lastBookingId.substring(4, 7)) + 1;

            let formattedId = newBookingId < 10 ? "CB-00" + newBookingId
                : newBookingId < 100 ? "CB-0" + newBookingId
                    : "CB-" + newBookingId;

            $("#bookingId").val(formattedId);
        } catch (e) {
            $("#bookingId").val("CB-001");
        }
    }
});