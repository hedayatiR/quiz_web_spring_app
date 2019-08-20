var selectedUserGlobal;
var $table;
var test = 0;
$(document).ready(function () {
    $table = $('#table');


    // uncomment one of below

    // 1. offline test - START
    if (test === 1) {

        var studentJson = [{
            "id": 2,
            "firstName": "fn2",
            "lastName": "ln2",
            "account": {
                "id": 2,
                "username": "mobi",
                "password": "$2a$10$7iNt2GsQj.pcbEOUYxPydeMbYYy0thE5UPKtHm2yQu.z8pEp.jrxW",
                "repeatPassword": null,
                "enabled": false,
                "role": {
                    "id": 1,
                    "name": "STUDENT"
                }
            }

        }, {
            "id": 1,
            "firstName": "fn1",
            "lastName": "ln1",
            "account": {
                "id": 1,
                "username": "reza",
                "password": "$2a$10$rQbcgo9v.s91gHGPPWvoPuciteFVH2.pUCFrlozHkplsluzfUqHo6",
                "repeatPassword": null,
                "enabled": true,
                "role": {
                    "id": 3,
                    "name": "TEACHER"
                }
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


            // if (String($table.bootstrapTable('getData')[0]).endsWith("Element]")) {
            //     $table.bootstrapTable({
            //         data: teacherJson,
            //     });
            //     console.log("NULL");
            // } else
            //     $table.bootstrapTable('append', teacherJson);

        });
        // 1. offline test - END
    } else {
        // 2. oline operation - START

        // check user cookie

        $.ajax({
            method: "GET",
            url: "http://localhost:8080/api/users",
            success: function (studentsJson, textStatus, xhr) {

                console.log(studentsJson);

                $table.bootstrapTable({
                    data: studentsJson
                });
                // } else
                // $table.bootstrapTable('append', studentsJson);
            },
            error: function (xhr, ajaxOptions, thrownError) {
                switch (xhr.status) {
                    case 403:
                        // Take action, referencing xhr.responseText as needed.
                        printErrorMessage('به این سرویس دسترسی ندارید. برای ورود' + '<a href="./login.html"> اینجا </a>' + '  را کلیلک کنید');
                        // console.log("removeCookie");
                        // $.removeCookie('user', { path: '/' });
                        // $("#userData").addClass('display-none');
                        break;
                }
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

            var valid = true;
            if (($("#password_id").val()) !== ($("#repeatPassword_id").val())) {
                printErrorMessageModal('!رمز عبورها برابر نیستند');
                valid = false;
            }

            if (valid) {
                var userToCreate = {};
                userToCreate.firstName = $("#firstName_id").val();
                userToCreate.lastName = $("#lastName_id").val();
                var accountField = {};
                accountField.username = $("#userName_id").val();
                accountField.password = $("#password_id").val();
                accountField.repeatPassword = $("#repeatPassword_id").val();
                if ($("#status_id").val() == 'فعال')
                    accountField.enabled = true;
                if ($("#status_id").val() == 'غیرفعال')
                    accountField.enabled = false;

                var role = {};
                role.name = $("#role_id").val();
                accountField.role = role;

                userToCreate.account = accountField;

                console.log(JSON.stringify(userToCreate));


                if (test === 1) {
                    console.log("success of create user");
                    document.getElementById('id01').style.display = 'none';
                    $table.bootstrapTable('append', userToCreate);
                    printSuccessMessage('ساخت کاربر جدید با موفقیت انجام شد.');
                } else {

                    // create new user ajax
                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: "http://localhost:8080/api/users",
                        data: JSON.stringify(userToCreate),
                        dataType: 'json',
                        success: function (createdUser, textStatus, xhr) {

                            console.log("success of create user");

                            document.getElementById('id01').style.display = 'none';

                            $table.bootstrapTable('append', createdUser);

                            printSuccessMessage('ساخت کاربر جدید با موفقیت انجام شد.');
                        },

                        error: function (e2) {
                            console.log("ERROR: ", e2);
                            printErrorMessageModal('خطایی در ساخت کاربر جدید رخ داده!');
                        }

                    }); // end of ajax

                }
            } // end of  if valid

            // end of create user
        } else {

            // start of edit user
            console.log("edit user");

            var valid = true;
            if (($("#password_id").val() != "") || ($("#repeatPassword_id").val() != "")) {
                if (($("#password_id").val()) !== ($("#repeatPassword_id").val())) {
                    printErrorMessageModal('!رمز عبورها برابر نیستند');
                    valid = false;
                }
            }
            if (valid) {

                selectedUserGlobal.firstName = $("#firstName_id").val();
                selectedUserGlobal.lastName = $("#lastName_id").val();
                if ($("#status_id").val() == 'فعال')
                    selectedUserGlobal.account.enabled = true;
                if ($("#status_id").val() == 'غیرفعال')
                    selectedUserGlobal.account.enabled = false;

                selectedUserGlobal.account.username = $("#userName_id").val();
                selectedUserGlobal.account.password = $("#password_id").val();
                selectedUserGlobal.account.repeatPassword = $("#repeatPassword_id").val();

                console.log(JSON.stringify(selectedUserGlobal));

                $.ajax({
                    type: "PUT",
                    contentType: "application/json",
                    url: "http://localhost:8080/api/users",
                    data: JSON.stringify(selectedUserGlobal),
                    dataType: 'json',
                    success: function (data, textStatus, xhr) {

                        $table.bootstrapTable('updateByUniqueId', {
                            id: selectedUserGlobal.id,
                            row: selectedUserGlobal
                        });

                        printSuccessMessageModal('ویرایش کاربر با موفقیت انجام شد.');
                    },

                    error: function (e2) {
                        console.log("ERROR: ", e2);
                        printErrorMessageModal('خطایی در ویرایش کاربر رخ داده!')
                    }

                }); // end of ajax
            }

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

    var $addUserBtn = $('#addUserBtn');

    $addUserBtn.click(function () {
        //show edit user modal
        $("#modalTitle").html('ایجاد کاربر');
        $('#password_id').prop('required', true);
        $('#repeatPassword_id').prop('required', true);

        document.getElementById('role_id').disabled = false;
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

        console.log(JSON.stringify(selectedUserToggle));

        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: "http://localhost:8080/api/users/" + selectedUserToggle.id + "/changeState",
            // data: JSON.stringify(selectedUserToggle),
            // dataType: 'json',
            success: function (data, textStatus, xhr) {
                selectedUserToggle.account.enabled = !selectedUserToggle.account.enabled;

                $table.bootstrapTable('updateByUniqueId', {
                    id: selectedUserToggle.id,
                    row: selectedUserToggle
                });

                console.log(xhr);
                printSuccessMessage('تغییر دسترسی با موفقیت انجام شد.');
            },

            error: function (e2) {
                console.log("ERROR: ", e2);
                printErrorMessage('خطایی در تغییر دسترسی کاربر رخ داده!');
            }
        }); // end of ajax

    }, // end of toggle event handler

    'click .edit': function (e, value, selectedUser, index) {

        $("#modalTitle").html('ویرایش کاربر');
        $('#password_id').prop('required', false);
        $('#repeatPassword_id').prop('required', false);
        // show id field
        document.getElementById('id_div').style.display = 'block';
        selectedUserGlobal = selectedUser;
        //show edit user modal
        document.getElementById('id01').style.display = 'block';
        // fill form field
        $("#id_id").val(selectedUser.id);
        $("#firstName_id").val(selectedUser.firstName);
        $("#lastName_id").val(selectedUser.lastName);

        if (selectedUser.account.enabled)
            document.getElementById("status_id").selectedIndex = "0";
        else
            document.getElementById("status_id").selectedIndex = "1";

        document.getElementById('role_id').disabled = true;

        $("#role_id").val(selectedUserGlobal.account.role.name);
        $("#userName_id").val(selectedUser.account.username);

        // $("#password_id").val(selectedUser.user.password);
    }, // end of edit event handler

    'click .remove': function (e, value, selectedUser, index) {
        if (confirm("مطمئنی؟")) {
            var destURL = "http://localhost:8080/api/users/";

            $.ajax({
                method: "DELETE",
                url: destURL + selectedUser.id,
                success: function (dummy, textStatus, xhr) {
                    $table.bootstrapTable('removeByUniqueId', selectedUser.id);
                    $(message).removeClass('display-none');
                    $(message).removeClass('alert-warning');
                    $(message).addClass('alert-success');
                    $("#message span strong").text('حذف کاربر با موفقیت انجام شد.');
                }
            }); // end of ajax


        }

    }
}