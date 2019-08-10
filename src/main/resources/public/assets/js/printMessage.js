function printErrorMessage(text) {

    $("#message").removeClass('display-none');
    $("#message").removeClass('alert-success');
    $("#message").addClass('alert-warning');
    $("#message span strong").html(text);

}

function printErrorMessageModal(text) {
    console.log("messageModal");
    $("#messageModal").removeClass('display-none');
    $("#messageModal").removeClass('alert-success');
    $("#messageModal").addClass('alert-warning');
    $("#messageModal span strong").html(text);

}

function printSuccessMessage(text) {

    $("#message").removeClass('display-none');
    $("#message").removeClass('alert-warning');
    $("#message").addClass('alert-success');
    $("#message span strong").html(text);

}

function printSuccessMessageModal(text) {

    $("#messageModal").removeClass('display-none');
    $("#messageModal").removeClass('alert-warning');
    $("#messageModal").addClass('alert-success');
    $("#messageModal span strong").html(text);

}