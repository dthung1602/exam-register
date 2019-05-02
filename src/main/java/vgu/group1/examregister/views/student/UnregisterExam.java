package vgu.group1.examregister.views.student;


import org.json.JSONArray;
import org.json.JSONObject;
import vgu.group1.examregister.database.Exam;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;

import static vgu.group1.examregister.views.Utils.getHTMLFile;

@Path("/view/exams/{id}") //TODO
public class UnregisterExam extends BaseView {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@FormParam("my_student") int student_id,
                           @PathParam("id") int exam_id) throws SQLException {
        Exam.unregisterExam(student_id, exam_id);
        return Response.ok().build();
    }
}