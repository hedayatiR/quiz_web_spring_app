var test = 0;
$(document).ready(function () {

    var form = $("#signupForm");

    form.submit(function (e) {
        e.preventDefault();

        printErrorMessage('', false);

        var valid = true;

        if (($('[name="password"]').val()) !== ($('[name="repeatPassword"]').val())) {
            printErrorMessage('!رمز عبورها برابر نیستند', true);
            valid = false;
        }

        var containDigitPattern = new RegExp(/\d/);

        if (containDigitPattern.test($('[name="firstName"]').val())) {
            printErrorMessage('!نام نباید حاوی عدد باشد', true);
            valid = false;
        }
        if (containDigitPattern.test($('[name="lastName"]').val())) {
            printErrorMessage('!نام خانوادگی نباید حاوی عدد باشد', true);
            valid = false;
        }

        if (valid) {
            // make json
            var json = {};
            json.firstName = $('[name="firstName"]').val();
            json.lastName = $('[name="lastName"]').val();
            var user = {};
            user.username = $('[name="username"]').val();
            user.password = $('[name="password"]').val();
            user.repeatPassword = $('[name="repeatPassword"]').val();
            var role = {};
            role.name = $('[name="role"]').val();
            var roleArray = [];
            roleArray.push(role);
            user.roles = roleArray;
            json.user = user;
            
            var destURL;
            if (role.name === "STUDENT") {
                destURL = "http://localhost:8080/api/students";
            } else if (role.name === "TEACHER") {
                destURL = "http://localhost:8080/api/teachers";
            }

            console.log(JSON.stringify(json));

             // create new user from signup ajax
             $.ajax({
                type: "POST",
                contentType: "application/json",
                url: destURL,
                data: JSON.stringify(json),
                dataType: 'json',
                success: function (createdUser, textStatus, xhr) {
                    console.log("success of create user");
                    printSuccessMessage('ساخت کاربر جدبد با موفقیت انجام شد.');
                },

                error: function (e2) {
                    console.log("ERROR: ", e2);
                    printErrorMessage('خطایی در ساخت کاربر جدید رخ داده!');
                }

            }); // end of ajax


        }


    });

})



function printErrorMessage(text, append) {

    $("#message").removeClass('display-none');
    $("#message").removeClass('alert-success');
    $("#message").addClass('alert-warning');
    if (append) {
        $("#message span strong").append(text);
    } else
        $("#message span strong").html(text);
}

function printSuccessMessage(text) {

    $("#message").removeClass('display-none');
    $("#message").removeClass('alert-warning');
    $("#message").addClass('alert-success');
    $("#message span strong").html(text);

}