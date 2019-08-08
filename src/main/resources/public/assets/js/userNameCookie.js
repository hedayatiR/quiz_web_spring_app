$(document).ready(function () {

    if (typeof $.cookie('user') === 'undefined') {
        //no cookie
        $("#userData").addClass('display-none');

    } else {
        $("#userNameProfile").text( $.cookie('user') );
    }

}); // end of ready function



