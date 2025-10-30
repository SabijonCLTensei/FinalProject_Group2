/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import util.IdGenerator;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author User
 */
public class AttendanceManager {
    private HashMap<String, Attendance> tasks;
    private final String FILE_PATH = "Attendance.ser";

    public AttendanceManager() {
        tasks = new HashMap();
    }
    public void addTask(Attendance Attendance) {
        
        String s = Attendance.getDate().substring(0, 4);
      
        String id = IdGenerator.generateId(Integer.parseInt(s));
        
        if (tasks != null) {
            while (tasks.containsKey(id)) {
                id = IdGenerator.generateId(Integer.parseInt(s));
            }
        }
        
        Attendance.setAttendanceId(id);
        tasks.put(id, Attendance);
    }
    public Attendance deleteTask(String id){
        return tasks.remove(id);
    }
    
    public Attendance findTask(String id){
        return tasks.get(id);
    }
    
    public HashMap<String, Attendance> findAll(){
        return tasks;
    }
    
    public void setTask(Attendance attendance){
        tasks.replace(attendance.getAttendanceId(), attendance);
    }
    
    public void loadTasks() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(FILE_PATH);
            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                Object readObj = ois.readObject();
                this.tasks = (HashMap<String, Attendance>) readObj;
            }
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            Logger.getLogger(AttendanceManager.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public int saveTasks() {
        FileOutputStream fos = null;
        int errorCode = 0;
        try {
            fos = new FileOutputStream(this.FILE_PATH);                
            try ( ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(tasks);
            }
            fos.close();
        } catch (IOException e) {
            errorCode = 1;
            Logger.getLogger(AttendanceManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return errorCode;
    }
}
