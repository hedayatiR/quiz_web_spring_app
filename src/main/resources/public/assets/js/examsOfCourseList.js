// var selectedUserGlobal;
var $table;
var test = 0;
var createExam = false;
$(document).ready(function () {
    $table = $('#table');

    // courseId = getParameterByName('id');
    
    // courseName = getParameterByName('name');

    var courseId = localStorage.getItem("id");
    // localStorage.removeItem("id");

    var courseName = localStorage.getItem("name");
    // localStorage.removeItem("id");

    var startDate = localStorage.getItem("startDate");
    // localStorage.removeItem("startDate");
    var endDate = localStorage.getItem("endDate");
    // localStorage.removeItem("endDate");



    // uncomment one of below

    // 1. offline test - START
    if (test === 1) {
        var courseJson = [{}];

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
            url: "http://localhost:8080/api/exams/findByCourseId/" + courseId,
            success: function (examsJson, textStatus, xhr) {

                $table.bootstrapTable({
                    data: examsJson
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

    var form = $('#createExamForm');

    // on click of تایید - START ------------------------------------------------
    $(form).submit(function (e) {

        console.log("createExamForm clicked")
        e.preventDefault();
        // start of create Exam
        if (createExam) {

            var valid = true;

            // check takeDate is between course startDate and endDate
            valid = ( compareDate(startDate, $("#takeDate_id").val()) ) && ( compareDate($("#takeDate_id").val(), endDate) );
            

            if (!valid) {
                printErrorMessageModal('تاریخ آزمون در محدوده برگزاری دوره نیست!')
            } else {

                var Exam = {};
                Exam.title = $("#title_id").val();
                Exam.takeDate = $("#takeDate_id").val();
                Exam.duration = $("#duration_id").val();
                Exam.description = $("#description_id").val();

                var courseOfExam = {};
                courseOfExam.id = courseId;
                Exam.course = courseOfExam;

                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: "http://localhost:8080/api/exams",
                    data: JSON.stringify(Exam),
                    dataType: 'json',
                    success: function (createdExam, textStatus, xhr) {
                        $table.bootstrapTable('append', createdExam);

                        printSuccessMessageModal('افزودن آزمون با موفقیت انجام شد.');

                        $("#title_id").val('');
                        $("#takeDate_id").val('');
                        $("#duration_id").val('');
                        $("#description_id").val('');
                    },

                    error: function (e2) {
                        console.log("ERROR: ", e2);
                        printErrorMessageModal('خطایی در افزودن آزمون رخ داده!');
                    }

                }); // end of ajax

            }
            // end of create exam
        } else { // start of edit exam
            var valid = true;

            // check takeDate is between course startDate and endDate
            valid = ( compareDate(startDate, $("#takeDate_id").val()) ) && ( compareDate($("#takeDate_id").val(), endDate) );

            if (!valid) {
                printErrorMessageModal('تاریخ آزمون در محدوده برگزاری دوره نیست!')
            } else {
                selectedExamGlobal.title = $("#title_id").val();
                selectedExamGlobal.takeDate = $("#takeDate_id").val();
                selectedExamGlobal.duration = $("#duration_id").val();
                selectedExamGlobal.description = $("#description_id").val();

                console.log(JSON.stringify(selectedExamGlobal));

                $.ajax({
                    type: "PUT",
                    contentType: "application/json",
                    url: "http://localhost:8080/api/exams",
                    data: JSON.stringify(selectedExamGlobal),
                    dataType: 'json',
                    success: function (data, textStatus, xhr) {

                        $table.bootstrapTable('updateByUniqueId', {
                            id: selectedExamGlobal.id,
                            row: selectedExamGlobal
                        });

                        printSuccessMessageModal('ویرایش آزمون با موفقیت انجام شد.');
                    },

                    error: function (e2) {
                        console.log("ERROR: ", e2);
                        printErrorMessageModal('خطایی در ویرایش آزمون رخ داده!')
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


    var $addCourseBtn = $('#addExamBtn');

    $addCourseBtn.click(function () {
        createExam = true;
        //show add course modal
        $("#modalTitle").html('ایجاد آزمون');
        document.getElementById('id01').style.display = 'block';
        $("#title_id").val('');
        $("#takeDate_id").val('');
        $("#duration_id").val('');
        $("#description_id").val('');
    });


}) // end of document ready


function operateFormatter() {
    return '<a href="#" class="deleteExam btn btn-danger btn-xs"> <i class="fa fa-trash-o"></i> حذف </a> &nbsp;' +
        '<a href="#" class="editExam btn btn-info btn-xs"><i class="fa fa-edit"></i> ویرایش </a> &nbsp;';
}

window.operateEvents = {
    'click .deleteExam': function (e, value, selectedExam, index) {

        if (confirm("مطمئنی؟")) {

            $.ajax({
                type: "DELETE",
                contentType: "application/json",
                url: "http://localhost:8080/api/exams/" + selectedExam.id,
                success: function (data, textStatus, xhr) {

                    $table.bootstrapTable('removeByUniqueId', selectedExam.id);

                    console.log(xhr);

                    $(message).removeClass('display-none');
                    $(message).removeClass('alert-warning');
                    $(message).addClass('alert-success');
                    $("#message span strong").text('حذف آزمون با موفقیت انجام شد.');
                }
            }); // end of ajax

        }
    }, // end of deleteTeacher event handler

    'click .editExam': function (e, value, selectedExam, index) {

        createExam = false;
        $("#modalTitle").html('ویرایش آزمون');
        // show id field
        selectedExamGlobal = selectedExam;
        //show edit user modal
        document.getElementById('id01').style.display = 'block';
        // fill form field
        $("#title_id").val(selectedExam.title);
        $("#takeDate_id").val(selectedExam.takeDate);
        $("#duration_id").val(selectedExam.duration);
        $("#description_id").val(selectedExam.description);




    } // end of editTeacher event handler

}