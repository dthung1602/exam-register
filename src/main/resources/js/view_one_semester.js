function loadOneSemesterSuccess(data) {
    $('#semester-id').text(data.id);
    $('#semester-start-date').text(data.start);
    $('#semester-end-date').text(data.end);
}

$(document).ready(function () {
    var semesterId = window.location.pathname.split("/")[3];
     $.ajax({
            url: '/view/semester/' + semesterId,
            type: 'POST',
            dataType: 'json',
            success: loadOneSemesterSuccess,
            error: displayError
        });
});