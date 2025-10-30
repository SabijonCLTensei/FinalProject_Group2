/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui;

import controller.AttendanceController;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import model.Attendance;
/**
 *
 * @author User
 */
public class Attendances extends javax.swing.JFrame {
    
    public Attendances(AttendanceController controller) {
       this.controller = controller;
       this.tableModel = new AttendanceTableModel();
                
       initComponents();
       setUpDefaultDisplay();
        
       setupListTable();
       setUpRowSelectionListener();        
        
       refreshAttendanceList(); 
    }
     
    private void setUpDefaultDisplay(){
        this.jLabel4.setVisible(false);
        this.jLabel5.setVisible(false);
        this.jTextField4status.setVisible(false);
        this.jTextField5date.setVisible(false);
        this.jTextField1names.setText("");
        this.jTextField2login.setText("");
        this.jTextField3logout.setText("");
        this.jTextField4status.setText("");
        this.jTextField5date.setText("");
        this.jButton2delete.setEnabled(false);
        this.saveMode = "ADD";
    }
    
    private void setupListTable() {
        jTable2listoftasks.setModel(tableModel);
        jTable2listoftasks.getTableHeader().setReorderingAllowed(false);
        jTable2listoftasks.setAutoCreateColumnsFromModel(true);
        jTable2listoftasks.createDefaultColumnsFromModel();
        setupTableColumnWidths();
    }
    
