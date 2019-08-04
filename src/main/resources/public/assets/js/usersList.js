var selectedUserGlobal;
var $table;
$(document).ready(function () {
    $table = $('#table');

    // uncomment one of below

    // // 1. offline test - START

    // var studentJson = [{
    //     "id": 1,
    //     "firstName": "fn1",
    //     "lastName": "ln1",
    //     "role": {
    //         "name": "STUDENT"
    //     },
    //     "user": {
    //         "userName": "u1",
    //         "password": "p1",
    //         "status": "INACTIVATED"
    //     }
    // }, {
    //     "id": 2,
    //     "firstName": "fn2",
    //     "lastName": "ln2",

    //     "role": {
    //         "name": "STUDENT"
    //     },
    //     "user": {
    //         "userName": "u2",
    //         "password": "p2",
    //         "status": "INACTIVATED"
    //     }
    // }];

    // var teacherJson = [{
    //     "id": 2,
    //     "firstName": "fn6",
    //     "lastName": "ln6",
    //     "role": {
    //         "id": 2,
    //         "name": "TEACHER"
    //     },
    //     "user": {
    //         "id": 4,
    //         "userName": "u6",
    //         "password": "p6",
    //         "status": "INACTIVATED"
    //     }
    // }, {
    //     "id": 1,
    //     "firstName": "fn5",
    //     "lastName": "ln5",
    //     "role": {
    //         "id": 2,
    //         "name": "TEACHER"
    //     },
    //     "user": {
    //         "id": 3,
    //         "userName": "u5",
    //         "password": "p5",
    //         "status": "INACTIVATED"
    //     }
    // }];

    // $(function () {

    //     if (String($table.bootstrapTable('getData')[0]).endsWith("Element]")) {
    //         $table.bootstrapTable({
    //             data: studentJson
    //         });
    //         console.log("NULL");
    //     } else
    //         $table.bootstrapTable('append', studentJson);


    //         if (String($table.bootstrapTable('getData')[0]).endsWith("Element]")) {
    //         $table.bootstrapTable({
    //             data: teacherJson
    //         });
    //         console.log("NULL");
    //     } else
    //         $table.bootstrapTable('append', teacherJson);
    // });
    // // 1. offline test - END


    // 2. oline operation - START
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/api/students",
        success: function (studentsJson, textStatus, xhr) {

            if (String($table.bootstrapTable('getData')[0]).endsWith("Element]")) {
                $table.bootstrapTable({
                    data: studentsJson
                });
                console.log("NULL");
            } else
                $table.bootstrapTable('append', studentsJson);
        }
    }); // end of ajax

    $.ajax({
        method: "GET",
        url: "http://localhost:8080/api/teachers",
        success: function (teachersJson, textStatus, xhr) {

            if (String($table.bootstrapTable('getData')[0]).endsWith("Element]")) {
                $table.bootstrapTable({
                    data: teachersJson
                });
                console.log("NULL");
            } else
                $table.bootstrapTable('append', teachersJson);

        }
    }); // end of ajax

    // 2. oline operation - END


    var form = $('#editUserForm');

    // on click of تایید - START ------------------------------------------------
    $(form).submit(function (e) {

        var message = $('#messageModal');
        e.preventDefault();

        selectedUserGlobal.firstName = $("#firstName_id").val();
        selectedUserGlobal.lastName = $("#lastName_id").val();
        selectedUserGlobal.user.status = $("#status_id").val();
        selectedUserGlobal.user.userName = $("#userName_id").val();
        selectedUserGlobal.user.password = $("#password_id").val();
        selectedUserGlobal.role.name = $("#role_id").val();

        var destURL;
        if (selectedUserGlobal.role.name === "STUDENT") {
            selectedUserGlobal.role.id = 1;
            destURL = "http://localhost:8080/api/students";
        } else if ((selectedUserGlobal.role.name === "TEACHER")) {
            destURL = "http://localhost:8080/api/teachers";
            selectedUserGlobal.role.id = 2;
        }

        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: destURL,
            data: JSON.stringify(selectedUserGlobal),
            dataType: 'json',
            success: function (data, textStatus, xhr) {

                $table.bootstrapTable('updateByUniqueId', {
                    id: selectedUserGlobal.id,
                    row: selectedUserGlobal
                });

                console.log(xhr);

                $(message).removeClass('display-none');
                $(message).removeClass('alert-warning');
                $(message).addClass('alert-success');
                $("#messageModal span strong").text('ویرایش کاربر با موفقیت انجام شد.');
            },

            error: function (e2) {
                console.log("ERROR: ", e2);
                $(message).removeClass('display-none');
                $(message).removeClass('alert-success');
                $(message).addClass('alert-warning');
                $("#messageModal span strong").text('خطایی در ویرایش کاربر رخ داده!');

            }

        }); // end of ajax

    });
    // on click of تایید - END ------------------------------------------------

    // on click of بازگشت - START ------------------------------------------------

    $("#editUserForm").on("click", ".btn-danger", function (e) {
        e.preventDefault();
        console.log("return");
        document.getElementById('id01').style.display = 'none';
        $("#messageModal span strong").text('');
    });

    // on click of بازگشت - END ------------------------------------------------

}) // end of document ready


