package vgu.group1.examregister.views.view;

import org.json.JSONArray;
import org.json.JSONObject;
import vgu.group1.examregister.database.Exam;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;


@Path("/view/exam/{id}")
public class OneExam extends BaseView {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("assistant/view_one_exam.html")).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@PathParam("id") int examId) throws SQLException {
        JSONObject exam = Exam.readExam(examId);
        return Response.ok(exam.toString()).build();
    }
}