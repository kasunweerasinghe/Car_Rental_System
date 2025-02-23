$(document).ready(function () {
    let bookings = [];

    const $carBrandSelect = $("#carBrand");
    const $carModelSelect = $("#carModel");
    const $pricePerDayInput = $("#pricePerDay");

    generateBookingID();
    loadBookings();
    getCurrentDate();
    getCurrentLoggedUserName();
    loadCarBrands();
    loadCarModels();
    loadAvailableDrivers();
    validateForm();
    fetchColomboCities();

    $("#driverName").change(function () {
        let selectedDriverId = $(this).val();
        let selectedDriver = $(this).find(":selected").data();

        if (selectedDriverId) {
            $("#driverId").val(selectedDriver.id);
            $("#driverAge").val(selectedDriver.age);
        } else {
            $("#driverId").val("");
            $("#driverAge").val("");
        }
    });

    $("#startDate, #endDate").change(function () {
        const startDate = $("#startDate").val();
        const endDate = $("#endDate").val();

        if (startDate && endDate) {
            calculateTotalPrice(startDate, endDate);
        }
    });

    // Initialize form validation
    $("#bookingForm input, #bookingForm select").on("input change", validateForm);

    // car book
    $("#bookingForm").submit(function (event) {
        event.preventDefault();

        $.ajax({
            url: "booking",
            type: "POST",
            data: {
                bookingId: document.getElementById('bookingId').value,
                customerName: document.getElementById('customerName').value,
                currentDate: document.getElementById('currentDate').value,
                carBrand: document.getElementById('carBrand').value,
                carModel: document.getElementById('carModel').value,
                price: document.getElementById('pricePerDay').value,
                pickupLocation: document.getElementById('pickupLocation').value,
                dropLocation: document.getElementById('dropLocation').value,
                startDate: document.getElementById('startDate').value,
                endDate: document.getElementById('endDate').value,
                totalPrice: document.getElementById('totalPrice').value,
                driverName: document.getElementById('driverName').options[document.getElementById('driverName').selectedIndex].text,
                driverId: document.getElementById('driverId').value,
                driverAge: document.getElementById('driverAge').value,
            },
            success: function (res) {
                alert("Booking successful!");
                loadBookings();
                generateBookingID();
                $("#bookingForm")[0].reset();
            },
            error: function (error) {
                let cause = JSON.parse(error.responseText).message;
                alert(cause);
            },
        });
    });

    // When brand changes, load models for that brand
    $carBrandSelect.change(function () {
        const selectedBrand = $(this).val();

        // Clear model and price
        $carModelSelect.empty().append('<option value="">Select Model</option>');
        $pricePerDayInput.val(""); // Clear price initially

        if (selectedBrand) {
            loadCarModels(selectedBrand); // Load models for the selected brand
        }
    });

    // When a model is selected, load the price
    $carModelSelect.change(function () {
        const selectedBrand = $carBrandSelect.val();
        const selectedModel = $(this).val();
        $pricePerDayInput.val(""); // Clear price initially

        if (selectedBrand && selectedModel) {
            $.ajax({
                url: `carBookingData?action=price&brand=${encodeURIComponent(selectedBrand)}&model=${encodeURIComponent(selectedModel)}`,
                method: "GET",
                success: function (data) {
                    if (data.price) {
                        $pricePerDayInput.val(data.price);
                    }
                },
                error: function () {
                    console.error("Failed to load car price.");
                }
            });
        }
    });

    function loadAvailableDrivers() {
        $.ajax({
            url: "getAvailableDrivers",
            method: "GET",
            dataType: "json",
            success: function (data) {
                let dropdown = $("#driverName");
                dropdown.empty().append('<option value="">Select a driver</option>');

                $.each(data, function (index, driver) {
                    dropdown.append(
                        `<option value="${driver.driverId}" data-id="${driver.driverId}" data-age="${driver.driverAge}">${driver.driverName}</option>`
                    );
                });
            },
            error: function (xhr, status, error) {
                console.error("Error fetching available drivers:", error);
            }
        });
    }

    // Load car brands
    function loadCarBrands() {
        $.ajax({
            url: 'carBookingData',
            method: 'GET',
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

    // Load car models based on selected brand
    function loadCarModels(brand) {
        $.ajax({
            url: `carBookingData?action=models&brand=${encodeURIComponent(brand)}`,
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

    $('#pickupLocation, #dropLocation').select2({
        placeholder: "Search for a location",
        allowClear: true
    });

    // fetch colombo cities
    function fetchColomboCities() {
        let username = "kasunweerasinghe";
        let url = `http://api.geonames.org/searchJSON?q=Colombo&country=LK&maxRows=30&username=${username}`;

        $.ajax({
            url: url,
            method: "GET",
            dataType: "json",
            success: function (data) {
                let cityList = data.geonames.map(city => city.name);
                populateDropdown("#pickupLocation", cityList);
                populateDropdown("#dropLocation", cityList);
            },
            error: function (error) {
                console.error("Error fetching Colombo cities:", error);
            }
        });
    }

    // populate dropdown
    function populateDropdown(selector, cityList) {
        let dropdown = $(selector);
        dropdown.empty();
        dropdown.append('<option value="">Select Location</option>');

        cityList.forEach(city => {
            dropdown.append(`<option value="${city}">${city}</option>`);
        });

        // Refresh Select2
        dropdown.trigger('change');
    }

    // validate form (enable/disable booking now btn)
    function validateForm() {
        let isValid = true;

        // Check all text inputs, number inputs, and date inputs
        $("#bookingForm input").each(function () {
            if ($(this).prop("disabled") === false && $(this).val().trim() === "") {
                isValid = false;
                return false;
            }
        });

        // Check all select dropdowns
        $("#bookingForm select").each(function () {
            if ($(this).val() === null || $(this).val() === "") {
                isValid = false;
                return false;
            }
        });

        // Enable or disable the button based on validation
        $("#bookingBtn").prop("disabled", !isValid);
    }

    // load all bookings
    function loadBookings() {
        $("#bookingDataList").empty();
        $.ajax({
            url: "booking",
            type: "GET",
            dataType: "json",
            success: function (resp) {
                bookings = resp;
                for (let booking of bookings) {
                    var row = `<tr><td>${booking.bookingId}</td><td>${booking.customerName}</td><td>${booking.currentDate}</td>
                           <td>${booking.carBrand}</td><td>${booking.carModel}</td><td>${booking.price}</td>
                           <td>${booking.pickupLocation}</td><td>${booking.dropLocation}</td><td>${booking.startDate}</td>
                           <td>${booking.endDate}</td><td>${booking.driverName}</td><td>${booking.driverId}</td>
                           <td>${booking.driverAge}</td></tr>`;
                    $("#bookingDataList").append(row);
                }

                generateBookingID();
            }
        });
    }

    // Calculate total price
    function calculateTotalPrice(startDate, endDate) {
        const start = new Date(startDate);
        const end = new Date(endDate);

        if (isNaN(start) || isNaN(end) || start > end) {
            $("#totalPrice").val("");
            return;
        }

        const differenceInTime = end - start;
        const differenceInDays = Math.ceil(differenceInTime / (1000 * 60 * 60 * 24));


        // Get price per day
        const pricePerDay = parseFloat(document.getElementById('pricePerDay').value) || 0;
        const TripTotalPrice = differenceInDays * pricePerDay;
        $("#totalPrice").val(TripTotalPrice);


    }
});