function operateFormatter() {
    return '<a href="#" class="toggle btn btn-warning btn-xs"> تغییر وضعیت </a> &nbsp;' +
        '<a href="#" class="edit btn btn-info btn-xs"><i class="fa fa-edit"></i>  </a> &nbsp;' +
        '<a href="#" class="remove btn btn-danger btn-xs"><i class="fa fa-trash-o"></i>  </a>';
}

window.operateEvents = {
    'click .toggle': function (e, value, selectedUserToggle, index) {

        var message = $('#message');
        e.preventDefault();


        if (selectedUserToggle.user.status === "ACTIVATED") {
            selectedUserToggle.user.status = "INACTIVATED";
        } else if (selectedUserToggle.user.status === "INACTIVATED") {
            selectedUserToggle.user.status = "ACTIVATED";
        }

        var destURL;
        if (selectedUserToggle.role.name === "STUDENT") {
            destURL = "http://localhost:8080/api/students";
        } else if ((selectedUserToggle.role.name === "TEACHER")) {
            destURL = "http://localhost:8080/api/teachers";
        }


        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: destURL,
            data: JSON.stringify(selectedUserToggle),
            dataType: 'json',
            success: function (data, textStatus, xhr) {

                $table.bootstrapTable('updateByUniqueId', {
                    id: selectedUserToggle.id,
                    row: selectedUserToggle
                });

                console.log(xhr);

                $(message).removeClass('display-none');
                $(message).removeClass('alert-warning');
                $(message).addClass('alert-success');
                $("#message span strong").text('تغییر دسترسی با موفقیت انجام شد.');
            },

            error: function (e2) {
                console.log("ERROR: ", e2);
                $(message).removeClass('display-none');
                $(message).removeClass('alert-success');
                $(message).addClass('alert-warning');
                $("#message span strong").text('خطایی در تغییر دسترسی کاربر رخ داده!');
            }
        }); // end of ajax

    }, // end of toggle event handler

    'click .remove': function (e, value, selectedUser, index) {

        if (confirm("مطمئنی؟")) {
            $('#table').bootstrapTable('remove', {
                field: 'id',
                values: [selectedUser.id]
            })
        }
    }, // end of remove event handler

    'click .edit': function (e, value, selectedUser, index) {
        selectedUserGlobal = selectedUser;
        //show edit user modal
        document.getElementById('id01').style.display = 'block';
        // fill form field
        $("#id_id").val(selectedUser.id);
        $("#firstName_id").val(selectedUser.firstName);
        $("#lastName_id").val(selectedUser.lastName);
        $("#status_id").val(selectedUser.user.status);
        $("#role_id").val(selectedUser.role.name);
        $("#userName_id").val(selectedUser.user.userName);
        $("#password_id").val(selectedUser.user.password);
    } // end of edit event handler

}