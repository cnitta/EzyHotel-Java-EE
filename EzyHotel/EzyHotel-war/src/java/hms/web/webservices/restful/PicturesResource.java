/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.common.session.PictureSessionLocal;
import hms.hotelstay.session.MenuItemSessionLocal;
import hms.hotelstay.session.MerchandiseSessionLocal;
import hms.hpm.session.RoomTypeSessionLocal;
import hms.sales.session.SalesPackageSessionLocal;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.entity.MenuItemEntity;
import util.entity.MerchandiseEntity;
import util.entity.PictureEntity;
import util.entity.RoomTypeEntity;
import util.entity.SalesPackageEntity;
import util.enumeration.MenuItemCategoryEnum;
import util.enumeration.MerchandiseStatusEnum;
import util.enumeration.PictureStatusEnum;
import util.exception.PictureNotFoundException;

/**
 * REST Web Service
 *
 * @author vincentyeo
 */
@Path("pictures")
public class PicturesResource {
    RoomTypeSessionLocal roomTypeSession = lookupRoomTypeSessionLocal();
    SalesPackageSessionLocal salesPackageSession = lookupSalesPackageSessionLocal();
    MerchandiseSessionLocal merchandiseSessionLocal = lookupMerchandiseSessionLocal();
    PictureSessionLocal pictureSessionLocal = lookupPictureSessionLocal();
    MenuItemSessionLocal menuItemSessionLocal = lookupMenuItemSessionLocal();
    SalesPackageSessionLocal salesPackageSessionLocal = lookupSalesPackageSessionLocal();

//    @Context
//    private UriInfo context;
    
    @Context
    private ServletContext context;
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPicture(PictureEntity p) {

        System.out.println("Create PictureEntity at PictureEntityResource");

        PictureEntity picture = pictureSessionLocal.createPicture(p);

        return Response.status(Status.OK).entity(picture).build();
        
    } //end createDeviceEntity 
    
    @POST
    @Path("/upload")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(JsonObject json) {           
        
        String imageName = json.getString("imageName");
        String entityStr = json.getString("entity");

        String filePath = getFilePath(imageName, entityStr);
        
        //this is the file path that is for different local server
        String UPLOAD_DIRECTORY = getUploadDirectory();                
                       System.out.println("BLAH BLAH: " + json.toString());
        try{
            saveToFile(json.getString("image"), UPLOAD_DIRECTORY + filePath);  

        }
        catch(Exception e)
        {
            System.err.println("Image not written....\n");
            e.printStackTrace();
        }
        
        //create picture entity
        PictureEntity picture = pictureSessionLocal.createPicture(new PictureEntity(filePath, imageName, PictureStatusEnum.VISIBLE));
        
        //create the other entity
        System.out.println("TESTTTTTTTTTTTTT3");
        return createEntity(json, picture);      
    }
    
    //UPLOAD_DIRECTORY + filePath for new file()
    private String getFilePath(String imageName, String entityStr)
    {          
//        System.out.println("imageName: " + imageName + "\n");
       
        String filePath = "/images/" + entityStr + "/" + imageName;
//        String filePath = context.getContextPath() + "/src/java/hms/web/webservices/images/" + entityStr + "/" + imageName;
//        System.out.println("filepath: " + filePath + "\n");
        
        return filePath;
    }
    
    //UPLOAD_DIRECTORY + filePath for new file()
    private String getUploadDirectory()
    {
        String substring = "/EzyHotel-J2E/EzyHotel/dist/gfdeploy/EzyHotel/EzyHotel-war_war/";
//        String substring = "/dist/gfdeploy/EzyHotel/EzyHotel-war_war/";
        System.out.println("Real Path " + context.getRealPath("/"));
        //this is the file path that is for different local server
        String UPLOAD_DIRECTORY = context.getRealPath("/").replace(substring, "");        
        System.out.println("#UPLOAD_DIRECTORY : " + UPLOAD_DIRECTORY);
        return UPLOAD_DIRECTORY;
    }
    
