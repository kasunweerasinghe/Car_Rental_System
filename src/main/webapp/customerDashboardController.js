$(document).ready(function () {
    $(".nav-link").click(function () {
        $(".nav-link").removeClass("active");
        $(this).addClass("active");
        let selectedTab = $(this).attr("id");
        if(selectedTab==="booking-tab"){
            $("#booking-content").show();
            $("#booking-details-content").hide();
            // generateCarID();
        } else if(selectedTab==="booking-details-tab") {
            $("#booking-content").hide();
            $("#booking-details-content").show();
        }
    });

    // Trigger the dashboard tab by default
    $("#booking-tab").click();
});
