/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import controller.AttendanceController;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.AttendanceManager;
import java.util.logging.Logger;
import java.util.logging.Level;
/**
 *
 * @author User
 */
public class Attendanced {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Attendanced.class.getName()).log(Level.SEVERE, "Error setting System Look and Feel", ex);
        }

        
        AttendanceManager AttendanceManager = new AttendanceManager();
        AttendanceManager.loadTasks();

        AttendanceController controller = new AttendanceController(AttendanceManager);
        
        java.awt.EventQueue.invokeLater(() -> {
            Attendances view = new Attendances(controller); 
            
            controller.setView(view);                                  
            view.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    AttendanceManager.saveTasks();
                    System.exit(0);
                }
            });
            view.setVisible(true);
        }); 
    }
}
