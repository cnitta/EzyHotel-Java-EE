/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.frontdesk.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.RoomBookingEntity;
import util.entity.RoomEntity;
import util.exception.NoResultException;

/**
 *
 * @author Nicholas Nah
 */
@Local
public interface RoomBookingSessionLocal {

    public RoomBookingEntity createBooking(RoomBookingEntity booking);

    public RoomBookingEntity retrieveBookingById(Long bId) throws NoResultException;

    public List<RoomBookingEntity> retrieveAllBookingForStaff();

    public void updateEntity(RoomBookingEntity entity) throws NoResultException;

    public List<RoomEntity> retrieveRoomNumber(RoomBookingEntity roomBooking);

    public List<RoomBookingEntity> retrieveTodayCheckIn();

    public List<RoomBookingEntity> retrieveTodayCheckOut();

    public List<RoomBookingEntity> retrieveAllRoomBookingEdit();
}
