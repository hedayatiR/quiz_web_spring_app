var selectedUserGlobal;
var $table;
var test = 1;
$(document).ready(function () {
    $table = $('#table');

    // uncomment one of below

    // 1. offline test - START
    if (test === 1) {

        var studentJson = [{
            "id": 1,
            "firstName": "fn1",
            "lastName": "ln1",
            "role": {
                "name": "STUDENT"
            },
            "user": {
                "userName": "u1",
                "password": "p1",
                "status": "INACTIVATED"
            }
        }, {
            "id": 2,
            "firstName": "fn2",
            "lastName": "ln2",

            "role": {
                "name": "STUDENT"
            },
            "user": {
                "userName": "u2",
                "password": "p2",
                "status": "INACTIVATED"
            }
        }];

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
                "status": "INACTIVATED"
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


            if (String($table.bootstrapTable('getData')[0]).endsWith("Element]")) {
                $table.bootstrapTable({
                    data: studentJson,
                });
                console.log("NULL");
            } else
                $table.bootstrapTable('append', studentJson);


            if (String($table.bootstrapTable('getData')[0]).endsWith("Element]")) {
                $table.bootstrapTable({
                    data: teacherJson,
                });
                console.log("NULL");
            } else
                $table.bootstrapTable('append', teacherJson);

        });
        // 1. offline test - END
    } else {


        // 2. oline operation - START
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/api/students",
            success: function (studentsJson, textStatus, xhr) {

                console.log(studentsJson);

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
    }

    var form = $('#editUserForm');

    // on click of تایید - START ------------------------------------------------
    $(form).submit(function (e) {
        e.preventDefault();
        // start of create user
        if (document.getElementById('id_div').style.display === 'none') {
            console.log("NEW USER");
            var userToCreate = {};
            userToCreate.firstName = $("#firstName_id").val();
            userToCreate.lastName = $("#lastName_id").val();
            var userField = {};
            userField.status = $("#status_id").val();
            userField.userName = $("#userName_id").val();
            userField.password = $("#password_id").val();
            userToCreate.user = userField;
            var roleField = {};
            roleField.name = $("#role_id").val();

            if (roleField.name === "STUDENT") {
                destURL = "http://localhost:8080/api/students/";
                roleField.id = 1;
            } else if ((roleField.name === "TEACHER")) {
                destURL = "http://localhost:8080/api/teachers/";
                roleField.id = 2;
            }

            userToCreate.role = roleField;



            if (test === 1) {
                console.log("success of create user");

                document.getElementById('id01').style.display = 'none';

                // $table.bootstrapTable('append', createdUser);

                $("#message").removeClass('display-none');
                $("#message").removeClass('alert-warning');
                $("#message").addClass('alert-success');
                $("#message span strong").text('ساخت کاربر جدبد با موفقیت انجام شد.');
            }

            // create new user ajax
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: destURL,
                data: JSON.stringify(userToCreate),
                dataType: 'json',
                success: function (createdUser, textStatus, xhr) {

                    console.log("success of create user");

                    document.getElementById('id01').style.display = 'none';

                    $table.bootstrapTable('append', createdUser);

                    $("#message").removeClass('display-none');
                    $("#message").removeClass('alert-warning');
                    $("#message").addClass('alert-success');
                    $("#message span strong").text('ساخت کاربر جدید با موفقیت انجام شد.');
                },

                error: function (e2) {
                    console.log("ERROR: ", e2);
                    $("#messageModal").removeClass('display-none');
                    $("#messageModal").removeClass('alert-success');
                    $("#messageModal").addClass('alert-warning');
                    $("#messageModal span strong").text('خطایی در ساخت کاربر جدید رخ داده!');

                }

            }); // end of ajax




            // end of create user
        } else {
            // start of edit user
            console.log("OLD USER");


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

        } // end of edit user

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



    $("#table").on("click", ".remove", function (e) {

        e.preventDefault();

        if (test === 1) {
            $(this).closest('tr').remove();
        }

        if (confirm("مطمئنی؟")) {
            var destURL;
            var role = $(this).closest('tr').find('td:eq(5)').text();
            if (role === "STUDENT") {
                destURL = "http://localhost:8080/api/students/";
            } else if ((role === "TEACHER")) {
                destURL = "http://localhost:8080/api/teachers/";
            }

            var current = $(this);
            $.ajax({
                method: "DELETE",
                url: destURL + $(this).closest('tr').find('td:eq(1)').text(),
                success: function (dummy, textStatus, xhr) {
                    current.closest('tr').remove();
                }
            }); // end of ajax

        }
    });



    var $addUserBtn = $('#addUserBtn');

    $addUserBtn.click(function () {
        //show edit user modal
        document.getElementById('id01').style.display = 'block';
        document.getElementById('id_div').style.display = 'none';
        $("#firstName_id").val('');
        $("#lastName_id").val('');
        $("#status_id").val('');
        $("#role_id").val('');
        $("#userName_id").val('');
        $("#password_id").val('');


    });




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

    'click .edit': function (e, value, selectedUser, index) {
        // show id field
        document.getElementById('id_div').style.display = 'block';
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