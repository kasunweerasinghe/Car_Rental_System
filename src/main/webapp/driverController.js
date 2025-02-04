$(document).ready(function () {
    let drivers = [];

    document.getElementById("btnDriverDelete").disabled = true;
    document.getElementById("btnDriverUpdate").disabled = true;
    document.getElementById("btnDriverSave").disabled = false;


    // generate ID for drivers
    function generateDriverID() {
        console.log('call')
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
});