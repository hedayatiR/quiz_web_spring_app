var $table;
var test = 1;
$(document).ready(function () {

    
    $table = $('#table');

    // uncomment one of below

    // 1. offline test - START
    if (test === 1) {
        var courseJson = [{
            "id": 1,
            "name": "c1",
            "code": 1,
            "startDate": "2019-10-10",
            "endDate": "2019-10-12",
            "teacher": {
                "id": 1,
                "firstName": "fn5",
                "lastName": "ln5",
                "role": {
                    "id": 2,
                    "name": "TEACHER"
                },
                "user": {
                    "id": 3,
                    "userName": "u5",
                    "password": "p5",
                    "status": "INACTIVATED"
                }
            }
        }, {
            "id": 2,
            "name": "c2",
            "code": 2,
            "startDate": "2021-10-10",
            "endDate": "2021-10-12",
            "teacher": {
                "id": 1,
                "firstName": "fn5",
                "lastName": "ln5",
                "role": {
                    "id": 2,
                    "name": "TEACHER"
                },
                "user": {
                    "id": 3,
                    "userName": "u5",
                    "password": "p5",
                    "status": "INACTIVATED"
                }
            }
        }];

        $(function () {

            $table.bootstrapTable({
                data: courseJson
            });

        });
        // 1. offline test - END

    } else {

        var username;
        if (!$.cookie('user')) {
            // no cookie
            // if cookie are lost, take username from backend
            $.ajax({
                method: "GET",
                url: "http://localhost:8080/api/config",
                success: function (configDTO, textStatus, xhr) {
                    username = configDTO.username;
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


        } else {
            username = $.cookie('user');
        }

        console.log(username);

        // 2. oline operation - START
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/api/courses/findByTeacherUsername/" + username,
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

        // 2. oline operation - END
    }

});



function operateFormatter() {
    return '<a href="#" class="showExams btn btn-success btn-xs">  مشاهده آزمون ها </a> &nbsp;'
        ;
}

window.operateEvents = {
    'click .showExams': function (e, value, selectedCourse, index) {
        
        var queryString = "?id=" + selectedCourse.id + "&name=" + selectedCourse.name;
        window.location.href = "examsOfCourseList.html" + queryString;
    }
}
