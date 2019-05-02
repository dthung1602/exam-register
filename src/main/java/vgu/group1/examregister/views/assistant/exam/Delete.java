package vgu.group1.examregister.views.assistant.exam;

<<<<<<< HEAD

import vgu.group1.examregister.database.Exam;
=======
import vgu.group1.examregister.database.Exam;
import vgu.group1.examregister.views.BaseView;
>>>>>>> 4f7da09173bbe855406a6363f3a09e5a7ce6901c

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/assistant/exam/delete/{id}")
<<<<<<< HEAD

public class Delete {


    @POST
    @Path("{id}")
    public Response doPost(@PathParam("id") int id) throws SQLException {
        //Semester.deleteSemester(id);
        Exam.deleteExam(id);
        return Response.ok("").build();
    }


}
=======
public class Delete extends BaseView {
    @POST
    public Response doPost(@PathParam("id") int examID) throws SQLException {
        Exam.cancelExam(examID);
        return Response.ok().build();
    }
}
>>>>>>> 4f7da09173bbe855406a6363f3a09e5a7ce6901c
