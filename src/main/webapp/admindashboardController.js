$(document).ready(function () {

    $('#addCarBtn').click(function () {
        $(".nav-link").removeClass("active");
        $("#cars-tab").addClass("active");

        $("#content").hide();
        $("#cars-content").show();
        $("#drivers-content").hide();
        $("#booking-details-content").hide();
        $("#checkout-data").hide();
    });

    $('#addDriverBtn').click(function () {
        $(".nav-link").removeClass("active");
        $("#drivers-tab").addClass("active");

        $("#content").hide();
        $("#cars-content").hide();
        $("#drivers-content").show();
        $("#booking-details-content").hide();
        $("#checkout-data").hide();
    });

    // Handle tab s witching and showing content
    $(".nav-link").click(function () {
        $(".nav-link").removeClass("active");
        $(this).addClass("active");
        let selectedTab = $(this).attr("id");
        if (selectedTab === "dashboard-tab") {
            $("#content").show();
            $("#cars-content").hide();
            $("#drivers-content").hide();
            $("#booking-details-content").hide();
            $("#checkout-data").hide();
        } else if (selectedTab === "cars-tab") {
            $("#content").hide();
            $("#cars-content").show();
            $("#drivers-content").hide();
            $("#booking-details-content").hide();
            $("#checkout-data").hide();
        } else if (selectedTab === "drivers-tab") {
            $("#content").hide();
            $("#cars-content").hide();
            $("#drivers-content").show();
            $("#booking-details-content").hide();
            $("#checkout-data").hide();
        } else if(selectedTab === "booking-details-tab") {
            $("#content").hide();
            $("#cars-content").hide();
            $("#drivers-content").hide();
            $("#booking-details-content").show();
            $("#checkout-data").hide();
        } else if(selectedTab === "checkout-data-tab") {
            $("#content").hide();
            $("#cars-content").hide();
            $("#drivers-content").hide();
            $("#booking-details-content").hide();
            $("#checkout-data").show();
        }
    });
    // Trigger the dashboard tab by default
    $("#dashboard-tab").click();
});