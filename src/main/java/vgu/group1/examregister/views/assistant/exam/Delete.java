package vgu.group1.examregister.views.assistant.exam;


import vgu.group1.examregister.database.Exam;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/assistant/exam/delete/{id}")

public class Delete {


    @POST
    @Path("{id}")
    public Response doPost(@PathParam("id") int id) throws SQLException {
        //Semester.deleteSemester(id);
        Exam.deleteExam(id);
        return Response.ok("").build();
    }


}