/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import model.Attendance;
import model.AttendanceManager;
import ui.Attendances;
/**
 *
 * @author User
 */
public class AttendanceController {
    private final AttendanceManager model;
    private Attendances view;

    public AttendanceController(AttendanceManager model) {
        this.model = model;
    }

    public void setView(Attendances view) {
        this.view = view;
    }

    public void handleAddTask(String names, String in, String out) {
        if (names.trim().isEmpty() || in.trim().isEmpty() || out.trim().isEmpty()) {
            return;
        }
        Attendance attendance = new Attendance();
        attendance.setNames(names);
        attendance.setIn(in);
        attendance.setOut(out);
        this.model.addTask(attendance);
        this.model.saveTasks();
        if (this.view != null) {
            this.view.refreshAttendanceList();
        }
    }

    public boolean handleDeleteTask(String id) {
        Attendance attendance = this.model.deleteTask(id);
        if (attendance != null) {
            this.model.saveTasks();
            System.out.println("Deleted Attendance: " + attendance.getNames());
            if (this.view != null) {
                this.view.refreshAttendanceList();
            }
            return true;
        } else {
            System.out.println("Attendance not found");
            return false;
        }
    }

    public boolean handleUpdateTask(String id, String names, String in, String out, String status) {
        if (names.trim().isEmpty() || in.trim().isEmpty() || out.trim().isEmpty()
                || status.trim().isEmpty()) {
            return false;
        }
        Attendance attendance = this.model.findTask(id);
        if (attendance == null) {
            return false;
        }
        attendance.setNames(names);
        attendance.setIn(in);
        attendance.setOut(out);
        attendance.setStatus(status);
        this.model.saveTasks();
        if (this.view != null) {
            this.view.refreshAttendanceList();
        }
        return true;
    }

    public List<Attendance> getAllTask() {
        Collection<Attendance> tasksFromMap = this.model.findAll().values();
        ArrayList<Attendance> taskList = new ArrayList<>(tasksFromMap);
        return taskList;
    }

    public Attendance getTaskDetails(String id) {
        return this.model.findTask(id);
    }
}
