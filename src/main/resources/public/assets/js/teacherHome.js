var $table;
var test = 0;
$(document).ready(function () {

    $table = $('#table');

    // 1. offline test - START
    if (test === 1) {
        var courseJson = [{
        }];

        $(function () {

            $table.bootstrapTable({
                data: courseJson
            });

        });
        // 1. offline test - END

    } else {
        // 2. oline operation - START

        var username;
        console.log($.cookie('account'));

        if (typeof $.cookie('account') === 'undefined') {

            // no cookie
            console.log("no cookie");
            // if cookie are lost, take username from backend
            $.ajax({
                method: "GET",
                url: "http://localhost:8080/api/config",
                success: function (configDTO, textStatus, xhr) {
                    username = configDTO.username;
                    $("#userNameProfile").text($.cookie('account'));

                },
                error: function (xhr, ajaxOptions, thrownError) {
                    switch (xhr.status) {
                        case 403:
                            // Take action, referencing xhr.responseText as needed.
                            printErrorMessage('به این سرویس دسترسی ندارید. برای ورود' + '<a href="./login.html"> اینجا </a>' + '  را کلیلک کنید');
                            break;
                    }
                },
                complete: function (data) {
                    fillTableAjax(username);
                }
            }); // end of ajax

        } else {
            // cookie existed
            console.log("cookie existed");
            username = $.cookie('account');
            fillTableAjax(username);
        }

        // 2. oline operation - END
    }

}); // end of document ready




function fillTableAjax(teacherUsername) {
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/api/courses/findByTeacherUsername/" + teacherUsername,
        success: function (coursesJson, textStatus, xhr) {

            $table.bootstrapTable({
                data: coursesJson
            });
        },
        error: function (xhr, ajaxOptions, thrownError) {
            switch (xhr.status) {
                case 403:
                    // Take action, referencing xhr.responseText as needed.
                    printErrorMessage('به این سرویس دسترسی ندارید. برای ورود' + '<a href="./login.html"> اینجا </a>' + '  را کلیلک کنید');
                    break;
            }
        }
    }); // end of ajax

}

function operateFormatter() {
    return '<a href="#" class="showExams btn btn-success btn-xs">  مشاهده آزمون ها </a> &nbsp;';
}

window.operateEvents = {
    'click .showExams': function (e, value, selectedCourse, index) {

        localStorage.setItem("id", selectedCourse.id);
        localStorage.setItem("name", selectedCourse.name);
        localStorage.setItem("startDate", selectedCourse.startDate);
        localStorage.setItem("endDate", selectedCourse.endDate);
        
        window.location.href = "examsOfCourseList.html";
    }
}