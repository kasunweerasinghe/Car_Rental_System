$(document).ready(function () {
    let bookings = [];
    document.getElementById("bookingBtn").disabled = true;
    const $carBrandSelect = $("#carBrand");
    const $carModelSelect = $("#carModel");

    generateBookingID();
    getCurrentDate();
    getCurrentLoggedUserName();
    loadCarBrands();
    loadCarModels();

    // Load car brands
    function loadCarBrands() {
        $.ajax({
            url: 'carBookingData',
            success: function (data) {
                $carBrandSelect.empty().append('<option value="">Select Brand</option>');
                data.forEach(function (brand) {
                    $carBrandSelect.append('<option value="' + brand + '">' + brand + '</option>');
                });
            },
            error: function () {
                console.error("Failed to load car brands.");
            }
        });
    }

    // Load models when a brand is selected
    function loadCarModels() {
        $carBrandSelect.change(function () {
            const selectedBrand = $(this).val();

            if (selectedBrand) {
                $.ajax({
                    url: "carBookingData?action=models&brand=" + encodeURIComponent(selectedBrand),
                    method: "GET",
                    success: function (data) {
                        $carModelSelect.empty().append('<option value="">Select Model</option>');
                        data.forEach(function (model) {
                            $carModelSelect.append('<option value="' + model + '">' + model + '</option>');
                        });
                    },
                    error: function () {
                        console.error("Failed to load car models.");
                    }
                });
            } else {
                $carModelSelect.empty().append('<option value="">Select Model</option>');
            }
        });
    }

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