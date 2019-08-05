var selectedUserGlobal;
var $table;
var courseId;
var test = 0;
$(document).ready(function () {
    $table = $('#table');
    
    courseId = getParameterByName('id');
    courseName = getParameterByName('name');

    var title = "انتخاب استاد برای درس ";
    title += courseName;
    $('#courseTitle').html(title);

    // uncomment one of below

    // 1. offline test - START
    if (test === 1) {
        var teacherJson = [{
            "id": 2,
            "firstName": "fn6",
            "lastName": "ln6",
            "role": {
                "id": 2,
                "name": "TEACHER"
            },
            "user": {
                "id": 4,
                "userName": "u6",
                "password": "p6",
                "status": "ACTIVATED"
            }
        }, {
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
        }];

        $(function () {
                $table.bootstrapTable({
                    data: teacherJson
                    });
                });

        // 1. offline test - END

    } else {

        // 2. oline operation - START
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/api/teachers/activated",
            success: function (teachersJson, textStatus, xhr) {

                    $table.bootstrapTable({
                        data: teachersJson
                    });
            }
        }); // end of ajax

        // 2. oline operation - END
    }


}) // end of document ready


function operateFormatter() {
    return '<a href="#" class="select btn btn-success btn-xs"> انتخاب </a> &nbsp;';
}

window.operateEvents = {
    'click .select': function (e, value, selectedTeacher, index) {

        var message = $('#message');
        e.preventDefault();



        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: "http://localhost:8080/api/courses/" + courseId + "/setTeacher",
            data: JSON.stringify(selectedTeacher),
            dataType: 'json',
            success: function (data, textStatus, xhr) {

                console.log(xhr);
                
                // back to coursesList.html
                window.location.href = "coursesList.html";

                // $(message).removeClass('display-none');
                // $(message).removeClass('alert-warning');
                // $(message).addClass('alert-success');
                // $("#message span strong").text('تغییر دسترسی با موفقیت انجام شد.');
            },

            error: function (e2) {
                console.log("ERROR: ", e2);
                $(message).removeClass('display-none');
                $(message).removeClass('alert-success');
                $(message).addClass('alert-warning');
                $("#message span strong").text('خطایی در انتخاب استاد کاربر رخ داده!');
            }
        }); // end of ajax

    } // end of select event handler

    
}


// read query parameter of url
function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}