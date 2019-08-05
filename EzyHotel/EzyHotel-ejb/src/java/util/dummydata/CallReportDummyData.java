/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dummydata;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.jstl.sql.Result;

/**
 *
 * @author Wai Kit
 */
public class CallReportDummyData {

    public static void main(String[] args) throws ClassNotFoundException {
        String username = "is4103";
        String password = "pt02";
        String connectionUrl = "jdbc:derby://localhost:1527/ezyhotel";
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");

        PreparedStatement ps = null;
        Connection con = null;
        Result rs = null;

        String callFrom;
        String city;
        String strCallDate;
        String telephoneNum;
        String regarding;
        String personContacted;
        String title;
        String remarks;
        String followup;

        try {
            BufferedReader br = new BufferedReader(new FileReader("dummyData/CallReportDummyData.txt"));
            con = DriverManager.getConnection(connectionUrl, username, password);
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            String line = null;
            while ((line = br.readLine()) != null) {
                String temp[] = line.split(",");
                callFrom = temp[0];
                city = temp[1];
                strCallDate = temp[2];
                Date callDate = ymdFormat.parse(strCallDate);
                telephoneNum = temp[3];
                regarding = temp[4];
                personContacted = temp[5];
                title = temp[6];
                remarks = temp[7];
                followup = temp[8];
                
                System.out.println(callFrom + "," + city + "," + strCallDate + "," + telephoneNum + "," + regarding + "," + personContacted + "," + title + "," + remarks + "," + followup);

                String sql = "INSERT INTO CallReportEntity (callFrom, city, callDate, telephoneNum, regarding, personContacted, title, remarks, followup) values ('"
                        + callFrom + "','" + city + "','" + ymdFormat.format(callDate) + "','" + telephoneNum + "','" + regarding + "','" + personContacted + "','" + title + "','" + remarks + "','" + followup + "')";
                
                ps = con.prepareStatement(sql);
                ps.executeUpdate();
            }

            br.close();
            con.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