    public void refreshAttendanceList(){
        String savedSelectedId = this.selectedTaskId; 
        System.out.println("Selected Attendance id:" + savedSelectedId);
                
        List<Attendance> Attendances = controller.getAllTask();
        for (Attendance Attendance : Attendances) {
            System.out.println(Attendance);
        }
        this.tableModel.setTasks(Attendances);
        
        if (savedSelectedId != null) {
            int newModelRow = -1;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (savedSelectedId.equals(tableModel.getAttendanceId(i))) {
                    newModelRow = i;
                    break;
                }
            }
            if (newModelRow != -1) {
                int newTableRow = jTable2listoftasks.convertRowIndexToView(newModelRow);
                jTable2listoftasks.setRowSelectionInterval(newTableRow, newTableRow);                
            } else {
                this.selectedTaskId = null;
                setUpDefaultDisplay();
            }
        }
    }
    
    private void setupTableColumnWidths(){
        javax.swing.table.TableColumnModel columnModel = jTable2listoftasks.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(0).setMaxWidth(100);
        columnModel.getColumn(0).setMinWidth(100);

        columnModel.getColumn(1).setPreferredWidth(200);
        
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(2).setMaxWidth(100); 
        columnModel.getColumn(2).setMinWidth(100);
        
        columnModel.getColumn(3).setPreferredWidth(70);
        columnModel.getColumn(3).setMaxWidth(70); 
        columnModel.getColumn(3).setMinWidth(70);
    
        columnModel.getColumn(4).setPreferredWidth(50);
        columnModel.getColumn(4).setMaxWidth(70); 
        columnModel.getColumn(4).setMinWidth(50);
    
        jTable2listoftasks.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
    }
    
    private void setUpRowSelectionListener(){
        jTable2listoftasks.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = jTable2listoftasks.getSelectedRow();
                if (selectedRow != -1) {
                    int modelRow = jTable2listoftasks.convertRowIndexToModel(selectedRow);
                    String AttendanceId = this.tableModel.getAttendanceId(modelRow);
                    Attendance selectedTask = controller.getTaskDetails(AttendanceId);
                    displaySelectedTask(selectedTask);
                }
            } else {
                setUpDefaultDisplay();
            }
        });        
    }
    
    private void displaySelectedTask(Attendance Attendance){
        this.jTextField1names.setText(Attendance.getNames());
        this.jTextField2login.setText(Attendance.getIn());
        this.jTextField3logout.setText(Attendance.getOut());
        this.selectedTaskId = Attendance.getAttendanceId();
        this.jTextField4status.setText(Attendance.getStatus());
        this.jTextField5date.setText(Attendance.getDate());
        
        this.jLabel4.setVisible(true);
        this.jLabel5.setVisible(true);
        this.jTextField5date.setVisible(true);
        this.jTextField4status.setVisible(true);
        
        this.jButton2delete.setEnabled(true);
        this.saveMode = "UPDATE";
        System.out.println("SaveMode:" + saveMode);
    }
    
    private void addTask(){
        String desc = this.jTextField1names.getText();
        String in = this.jTextField2login.getText();
        String out = this.jTextField3logout.getText();
        String status = this.jTextField4status.getText();
        boolean success = false;
        if (this.saveMode.equalsIgnoreCase("ADD")) {
            if (desc.trim().isEmpty() || in.trim().isEmpty() || out.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "All Fields are required", "Add Attendance", 0);
                return;
            }
            controller.handleAddTask(desc, in, out);
            success = true;
            JOptionPane.showMessageDialog(this, "Attendance Successfully added", "Add Attendance", 1);
        } else if(this.saveMode.equalsIgnoreCase("UPDATE")){
            boolean res = controller.handleUpdateTask(this.selectedTaskId, desc, in, out, status);
            if (res) {
                JOptionPane.showMessageDialog(this, "Attendance Successfully Updated", "Update Attendance", 1);
                success = true;
            } else {
                JOptionPane.showMessageDialog(this, "An error occured while updating Attendance", "Update Attendance", 0);
            }
        }
        if (success) {
            this.jTable2listoftasks.clearSelection();
            setUpDefaultDisplay();
            this.selectedTaskId = null;
        }       
    }    
        
    private void deleteTask(){
        if (this.selectedTaskId != null) {
            int confirmDeletion = JOptionPane.showConfirmDialog(
                    this, 
                    "Are you sure you want to delete this Attendance? This cannot be undone.",
                    "Confirm Attendance Deletion", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (confirmDeletion == JOptionPane.YES_OPTION) {
                boolean success = controller.handleDeleteTask(this.selectedTaskId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Attendance Successfully Deleted", "Attendance Deletion", 1);
                    this.jTable2listoftasks.clearSelection();
                } else { 
                    JOptionPane.showMessageDialog(this, "An error occured while deleting the Attendance", "Attendance Deletion", 0);
                }
            } else {
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();
        jTextField1names = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2login = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3logout = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField4status = new javax.swing.JTextField();
        jButton1save = new javax.swing.JButton();
        jButton2delete = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField5date = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButtonRefresh = new javax.swing.JButton();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2listoftasks = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ATTENDANCE", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 24), new java.awt.Color(0, 0, 0))); // NOI18N

        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("NAMES");
        jLayeredPane1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, -1));

        jTextField1names.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1names.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1namesActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jTextField1names, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 28, 160, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("LOG IN");
        jLayeredPane1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 64, -1, -1));

        jTextField2login.setBackground(new java.awt.Color(255, 255, 255));
        jTextField2login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2loginActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jTextField2login, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 86, 160, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("LOG OUT");
        jLayeredPane1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 122, -1, -1));

        jTextField3logout.setBackground(new java.awt.Color(255, 255, 255));
        jTextField3logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3logoutActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jTextField3logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 144, 160, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("PROGRAM");
        jLayeredPane1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 180, -1, -1));

        jTextField4status.setBackground(new java.awt.Color(255, 255, 255));
        jTextField4status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4statusActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jTextField4status, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 202, 160, 30));

        jButton1save.setBackground(new java.awt.Color(255, 255, 255));
        jButton1save.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1save.setForeground(new java.awt.Color(0, 0, 0));
        jButton1save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/save.png"))); // NOI18N
        jButton1save.setText("SAVE");
        jButton1save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1saveActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton1save, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 150, -1));

        jButton2delete.setBackground(new java.awt.Color(255, 255, 255));
        jButton2delete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2delete.setForeground(new java.awt.Color(0, 0, 0));
        jButton2delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/delete.png"))); // NOI18N
        jButton2delete.setText("DELETE");
        jButton2delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2deleteActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton2delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 150, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("DATE");
        jLayeredPane1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 238, -1, -1));

        jTextField5date.setBackground(new java.awt.Color(255, 255, 255));
        jTextField5date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5dateActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jTextField5date, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 260, 160, 30));
        jLayeredPane1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 618, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/tulipppp.png"))); // NOI18N
        jLayeredPane1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 377, -1, 241));
        jLayeredPane1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, -1, -1));

        jButtonRefresh.setBackground(new java.awt.Color(255, 255, 255));
        jButtonRefresh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonRefresh.setForeground(new java.awt.Color(0, 0, 0));
        jButtonRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/refreshs.png"))); // NOI18N
        jButtonRefresh.setText("REFRESH");
        jButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButtonRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jTable2listoftasks.setBackground(new java.awt.Color(255, 255, 255));
        jTable2listoftasks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable2listoftasks);

        jLayeredPane2.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLayeredPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLayeredPane1)
                    .addComponent(jLayeredPane2))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1namesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1namesActionPerformed
    }//GEN-LAST:event_jTextField1namesActionPerformed

    private void jTextField2loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2loginActionPerformed
    }//GEN-LAST:event_jTextField2loginActionPerformed

    private void jTextField3logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3logoutActionPerformed
    }//GEN-LAST:event_jTextField3logoutActionPerformed

    private void jTextField4statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4statusActionPerformed
    }//GEN-LAST:event_jTextField4statusActionPerformed

    private void jTextField5dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5dateActionPerformed
    }//GEN-LAST:event_jTextField5dateActionPerformed

    private void jButton1saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1saveActionPerformed
        System.out.println("Button Clicked");
        addTask();
    }//GEN-LAST:event_jButton1saveActionPerformed

    private void jButton2deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2deleteActionPerformed
        System.out.println("Delete is clicked!");
        deleteTask();
    }//GEN-LAST:event_jButton2deleteActionPerformed

    private void jButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshActionPerformed
        System.out.println("Refresh is clicked!");
        setUpDefaultDisplay();
    }//GEN-LAST:event_jButtonRefreshActionPerformed

    private AttendanceController controller;
    private AttendanceTableModel tableModel;
    private String selectedTaskId;
    private String saveMode;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1save;
    private javax.swing.JButton jButton2delete;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2listoftasks;
    private javax.swing.JTextField jTextField1names;
    private javax.swing.JTextField jTextField2login;
    private javax.swing.JTextField jTextField3logout;
    private javax.swing.JTextField jTextField4status;
    private javax.swing.JTextField jTextField5date;
    // End of variables declaration//GEN-END:variables
}
