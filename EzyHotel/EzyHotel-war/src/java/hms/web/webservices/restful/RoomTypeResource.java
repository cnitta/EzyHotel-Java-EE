/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.common.session.PictureSessionLocal;
import hms.hpm.session.HotelSessionLocal;
import hms.hpm.session.RoomAmenitySessionLocal;
import hms.hpm.session.RoomTypeSessionLocal;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.NoResultException;
import javax.servlet.ServletContext;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.entity.HotelEntity;
import util.entity.PictureEntity;
import util.entity.RoomAmenityEntity;
import util.entity.RoomTypeEntity;

/**
 * REST Web Service
 *
 * @author nittayawancharoenkharungrueang
 */
@Path("roomtypes")
public class RoomTypeResource {

    RoomAmenitySessionLocal roomAmenitySessionLocal = lookupRoomAmenitySessionLocal();
    HotelSessionLocal hotelSessionLocal = lookupHotelSessionLocal();
    PictureSessionLocal pictureSessionLocal = lookupPictureSessionLocal();

    @EJB
    RoomTypeSessionLocal roomTypeSessionLocal = lookupRoomTypeSessionLocal();

    @javax.ws.rs.core.Context
    private ServletContext context;

    /**
     * Creates a new instance of RoomTypeResource
     */
    public RoomTypeResource() {
    }

