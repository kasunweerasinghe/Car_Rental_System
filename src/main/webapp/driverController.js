$(document).ready(function () {
    let drivers = [];

    document.getElementById("btnDriverDelete").disabled = true;
    document.getElementById("btnDriverUpdate").disabled = true;
    document.getElementById("btnDriverSave").disabled = false;

    loadDrivers();

    // save new driver
    $("#driverForm").submit(function () {
        event.preventDefault();

        $.ajax({
            url: "driver",
            type: "POST",
            data: {
                driverId: $("#driverId").val(),
                driverName: $("#driverName").val(),
                driverAddress: $("#driverAddress").val(),
                driverAge: $("#driverAge").val(),
                driverNationalId: $("#driverNationalId").val()
            },
            success: function (response) {
                if (response.trim() === "success") {
                    Swal.fire({
                        position: "top-end",
                        icon: "success",
                        title: "Driver added successfully!",
                        showConfirmButton: false,
                        timer: 1500
                    }).then(() => {
                        loadDrivers();
                        generateDriverID();
                        $("#driverForm")[0].reset();
                    });
                } else {
                    Swal.fire({
                        icon: "error",
                        title: "Oops...",
                        text: "Failed to add driver! Server response: " + response
                    });
                }
            },
            error: function (xhr) {
                Swal.fire({
                    icon: "error",
                    title: "Error!",
                    text: "An error occurred while adding the driver. Server response: " + xhr.responseText
                });
            }
        });
    });

    $("#btnDriverUpdate").click(function () {
        let driver = {
            driverId: $("#driverId").val(),
            driverName: $("#driverName").val(),
            driverAddress: $("#driverAddress").val(),
            driverAge: $("#driverAge").val(),
            driverNationalId: $("#driverNationalId").val()
        };

        $.ajax({
            url: "driver",
            method: "PUT",
            data: JSON.stringify(driver),
            contentType: "application/json",
            success: function (res) {
                alert(res.message);
                loadDrivers();
            },
            error: function (error) {
                let cause = JSON.parse(error.responseText).message;
                alert(cause);
            }
        });
    });

    //delete selected driver
    $("#btnDriverDelete").click(function () {
        let id = $("#driverId").val();

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
                    url: "driver?id=" + id,
                    method: "DELETE",
                    dataType: "json",
                    success: function (resp) {
                        Swal.fire("Deleted!", resp.message, "success").then(() => {
                            loadDrivers();
                            document.getElementById("btnDriverDelete").disabled = true;
                            document.getElementById("btnDriverUpdate").disabled = true;
                            document.getElementById("btnDriverSave").disabled = false;
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
                generateDriverID();
            }
        });
    });

    //clear text fields when clear button is clicked
    $("#btnDriverClear").click(function () {
        setTextFieldValues("", "", "", "", "");
        document.getElementById("btnDriverDelete").disabled = true;
        document.getElementById("btnDriverUpdate").disabled = true;
        document.getElementById("btnDriverSave").disabled = false;
        generateDriverID();
    });

    //Car Register Search Bar
    $('#driverRegisterInput').on('keyup', function () {
        let value = $(this).val().toLowerCase();
        $('#driverList>tr').filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });

    // Load drivers on page load
    function loadDrivers() {
        $("#driverList").empty();
        $.ajax({
            url: "driver",
            type: "GET",
            dataType: "json",
            success: function (resp) {
                drivers = resp;
                for (let driver of drivers) {
                    var row = `<tr><td>${driver.driverId}</td><td>${driver.driverName}</td><td>${driver.driverAddress}</td>
                           <td>${driver.driverAge}</td><td>${driver.driverNationalId}</td></tr>`;
                    $("#driverList").append(row);
                }

                bindRowClickEvents();
                setTextFieldValues("", "", "", "");
                generateDriverID();
                getDriverCount();
            }
        });
    }

    function getDriverCount() {
        $.ajax({
            url: "driver",
            method: "GET",
            dataType: "json",
            success: function (data) {
                let count = data.length;
                $("#driverCount").text(count);
            },
            error: function (xhr, status, error) {
                console.error("Error fetching car count:", error);
            }
        });
    }

    // generate ID for drivers
    function generateDriverID() {
        try {
            let lastDriverId = drivers.length > 0 ? drivers[drivers.length - 1].driverId : "DID-000";
            let newDriverId = parseInt(lastDriverId.substring(4, 7)) + 1;

            let formattedId = newDriverId < 10 ? "DID-00" + newDriverId
                : newDriverId < 100 ? "DID-0" + newDriverId
                    : "DID-" + newDriverId;

            $("#driverId").val(formattedId);
        } catch (e) {
            $("#driverId").val("DID-001");
        }
    }

    // Bind row click events
    function bindRowClickEvents() {
        $("#driverList>tr").click(function () {
            let driverId = $(this).children(":eq(0)").text();
            let driverName = $(this).children(":eq(1)").text();
            let driverAddress = $(this).children(":eq(2)").text();
            let driverAge = $(this).children(":eq(3)").text();
            let driverNationalId = $(this).children(":eq(4)").text();

            //setting table details values to text fields
            $('#driverId').val(driverId);
            $('#driverName').val(driverName);
            $('#driverAddress').val(driverAddress);
            $('#driverAge').val(driverAge);
            $('#driverNationalId').val(driverNationalId);

            document.getElementById("btnDriverDelete").disabled = false;
            document.getElementById("btnDriverUpdate").disabled = false;
            document.getElementById("btnDriverSave").disabled = true;

        });
    }

    //set values for empty text fields
    function setTextFieldValues(driverId, driverName, driverAddress, driverAge, driverNationalId) {
        $("#driverId").val(driverId);
        $("#driverName").val(driverName);
        $("#driverAddress").val(driverAddress);
        $("#driverAge").val(driverAge);
        $("#driverNationalId").val(driverNationalId);
    }
});