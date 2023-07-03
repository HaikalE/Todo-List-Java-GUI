/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.todolistapp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
//import java.awt.List;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import java.util.ArrayList;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 *
 * @author LENOVO
 */

public class dashboard extends javax.swing.JFrame {
    private Object[][] data;
    private final int rowCount = 100;
    DefaultTableModel listModel = new DefaultTableModel();
    String filePath = "C:\\Users\\LENOVO\\Documents\\Folder_tugas_UiTM\\OOP\\tugas13\\data\\put.json";
    /**
     * Creates new form dashboard
     */
    public dashboard() {
        initComponents();
        textField1.setVisible(false);
        textField1.setText("");
    // Set the column names for the list
    String[] listColumnNames = {"Title", "Date"};
    listModel.setColumnIdentifiers(listColumnNames);
    /*REV1
    // Generate data dynamically for the list
    Object[][] data = new Object[rowCount][2];
    for (int i = 0; i < rowCount; i++) {
        data[i][0] = "Title" + (i + 1);
        data[i][1] = "Date" + (i + 1);
    }
    
    // Populate the list table with data
    for (Object[] rowData : data) {
        listModel.addRow(rowData);
    }*/
    
    // Create an instance of JsonConfiguration
    
JsonConfiguration jsonConfigInstance = new JsonConfiguration(jTable1, "C:\\Users\\LENOVO\\Documents\\Folder_tugas_UiTM\\OOP\\tugas13\\data\\put.json");

// Read data from JSON file
List<Map<String, Object>> dataList = jsonConfigInstance.readDataFromJson();

// Populate the list table with data
//DefaultTableModel listModel = new DefaultTableModel();
//listModel.addColumn("Title");
//listModel.addColumn("Date");

for (Map<String, Object> data : dataList) {
    String title = (String) data.get("Title");
    String date = (String) data.get("Date");
    Object[] rowData = {title, date};
    listModel.addRow(rowData);
}
jTable1.setModel(listModel);

    // Set the table model for the list table
    //jTable1.setModel(listModel);
    jTable1.setEnabled(false); // Make the table unselectable

        jTable1.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
    if (e.getClickCount() == 2) {
        int row = jTable1.rowAtPoint(e.getPoint());
        int column = jTable1.columnAtPoint(e.getPoint());
        if (row >= 0 && column >= 0) {
            TableModel model = jTable1.getModel();
            Object title = model.getValueAt(row, 0);
            Object date = model.getValueAt(row, 1);
            System.out.println("Title: " + title + ", Date: " + date);
            /*
            add_frame newForm = new add_frame((String) title, (String) date);
            newForm.setVisible(true);
            */
            openNewForm((String) title, (String) date);
        }
    }
}
        
});
//pas
    // Set the cell renderer for the "Edit" column to display buttons
    /*
    JsonConfiguration jsonConfig = new JsonConfiguration(jTable1, filePath);
    jsonConfig.saveDataToJson();*/
    
    LabelEdit.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (!textField1.isVisible()) {addCheckboxColumn(listModel);}
            else {
                textField1.setVisible(false);
                JsonConfiguration jsonConfig = new JsonConfiguration(jTable1, "C:\\Users\\LENOVO\\Documents\\Folder_tugas_UiTM\\OOP\\tugas13\\data\\put.json");
                jsonConfig.fillTableWithData();
                LabelEdit.setText("EDIT");
                textField1.setText("");
            }
        }
    });
    jLabel2.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if ("ALL".equals(jLabel2.getText())) {selectAllCheckboxes();}
            else {
                //searchAndDisplayResults();
                textField1.setVisible(true);
                LabelEdit.setText("CANCEL");
                if (!"".equals(textField1.getText())) {searchAndDisplayResults(textField1.getText());}
            }
        }
    });
    }
    public void refTable(){
        JsonConfiguration jsonConfigu = new JsonConfiguration(jTable1, "C:\\Users\\LENOVO\\Documents\\Folder_tugas_UiTM\\OOP\\tugas13\\data\\put.json");
        jsonConfigu.fillTableWithData();
    }
    private void searchAndDisplayResults(String searchValue) {
    // Read data from JSON file
// Read data from JSON file
JsonConfiguration jsonConfig = new JsonConfiguration(jTable1, "C:\\Users\\LENOVO\\Documents\\Folder_tugas_UiTM\\OOP\\tugas13\\data\\put.json");
List<Map<String, Object>> dataList = jsonConfig.readDataFromJson();

// Filter the data based on the search value
List<Map<String, Object>> filteredData = new ArrayList<>();
for (Map<String, Object> data : dataList) {
    String title = (String) data.get("Title");
    String date = (String) data.get("Date");
    if (title.toLowerCase().contains(searchValue.toLowerCase()) || date.toLowerCase().contains(searchValue.toLowerCase())) {
        filteredData.add(data);
    }
}

// Display the filtered data in the table
DefaultTableModel filteredModel = new DefaultTableModel();
filteredModel.addColumn("Title");
filteredModel.addColumn("Date");
if (!filteredData.isEmpty()) {
    for (Map<String, Object> data : filteredData) {
        String title = (String) data.get("Title");
        String date = (String) data.get("Date");
        filteredModel.addRow(new Object[]{title, date});
    }
} else {
    // If no data is found, display a message or perform any desired action
    JOptionPane.showMessageDialog(null, "No matching data found.");
}
jTable1.setModel(filteredModel);




}
    private void selectAllCheckboxes() {
    int columnIndex = jTable1.getColumnCount() - 1;
    for (int row = 0; row < jTable1.getRowCount(); row++) {
        jTable1.setValueAt(true, row, columnIndex);
    }
    }
    private void addCheckboxColumn(DefaultTableModel model) {
        if ("EDIT".equals(LabelEdit.getText()) && jTable1.getColumnCount() == 2) {
            jTable1.setEnabled(true);
        model.addColumn("Checkbox");
        int columnIndex = model.getColumnCount() - 1;
        button1.setLabel("Delete");
        jLabel4.setText("DELETE THIS ?");
        jLabel2.setText("ALL");
        jTable1.getColumnModel().getColumn(columnIndex).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setSelected(value != null && (boolean) value);
                return checkBox;
            }
        });

        jTable1.getColumnModel().getColumn(columnIndex).setCellEditor(new DefaultCellEditor(new JCheckBox()));
        LabelEdit.setText("CANCEL");
        }
        else {
            jTable1.setEnabled(false);
            if (jTable1.getColumnCount() > 2) {
        int lastColumnIndex = jTable1.getColumnCount() - 1;
        jTable1.removeColumn(jTable1.getColumnModel().getColumn(lastColumnIndex));
        model.setColumnCount(model.getColumnCount() - 1);
        LabelEdit.setText("EDIT");
        button1.setLabel("Add");
        jLabel4.setText("ADD NEW LIST");
        jLabel2.setText("SEARCH");
    }
            else {
                System.out.println("Condition not met. No column removed."+jTable1.getColumnCount());
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

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        LabelEdit = new javax.swing.JLabel();
        textField1 = new java.awt.TextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        button1 = new java.awt.Button();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(2, 84, 100));

        jLabel1.setForeground(new java.awt.Color(248, 241, 241));
        jLabel1.setText("TODO LIST APP");

        jLabel2.setForeground(new java.awt.Color(248, 241, 241));
        jLabel2.setText("SEARCH");

        LabelEdit.setForeground(new java.awt.Color(248, 241, 241));
        LabelEdit.setText("EDIT");

        textField1.setText("textField1");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel2)
                .addGap(63, 63, 63)
                .addComponent(LabelEdit)
                .addGap(22, 22, 22))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)
                        .addComponent(LabelEdit)))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(2, 84, 100));

        jLabel4.setForeground(new java.awt.Color(248, 241, 241));
        jLabel4.setText("ADD NEW LIST");

        button1.setLabel("Add");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 240, Short.MAX_VALUE)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
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

    private void collectSelectedRows() {
    ArrayList<Integer> selectedRows = new ArrayList<>();
    System.out.println(selectedRows);
    int columnIndex = 2; // Adjust the column index to match the checkbox column

    // Find the selected rows
    for (int row = jTable1.getRowCount() - 1; row >= 0; row--) {
        Boolean value = (Boolean) jTable1.getValueAt(row, columnIndex);
        if (value != null && value) {
            selectedRows.add(row);
        }
    }

    System.out.println("Selected rows: " + selectedRows);

    // Delete the rows
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    for (int i = selectedRows.size() - 1; i >= 0; i--) {
        int rowToDelete = selectedRows.get(i);
        model.removeRow(rowToDelete);
    }

    System.out.println("Deleted rows: " + selectedRows);

    // Update the JSON file after deleting the rows
    JsonConfiguration jsonConfig = new JsonConfiguration(jTable1, "C:\\Users\\LENOVO\\Documents\\Folder_tugas_UiTM\\OOP\\tugas13\\data\\put.json");
    jsonConfig.updateJsonFile();

    // Refresh the table data from the updated JSON file
    jsonConfig.refreshTableData();
}



    private void openNewForm(String title, String date) {
    add_frame newForm = new add_frame(title, date);
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
    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        if ("Add".equals(button1.getLabel())) {
            NewJDialog dialog = new NewJDialog(this, true); // Create an instance of the dialog form
            dialog.setLocationRelativeTo(this); // Set the dialog form's location relative to the main form
            dialog.setVisible(true);
                  }
        else if ("Delete".equals(button1.getLabel())) {
            //delete
            collectSelectedRows();
        }
    }//GEN-LAST:event_button1ActionPerformed
    
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
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dashboard().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelEdit;
    private java.awt.Button button1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private java.awt.TextField textField1;
    // End of variables declaration//GEN-END:variables
}
