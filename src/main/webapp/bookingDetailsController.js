$(document).ready(function () {
    let bookingDetails = [];

    loadBookingDetails();

    // Calculate and update balance dynamically
    $("#amountInput").on("input", function () {
        let totalPrice = parseFloat($("#modalTotalPrice").text()) || 0;
        let enteredAmount = parseFloat($(this).val()) || 0;
        let balance = enteredAmount - totalPrice;

        $("#balanceAmount").val(balance); // Update balance field
    });

    // load all bookings
    function loadBookingDetails() {
        $("#bookingDetailsList").empty();
        $.ajax({
            url: "bookingDetails",
            type: "GET",
            dataType: "json",
            success: function (resp) {
                bookingDetails = resp;
                for (let bookingData of bookingDetails) {
                    var row = `<tr>
                    <td>${bookingData.bookingId}</td>
                    <td>${bookingData.customerName}</td>
                    <td>${bookingData.currentDate}</td>
                    <td>${bookingData.carBrand}</td>
                    <td>${bookingData.carModel}</td>
                    <td>${bookingData.totalPrice}</td>
                    <td>${bookingData.startDate}</td>
                    <td>${bookingData.endDate}</td>
                    <td>${bookingData.driverName}</td>
                    <td>${bookingData.driverId}</td>
                    <td>
                        <button class="btn btn-danger checkout-btn"
                            data-booking-id="${bookingData.bookingId}"
                            data-customer-name="${bookingData.customerName}"
                            data-current-date="${bookingData.currentDate}"
                            data-car-brand="${bookingData.carBrand}"
                            data-car-model="${bookingData.carModel}"
                            data-total-price="${bookingData.totalPrice}"
                            data-start-date="${bookingData.startDate}"
                            data-end-date="${bookingData.endDate}"
                            data-driver-name="${bookingData.driverName}"
                            data-driver-id="${bookingData.driverId}">
                            Checkout
                        </button>
                    </td>
                </tr>`;
                    $("#bookingDetailsList").append(row);
                }

                // Attach event listener for checkout button
                $(".checkout-btn").click(function () {
                    let button = $(this);

                    // Populate modal with selected booking details
                    $("#modalBookingId").text(button.data("booking-id"));
                    $("#modalCustomerName").text(button.data("customer-name"));
                    $("#modalCurrentDate").text(button.data("current-date"));
                    $("#modalCarBrand").text(button.data("car-brand"));
                    $("#modalCarModel").text(button.data("car-model"));
                    $("#modalStartDate").text(button.data("start-date"));
                    $("#modalEndDate").text(button.data("end-date"));
                    $("#modalDriverName").text(button.data("driver-name"));
                    $("#modalDriverId").text(button.data("driver-id"));
                    $("#modalTotalPrice").text(button.data("total-price"));

                    // Open Bootstrap Modal
                    $("#checkoutModal").modal("show");
                });

                // Handle Confirm Checkout Button
                $("#confirmCheckout").click(function () {
                    let bookingId = $("#modalBookingId").text();
                    let amount = $("#amountInput").val();

                    if (!amount || amount <= 0) {
                        alert("Please enter a valid amount.");
                        return;
                    }

                    handleCheckout(bookingId, amount);
                    $("#checkoutModal").modal("hide");
                });
            }
        });
    }

    // Function to handle checkout action
    function handleCheckout(bookingId, amount) {
        console.log("Checked out Booking ID:", bookingId);
        console.log("Amount Entered:", amount);

        // Example: You can send the amount to the backend via AJAX
        // $.ajax({
        //     url: "checkoutBooking",
        //     type: "POST",
        //     data: { bookingId: bookingId, amount: amount },
        //     success: function (response) {
        //         alert("Checkout Successful!");
        //         location.reload(); // Reload the page after successful checkout
        //     },
        //     error: function () {
        //         alert("Error processing checkout.");
        //     }
        // });
    }

});