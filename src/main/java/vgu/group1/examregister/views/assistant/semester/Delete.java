package vgu.group1.examregister.views.assistant.semester;


import vgu.group1.examregister.database.Semester;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

import static vgu.group1.examregister.views.Utils.getHTMLFile;

@Path("/assistant/semester/delete/{id}")

public class Delete{


    @POST
    @Path("{id}")
    public Response doPost(@PathParam("id") int id) throws  SQLException {
        Semester.deleteSemester(id);
        return Response.ok("").build();
    }

}