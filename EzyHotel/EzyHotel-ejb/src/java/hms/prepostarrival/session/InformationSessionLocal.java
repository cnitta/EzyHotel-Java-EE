/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.prepostarrival.session;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.ejb.Local;
import util.entity.PriceRateEntity;
import util.entity.RoomBookingEntity;
import util.entity.RoomTypeEntity;

/**
 *
 * @author berni
 */
@Local
public interface InformationSessionLocal {

    public InformationRetrievalClass getAllInformationForHotel(String hotelName) throws Exception;

    public boolean checkVacancyForDateRange(Date startDate, Date endDate, String roomType, String hotelName) throws Exception;

    public List<RoomTypeEntity> retrieveRoomTypeByHotel(String attribute, Long hotelId);

    public List<PriceRateEntity> retrievePriceRatesByRoomType(String attribute, Long roomTypeId);

    public List<PriceRateRoomTypeClass> formatPriceRoomTypeInformationByHotelId(Long hotelId);

    public List<PriceRateEntity> getPriceRateByDay(Long roomTypeId);

}
