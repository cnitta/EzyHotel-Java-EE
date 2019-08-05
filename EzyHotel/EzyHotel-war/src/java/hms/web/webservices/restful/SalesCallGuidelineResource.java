/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.sales.session.SalesCallGuidelineSessionLocal;
import hms.sales.session.SalesCallGuidelineWysiwygSessionLocal;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.persistence.NoResultException;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.entity.SalesCallGuidelineEntity;
import util.entity.SalesCallGuidelineWysiwygEntity;

/**
 * REST Web Service
 *
 * @author Wai Kit
 */
@Path("salescallguidelines")
public class SalesCallGuidelineResource {
    
    @EJB
    private SalesCallGuidelineSessionLocal salesCallGuidelineSessionLocal;
    
    @EJB
    private SalesCallGuidelineWysiwygSessionLocal salesCallGuidelineWysiwygSessionLocal;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SalesCallGuidelineResource
     */
    public SalesCallGuidelineResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllSalesCallGuidelines()
    {
        List<SalesCallGuidelineEntity> listSalesCallGuidelineEntitys = salesCallGuidelineSessionLocal.retrieveAllEntities();
        
        JsonArrayBuilder salesCallGuidelines = Json.createArrayBuilder();
      
        for (int i = 0; i < listSalesCallGuidelineEntitys.size(); i++) {
            String wysiwygOutput = "";
           
            List<SalesCallGuidelineWysiwygEntity> listSalesCallGuidelineWysiwyg = salesCallGuidelineWysiwygSessionLocal.retrieveAllEntityBySalesCallGuidelineId(listSalesCallGuidelineEntitys.get(i).getSalesCallGuidelineId());

            for (int j = 0; j < 20; j++) {

                if (j < listSalesCallGuidelineWysiwyg.size()) {
                    if (listSalesCallGuidelineWysiwyg.get(j).getType().equals("ordered-list-item")) {
                        if (j == 0) {
                            int orderList = j + 1;
                            wysiwygOutput = orderList + ". " + listSalesCallGuidelineWysiwyg.get(j).getText() + "\'" + ",";
                            //System.out.println(wysiwygOutput);
                        } else {
                            int orderList = j + 1;
                            wysiwygOutput = wysiwygOutput + "\'" + orderList + ". " + listSalesCallGuidelineWysiwyg.get(j).getText() + "\'" + ",";
                            //System.out.println(wysiwygOutput);
                        }
                    }else{
                        if (j == 0) {
                            wysiwygOutput = listSalesCallGuidelineWysiwyg.get(j).getText() + "\'" + ",";
                            //System.out.println(wysiwygOutput);
                        } else {
                            wysiwygOutput = wysiwygOutput + "\'" + listSalesCallGuidelineWysiwyg.get(j).getText() + "\'" + ",";
                            //System.out.println(wysiwygOutput);
                        }
                    }
                } else {
                    if (j == 0) {
                        String output = "\u00a0";
                        wysiwygOutput = output;
                        //System.out.println(wysiwygOutput);
                    } else {
                        if (j == 19) {
                            String output = "\u00a0";
                            wysiwygOutput = wysiwygOutput + "\'" + output;
                            //System.out.println(wysiwygOutput);
                        } else {
                            String output = "\u00a0";
                            wysiwygOutput = wysiwygOutput + "\'" + output + "\'" + ",";
                            //System.out.println(wysiwygOutput);
                        }
                    }
                }
            }
            salesCallGuidelines.add(Json.createObjectBuilder()
                    .add("page", i+1)
                    .add("guidelineTitle", listSalesCallGuidelineEntitys.get(i).getTitle())
                    .add("content", wysiwygOutput)
                    .add("contentsPerPage", 20));
        }
        
        JsonArray array = salesCallGuidelines.build();

        //JsonObject salesCallGuidelines = Json.createObjectBuilder()
        //        .add("page", "1")
        //        .add("content", wysiwygOutput)
        //        .add("contentsPerPage", 20)
        //        .build();
        
        //return salesCallGuidelineSessionLocal.retrieveAllEntities();
        return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/{salesCallGuidelineId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSalesCallGuideline(@PathParam("salesCallGuidelineId") Long salesCallGuidelineId){
        try{
            List<SalesCallGuidelineWysiwygEntity> listSalesCallGuidelineWysiwyg = salesCallGuidelineWysiwygSessionLocal.retrieveAllEntityBySalesCallGuidelineId(salesCallGuidelineId);
                        
            //System.out.println(listSalesCallGuidelineWysiwyg);
            JsonArrayBuilder salesCallGuidelinesWysiwyg = Json.createArrayBuilder();
            
            for (int i = 0; i < listSalesCallGuidelineWysiwyg.size(); i++) {
                salesCallGuidelinesWysiwyg.add(Json.createObjectBuilder()
                        .add("guidelineTitle", listSalesCallGuidelineWysiwyg.get(i).getSalesCallGuidelineEntity().getTitle())
                        .add("key", listSalesCallGuidelineWysiwyg.get(i).getKey())
                        .add("text", listSalesCallGuidelineWysiwyg.get(i).getText())
                        .add("type", listSalesCallGuidelineWysiwyg.get(i).getType()));
            }
            
            JsonArray array = salesCallGuidelinesWysiwyg.build();            
            return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e){
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SalesCallGuidelineEntity createSalesCallGuideline(SalesCallGuidelineEntity scg){
        LocalDate localDate = LocalDate.now();
        //cp.setCallDate(localDate);
        salesCallGuidelineSessionLocal.createEntity(scg);
        return scg;
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSalesCallGuideline(@PathParam("id") Long scgId, SalesCallGuidelineEntity scg){
        scg.setSalesCallGuidelineId(scgId);
        try{
            salesCallGuidelineSessionLocal.updateEntity(scg);
            return Response.status(204).build();
        } catch (NoResultException e){
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSalesCallGuideline(@PathParam("id") Long scgId){
        try{
            salesCallGuidelineSessionLocal.deleteEntity(scgId);
            return Response.status(204).build();
        } catch (NoResultException e){
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Sales Call Guideline not found")
                    .build();
            
            return Response.status(404).entity(exception).build();
        }
    }
}
