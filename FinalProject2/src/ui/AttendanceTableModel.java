/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Attendance;
/**
 *
 * @author User
 */
public class AttendanceTableModel extends AbstractTableModel {
    
    private List<Attendance> tasks;
    private final String[] COLUMN_NAMES = {"ATTENDANCE ID","NAMES",
        "DATE CREATED", "IN", "OUT","PROGRAM"};

    public AttendanceTableModel(){
        this.tasks = new ArrayList<>();
    }    
    @Override
    public int getRowCount() {
        return this.tasks.size();
    }
    @Override
    public int getColumnCount() {
        return this.COLUMN_NAMES.length;
    }        
    @Override
    public String getColumnName(int index) {
        return COLUMN_NAMES[index]; 
    }    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Attendance Attendance = tasks.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> Attendance.getAttendanceId();
            case 1 -> Attendance.getNames();
            case 2 -> Attendance.getDate();
            case 3 -> Attendance.getIn();
            case 4 -> Attendance.getOut();
            case 5 -> Attendance.getStatus();
            default -> null;
        };
    }
    
    public void setTasks(List<Attendance> tasks){
        this.tasks = tasks;
        fireTableDataChanged();
    }
    
    public String getAttendanceId(int rowIndex){
        return tasks.get(rowIndex).getAttendanceId();
    }
}
