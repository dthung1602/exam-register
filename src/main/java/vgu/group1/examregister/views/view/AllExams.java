package vgu.group1.examregister.views.view;

import org.json.JSONArray;
import vgu.group1.examregister.database.Exam;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;


@Path("/view/exam/")
public class AllExams extends BaseView {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        String role = getUserRole();
        return Response.ok(getHTMLFile(role + "/view_all_exams.html")).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost() throws SQLException {
        JSONArray exams;
        String role = getUserRole();
        if (role.equals("student"))
            exams = Exam.listCanRegisterExams(getAccountId());
        else if (role.equals("assistant"))
            exams = Exam.listAllExam();
        else
            exams = Exam.listLectureExam(getAccountId());

        return Response.ok(exams.toString()).build();
    }
}