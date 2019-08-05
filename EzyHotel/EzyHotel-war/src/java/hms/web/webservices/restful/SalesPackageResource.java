/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.common.session.PictureSessionLocal;
import hms.sales.session.SalesPackageSessionLocal;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.NoResultException;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.entity.PictureEntity;
import util.entity.SalesPackageEntity;
import util.enumeration.PictureStatusEnum;

/**
 * REST Web Service
 *
 * @author Wai Kit
 */
@Path("salespackages")
public class SalesPackageResource {

    @EJB
    private SalesPackageSessionLocal salesPackageSessionLocal;

    @EJB
    private PictureSessionLocal pictureSessionLocal;

    @Context
    private ServletContext context;

    /**
     * Creates a new instance of SalesPackageResource
     */
    public SalesPackageResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllSalesPackages() {
        List<SalesPackageEntity> listOfSalesPackageEntity = salesPackageSessionLocal.retrieveAllEntities();

        JsonArrayBuilder salesPackage = Json.createArrayBuilder();

        for (int i = 0; i < listOfSalesPackageEntity.size(); i++) {

            String fullFilePath = getUploadDirectory() + listOfSalesPackageEntity.get(i).getPicture().getPictureFilePath();
            System.out.println(fullFilePath);

            try {
                //File getUploadedFile = new File(context.getRealPath(listOfSalesPackageEntity.get(i).getPicture().getPictureFilePath()));
                //FileInputStream fileInputStream = new FileInputStream(getUploadedFile);
                //byte fileContent[] = new byte[(int) getUploadedFile.length()];
                //fileInputStream.read(fileContent);

                //encode byte
                //byte[] data = Base64.getEncoder().encode(fileContent);
                String startDate = listOfSalesPackageEntity.get(i).getStartDate().toString();
                String startDateSubString = startDate.substring(0, 11);
                String startDateYearSubString = startDate.substring(24);
                String startDateDateTime = startDateSubString + startDateYearSubString + " 00:00:00 GMT+0800 (Singapore Standard Time)";

                //String fileContentStr = new String(data);
                //System.out.println("File content:" + fileContentStr);
                salesPackage.add(Json.createObjectBuilder()
                        .add("salesPackageId", listOfSalesPackageEntity.get(i).getSalesPackageId())
                        .add("detail", listOfSalesPackageEntity.get(i).getDetail())
                        .add("duration", listOfSalesPackageEntity.get(i).getDuration())
                        .add("facilityType", listOfSalesPackageEntity.get(i).getFacilityType())
                        .add("price", listOfSalesPackageEntity.get(i).getPrice())
                        .add("startDate", startDateDateTime)
                        .add("title", listOfSalesPackageEntity.get(i).getTitle()));
                //.add("image", context.getRealPath(listOfSalesPackageEntity.get(i).getPicture().getPictureFilePath())));                
            } catch (Exception ex) {
                System.out.println(ex);
            }

        }

        JsonArray array = salesPackage.build();

        return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/image/{salesPackageId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrievePictureById(@PathParam("salesPackageId") long salesPackageId) {
        SalesPackageEntity salesPackageEntity = salesPackageSessionLocal.retrieveEntityById(salesPackageId);
        System.out.println(salesPackageEntity);

        JsonArrayBuilder salesPackage = Json.createArrayBuilder();

        try {
                PictureEntity picture = salesPackageEntity.getPicture();

                String image = getEncodedImageString(picture.getPictureFilePath());
               
//            File getUploadedFile = new File(context.getRealPath(salesPackageEntity.getPicture().getPictureFilePath()));
//            FileInputStream fileInputStream = new FileInputStream(getUploadedFile);
//            byte fileContent[] = new byte[(int) getUploadedFile.length()];
//            fileInputStream.read(fileContent);
//
//            //encode byte
//            byte[] data = Base64.getEncoder().encode(fileContent);
//
//            String fileContentStr = new String(data);
            //System.out.println("File content:" + fileContentStr);
            salesPackage.add(Json.createObjectBuilder()
                    .add("image", image));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        JsonArray array = salesPackage.build();

        return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
    }

    //UPLOAD_DIRECTORY + filePath for new file()
    private String getUploadDirectory() {
        String substring = "/EzyHotel-J2E/EzyHotel/dist/gfdeploy/EzyHotel/EzyHotel-war_war/";
        //String substring = "/dist/gfdeploy/EzyHotel/EzyHotel-war_war/";
        System.out.println("Real Path " + context.getRealPath("/"));
        //this is the file path that is for different local server
        String UPLOAD_DIRECTORY = context.getRealPath("/").replace(substring, "");
        System.out.println("#UPLOAD_DIRECTORY : " + UPLOAD_DIRECTORY);
        return UPLOAD_DIRECTORY;
    }
    
    private String getEncodedImageString(String filePath) throws IOException
    {
        String UPLOAD_DIRECTORY = getUploadDirectory();
        try{
            String image = pictureSessionLocal.getPictureBase64String(UPLOAD_DIRECTORY + filePath);
//            System.out.println("image: " + image);
            return image;
        }
        
        catch(IOException ex)
        {
            throw new IOException(ex.getMessage());
        }       
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getsalesPackage(@PathParam("id") Long spId) {
        try {
            SalesPackageEntity cp = salesPackageSessionLocal.retrieveEntityById(spId);
            return Response.status(200).entity(cp).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SalesPackageEntity createSalesPackage(SalesPackageEntity sp) {
        LocalDate localDate = LocalDate.now();
        //cp.setCallDate(localDate);
        salesPackageSessionLocal.createEntity(sp);
        return sp;
    }

    @PUT
    @Path("/{salesPackageId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void updateSalesPackage(@PathParam("salesPackageId") Long salesPackageId, JsonObject salesPackageObject) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy kk:mm:ss");
        try {
            String requestStartDateStr = salesPackageObject.getJsonObject("salesPackage").getString("startDate").replace("\"", "");
            Date requestStartDate = new Date();
            Date finalDateTime = new Date();

            try {
                requestStartDate = sdf.parse(requestStartDateStr);
                Calendar c = Calendar.getInstance();
                c.setTime(requestStartDate);
                c.add(Calendar.DATE, 1);
                String finalDateTimeStr = sdf.format(c.getTime());
                finalDateTime = sdf.parse(finalDateTimeStr);
                System.out.println(finalDateTime);
            } catch (Exception ex) {
                String startDateSubString = requestStartDateStr.substring(0, 24);
                System.out.println(startDateSubString);
                finalDateTime = dateFormat.parse(startDateSubString);
                System.out.println(requestStartDate);
            }

            String imageName = salesPackageObject.getString("imageName");
            String entityStr = salesPackageObject.getString("entity");

            String filePath = getFilePath(imageName, entityStr);

            //this is the file path that is for different local server
            String UPLOAD_DIRECTORY = getUploadDirectory();
            //System.out.println("BLAH BLAH: " + json.toString());
            try {
                saveToFile(salesPackageObject.getString("image"), UPLOAD_DIRECTORY + filePath);

            } catch (Exception e) {
                System.err.println("Image not written....\n");
                e.printStackTrace();
            }

            //create picture entity
            PictureEntity picture = pictureSessionLocal.createPicture(new PictureEntity(filePath, imageName, PictureStatusEnum.VISIBLE));

            SalesPackageEntity salesPackageEntity = salesPackageSessionLocal.retrieveEntityById(salesPackageId);
            
            System.out.println("package entity " + salesPackageObject.getJsonObject("salesPackage"));
            salesPackageEntity.setDetail(salesPackageObject.getJsonObject("salesPackage").getString("detail"));
            try{
                salesPackageEntity.setDuration(salesPackageObject.getJsonObject("salesPackage").getInt("duration"));
            }
            catch(Exception ex)
            {
                salesPackageEntity.setDuration(Integer.parseInt(salesPackageObject.getJsonObject("salesPackage").getString("duration")));
            }
            salesPackageEntity.setFacilityType(salesPackageObject.getJsonObject("salesPackage").getString("facilityType"));
            
            try{
                salesPackageEntity.setPrice(salesPackageObject.getJsonObject("salesPackage").getInt("price"));
            }
            catch(Exception ex)
            {
                  salesPackageEntity.setPrice(Integer.parseInt(salesPackageObject.getJsonObject("salesPackage").getString("price")));
            }
                        
            salesPackageEntity.setStartDate(finalDateTime);
            salesPackageEntity.setTitle(salesPackageObject.getJsonObject("salesPackage").getString("title"));
            if (salesPackageObject.getString("imageName") == "no photo") {

            } else {
                salesPackageEntity.setPicture(picture);
            }
            salesPackageSessionLocal.updateEntity(salesPackageEntity);
            System.out.println(salesPackageObject);
            System.out.println(salesPackageObject.getJsonObject("salesPackage").getString("detail"));
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSalesPackage(@PathParam("id") Long spId) {
        try {
            salesPackageSessionLocal.deleteEntity(spId);
            return Response.status(204).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Sales Package not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }

    //UPLOAD_DIRECTORY + filePath for new file()
    private String getFilePath(String imageName, String entityStr) {
//        System.out.println("imageName: " + imageName + "\n");

        String filePath = "/images/" + entityStr + "/" + imageName;
//        String filePath = context.getContextPath() + "/src/java/hms/web/webservices/images/" + entityStr + "/" + imageName;
//        System.out.println("filepath: " + filePath + "\n");

        return filePath;
    }

    private void saveToFile(String imageStr, String filePath) throws IOException {
        //https://stackoverflow.com/questions/28584080/base64-java-lang-illegalargumentexception-illegal-character

        System.out.println("Context: " + context.getContextPath());
        System.out.println("Filepath: " + filePath);

        //decode string
        String newEncodedString = new String(imageStr.getBytes(), StandardCharsets.UTF_8);
        System.out.println(imageStr);
        byte[] data = Base64.getDecoder().decode(newEncodedString);

        File uploadedFile = new File(filePath);
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
        System.out.println("Being Written....");
        outputStream.write(data);

    }
}
