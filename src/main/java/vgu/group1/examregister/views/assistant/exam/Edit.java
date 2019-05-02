package vgu.group1.examregister.views.assistant.exam;


import vgu.group1.examregister.database.Exam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import static vgu.group1.examregister.views.Utils.getHTMLFile;

@Path("/assistant/exam/edit/{id}")
public class Edit {

    @PUT
    public Response doPut(
            @FormParam("examId") String examId,
            @FormParam("moduleId") String moduleId,
            @FormParam("examDate") String examDate,
            @FormParam("deadline") String deadline,
            @FormParam("start") String start,
            @FormParam("end") String end) throws SQLException{
        //Semester.updateSemester(id,Date.valueOf(startDate),Date.valueOf(endDate));
        Exam.updateExam(Integer.parseInt(examId),Integer.parseInt(moduleId),Date.valueOf(examDate), Date.valueOf(deadline), Time.valueOf(start), Time.valueOf(end));
        return Response.ok("").build();
    }
}