    private Response createEntity(JsonObject json, PictureEntity picture)
    {        
        switch(json.getString("entity")){
            case "affiliatecontent":
                break;
            case "facility":
                break;
            case "hotel":
                break;
            case "lostfound":
                break;
            case "maintenancerecord":
                break;
            case "menuItem":
                System.out.println("TESTTTTTTTTTTTTT1");
                JsonObject menuItemJson = json.getJsonObject("menuItem");
                System.out.println(menuItemJson.toString());
                System.out.println("TESTTTTTTTTTTTTT2");
                MenuItemEntity menuItem = new MenuItemEntity();
                menuItem.setMenuItemName(menuItemJson.getString("menuItemName"));
                menuItem.setDescription(menuItemJson.getString("description"));
                menuItem.setUnitPrice(Double.parseDouble(menuItemJson.getString("unitPrice")));
                switch(menuItemJson.getString("category")) {
                    case "BREAKFAST":
                        menuItem.setCategory(MenuItemCategoryEnum.BREAKFAST);
                        break;
                    case "LUNCH":
                        menuItem.setCategory(MenuItemCategoryEnum.LUNCH);
                        break;
                    case "DINNER":
                        menuItem.setCategory(MenuItemCategoryEnum.DINNER);
                        break;
                    case "DESSERT":
                        menuItem.setCategory(MenuItemCategoryEnum.DESSERT);
                        break;
                    case "DRINKS":
                        menuItem.setCategory(MenuItemCategoryEnum.DRINKS);
                        break;
                    default:
                        break;
                }
                menuItem.setPicture(picture);
                
                MenuItemEntity m = menuItemSessionLocal.createMenuItem(menuItem);
                return Response.status(Status.OK).entity(m).build();  
            case "merchandise":
                JsonObject merchandiseJson = json.getJsonObject("merchandise");
                 System.out.println("merchandiseJson***");
                System.out.println(merchandiseJson.toString());
                
                MerchandiseEntity merchandise = new MerchandiseEntity();
                merchandise.setCostPoints(merchandiseJson.getInt("costPoints"));
                merchandise.setDescription(merchandiseJson.getString("description"));
                merchandise.setIsTriggerOn(merchandiseJson.getBoolean("isTriggerOn"));
                merchandise.setMaxCostPriceLimit(merchandiseJson.getInt("maxCostPriceLimit"));
                merchandise.setName(merchandiseJson.getString("name"));
                merchandise.setPoTriggerLevel(merchandiseJson.getInt("poTriggerLevel"));
                merchandise.setQuantityOnHand(merchandiseJson.getInt("quantityOnHand"));
                merchandise.setMerchandiseStatus(MerchandiseStatusEnum.valueOf(merchandiseJson.getString("merchandiseStatus")));
                merchandise.setPicture(picture);
                
                MerchandiseEntity entity = merchandiseSessionLocal.createMerchandise(merchandise);
                
                return Response.status(Status.OK).entity(entity).build();  
            case "orderitem":
                break;     
            case "promotion":
                break;  
            case "roomtype":
                break;   
            case "salespackage":
                JsonObject salesPackageJson = json.getJsonObject("salespackage");
                System.out.println("salespackageJson***");
                System.out.println(salesPackageJson.toString());
                
                String strPackageStartDate = salesPackageJson.get("packageStartDate").toString().replace("\"", "");
                Date packageStartDate = new Date();
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    packageStartDate = formatter.parse(strPackageStartDate);
                } catch (Exception ex) {

                }
                RoomTypeEntity roomTypeEntity = roomTypeSession.retrieveEntity("name", salesPackageJson.getString("roomType"));
                                
                SalesPackageEntity salesPackageEntity = new SalesPackageEntity();
                salesPackageEntity.setTitle(salesPackageJson.getString("packageTitle"));
                salesPackageEntity.setStartDate(packageStartDate);
                salesPackageEntity.setFacilityType(salesPackageJson.getString("roomType"));
                
                salesPackageEntity.setDuration(salesPackageJson.getInt("packageDuration"));
                salesPackageEntity.setDetail(salesPackageJson.getString("packageDetail"));
                System.out.println("setPrice" + new Double(salesPackageJson.getInt("packagePrice")));
                salesPackageEntity.setPrice(new Double(salesPackageJson.getInt("packagePrice")));
//            salesPackageEntity.setPrice(Double.parseDouble(salesPackageJson.getString("packagePrice")));
                salesPackageEntity.setPicture(picture);
                salesPackageEntity.setRoomType(roomTypeEntity);
                
                SalesPackageEntity salesPackage = salesPackageSessionLocal.createEntity(salesPackageEntity);
                
                return Response.status(Status.OK).build();
            case "staff":
                break;                  
            default:
                break;                
        }
        
