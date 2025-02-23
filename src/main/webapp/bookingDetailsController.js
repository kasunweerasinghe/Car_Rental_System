$(document).ready(function () {
    let bookingDetails = [];
    document.getElementById("confirmCheckout").disabled = true;

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

                disableCheckedOutButtons();

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

                    // Disable confirm button initially
                    $("#confirmCheckout").prop("disabled", true);

                    // Open Bootstrap Modal
                    $("#checkoutModal").modal("show");
                });

                // Event listener to check amount input
                $("#amountInput").on("input", function () {
                    let totalPrice = parseFloat($("#modalTotalPrice").text());
                    let enteredAmount = parseFloat($(this).val());

                    if (enteredAmount > totalPrice) {
                        $("#confirmCheckout").prop("disabled", false);
                    } else {
                        $("#confirmCheckout").prop("disabled", true);
                    }
                });

                // Handle Confirm Checkout Button
                $("#confirmCheckout").click(function () {
                    let bookingId = $("#modalBookingId").text();
                    let amount = $("#amountInput").val();

                    if (!amount || amount <= 0) {
                        alert("Please enter a valid amount.");
                        return;
                    }

                    handleCheckout();
                    $("#checkoutModal").modal("hide");
                });
            }
        });
    }

    // Helper function to format date
    function formatDate(dateString) {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are 0-based
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    }

    // Function to handle checkout action
    function handleCheckout() {
        let bookingData = {
            bookingId: $("#modalBookingId").text(),
            customerName: $("#modalCustomerName").text(),
            currentDate: formatDate($("#modalCurrentDate").text()),
            carBrand: $("#modalCarBrand").text(),
            carModel: $("#modalCarModel").text(),
            totalPrice: parseInt($("#modalTotalPrice").text()),
            balance: parseInt($("#balanceAmount").val()),
            startDate: formatDate($("#modalStartDate").text()),
            endDate: formatDate($("#modalEndDate").text()),
            driverName: $("#modalDriverName").text(),
            driverId: $("#modalDriverId").text(),
        };

        $.ajax({
            url: "bookingDetails",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(bookingData),
            success: function (response) {
                if (response === "success") {
                    alert("Checkout Successful!");
                    let bookingId = bookingData.bookingId;
                    let checkedOutBookings = JSON.parse(localStorage.getItem("checkedOutBookings") || "[]");

                    if (!checkedOutBookings.includes(bookingId)) {
                        checkedOutBookings.push(bookingId);
                        localStorage.setItem("checkedOutBookings", JSON.stringify(checkedOutBookings));
                    }

                    // Call the function to disable the button immediately
                    disableCheckedOutButtons();
                } else {
                    alert("Checkout Failed!");
                }
            },
            error: function () {
                alert("Error processing checkout.");
            }
        });
    }

    function disableCheckedOutButtons() {
        let checkedOutBookings = JSON.parse(localStorage.getItem("checkedOutBookings")) || [];

        checkedOutBookings.forEach(bookingId => {
            let button = $(`button[data-booking-id="${bookingId}"]`);

            if (button.length) {
                button.prop("disabled", true)
                    .removeClass("btn-danger")
                    .addClass("btn-secondary")
                    .text("Checked Out");
            } else {
                alert("Invalid booking ID: " + bookingId);
            }
        });
    }
});