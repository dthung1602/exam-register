function addAnExam(){
    var module_id = $("#module-id").val();
    var exam_date = $("#exam-date").val();
    var exam_deadline = $("#exam-deadline").val();
    var exam_start = $("#exam-start").val();
    var exam_end = $("#exam-end").val();

    // Returns successful data submission message when the entered information is stored in database.
    var dataString = 'module-id=' + module_id + '&exam-date=' + exam_date + '&exam-deadline=' + exam_deadline + '&exam-start=' + exam_start + '&exam-end=' + exam_end;
    if (module_id == '' || exam_date == '' || exam_deadline == '' || exam_start == '' || exam_end == '')
        alert("Please fill all fields");
    else
    {
        //AJAX code to submit form
        $.ajax({
            url: '/assistant/exam/add',
            type: 'POST',
            data: dataString,
            cache: false,
            success: function(html) {
                alert(html);
            }
        });
    }
    return false;
}