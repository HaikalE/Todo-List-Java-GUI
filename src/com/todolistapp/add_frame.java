/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.todolistapp;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author LENOVO
 */
public class add_frame extends javax.swing.JFrame {

    /**
     * Creates new form add_frame
     */
    private String title;
    private String date;
    private int value,total;
    DefaultTableModel model = new DefaultTableModel();
    public add_frame() {
        initComponents();
        title = "";
        date = "";
    }
    public add_frame(String title, String date) {
        initComponents();
        this.title = title;
        this.date = date;
        //
        value=0;
        total=getChecklistSizeByTitle(title);
    label3.setText(String.valueOf(total));
//
//
        model.addColumn("Task");
        //model.addColumn("Checklist");
        jTable2.setModel(model);
        
        JsonConfiguration jsonConfig = new JsonConfiguration(jTable2, "C:\\Users\\LENOVO\\Documents\\Folder_tugas_UiTM\\OOP\\tugas13\\data\\put.json");
        jsonConfig.fillTableWithSpecificData(title);
        addCheckboxColumn(model);
        //
        label2.setVisible(false);
        textField1.setVisible(false);
        jLabel4.setText("ADD NEW TASK");
// Display the passed title and date in the UI
        jLabel1.setText(title);
        // ...
        jLabel3.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            backPage();
        }
    });
    }
    public int getChecklistSizeByTitle(String title) {
    // Read existing data from JSON file
    JsonConfiguration jsonConfig = new JsonConfiguration(jTable2, "C:\\Users\\LENOVO\\Documents\\Folder_tugas_UiTM\\OOP\\tugas13\\data\\put.json");
    List<Map<String, Object>> dataList = jsonConfig.readDataFromJson();
    
    // Iterate over each object in the data list
    for (Map<String, Object> data : dataList) {
        String dataTitle = (String) data.get("Title");

        // Check if the title matches the provided title
        if (dataTitle.equals(title)) {
            // Get the "Checklist" array for the matching title
            List<Boolean> checklist = (List<Boolean>) data.get("Checklist");

            // Return the size of the "Checklist" array
            return checklist.size();
        }
    }
    // If no matching title is found, return 0
    return 0;
}
    private void backPage(){
        dashboard newForm = new dashboard();
    // Set the location of the newForm
    int parentX = getX();
    int parentY = getY();
    int parentWidth = getWidth();
    int parentHeight = getHeight();
    int newX = parentX + (parentWidth - newForm.getWidth()) / 2;
    int newY = parentY + (parentHeight - newForm.getHeight()) / 2;
    newForm.setLocation(newX, newY);
    if (getExtendedState() == JFrame.MAXIMIZED_BOTH) {
        newForm.setExtendedState(JFrame.MAXIMIZED_BOTH);
    } else {
        newForm.setSize(getSize());
    }
    Container contentPane = getContentPane();

    // Check if the content pane is a JDesktopPane
    if (contentPane instanceof JDesktopPane) {
        JDesktopPane desktopPane = (JDesktopPane) contentPane;
        // Add the newForm to the desktop pane
        desktopPane.add(newForm);
    }
    newForm.setVisible(true);
    dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        LabelEdit = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        button1 = new java.awt.Button();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        label1 = new java.awt.Label();
        label3 = new java.awt.Label();
        textField1 = new java.awt.TextField();
        label2 = new java.awt.Label();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(573, 475));

        jPanel2.setBackground(new java.awt.Color(2, 84, 100));

        jLabel2.setForeground(new java.awt.Color(248, 241, 241));
        jLabel2.setText("TODO LIST APP");

        LabelEdit.setForeground(new java.awt.Color(248, 241, 241));
        LabelEdit.setText("EDIT");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LabelEdit)
                .addGap(22, 22, 22))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(LabelEdit))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(2, 84, 100));

        jLabel4.setForeground(new java.awt.Color(248, 241, 241));
        jLabel4.setText("ADD NEW TASK");

        button1.setLabel("Add");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(248, 241, 241));
        jLabel3.setText("BACK");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jLabel1.setText("jLabel1");

        label1.setText("Percentage : ");

        label3.setText("label3");

        textField1.setText("textField1");

        label2.setText("NEW TASK :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        if ("Add".equals(button1.getLabel())){
            label2.setVisible(true);
        textField1.setVisible(true);
        button1.setLabel("SUBMIT");
        jLabel4.setText("SUBMIT NEW TASK");
        }
        else if ("SUBMIT".equals(button1.getLabel())){
            JsonConfiguration jsonConfig = new JsonConfiguration(jTable2, "C:\\Users\\LENOVO\\Documents\\Folder_tugas_UiTM\\OOP\\tugas13\\data\\put.json");

        // Set the file path for the JSON file

        // Call the addTask method to add a new task
        String newTask = textField1.getText();
        jsonConfig.addTask(title, newTask);
        
        label2.setVisible(false);
        textField1.setVisible(false);
        button1.setLabel("Add");
        jLabel4.setText("ADD NEW TASK");
        total++;
        label3.setText(String.valueOf(total));
        // Verify that the data was added to the JSON file
        //jsonConfig.fillTableWithData();
        }
        
    }//GEN-LAST:event_button1ActionPerformed
    private void addCheckboxColumn(DefaultTableModel model) {
    try {
        JsonConfiguration jsonConfig = new JsonConfiguration(jTable2, "C:\\Users\\LENOVO\\Documents\\Folder_tugas_UiTM\\OOP\\tugas13\\data\\put.json");
        jTable2.setEnabled(true);
        model.addColumn("Checkbox");
        int columnIndex = model.getColumnCount() - 1;

        jTable2.getColumnModel().getColumn(columnIndex).setCellRenderer(new DefaultTableCellRenderer() {
            private final JCheckBox checkBox = new JCheckBox();

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof Boolean) {
                    checkBox.setSelected((boolean) value);
                }
                return checkBox;
            }
        });

        jTable2.getColumnModel().getColumn(columnIndex).setCellEditor(new DefaultCellEditor(new JCheckBox()));

        // Update the model with checkbox values from JSON data
List<Map<String, Object>> dataList = jsonConfig.readDataFromJson();
System.out.println("Data List: " + dataList);

for (int row = 0; row < dataList.size(); row++) {
    Map<String, Object> data = dataList.get(row);
    String titles = (String) data.get("Title");
    List<Boolean> checklist = (List<Boolean>) data.get("Checklist");

    if (titles.equals(title) && checklist != null) {
        for (int i = 0; i < checklist.size(); i++) {
            boolean checkboxValue = checklist.get(i);
            model.setValueAt(checkboxValue, i, columnIndex);
            System.out.print("Checkbox set to: " + checkboxValue + " in row: " + row);
            if (checkboxValue){
                
                System.out.println("BENAR");
                value++;
            }else if (!checkboxValue){
                
                System.out.println("SALAH");
            }
        }
    }/* 
    else {
        boolean checkboxValue = false;
        model.setValueAt(checkboxValue, row, columnIndex);
        System.out.println("Checkbox set to: " + checkboxValue + " in row: " + row);
    }*/
}



        LabelEdit.setText("CANCEL");
    } catch (Exception e) {
        System.out.println("Exception caught: " + e.getMessage());
    }
}
















    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(add_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(add_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(add_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(add_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new add_frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelEdit;
    private java.awt.Button button1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.TextField textField1;
    // End of variables declaration//GEN-END:variables
}