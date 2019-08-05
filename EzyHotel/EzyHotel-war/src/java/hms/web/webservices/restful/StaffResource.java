/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.common.session.PictureSessionLocal;
import hms.hpm.session.HotelSessionLocal;
import hms.hr.session.LeaveSessionLocal;
import hms.hr.session.StaffSessionLocal;
import java.io.IOException;
import hms.hr.session.WorkRosterSessionLocal;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.NoResultException;
import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import util.entity.HotelEntity;
import util.entity.LeaveEntity;
import util.entity.PictureEntity;
import util.entity.StaffEntity;
import util.entity.WorkRosterEntity;
import util.enumeration.DepartmentEnum;
import util.enumeration.GenderEnum;
import util.enumeration.JobPositionEnum;
import util.enumeration.JobTypeEnum;
import util.enumeration.StaffStatusEnum;
import util.exception.CustomNotFoundException;
import util.exception.StaffException;
import util.exception.WorkRosterException;

/**
 * REST Web Service
 *
 * @author USER
 */
@Path("staffs")
public class StaffResource {
//    PictureSessionLocal pictureSessionLocal = lookupPictureSessionLocal();

    WorkRosterSessionLocal workRosterSessionLocal = lookupWorkRosterSessionLocal();

    HotelSessionLocal hotelSessionLocal = lookupHotelSessionLocal();
    LeaveSessionLocal leaveSessionLocal = lookupLeaveSessionLocal();
    StaffSessionLocal staffSessionLocal = lookupStaffSessionLocal();

