package vgu.group1.examregister.views.assistant.exam.participants;

import vgu.group1.examregister.database.Exam;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/assistant/exam/{id}/participants/delete")
public class Delete extends BaseView {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response doPost(@PathParam("id") int examID,
                           @FormParam("student-id") int studentID) throws SQLException {
        //Module.deleteParticipant(studentID, moduleID);
        Exam.unregisterExam(studentID, examID);
        return Response.ok().build();
    }
}