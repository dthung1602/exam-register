function myFunction(){
    var module_id = document.getElementById("module-id").value;
    var exam_date = document.getElementById("exam-date").value;
    var exam_deadline = document.getElementById("exam-deadline").value;
    var exam_start = document.getElementById("exam-start").value;
    var exam_end = document.getElementById("exam-end").value;

    // Returns successful data submission message when the entered information is stored in database.
    var dataString = 'module-id=' + module_id + '&exam-date=' + exam_date + '&exam-deadline=' + exam_deadline + '&exam-start=' + exam_start + '&exam-end=' + exam_end;
    if (module_id == '' || exam_date == '' || exam_deadline == '' || exam_start == '' || exam_end == '')
        alert("Please fill all fields");
    else
    {
        //AJAX code to submit form
        $.ajax({
            type: "POST",
            url: ajaxjs.php,
            data: dataString,
            cache: false,
            success: function(html) {
                alert(html);
            }
        });
    }
    return false;
}