var selectedUserGlobal;
var $table;
var courseId;
var test = 0;
$(document).ready(function () {
    $table = $('#table');

    

    courseId = getParameterByName('id');
    courseName = getParameterByName('name');

    var title = "لیست دانشجویان درس ";
    title += courseName;
    $('#courseTitle').html(title);
    

    console.log("courseId=" + courseId);

    // uncomment one of below

    // 1. offline test - START
    if (test === 1) {

        courseId = 1;

        var studentJson = [{
            "id": 2,
            "firstName": "fn2",
            "lastName": "ln2",
            "role": {
                "id": 1,
                "name": "STUDENT"
            },
            "user": {
                "id": 2,
                "userName": "u2",
                "password": "p2",
                "status": "ACTIVATED"
            },
            "courses": [{
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
                        "status": "ACTIVATED"
                    }
                }
            }]
        }, {
            "id": 1,
            "firstName": "fn1",
            "lastName": "ln1",
            "role": {
                "id": 1,
                "name": "STUDENT"
            },
            "user": {
                "id": 1,
                "userName": "u1",
                "password": "p1",
                "status": "ACTIVATED"
            },
            "courses": [{
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
                        "status": "ACTIVATED"
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
                        "status": "ACTIVATED"
                    }
                }
            }]
        }];


        $table.bootstrapTable({
            data: studentJson
        });

        // check students attended in this course before
        studentJson.forEach(function (itemStudent, indexStudent) {

            for (var itemCourse of itemStudent.courses) {
                console.log(indexStudent);
                
                if (itemCourse.id === courseId) {
                    
                    $("#table").bootstrapTable("check", indexStudent);
                    break;
                }
            } // END of inner for
        }); // END of outer foreach


        // 1. offline test - END

    } else {

        // 2. oline operation - START
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/api/students/activated",
            success: function (studentsJson, textStatus, xhr) {
                // fill table
                $table.bootstrapTable({
                    data: studentsJson
                });

                console.log(studentsJson);
                // check students attended in this course before
                studentsJson.forEach(function (itemStudent, indexStudent) {
                    
                    console.log(courseId);

                    for (var itemCourse of itemStudent.courses) {
                        console.log(itemCourse.id);
                        if (Number(itemCourse.id) === Number(courseId)) {
                            $("#table").bootstrapTable("check", indexStudent);
                            break;
                        }
                    } // END of inner for

                }); // END of outer foreach

            } // end of success
        }); // end of ajax

        // 2. oline operation - END
    }


    
    var $applyBtn = $('#applyBtn');

    $applyBtn.click(function () {

        var checkedRows = $table.bootstrapTable('getAllSelections');
        var ids = [];
        $.each(checkedRows, function (index, value) {
            ids.push(value.id);
        });

        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: "http://localhost:8080/api/courses/" + courseId + "/addStudents",
            data: JSON.stringify(ids),
            dataType: 'json',
            success: function (data, textStatus, xhr) {
                console.log("SUCCESS");
                
                $(message).removeClass('display-none');
                $(message).removeClass('alert-warning');
                $(message).addClass('alert-success');
                $("#message span strong").text('تغییرات دانشجویان درس با موفقیت انجام شد.');
            },

            error: function (e2) {
                console.log("ERROR: ", e2);
                $(message).removeClass('display-none');
                $(message).removeClass('alert-success');
                $(message).addClass('alert-warning');
                $("#message span strong").text('خطایی در تغییرات دانشجویان رخ داده!');
            }
        }); // end of ajax

    });

}) // end of document ready


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