/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author User
 */
public class Attendance implements Serializable {
    private String attendanceId;
    private String names;
    private String date;
    private String in;
    private String out;
    private String status;
    private static final long serialVersionUID = 1L;
    
    public Attendance() {        
        this.status = ">>Insert<<";
        setDate();
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String AttendanceId) {
        this.attendanceId = AttendanceId;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getDate() {
        return date;
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        this.date = now.toString();
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Attendance{" + "attendanceId=" + attendanceId + ", names=" + names + ", date=" + date + ", in=" + in + ", out=" + out + ", status=" + status + '}';
    }
     
     
}