    @javax.ws.rs.core.Context
    private ServletContext context;      
    /**
     * Creates a new instance of StaffResource
     *
     * @param hId
     * @param c
     * @return
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.UnsupportedEncodingException
     */
    /*
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     @Produces(MediaType.APPLICATION_JSON)
     public Response createStaff(StaffEntity c) throws NoSuchAlgorithmException, UnsupportedEncodingException {
     try{   
     StaffEntity s = staffSessionLocal.createEntity(c); 
     return Response.status(200).entity(s).type(MediaType.APPLICATION_JSON).build();
     }catch (StaffException|NoResultException e) {
     JsonObject exception = Json.createObjectBuilder()
     .add("error", e.getMessage())
     .build();

     return Response.status(404).entity(exception)
     .type(MediaType.APPLICATION_JSON).build();
     }
     } //end createStaff*/
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/hotel/{hotel_id}")
    public Response createStaff(@PathParam("hotel_id") Long hId, StaffEntity c) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        try {
            if (hId == null) {
                throw new CustomNotFoundException("No hotel id found");
            }
            HotelEntity h = hotelSessionLocal.retrieveEntityById(hId);
            System.out.print("before format");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String date = formatter.format(c.getDateOfBirth());
            Date newDate = formatter.parse(date);

            Calendar a = Calendar.getInstance();
            a.setTime(newDate);
            a.add(Calendar.DATE, 1);
            Date addDate = a.getTime();
            //String formatedDate = a.get(Calendar.YEAR) + "-"
            //+ (a.get(Calendar.MONTH) + 1) + "-" + a.get(Calendar.DATE);
            //Date newDate = a.getTime();

            //c.setDateOfBirth(addDate);
            c.setDateOfBirth(addDate);
            System.out.println(c.getDateOfBirth());
            System.out.print("after format");
            StaffEntity s = staffSessionLocal.createEntity(c);
            System.out.print("after create");
            staffSessionLocal.addHotel(s.getStaffId(), h);
            return Response.status(200).entity(s).type(MediaType.APPLICATION_JSON).build();
        } catch (StaffException | NoResultException | CustomNotFoundException | ParseException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    } //end createStaffwithHotel
    /*
     @GET
     @Produces(MediaType.APPLICATION_JSON)
     public List<StaffEntity> getAllStaff() {
     return staffSessionLocal.retrieveAllEntities();
     }//Get All Staff 
     */

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStaff() {
        List<StaffEntity> staffs = staffSessionLocal.retrieveAllEntities();
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (StaffEntity s : staffs) {
//            if(s.getPicture() != null)
//            {
//                try{
//                PictureEntity picture = s.getPicture();
//                String image = getEncodedImageString(picture.getPictureFilePath());
//                picture.setImage(image);
//                }
//                catch(IOException ex)
//                {
//                    System.out.println(ex.getMessage());
//                }
//            }
            builder = createJson(builder, s);
        }
        JsonArray array = builder.build();
        return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStaffById(@PathParam("id") Long cId) {
        try {
            StaffEntity s = staffSessionLocal.retrieveEntityById(cId);
//            if(s.getPicture() != null)
//            {
//                try{
//                PictureEntity picture = s.getPicture();
//                String image = getEncodedImageString(picture.getPictureFilePath());
//                picture.setImage(image);
//                }
//                catch(IOException ex)
//                {
//                    System.out.println(ex.getMessage());
//                }
//            }
            JsonArrayBuilder builder = Json.createArrayBuilder();
            builder = createJson(builder, s);
            JsonArray array = builder.build();
            return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
        } catch (CustomNotFoundException e) {
            System.out.println("catch error");
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }//Single Staff 
    /*
     @PUT
     @Path("/{id}")
     @Consumes(MediaType.APPLICATION_JSON)
     @Produces(MediaType.APPLICATION_JSON)
     public Response editStaff(@PathParam("id") Long sId, StaffEntity s) {
     s.setStaffId(sId);
     try {
     StaffEntity staff = staffSessionLocal.retrieveEntityById(sId);
     staffSessionLocal.updateEntity(s);
     return Response.status(204).build();
     } catch (CustomNotFoundException e) {
     JsonObject exception = Json.createObjectBuilder()
     .add("error", e.getMessage())
     .build();

     return Response.status(404).entity(exception)
     .type(MediaType.APPLICATION_JSON).build();
     }
     } //end editStaff
     */

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editStaff(@PathParam("id") Long sId, JsonObject json) {
        try {
            System.out.println(json.toString());
            //retrieve all fields 
            String name = json.getString("name");
            String ic_num = json.getString("ic_num");
            String dateOfBirth = json.getString("dateOfBirth");
            String nationality = json.getString("nationality");
            String phoneNum = json.getString("phoneNum");
            String homeNum = json.getString("homeNum");
            int leaveQuota = json.getInt("leaveQuota");
            double salary = json.getJsonNumber("salary").doubleValue();
            double bonus = json.getJsonNumber("bonus").doubleValue();
            String jobTitle = json.getString("jobTitle");
            String email = json.getString("email");
            GenderEnum gender = GenderEnum.valueOf(json.getString("gender"));
            DepartmentEnum department = DepartmentEnum.valueOf(json.getString("department"));
            JobTypeEnum jobType = JobTypeEnum.valueOf(json.getString("jobType"));
            JobPositionEnum jobPosition = JobPositionEnum.valueOf(json.getString("jobPosition"));
            StaffStatusEnum staffStatus = StaffStatusEnum.valueOf(json.getString("staffStatus"));
            String username = json.getString("username");
            System.out.println("date");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date dob = df.parse(dateOfBirth);

            Calendar c = Calendar.getInstance();
            c.setTime(dob);
            c.add(Calendar.DATE, 1);
            Date newDate = c.getTime();

            Long hotelId = json.getJsonNumber("hotelName").longValue();
            System.out.print("hotel Id" + hotelId);
            StaffEntity s = new StaffEntity();
            System.out.println("after retrieve");
//check if staff exists 
            StaffEntity staff = staffSessionLocal.retrieveEntityById(sId);
            //Check if hotel exists 
            HotelEntity hotel = hotelSessionLocal.retrieveEntityById(hotelId);
            System.out.println("after retrieve entity");
//set the values 
            s.setStaffId(sId);
            s.setName(name);
            s.setBonus(bonus);
            s.setDateOfBirth(dob);
            s.setEmail(email);
            s.setGender(gender);
            s.setHomeNum(homeNum);
            s.setIc_num(ic_num);
            s.setJobPosition(jobPosition);
            s.setJobTitle(jobTitle);
            s.setJobType(jobType);
            s.setLeaveQuota(leaveQuota);
            s.setName(name);
            s.setNationality(nationality);
            s.setPhoneNum(phoneNum);
            s.setSalary(salary);
            s.setStaffStatus(staffStatus);
            s.setUsername(username);
            s.setDepartment(department);
            s.setPassword(staff.getPassword());
            //set staff Entity and add hotel 
            staffSessionLocal.updateEntity(s);
            staffSessionLocal.editHotel(sId, hotel);
            return Response.status(204).build();
        } catch (ParseException | IllegalArgumentException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Input error")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        } catch (CustomNotFoundException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    } //end editStaff

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteStaff(@PathParam("id") Long sId) throws CustomNotFoundException {
        try {
            staffSessionLocal.deleteEntity(sId);
            return Response.status(204).build();
        } catch (CustomNotFoundException | StaffException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end deleteStaff

    @DELETE
    @Path("/{staff_id}/leave/{leave_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeLeave(@PathParam("staff_id") Long sId, @PathParam("leave_id") Long lId) {
        try {
            LeaveEntity leave = leaveSessionLocal.retrieveEntityById(lId);
            staffSessionLocal.removeLeave(sId, leave);
            StaffEntity staff = staffSessionLocal.retrieveEntityById(sId);
            return Response.status(204).entity(staff).build();
        } catch (CustomNotFoundException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end removeStaff

    @GET
    @Path("/workRoster/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStaffByRosterId(@PathParam("id") Long wId) {
        try {
            WorkRosterEntity w = workRosterSessionLocal.retrieveEntityById(wId);
            List<StaffEntity> staffs = workRosterSessionLocal.retrieveStaffFromRosterId(wId);
            JsonArrayBuilder builder = Json.createArrayBuilder();
            for (StaffEntity s : staffs) {
                builder = createJson(builder, s);
            }
            JsonArray array = builder.build();
            return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
        } catch (CustomNotFoundException | NoResultException | WorkRosterException e) {
            System.out.println("catch error");
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    @POST
    @Path("/workrosterstaffs")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStaffListForRoster(JsonObject json) {
        try {
            List<StaffEntity> staffs = staffSessionLocal.retrieveActiveStaff();
            System.out.println("size"+staffs.size());
            JsonArrayBuilder builder = Json.createArrayBuilder();
            
            String rawdate = json.getString("date");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = df.parse(rawdate);
            for (StaffEntity s : staffs) {
                builder = createRosterJson(builder, s, date);
            }
            JsonArray array = builder.build();
            return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
        } catch (CustomNotFoundException|ParseException e) {
            System.out.println("catch error");
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    private JsonArrayBuilder createRosterJson(JsonArrayBuilder builder, StaffEntity s, Date date) throws CustomNotFoundException {
        JsonObjectBuilder object = Json.createObjectBuilder();
        if (s.getStaffId() != null) {
            object.add("staffId", s.getStaffId());
        }
        if (s.getName() != null) {
            object.add("name", s.getName());
        }
        if (s.getDepartment() != null) {
            object.add("department", s.getDepartment().toString());
        }
        if (s.getJobType() != null) {
            object.add("jobType", s.getJobType().toString());
        }
        if (s.getJobPosition() != null) {
            object.add("jobPosition", s.getJobPosition().toString());
        }
        if (s.getJobTitle() != null) {
            object.add("jobTitle", s.getJobTitle());
        }
        if (date != null) {
            boolean checkAvailability = staffSessionLocal.checkAvailability(s.getStaffId(), date);
            object.add("availability", checkAvailability);
        }
        builder.add(object);
        return builder;
    }

    private JsonArrayBuilder createJson(JsonArrayBuilder builder, StaffEntity s) {

        JsonObjectBuilder object = Json.createObjectBuilder();
        if (s.getStaffId() != null) {
            object.add("staffId", s.getStaffId());
        }
        if (s.getName() != null) {
            object.add("name", s.getName());
        }
        if (s.getIc_num() != null) {
            object.add("ic_num", s.getIc_num());
        }
        if (s.getDateOfBirth() != null) {
            //SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy'T'HH:mm:ss");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            object.add("dateOfBirth", df.format(s.getDateOfBirth()));
        }
        if (s.getNationality() != null) {
            object.add("nationality", s.getNationality());
        }
        if (s.getHomeNum() != null) {
            object.add("homeNum", s.getHomeNum());
        }
        if (s.getPhoneNum() != null) {
            object.add("phoneNum", s.getPhoneNum());
        }
        if (Integer.toString(s.getLeaveQuota()) != null) {
            object.add("leaveQuota", s.getLeaveQuota());
        }
        if (Double.toString(s.getSalary()) != null) {
            object.add("salary", s.getSalary());
        }
        if (Double.toString(s.getBonus()) != null) {
            object.add("bonus", s.getBonus());
        }
        if (s.getJobTitle() != null) {
            object.add("jobTitle", s.getJobTitle());
        }
        if (s.getEmail() != null) {
            object.add("email", s.getEmail());
        }
        if (s.getGender() != null) {
            object.add("gender", s.getGender().toString());
        }
        if (s.getDepartment() != null) {
            object.add("department", s.getDepartment().toString());
        }
        if (s.getJobType() != null) {
            object.add("jobType", s.getJobType().toString());
        }
        if (s.getJobPosition() != null) {
            object.add("jobPosition", s.getJobPosition().toString());
        }
        if (s.getStaffStatus() != null) {
            object.add("staffStatus", s.getStaffStatus().toString());
        }
        if (s.getUsername() != null) {
            object.add("username", s.getUsername());
        }
        if (s.getPassword() != null) {
            object.add("password", s.getPassword());
        }

        if (s.getHotel() != null) {
            object.add("hotelName", s.getHotel().getHotelId());
        }

        builder.add(object);
        return builder;
    }

    private StaffSessionLocal lookupStaffSessionLocal() {
        try {
            Context c = new InitialContext();
            return (StaffSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/StaffSession!hms.hr.session.StaffSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private LeaveSessionLocal lookupLeaveSessionLocal() {
        try {
            Context c = new InitialContext();
            return (LeaveSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/LeaveSession!hms.hr.session.LeaveSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private HotelSessionLocal lookupHotelSessionLocal() {
        try {
            Context c = new InitialContext();
            return (HotelSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/HotelSession!hms.hpm.session.HotelSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private WorkRosterSessionLocal lookupWorkRosterSessionLocal() {
        try {
            Context c = new InitialContext();
            return (WorkRosterSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/WorkRosterSession!hms.hr.session.WorkRosterSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
