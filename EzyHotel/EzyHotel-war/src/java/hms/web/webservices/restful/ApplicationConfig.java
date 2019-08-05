/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author berni
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(hms.web.webservices.restful.AccountsResource.class);
        resources.add(hms.web.webservices.restful.AffiliateContentsResource.class);
        resources.add(hms.web.webservices.restful.AffiliatesResource.class);
        resources.add(hms.web.webservices.restful.BidsResource.class);
        resources.add(hms.web.webservices.restful.CORSFilter.class);
        resources.add(hms.web.webservices.restful.CallReportResource.class);
        resources.add(hms.web.webservices.restful.ConfirmationEmailResource.class);
        resources.add(hms.web.webservices.restful.ConventionInstructionMemoResource.class);
        resources.add(hms.web.webservices.restful.CustomerMailingResource.class);
        resources.add(hms.web.webservices.restful.CustomerResource.class);
        resources.add(hms.web.webservices.restful.DevicesResource.class);
        resources.add(hms.web.webservices.restful.ExternalMaintenanceCompanysResource.class);
        resources.add(hms.web.webservices.restful.FacilitiesResource.class);
        resources.add(hms.web.webservices.restful.FeedbacksResource.class);
        resources.add(hms.web.webservices.restful.HotelsResource.class);
        resources.add(hms.web.webservices.restful.HousekeepingForcastResource.class);
        resources.add(hms.web.webservices.restful.HousekeepingRecordsResource.class);
        resources.add(hms.web.webservices.restful.HousekeepingRequestResource.class);
        resources.add(hms.web.webservices.restful.HousekeepingSOPResource.class);
        resources.add(hms.web.webservices.restful.InformationResource.class);
        resources.add(hms.web.webservices.restful.InventoryResource.class);
        resources.add(hms.web.webservices.restful.InvoiceResource.class);
        resources.add(hms.web.webservices.restful.LeaveResource.class);
        resources.add(hms.web.webservices.restful.LogsResource.class);
        resources.add(hms.web.webservices.restful.LoyaltyResource.class);
        resources.add(hms.web.webservices.restful.LoyaltyTransactionsResource.class);
        resources.add(hms.web.webservices.restful.MenuItemsResource.class);
        resources.add(hms.web.webservices.restful.MerchandiseOrderItemsResource.class);
        resources.add(hms.web.webservices.restful.MerchandiseOrdersResource.class);
        resources.add(hms.web.webservices.restful.MerchandisePurchaseOrdersResource.class);
        resources.add(hms.web.webservices.restful.MerchandisesResource.class);
        resources.add(hms.web.webservices.restful.MobilecheckinResource.class);
        resources.add(hms.web.webservices.restful.OnlineCustomersResource.class);
        resources.add(hms.web.webservices.restful.OnlineRoomBookingsResource.class);
        resources.add(hms.web.webservices.restful.PaymentsResource.class);
        resources.add(hms.web.webservices.restful.PicturesResource.class);
        resources.add(hms.web.webservices.restful.PriceRateResource.class);
        resources.add(hms.web.webservices.restful.ProgramEntryResource.class);
        resources.add(hms.web.webservices.restful.PromotionResource.class);
        resources.add(hms.web.webservices.restful.ProxyBidsResource.class);
        resources.add(hms.web.webservices.restful.ReportingAnalyticsResource.class);
        resources.add(hms.web.webservices.restful.ReserveFacilityResource.class);
        resources.add(hms.web.webservices.restful.RoomAmenitiesResource.class);
        resources.add(hms.web.webservices.restful.RoomAssetsResource.class);
        resources.add(hms.web.webservices.restful.RoomBookingResource.class);
        resources.add(hms.web.webservices.restful.RoomOrderResource.class);
        resources.add(hms.web.webservices.restful.RoomServiceOrdersResource.class);
        resources.add(hms.web.webservices.restful.RoomTypeResource.class);
        resources.add(hms.web.webservices.restful.RoomsResource.class);
        resources.add(hms.web.webservices.restful.SalesCallGuidelineResource.class);
        resources.add(hms.web.webservices.restful.SalesCallGuidelineWysiwygResource.class);
        resources.add(hms.web.webservices.restful.SalesCallResultResource.class);
        resources.add(hms.web.webservices.restful.SalesPackageResource.class);
        resources.add(hms.web.webservices.restful.SalesReportingResource.class);
        resources.add(hms.web.webservices.restful.StaffResource.class);
        resources.add(hms.web.webservices.restful.SuppliersResource.class);
        resources.add(hms.web.webservices.restful.TypesRoomResource.class);
        resources.add(hms.web.webservices.restful.WorkRoster2Resource.class);
        resources.add(hms.web.webservices.restful.WorkRosterResource.class);
    }
}
