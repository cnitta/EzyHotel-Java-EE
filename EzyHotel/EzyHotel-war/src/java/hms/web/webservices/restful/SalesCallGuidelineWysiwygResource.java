/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.sales.session.SalesCallGuidelineSessionLocal;
import hms.sales.session.SalesCallGuidelineWysiwygSessionLocal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.NoResultException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.entity.SalesCallGuidelineEntity;
import util.entity.SalesCallGuidelineWysiwygEntity;

/**
 * REST Web Service
 *
 * @author Wai Kit
 */
@Path("salescallguidelineswysiwyg")
public class SalesCallGuidelineWysiwygResource {
    
    @EJB
    private SalesCallGuidelineSessionLocal salesCallGuidelineSessionLocal;
    
    @EJB
    private SalesCallGuidelineWysiwygSessionLocal salesCallGuidelineWysiwygSessionLocal;
    

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SalesCallGuidelineWysiwygResource
     */
    public SalesCallGuidelineWysiwygResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SalesCallGuidelineWysiwygEntity> retrieveAllSalesCallGuidelines()
    {
        return salesCallGuidelineWysiwygSessionLocal.retrieveAllEntities();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSalesCallGuidelineWysiwyg(@PathParam("id") Long scgwId){
        try{
            SalesCallGuidelineWysiwygEntity scgw = salesCallGuidelineWysiwygSessionLocal.retrieveEntityById(scgwId);
            return Response.status(200).entity(scgw).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e){
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @POST
    @Path("/title/{salesCallGuidelineTitle}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSalesCallGuidelineWysiwyg(JsonObject request, @PathParam("salesCallGuidelineTitle") String salesCallGuidelineTitle){

        try {
            SalesCallGuidelineEntity salesCallGuidelineEntity = new SalesCallGuidelineEntity();
            Date createDate = new Date();
            salesCallGuidelineEntity.setTitle(salesCallGuidelineTitle);
            salesCallGuidelineEntity.setCreateDate(createDate);
            salesCallGuidelineSessionLocal.createEntity(salesCallGuidelineEntity);

            //System.out.println(request);
            JsonObject immutable = request.getJsonObject("_immutable");
            //System.out.println(immutable);
            JsonObject currentContent = immutable.getJsonObject("currentContent");
            //System.out.println(currentContent);
            JsonObject blockMap = currentContent.getJsonObject("blockMap");
            //System.out.println(blockMap);
            List<String> keys = new ArrayList<String>();
            for (Object key : blockMap.keySet()) {
                if(!keys.contains(key)){
                    keys.add(key.toString());
                }
                //System.out.println("size:" + keys.size());
            }
            for (int i = 0; i < keys.size(); i++) {
                JsonObject obj = blockMap.getJsonObject(keys.get(i));
                String key = obj.get("key").toString().replace("\"","");
                String type = obj.get("type").toString().replace("\"","");
                String text = obj.get("text").toString().replace("\"","");

                SalesCallGuidelineWysiwygEntity salesCallGuidelineWysiwyg = new SalesCallGuidelineWysiwygEntity();      
                salesCallGuidelineWysiwyg.setKey(key);
                salesCallGuidelineWysiwyg.setType(type);
                salesCallGuidelineWysiwyg.setText(text);
                salesCallGuidelineWysiwyg.setSalesCallGuidelineEntity(salesCallGuidelineEntity);

                salesCallGuidelineWysiwygSessionLocal.createEntity(salesCallGuidelineWysiwyg);
                //System.out.println(key);
                //System.out.println(type);
                //System.out.println(text);
            }
            
            JsonObject success = Json.createObjectBuilder()
                    .add("message", "Successfully created")
                    .build();
            return Response.status(200).entity(success).type(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }


        //cp.setCallDate(localDate);
        //salesCallGuidelineWysiwygSessionLocal.createEntity(scgw);
        //return scgw;
    }
    
    public Response createSalesCallGuidelineWysiwygUsingId(JsonObject request, Long salesCallGuidelineId, String salesCallGuidelineTitle) {

        try {
            SalesCallGuidelineEntity scg = salesCallGuidelineSessionLocal.retrieveEntityById(salesCallGuidelineId);
            scg.setTitle(salesCallGuidelineTitle);
            //System.out.println(salesCallGuidelineTitle);
            salesCallGuidelineSessionLocal.updateEntity(scg);

            JsonObject immutable = request.getJsonObject("_immutable");
            JsonObject currentContent = immutable.getJsonObject("currentContent");
            JsonObject blockMap = currentContent.getJsonObject("blockMap");
            List<String> keys = new ArrayList<String>();
            for (Object key : blockMap.keySet()) {
                if (!keys.contains(key)) {
                    keys.add(key.toString());
                }
            }
            for (int i = 0; i < keys.size(); i++) {
                JsonObject obj = blockMap.getJsonObject(keys.get(i));
                String key = obj.get("key").toString().replace("\"", "");
                String type = obj.get("type").toString().replace("\"", "");
                String text = obj.get("text").toString().replace("\"", "");

                SalesCallGuidelineWysiwygEntity salesCallGuidelineWysiwyg = new SalesCallGuidelineWysiwygEntity();
                salesCallGuidelineWysiwyg.setKey(key);
                salesCallGuidelineWysiwyg.setType(type);
                salesCallGuidelineWysiwyg.setText(text);
                salesCallGuidelineWysiwyg.setSalesCallGuidelineEntity(scg);

                salesCallGuidelineWysiwygSessionLocal.createEntity(salesCallGuidelineWysiwyg);
            }

            JsonObject success = Json.createObjectBuilder()
                    .add("message", "Successfully created")
                    .build();
            return Response.status(200).entity(success).type(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @PUT
    @Path("/{salesCallGuidelineId}/title/{salesCallGuidelineTitle}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSalesCallGuidelineWysiwyg(@PathParam("salesCallGuidelineId") Long salesCallGuidelineId, @PathParam("salesCallGuidelineTitle") String salesCallGuidelineTitle, JsonObject request){
        List<SalesCallGuidelineWysiwygEntity> listSalesCallGuidelineWysiwyg= salesCallGuidelineWysiwygSessionLocal.retrieveAllEntityBySalesCallGuidelineId(salesCallGuidelineId);
        
        for(int i = 0; i < listSalesCallGuidelineWysiwyg.size(); i++){
            salesCallGuidelineWysiwygSessionLocal.deleteEntity(listSalesCallGuidelineWysiwyg.get(i).getSalesCallGuidelineWysiwygId());
        }        
        return createSalesCallGuidelineWysiwygUsingId(request, salesCallGuidelineId, salesCallGuidelineTitle);
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSalesCallGuidelineWysiwyg(@PathParam("id") Long scgId){
        try{
            salesCallGuidelineWysiwygSessionLocal.deleteEntity(scgId);
            return Response.status(204).build();
        } catch (NoResultException e){
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Sales Call Guideline Wysiwyg not found")
                    .build();
            
            return Response.status(404).entity(exception).build();
        }
    }
}
