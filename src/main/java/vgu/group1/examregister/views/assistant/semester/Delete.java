package vgu.group1.examregister.views.assistant.semester;


import vgu.group1.examregister.database.Semester;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.SQLException;



@Path("/assistant/semester/delete/{id}")
public class Delete extends BaseView {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@PathParam("id") int semesterID) throws SQLException {
        Semester.deleteSemester(
                semesterID
        );
        return Response.ok(Semester.listAllSemester().toString(), MediaType.APPLICATION_JSON).build();
    }
}
