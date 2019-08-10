$(document).ready(function () {

    if (typeof $.cookie('account') === 'undefined') {
        //no cookie
        $("#userData").addClass('display-none');

    } else {
        $("#userNameProfile").text( $.cookie('account') );
    }

}); // end of ready function