    private RoomTypeSessionLocal lookupRoomTypeSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RoomTypeSessionLocal) c
                    .lookup("java:global/EzyHotel/EzyHotel-ejb/RoomTypeSession!hms.hpm.session.RoomTypeSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllRoomTypes() {

        List<RoomTypeEntity> roomTypes = roomTypeSessionLocal.retrieveAllEntities();

        System.out.println("Size of list = " + roomTypes.size());

        // TODO: Check if the RESTful display what's required
        for (RoomTypeEntity rt : roomTypes) {
            System.out.println("roomtype: " + rt);
            System.out.println(rt.getRooms());
            rt.setRooms(null);
            rt.setHotel(null);
            //rt.setHousekeepingSOP(null);
            if (rt.getPicture() != null) {
                try {
                    PictureEntity picture = rt.getPicture();

                    String image = getEncodedImageString(picture.getPictureFilePath());
                    picture.setImage(image);

                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }

        }
        GenericEntity<List<RoomTypeEntity>> entity = new GenericEntity<List<RoomTypeEntity>>(roomTypes) {
        };

        return Response.status(200).entity(entity).type(MediaType.APPLICATION_JSON).build();
    } // end retrieve all rooms

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomType(@PathParam("id") Long rId) {
        try {
            System.out.println("### Im at get room type: " + rId);
            RoomTypeEntity r = roomTypeSessionLocal.retrieveEntityById(rId);
            r.setHotel(null);
            r.setRooms(null);
            if (r.getPicture() != null) {
                PictureEntity picture = r.getPicture();
                String image = getEncodedImageString(picture.getPictureFilePath());
                picture.setImage(image);
            }

            return Response.status(200).entity(r).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder().add("error", "Room Type Not found").build();

            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        } catch (IOException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", "Room Type Not found").build();

            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomTypesByHotelId(@QueryParam("hotelId") Long hId) {
        System.out.println("### Im at get room types by hotel Id: " + hId);
        try {
            List<RoomTypeEntity> rmTypes = roomTypeSessionLocal.retrieveAllRoomTypesByHotelId(hId);

            System.out.println("### Room Types:");
            System.out.println(rmTypes.toString());

            // TODO: Check if the RESTful display what's required
            for (RoomTypeEntity rt : rmTypes) {
                System.out.println("Each Roomtype: " + rt);
                rt.setRooms(null);
                if (rt.getPicture() != null) {
                    try {
                        PictureEntity picture = rt.getPicture();

                        String image = getEncodedImageString(picture.getPictureFilePath());
                        picture.setImage(image);

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }

            GenericEntity<List<RoomTypeEntity>> roomTypes = new GenericEntity<List<RoomTypeEntity>>(rmTypes) {
            };

            return Response.status(200).entity(roomTypes).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder().add("error", "Room Types Not found").build();

            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RoomTypeEntity createRoomType(RoomTypeEntity r) {
        roomTypeSessionLocal.createEntity(r);
        return r;
    }

    @POST
    @Path("/hotel/{hotelId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoomTypeAndRoomAmenities(@PathParam("hotelId") Long hId, JsonObject json) {
        try {
            System.out.println("### im at createRoomTypeAndRoomAmenities");
            HotelEntity hotel = hotelSessionLocal.retrieveEntityById(hId);
            System.out.println("hotel: " + hotel);

            boolean airConditioning = json.getBoolean("airConditioning");
            boolean bathroomAmenities = json.getBoolean("bathroomAmenities");
            boolean bathTowels = json.getBoolean("bathTowels");
            boolean bdromSlippers = json.getBoolean("bdromSlippers");
            boolean coffeeNTeaMaker = json.getBoolean("coffeeNTeaMaker");
            boolean culteryNUtensils = json.getBoolean("culteryNUtensils");

            boolean diningArea = json.getBoolean("diningArea");
            boolean electricCooktop = json.getBoolean("electricCooktop");
            boolean electronicSafe = json.getBoolean("electronicSafe");
            boolean freeWifi = json.getBoolean("freeWifi");
            boolean hairDryer = json.getBoolean("hairDryer");
            boolean ironNIroningBoard = json.getBoolean("ironNIroningBoard");

            boolean kitchenette = json.getBoolean("kitchenette");
            boolean livingRoom = json.getBoolean("livingRoom");
            boolean microwaveOven = json.getBoolean("microwaveOven");
            boolean miniBar = json.getBoolean("miniBar");
            boolean mobilePhoneDeviceNCharger = json.getBoolean("mobilePhoneDeviceNCharger");
            boolean nespressoCoffeeMachine = json.getBoolean("nespressoCoffeeMachine");

            boolean nonSmoking = json.getBoolean("nonSmoking");
            boolean televisionNcableChn = json.getBoolean("televisionNcableChn");
            boolean toaster = json.getBoolean("toaster");
            boolean washerCumDryer = json.getBoolean("washerCumDryer");
            boolean writingDeskNChair = json.getBoolean("writingDeskNChair");

            RoomAmenityEntity rmAmenity = new RoomAmenityEntity();

            rmAmenity.setAirConditioning(airConditioning);
            rmAmenity.setBathroomAmenities(bathroomAmenities);
            rmAmenity.setBathTowels(bathTowels);
            rmAmenity.setBdromSlippers(bdromSlippers);
            rmAmenity.setCoffeeNTeaMaker(coffeeNTeaMaker);
            rmAmenity.setCulteryNUtensils(culteryNUtensils);

            rmAmenity.setDiningArea(diningArea);
            rmAmenity.setElectricCooktop(electricCooktop);
            rmAmenity.setElectronicSafe(electronicSafe);
            rmAmenity.setFreeWifi(freeWifi);
            rmAmenity.setHairDryer(hairDryer);
            rmAmenity.setIronNIroningBoard(ironNIroningBoard);

            rmAmenity.setKitchenette(kitchenette);
            rmAmenity.setLivingRoom(livingRoom);
            rmAmenity.setMicrowaveOven(microwaveOven);
            rmAmenity.setMiniBar(miniBar);
            rmAmenity.setMobilePhoneDeviceNCharger(mobilePhoneDeviceNCharger);
            rmAmenity.setNespressoCoffeeMachine(nespressoCoffeeMachine);

            rmAmenity.setNonSmoking(nonSmoking);
            rmAmenity.setTelevisionNcableChn(televisionNcableChn);
            rmAmenity.setToaster(toaster);
            rmAmenity.setWasherCumDryer(washerCumDryer);
            rmAmenity.setWritingDeskNChair(writingDeskNChair);

            RoomAmenityEntity result = roomAmenitySessionLocal.createEntity(rmAmenity);

            String name = json.getString("name");
            String roomTypecode = json.getString("roomTypecode");
            String bedType = json.getString("bedType");
            String description = json.getString("description");
            int numMaxGuest = Integer.parseInt(json.getString("numMaxGuest"));
            int totalRooms = Integer.parseInt(json.getString("totalRooms"));
            double roomSize = Double.parseDouble(json.getString("roomSize"));

            System.out.println("### name: " + name);
            System.out.println("### roomTypecode: " + roomTypecode);
            System.out.println("### bedType: " + bedType);
            System.out.println("### description: " + description);
            System.out.println("### numMaxGuest: " + numMaxGuest);
            System.out.println("### totalRooms: " + totalRooms);
            System.out.println("### roomSize: " + roomSize);

            RoomTypeEntity newRmType = new RoomTypeEntity();

            newRmType.setName(name);
            newRmType.setRoomTypecode(roomTypecode);
            newRmType.setBedType(bedType);
            newRmType.setDescription(description);
            newRmType.setNumMaxGuest(numMaxGuest);
            newRmType.setTotalRooms(totalRooms);
            newRmType.setRoomSize(roomSize);
            newRmType.setHotel(hotel);
            newRmType.setRooms(null);
            newRmType.setAmenity(result);

            RoomTypeEntity addedRmType = roomTypeSessionLocal.createEntity(newRmType);

            System.out.println("addedRmType" + addedRmType);

            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder().add("error", e.getMessage()).build();

            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRoomType(@PathParam("id") Long rId, JsonObject json) {

        try {
            System.out.println("### im at updateRoomType: ");

            RoomTypeEntity oldRoomType = roomTypeSessionLocal.retrieveEntityById(rId);

            System.out.println("getHotel: " + oldRoomType.getHotel());
            System.out.println("oldRoomType.getAmenity().getAirConditioning(): " + oldRoomType.getAmenity().getAirConditioning());

            System.out.println("oldRoomType.getAmenity().getRoomAmenityId(): " + oldRoomType.getAmenity().getRoomAmenityId());

            long rmId = oldRoomType.getAmenity().getRoomAmenityId();

            RoomAmenityEntity oldRoomAmenity = roomAmenitySessionLocal.retrieveEntityById(rmId);
            // Set Amenity
            oldRoomAmenity.setAirConditioning(json.getBoolean("airConditioning"));
            oldRoomAmenity.setBathroomAmenities(json.getBoolean("bathroomAmenities"));
            oldRoomAmenity.setBathTowels(json.getBoolean("bathTowels"));
            oldRoomAmenity.setBdromSlippers(json.getBoolean("bdromSlippers"));
            oldRoomAmenity.setCoffeeNTeaMaker(json.getBoolean("coffeeNTeaMaker"));
            oldRoomAmenity.setCulteryNUtensils(json.getBoolean("culteryNUtensils"));

            oldRoomAmenity.setDiningArea(json.getBoolean("diningArea"));
            oldRoomAmenity.setElectricCooktop(json.getBoolean("electricCooktop"));
            oldRoomAmenity.setElectronicSafe(json.getBoolean("electronicSafe"));
            oldRoomAmenity.setFreeWifi(json.getBoolean("freeWifi"));
            oldRoomAmenity.setHairDryer(json.getBoolean("hairDryer"));
            oldRoomAmenity.setIronNIroningBoard(json.getBoolean("ironNIroningBoard"));

            oldRoomAmenity.setKitchenette(json.getBoolean("kitchenette"));
            oldRoomAmenity.setLivingRoom(json.getBoolean("livingRoom"));
            oldRoomAmenity.setMicrowaveOven(json.getBoolean("microwaveOven"));
            oldRoomAmenity.setMiniBar(json.getBoolean("miniBar"));
            oldRoomAmenity.setMobilePhoneDeviceNCharger(json.getBoolean("mobilePhoneDeviceNCharger"));
            oldRoomAmenity.setNespressoCoffeeMachine(json.getBoolean("nespressoCoffeeMachine"));

            oldRoomAmenity.setNonSmoking(json.getBoolean("nonSmoking"));
            oldRoomAmenity.setTelevisionNcableChn(json.getBoolean("televisionNcableChn"));
            oldRoomAmenity.setToaster(json.getBoolean("toaster"));
            oldRoomAmenity.setWasherCumDryer(json.getBoolean("washerCumDryer"));
            oldRoomAmenity.setWritingDeskNChair(json.getBoolean("writingDeskNChair"));
            
            roomAmenitySessionLocal.updateEntity(oldRoomAmenity);

            //Set RoomType Details
            oldRoomType.setName(json.getString("name"));
            oldRoomType.setRoomTypecode(json.getString("roomTypecode"));
            oldRoomType.setBedType(json.getString("bedType"));
            oldRoomType.setDescription(json.getString("description"));
            oldRoomType.setNumMaxGuest(json.getJsonNumber("numMaxGuest").intValue());
            oldRoomType.setTotalRooms(json.getJsonNumber("totalRooms").intValue());
            oldRoomType.setRoomSize(json.getJsonNumber("totalRooms").doubleValue());

            roomTypeSessionLocal.updateEntity(oldRoomType);
            
            return Response.status(204).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder().add("error", "Room Not found").build();

            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRoomType(@PathParam("id") Long rId) {
        try {
            roomTypeSessionLocal.deleteEntity(rId);
            return Response.status(204).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder().add("error", "Room not found").build();

            return Response.status(404).entity(exception).build();
        }
    }

    private String getEncodedImageString(String filePath) throws IOException {
        String UPLOAD_DIRECTORY = getUploadDirectory();
        try {
            String image = pictureSessionLocal.getPictureBase64String(UPLOAD_DIRECTORY + filePath);
            // System.out.println("image: " + image);
            return image;
        } catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }
    }

    // UPLOAD_DIRECTORY + filePath for new file()
    private String getUploadDirectory() {
        String substring = "/EzyHotel-J2E/EzyHotel/dist/gfdeploy/EzyHotel/EzyHotel-war_war/";
        // this is the file path that is for different local server
        String UPLOAD_DIRECTORY = context.getRealPath("/").replace(substring, "");
        System.out.println("#UPLOAD_DIRECTORY : " + UPLOAD_DIRECTORY);
        return UPLOAD_DIRECTORY;
    }

    private PictureSessionLocal lookupPictureSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PictureSessionLocal) c
                    .lookup("java:global/EzyHotel/EzyHotel-ejb/PictureSession!hms.common.session.PictureSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private HotelSessionLocal lookupHotelSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (HotelSessionLocal) c
                    .lookup("java:global/EzyHotel/EzyHotel-ejb/HotelSession!hms.hpm.session.HotelSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private RoomAmenitySessionLocal lookupRoomAmenitySessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RoomAmenitySessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/RoomAmenitySession!hms.hpm.session.RoomAmenitySessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
