/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.enumeration;

/**
 *
 * @author Nicholas Nah
 */
public enum RoomBookingStatusEnum {
 
    CHECKED_IN, //Customer Check in after staff assigns a room
    CHECKED_OUT,     //Customer Check out after staff un-assign a room
    RESERVED,   //Customer has paid and reserved the slot
    CANCELLED, //Customer cancelled booking
    AUTO, // customer use self service kiosk
    MANUAL // customer comes to FrontDesk to check in
}
