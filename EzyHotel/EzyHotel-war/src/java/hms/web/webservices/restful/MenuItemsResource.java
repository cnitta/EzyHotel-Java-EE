/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.common.session.PictureSessionLocal;
import hms.hotelstay.session.ListMenuItemSessionLocal;
import hms.hotelstay.session.MenuItemSessionLocal;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.entity.ListMenuItemEntity;
import util.entity.MenuItemEntity;
import util.entity.PictureEntity;
import util.enumeration.MenuItemCategoryEnum;
import util.exception.MenuItemNotFoundException;

/**
 * REST Web Service
 *
 * @author vincentyeo
 */
@Path("menuitems")
public class MenuItemsResource {
    PictureSessionLocal pictureSessionLocal = lookupPictureSessionLocal();
    ListMenuItemSessionLocal listMenuItemSessionLocal = lookupListMenuItemSessionLocal();
    
    MenuItemSessionLocal menuItemSessionLocal = lookupMenuItemSessionLocal();
    
    
    @javax.ws.rs.core.Context
    private ServletContext context;   
    
//    @Context
//    private UriInfo context;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMenuItem(MenuItemEntity m) {

            System.out.println("Create MenuItemEntity at MenuItemEntityResource");
            
            MenuItemEntity menuItem = menuItemSessionLocal.createMenuItem(m);

            return Response.status(Status.OK).entity(menuItem).build();
        
    } //end createMenuItemEntity 

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchMenuItemsByAttributes(@QueryParam("menuName") String menuName,
            @QueryParam("description") String description,
            @QueryParam("unitPrice") Double unitPrice,
            @QueryParam("category") MenuItemCategoryEnum category){             
            
            MenuItemEntity menuItem = new MenuItemEntity(menuName, description, unitPrice, category);
         
            List<MenuItemEntity> results = menuItemSessionLocal.retrieveMenuItemByMenuItemAttributes(menuItem);

            GenericEntity<List<MenuItemEntity>> entity = new GenericEntity<List<MenuItemEntity>>(results) {
            };
            
            return Response.status(200).entity(entity).build();
        } //end searchMenuItemEntitys           
        
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAllMenuItemEntity() {
        System.out.println("***Restful retrieveAllMenuItems Started***");
        List<MenuItemEntity> results = menuItemSessionLocal.retrieveAllMenuItems(); 
        System.out.println("***Restful retrieveAllMenuItems Ended***");
        
        for (MenuItemEntity menuItem : results) {
            try{
                PictureEntity picture = menuItem.getPicture();

                String image = getEncodedImageString(picture.getPictureFilePath());
                picture.setImage(image);
            }
            catch(IOException ex)
            {
                System.out.println("Error received " + ex.getMessage());
                JsonObject exception = Json.createObjectBuilder()
                    .add("error", "MenuItemEntity Not Found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
            }
            }        

        GenericEntity<List<MenuItemEntity>> entity = new GenericEntity<List<MenuItemEntity>>(results) {
        };

        return Response.status(Status.OK).entity(entity).build();
    }
    
    
    @GET
    @Path("/listItems")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAllListMenuItemEntity() {
        System.out.println("***Restful retrieveAllListMenuItems Started***");
        List<ListMenuItemEntity> results = listMenuItemSessionLocal.retrieveAllListMenuItems(); 
        System.out.println("***Restful retrieveAllListMenuItems Ended***");

        GenericEntity<List<ListMenuItemEntity>> entity = new GenericEntity<List<ListMenuItemEntity>>(results) {
        };

        return Response.status(Status.OK).entity(entity).build();
    }      

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMenuItemEntityById(@PathParam("id") Long mId) {
        try {
            System.out.println("Get MenuItemEntity at MenuItemEntityResource");
            MenuItemEntity menuItem = menuItemSessionLocal.retrieveMenuItemById(mId);
            PictureEntity picture = menuItem.getPicture();

            String image = getEncodedImageString(picture.getPictureFilePath());
            picture.setImage(image);

            System.out.println("MenuItemEntity e with id" + mId);          

            return Response.status(Status.OK).entity(menuItem).build();

        } catch (MenuItemNotFoundException e) {
            System.out.println("Error received " + e.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "MenuItemEntity Not Found")
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
    } //end getMenuItemEntity
    
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
    
    //UPLOAD_DIRECTORY + filePath for new file()
    private String getUploadDirectory()
    {
        String substring = "/EzyHotel-J2E/EzyHotel/dist/gfdeploy/EzyHotel/EzyHotel-war_war/";
        
        //this is the file path that is for different local server
        String UPLOAD_DIRECTORY = context.getRealPath("/").replace(substring, "");
//        System.out.println("#UPLOAD_DIRECTORY : " + UPLOAD_DIRECTORY);
        return UPLOAD_DIRECTORY;
    }    
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editMenuItem(@PathParam("id") Long mId, MenuItemEntity m) {        

            menuItemSessionLocal.updateMenuItem(m);
            return Response.status(Status.OK).build();
    } //end editMerchandiseEntity     

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMenuItem(@PathParam("id") Long mId) {
        try {
            System.out.println("Delete MenuItemEntity is triggered at menuItemResources");
            menuItemSessionLocal.deleteMenuItem(mId);
            return Response.status(Status.OK).build();
        } 
        catch (MenuItemNotFoundException ex) 
        {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "MenuItemEntity is not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end deleteMenuItemEntity      
        
    

    private MenuItemSessionLocal lookupMenuItemSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (MenuItemSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/MenuItemSession!hms.hotelstay.session.MenuItemSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ListMenuItemSessionLocal lookupListMenuItemSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ListMenuItemSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/ListMenuItemSession!hms.hotelstay.session.ListMenuItemSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PictureSessionLocal lookupPictureSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PictureSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/PictureSession!hms.common.session.PictureSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
