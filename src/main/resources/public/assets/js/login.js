$(document).ready(function () {

    var $form = $("#loginForm");

    $form.submit(function (e) {
        e.preventDefault();

        var formData = $("#loginForm").serializeArray();

        $.ajax({
            type: "POST",
            url: "http://localhost:8080/login",
            data: formData,
            success: function (data, textStatus, request) {

                var roles = request.getResponseHeader("ROLES");
                if (roles === "STUDENT") {
                    window.location.href = "studentHome.html"
                } else if (roles === "TEACHER") {
                    window.location.href = "teacherHome.html"
                } else if (roles === "ADMIN") {
                    window.location.href = "managerHome.html"
                }
            },

            error: function (request, textStatus, errorThrown) {
                printErrorMessage('نام کاربری یا رمز عبور اشتباه است!');
            }
        });


    }); // end of submit


});


function printErrorMessage(text) {

    $("#message").removeClass('display-none');
    $("#message").removeClass('alert-success');
    $("#message").addClass('alert-warning');
    $("#message span strong").html(text);

}

// function printSuccessMessage(text) {

//     $("#message").removeClass('display-none');
//     $("#message").removeClass('alert-warning');
//     $("#message").addClass('alert-success');
//     $("#message span strong").html(text);

// }