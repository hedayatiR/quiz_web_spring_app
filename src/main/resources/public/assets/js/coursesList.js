// var selectedUserGlobal;
var $table;
var test = 0;
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

        // 2. oline operation - START
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/api/courses",
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

    var form = $('#createCourseForm');

    // on click of تایید - START ------------------------------------------------
    $(form).submit(function (e) {

        console.log("createCourseForm clicked")
        e.preventDefault();
        var message = $('#messageModal');

        var course = {};

        course.name = $("#name_id").val();
        course.startDate = $("#startDate_id").val();
        course.endDate = $("#endDate_id").val();


        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "http://localhost:8080/api/courses",
            data: JSON.stringify(course),
            dataType: 'json',
            success: function (createdCourse, textStatus, xhr) {
                $table.bootstrapTable('append', createdCourse);

                
                console.log(xhr);

                $(message).removeClass('display-none');
                $(message).removeClass('alert-warning');
                $(message).addClass('alert-success');
                $("#messageModal span strong").text('افزودن دوره با موفقیت انجام شد.');
                $("#name_id").val('');
                $("#startDate_id").val('');
                $("#endDate_id").val('');
            },

            error: function (e2) {
                console.log("ERROR: ", e2);
                $(message).removeClass('display-none');
                $(message).removeClass('alert-success');
                $(message).addClass('alert-warning');
                $("#messageModal span strong").text('خطایی در افزودن دوره رخ داده!');

            }

        }); // end of ajax

    });
    // on click of تایید - END ------------------------------------------------

    // on click of بازگشت - START ------------------------------------------------

    form.on("click", ".btn-danger", function (e) {
        e.preventDefault();
        console.log("return");
        document.getElementById('id01').style.display = 'none';
        $("#messageModal span strong").text('');
    });

    // on click of بازگشت - END ------------------------------------------------


    var $addCourseBtn = $('#addCourseBtn');

    $addCourseBtn.click(function () {
        //show add course modal
        document.getElementById('id01').style.display = 'block';
        $("#name_id").val('');
        $("#startDate_id").val('');
        $("#endDate_id").val('');
    });


}) // end of document ready


function operateFormatter() {
    return '<a href="#" class="deleteTeacher btn btn-danger btn-xs"> <i class="fa fa-trash-o"></i> حذف استاد از درس </a> &nbsp;' +
        '<a href="#" class="editTeacher btn btn-info btn-xs"><i class="fa fa-edit"></i> تغییر استاد </a> &nbsp;' +
        '<a href="#" class="addStudents btn btn-warning btn-xs"> اضافه کردن دانشجو </a> &nbsp;' + 
        '<a href="#" class="showStudents btn btn-success btn-xs"> لیست دانشجویان درس </a>'
        
        ;
}

window.operateEvents = {
    'click .deleteTeacher': function (e, value, selectedCourse, index) {

        if (confirm("مطمئنی؟")) {
            
            selectedCourse.teacher = {};
            if (test === 1) {
                console.log(jQuery.isEmptyObject(selectedCourse.teacher));
                console.log(selectedCourse.teacher);
                console.log(jQuery.isEmptyObject(selectedCourse.teacher));
                $table.bootstrapTable('updateByUniqueId', {
                    id: selectedCourse.id,
                    row: selectedCourse
                });
            }


            $.ajax({
                type: "PUT",
                contentType: "application/json",
                url: "http://localhost:8080/api/courses",
                data: JSON.stringify(selectedCourse),
                dataType: 'json',
                success: function (data, textStatus, xhr) {

                    $table.bootstrapTable('updateByUniqueId', {
                        id: selectedCourse.id,
                        row: selectedCourse
                    });

                    console.log(xhr);

                    $(message).removeClass('display-none');
                    $(message).removeClass('alert-warning');
                    $(message).addClass('alert-success');
                    $("#message span strong").text('حذف استاد از درس با موفقیت انجام شد.');
                }
            }); // end of ajax

        }
    }, // end of deleteTeacher event handler

    'click .editTeacher': function (e, value, selectedCourse, index) {
        
        var queryString = "?id=" + selectedCourse.id + "&name=" + selectedCourse.name;
        window.location.href = "selectTeacher.html" + queryString;
    }, // end of editTeacher event handler

    'click .addStudents': function (e, value, selectedCourse, index) {
        
        var queryString = "?id=" + selectedCourse.id + "&name=" + selectedCourse.name;
        window.location.href = "addStudentsToCourse.html" + queryString;
    }, // end of editTeacher event handler

    'click .showStudents': function (e, value, selectedCourse, index) {
        
        var queryString = "?id=" + selectedCourse.id + "&name=" + selectedCourse.name;
        window.location.href = "showStudentsOfCourse.html" + queryString;
    } // end of editTeacher event handler

}

function teacherNmeFormatter(value, row, index) {
    if (jQuery.isEmptyObject(row.teacher))
        return "-";
    else
        return row.teacher.firstName + " " + row.teacher.lastName;
}

function printErrorMessage(text) {

    $("#message").removeClass('display-none');
    $("#message").removeClass('alert-success');
    $("#message").addClass('alert-warning');
    $("#message span strong").html(text);

}
