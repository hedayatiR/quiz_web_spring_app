$(document).ready(function () {
    var $table = $('#table');

    var noAuth = getParameterByName('noAuth');

    if (noAuth == "true"){
        // $("#messageBox").html("You have redirected to this page. You need to auth");
        $("#messageBox span strong").text("You have redirected to this page. You need to auth");
    }


    $(function () {

        var data = [{
                'id': 0,
                'name': 'Item 0',
                'price': '$0'
            },
            {
                'id': 1,
                'name': 'Item 1',
                'price': '$1'
            },
            {
                'id': 2,
                'name': 'Item 2',
                'price': '$2'
            },
            {
                'id': 3,
                'name': 'Item 3',
                'price': '$3'
            },
            {
                'id': 4,
                'name': 'Item 4',
                'price': '$4'
            },
            {
                'id': 5,
                'name': 'Item 5',
                'price': '$5'
            }
        ]
        $table.bootstrapTable({
            data: data
        })

        $("#table").bootstrapTable("check", 0);

        var $button = $('#register')

        $button.click(function () {

            var checkedRows = $table.bootstrapTable('getAllSelections');

            $("#output").empty();
            $.each(checkedRows, function (index, value) {
                $('#output').append($('<li></li>').text(value.id + " | " + value.name + " | " + value.price));
            });
        });

    });
    
});



function operateFormatter() {
    return '<a href="#" class="like btn btn-info btn-xs"><i class="fa fa-edit" style="font-size: 15px;"></i>  </a>  <a href="#" class="remove btn btn-danger btn-xs"><i class="fa fa-trash-o"></i>  </a>';
}

window.operateEvents = {
    'click .like': function (e, value, row, index) {
        alert('You click like action, row: ' + JSON.stringify(row))
    },
    'click .remove': function (e, value, row, index) {
        $('#table').bootstrapTable('remove', {
            field: 'id',
            values: [row.id]
        })
    }
}

