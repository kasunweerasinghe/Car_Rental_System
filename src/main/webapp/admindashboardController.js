$(document).ready(function () {
    // Handle tab s witching and showing content
    $(".nav-link").click(function () {
        $(".nav-link").removeClass("active");
        $(this).addClass("active");
        let selectedTab = $(this).attr("id");
        if (selectedTab === "dashboard-tab") {
            $("#content").show();
            $("#cars-content").hide();
            $("#drivers-content").hide();
        } else if (selectedTab === "cars-tab") {
            $("#content").hide();
            $("#cars-content").show();
            $("#drivers-content").hide();
            generateCarID();
            // Trigger the dashboard tab by default
            $("#dashboard-tab").click();
        } else if (selectedTab === "drivers-tab") {
            $("#content").hide();
            $("#cars-content").hide();
            $("#drivers-content").show();
            generateDriverID();
        }
    });
    // Trigger the dashboard tab by default
    $("#dashboard-tab").click();
});