        return Response.status(Status.NOT_IMPLEMENTED).build(); 
    }
    
    private void saveToFile(String imageStr, String filePath) throws IOException{
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

    
    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchPicturesByAttributes(
//            @QueryParam("pictureName") String pictureName,
            @QueryParam("pictureFilePath") String pictureFilePath,
//            @QueryParam("picDescription") String picDescription,
            @QueryParam("fileName") String fileName,
            @QueryParam("DateTaken") String dateString,
            @QueryParam("status") PictureStatusEnum status){             
            
//            PictureEntity picture = new PictureEntity(fileName, pictureFilePath, picDescription, fileName, null);
        
            PictureEntity picture = new PictureEntity(pictureFilePath, fileName,  status);
            picture.setPicStatus(status);
            
//            if(dateString != null)
//            {
//                try {            
//                String[] splited = dateString.split("\\s+");
//
//                Date dateTaken = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(splited[0] + "+" + splited[1]);
//                picture.setDateTaken(dateTaken);
//                }
//                catch (ParseException ex) {
//                    Logger.getLogger(DevicesResource.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }              
            
         
            List<PictureEntity> results = pictureSessionLocal.retrievePicturesByPictureAttributes(picture);

            GenericEntity<List<PictureEntity>> entity = new GenericEntity<List<PictureEntity>>(results) {
            };
            
            return Response.status(200).entity(entity).build();
        } //end searchPictureEntitys           
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAllPictureEntity() {
        System.out.println("***Restful retrieveAllPictures Started***");
        List<PictureEntity> results = pictureSessionLocal.retrieveAllPictures(); 
        System.out.println("***Restful retrieveAllPictures Ended***");

        GenericEntity<List<PictureEntity>> entity = new GenericEntity<List<PictureEntity>>(results) {
        };

        return Response.status(Status.OK).entity(entity).build();
    }  

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPictureEntityById(@PathParam("id") Long pId) {
        try {
            System.out.println("Get PictureEntity at PictureEntityResource");
            PictureEntity picture = pictureSessionLocal.retrievePictureById(pId);

            System.out.println("PictureEntity p with id" + pId + " and fileName " + picture.getFileName());          

            return Response.status(Status.OK).entity(picture).build();

        } catch (PictureNotFoundException e) {
            System.out.println("Error received " + e.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "PictureEntity Not Found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
            
        } 
        catch (Exception ex) {
            System.out.println("Error received " + ex.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", ex.getMessage())
                    .build();

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    } //end getPictureEntity

    //Not working
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPicture(@PathParam("id") Long dId, PictureEntity d) {        

            pictureSessionLocal.updatePicture(d);
            return Response.status(Status.OK).build();
    } //end editPictureEntity   

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePicture(@PathParam("id") Long dId) {
        try {
            System.out.println("Delete PictureEntity is triggered at pictureResources");
            pictureSessionLocal.deletePicture(dId);
            return Response.status(Status.OK).build();
        } 
        catch (PictureNotFoundException ex) 
        {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "PictureEntity is not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end deletePictureEntity    
    

    private PictureSessionLocal lookupPictureSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PictureSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/PictureSession!hms.common.session.PictureSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MerchandiseSessionLocal lookupMerchandiseSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (MerchandiseSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/MerchandiseSession!hms.hotelstay.session.MerchandiseSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    private MenuItemSessionLocal lookupMenuItemSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (MenuItemSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/MenuItemSession!hms.hotelstay.session.MenuItemSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private SalesPackageSessionLocal lookupSalesPackageSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (SalesPackageSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/SalesPackageSession!hms.sales.session.SalesPackageSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private RoomTypeSessionLocal lookupRoomTypeSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RoomTypeSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/RoomTypeSession!hms.hpm.session.RoomTypeSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    

}
