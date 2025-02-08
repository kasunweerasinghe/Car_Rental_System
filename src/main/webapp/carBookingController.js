$(document).ready(function () {
    let bookings = [];

    generateBookingID();
    getCurrentDate();
    getCurrentLoggedUserName();

    //get current logged user name
    function getCurrentLoggedUserName() {
        let currentUser = localStorage.getItem("currentUser");

        if (currentUser) {
            try {
                let userData = JSON.parse(currentUser);

                if (userData && userData.username) {
                    $("#customerName").val(userData.username);
                } else {
                    redirectToLogin();
                }
            } catch (error) {
                redirectToLogin();
            }
        } else {
            redirectToLogin();
        }

        function redirectToLogin() {
            window.location.href = "login.html";
        }
    }

    //get current data
    function getCurrentDate() {
        let currentDate = new Date();
        let month = String(currentDate.getMonth() + 1).padStart(2, '0');
        let day = String(currentDate.getDate()).padStart(2, '0');
        let year = currentDate.getFullYear();

        let formattedDate = `${year}-${month}-${day}`;
        $("#currentDate").val(formattedDate);
    }

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