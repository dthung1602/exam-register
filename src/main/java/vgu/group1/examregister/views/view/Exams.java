package vgu.group1.examregister.views.view;


import org.json.JSONArray;
import vgu.group1.examregister.database.Exam;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;


@Path("/view/exam/")
public class Exams extends BaseView {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("view_exams.html")).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost() throws SQLException {
        JSONArray exam = Exam.viewExam();
        return Response.ok(exam.toString()).build();
    }
}