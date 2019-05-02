package vgu.group1.examregister.views.assistant.semester;


import vgu.group1.examregister.database.Semester;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import static vgu.group1.examregister.views.Utils.getHTMLFile;

@Path("/assistant/semester/edit/{id}")
public class Edit {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response doPost(@FormParam("start-date") Date startDate,
                          @FormParam("end-date") Date endDate,
                          @PathParam("id") int id) throws SQLException{
        Semester.updateSemester(id, startDate,endDate);
        return Response.ok("").build();
    }
}
