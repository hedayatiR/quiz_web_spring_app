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
        // start of create course
        if (document.getElementById('id_div').style.display === 'none') {

            var valid = true;

            // check endDate > startDate
            valid = compareDate($("#startDate_id").val(), $("#endDate_id").val());

            if (!valid) {
                printErrorMessageModal('تاریخ پایان قبل از تاریخ شروع است!')
            } else {

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

                        $('#messageModal').removeClass('display-none');
                        $('#messageModal').removeClass('alert-warning');
                        $('#messageModal').addClass('alert-success');
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

            }
            // end of create course
        } else { // start of edit course
            var valid = true;

            // check endDate > startDate
            valid = compareDate($("#startDate_id").val(), $("#endDate_id").val());

            if (!valid) {
                printErrorMessageModal('تاریخ پایان قبل از تاریخ شروع است!')
            } else {
                selectedCourseGlobal.name = $("#name_id").val();
                selectedCourseGlobal.startDate = $("#startDate_id").val();
                selectedCourseGlobal.endDate = $("#endDate_id").val();


                console.log(valid);

                console.log(JSON.stringify(selectedCourseGlobal));

                $.ajax({
                    type: "PUT",
                    contentType: "application/json",
                    url: "http://localhost:8080/api/courses",
                    data: JSON.stringify(selectedCourseGlobal),
                    dataType: 'json',
                    success: function (data, textStatus, xhr) {

                        $table.bootstrapTable('updateByUniqueId', {
                            id: selectedCourseGlobal.id,
                            row: selectedCourseGlobal
                        });

                        printSuccessMessageModal('ویرایش دوره با موفقیت انجام شد.');
                    },

                    error: function (e2) {
                        console.log("ERROR: ", e2);
                        printErrorMessageModal('خطایی در ویرایش دوره رخ داده!')
                    }

                }); // end of ajax
            }

        } // end of edit course
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
        $("#modalTitle").html('اضافه کردن دوره');
        document.getElementById('id01').style.display = 'block';
        document.getElementById('id_div').style.display = 'none';
        $("#name_id").val('');
        $("#startDate_id").val('');
        $("#endDate_id").val('');
    });

}) // end of document ready


function operateFormatter() {
    return '<a href="#" class="editCourse btn btn-info btn-xs"> <i class="fa fa-edit"></i> ویرایش درس </a> &nbsp;' +
        '<a href="#" class="deleteTeacher btn btn-danger btn-xs"> <i class="fa fa-trash-o"></i> حذف استاد از درس </a> &nbsp;' +
        '<a href="#" class="changeTeacher btn btn-info btn-xs"><i class="fa fa-edit"></i> تغییر استاد </a> &nbsp;' +
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

    'click .changeTeacher': function (e, value, selectedCourse, index) {

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
    }, // end of editTeacher event handler

    'click .editCourse': function (e, value, selectedCourse, index) {

        $("#modalTitle").html('ویرایش دوره');
        // show id field
        document.getElementById('id_div').style.display = 'block';
        selectedCourseGlobal = selectedCourse;
        //show edit user modal
        document.getElementById('id01').style.display = 'block';
        // fill form field
        $("#id_id").val(selectedCourse.id);
        $("#name_id").val(selectedCourse.name);
        $("#startDate_id").val(selectedCourse.startDate);
        $("#endDate_id").val(selectedCourse.endDate);
    }

}


function compareDate(startDate, endDate) {
    // part[0] : year
    // part[1] : month
    // part[2] : day
    // Please pay attention to the month (parts[1]); JavaScript counts months from 0:
    // January - 0, February - 1, etc.
    var startParts = startDate.split("/");
    var endParts = endDate.split("/");
    Number
    console.log(startParts[0] + "-" + startParts[1] + "-" + startParts[2]);
    console.log(endParts[0] + "-" + endParts[1] + "-" + endParts[2]);

    if (!(Number(startParts[0]) === Number(endParts[0])))
        return Number(endParts[0]) > Number(startParts[0]);

    if (!(Number(startParts[1]) === Number(endParts[1])))
        return Number(endParts[1]) > Number(startParts[1]);

    if (!(Number(startParts[2]) === Number(endParts[2])))
        return Number(endParts[2]) > Number(startParts[2]);

    return false;

}