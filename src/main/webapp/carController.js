$(document).ready(function () {
    let cars = [];

    document.getElementById("btnDelete").disabled = true;
    document.getElementById("btnUpdate").disabled = true;
    document.getElementById("btnSave").disabled = false;

    loadCars();

    // save new car
    $("#carForm").submit(function (event) {
        event.preventDefault();

        $.ajax({
            url: "car",
            type: "POST",
            data: {
                carId: $("#carId").val(),
                brand: $("#carBrand").val(),
                model: $("#carModel").val(),
                year: $("#carYear").val(),
                price: $("#carPrice").val()
            },
            success: function (response) {
                if (response.trim() === "success") {
                    Swal.fire({
                        position: "top-end",
                        icon: "success",
                        title: "Car added successfully!",
                        showConfirmButton: false,
                        timer: 1500
                    }).then(() => {
                        loadCars();
                        generateCarID();
                        $("#carForm")[0].reset();
                    });
                } else {
                    Swal.fire({
                        icon: "error",
                        title: "Oops...",
                        text: "Failed to add car! Server response: " + response
                    });
                }
            },
            error: function (xhr) {
                Swal.fire({
                    icon: "error",
                    title: "Error!",
                    text: "An error occurred while adding the car. Server response: " + xhr.responseText
                });
            }
        });
    });

    //update selected car
    $("#btnUpdate").click(function () {
        let car = {
            carId: $("#carId").val(),
            brand: $("#carBrand").val(),
            model: $("#carModel").val(),
            year: $("#carYear").val(),
            price: $("#carPrice").val()
        };

        $.ajax({
            url: "car",
            method: "PUT",
            data: JSON.stringify(car),
            contentType: "application/json",
            success: function (res) {
                alert(res.message);
                loadCars();
            },
            error: function (error) {
                let cause = JSON.parse(error.responseText).message;
                alert(cause);
            }
        });
    });

    //delete selected car
    $("#btnDelete").click(function () {
        let id = $("#carId").val();

        Swal.fire({
            title: "Are you sure?",
            text: "You won't be able to undo this!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#d33",
            cancelButtonColor: "#3085d6",
            confirmButtonText: "Yes, delete it!"
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: "car?id=" + id,
                    method: "DELETE",
                    dataType: "json",
                    success: function (resp) {
                        Swal.fire("Deleted!", resp.message, "success").then(() => {
                            loadCars();
                            document.getElementById("btnDelete").disabled = true;
                            document.getElementById("btnUpdate").disabled = true;
                            document.getElementById("btnSave").disabled = false;
                        });
                    },
                    error: function (error) {
                        try {
                            let responseJSON = JSON.parse(error.responseText);
                            Swal.fire("Error!", responseJSON.message, "error");
                        } catch (e) {
                            Swal.fire("Error!", "Unexpected server response", "error");
                        }
                    }

                });
            } else if (result.dismiss === Swal.DismissReason.cancel) {
                setTextFieldValues();
                generateCarID();
            }
        });
    });

    //clear text fields when clear button is clicked
    $("#btnClear").click(function () {
        setTextFieldValues("", "", "", "", "");
        document.getElementById("btnDelete").disabled = true;
        document.getElementById("btnUpdate").disabled = true;
        document.getElementById("btnSave").disabled = false;
        generateCarID();
    });

    // Load cars on page load
    function loadCars() {
        $("#carList").empty();
        $.ajax({
            url: "car",
            type: "GET",
            dataType: "json",
            success: function (resp) {
                cars = resp;
                for (let car of cars) {
                    var row = `<tr><td>${car.carId}</td><td>${car.brand}</td><td>${car.model}</td>
                           <td>${car.year}</td><td>${car.price}</td></tr>`;
                    $("#carList").append(row);
                }

                bindRowClickEvents();
                setTextFieldValues("", "", "", "");
                generateCarID();
                getCarCount();
            }
        });
    }

    function getCarCount() {
        $.ajax({
            url: "car",
            method: "GET",
            dataType: "json",
            success: function (data) {
                let count = data.length;
                $("#carCount").text(count);
            },
            error: function (xhr, status, error) {
                console.error("Error fetching car count:", error);
            }
        });
    }

    // generate ID for cars
    function generateCarID() {
        try {
            let lastCarId = cars.length > 0 ? cars[cars.length - 1].carId : "CAR-000";
            let newCarId = parseInt(lastCarId.substring(4, 7)) + 1;

            let formattedId = newCarId < 10 ? "CAR-00" + newCarId
                : newCarId < 100 ? "CAR-0" + newCarId
                    : "CAR-" + newCarId;

            $("#carId").val(formattedId);
        } catch (e) {
            $("#carId").val("CAR-001"); // If no cars exist
        }
    }

    // Bind row click events
    function bindRowClickEvents() {
        $("#carList>tr").click(function () {
            let carId = $(this).children(":eq(0)").text();
            let brand = $(this).children(":eq(1)").text();
            let model = $(this).children(":eq(2)").text();
            let year = $(this).children(":eq(3)").text();
            let price = $(this).children(":eq(4)").text();

            //setting table details values to text fields
            $('#carId').val(carId);
            $('#carBrand').val(brand);
            $('#carModel').val(model);
            $('#carYear').val(year);
            $('#carPrice').val(price);

            document.getElementById("btnDelete").disabled = false;
            document.getElementById("btnUpdate").disabled = false;
            document.getElementById("btnSave").disabled = true;

        });
    }

    //set values for empty text fields
    function setTextFieldValues(carId, brand, model, year, price) {
        $("#carId").val(carId);
        $("#carBrand").val(brand);
        $("#carModel").val(model);
        $("#carYear").val(year);
        $("#carPrice").val(price);
    }

});