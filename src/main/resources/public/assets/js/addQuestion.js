var $table;
$(document).ready(function () {

    $table = $('#table');

    var rawData = [{
    }];

    $(function () {
        $('#table').bootstrapTable({
            data: rawData
        });

    });

    $("#qType_id").click(function (e) {
        e.preventDefault();
        if ($("#qType_id").val() === "DESC")
            document.getElementById("table").style.display = "none";
        else
            document.getElementById("table").style.display = "block";
    });


    $('#addOptionBtn').click(function (e) {
        e.preventDefault();
        $('#table').bootstrapTable('append', rawData );
    });


    var form = $('#questionForm');

    var question = {};

    var options = [];

    $("#submitBtn").click(function (e) {
        e.preventDefault();

        question.title = $("#title_id").val();
        question.type = $("#qType_id").val();
        question.body = $("#questionBody_id").val();

        var index;
        $('input[name="selectItemName"]:checked').each(function () {
            index = $(this).data('index');
        });

        $(".optionClass").each(function () {
            var option = {};
            option.text = this.value;
            options.push(option);
        });


        question.options = options;
        question.rightOption = index;

        alert(JSON.stringify(question));
    });

}) // end of doc ready


function optionFormatter() {
    return '<textarea class=" optionClass form-control align-content-end" rows="3" cols="100"></textarea>'
}

// window.operateEvents = {
//     'click .remove': function (e, value, selectedUserToggle, index) {


//     }, // end of toggle event handler

//     'click .edit': function (e, value, selectedUser, index) {

//     } // end of edit event handler

// }