/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pl.polsl.lab.dcieslik.warcaby.view;

import pl.polsl.lab.dcieslik.warcaby.exceptions.InvalidNameException;
import pl.polsl.lab.dcieslik.warcaby.model.Player;

/**
 * Game menu window.
 *
 * @author Dawid Cieślik
 */
public class Menu extends javax.swing.JFrame {

    /**
     * White player.
     */
    private Player player1;

    /**
     * Black player.
     */
    private Player player2;

    /**
     * Game window.
     */
    private GameWindow window;

    /**
     * Creates new form Menu.
     */
    public Menu() {
        initComponents();
        setTitle("Warcaby - Menu");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(GameWindow.EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        this.player1 = new Player("Bialy");
        this.player2 = new Player("Czarny");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        start = new javax.swing.JButton();
        end = new javax.swing.JButton();
        exp = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        nameTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        start.setText("Rozpocznij grę");
        start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startActionPerformed(evt);
            }
        });

        end.setText("Zakończ");
        end.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endActionPerformed(evt);
            }
        });

        exp.setForeground(new java.awt.Color(255, 0, 0));
        exp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        exp.setText(" ");
        exp.setToolTipText("");

        nameTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Bialy", "Czarny"}
            },
            new String [] {
                "Gracz Biały", "Gracz Czarny"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        nameTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        nameTable.setToolTipText("Wpisz nazwy graczy");
        nameTable.setColumnSelectionAllowed(true);
        nameTable.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        nameTable.setName(""); // NOI18N
        nameTable.setRowSelectionAllowed(false);
        nameTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        nameTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        nameTable.setShowGrid(true);
        nameTable.setSurrendersFocusOnKeystroke(true);
        nameTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(nameTable);
        nameTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (nameTable.getColumnModel().getColumnCount() > 0) {
            nameTable.getColumnModel().getColumn(0).setCellEditor(null);
            nameTable.getColumnModel().getColumn(1).setResizable(false);
            nameTable.getColumnModel().getColumn(1).setCellEditor(null);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(start)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addComponent(end, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(exp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(exp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(start)
                    .addComponent(end))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Starts the game.
     *
     * @param evt action event.
     */
    private void startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startActionPerformed
        player1.setName((String) nameTable.getValueAt(0,0));
        player2.setName((String) nameTable.getValueAt(0,1));
        try {
            player1.validate(player2);
            window = new GameWindow(player1, player2);
            window.setVisible(true);
            dispose();
        } catch (InvalidNameException e) {
            exp.setText(e.getLocalizedMessage());
        }
    }//GEN-LAST:event_startActionPerformed

    /**
     * Closes the menu window.
     *
     * @param evt action event.
     */
    private void endActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endActionPerformed
        dispose();
    }//GEN-LAST:event_endActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton end;
    private javax.swing.JLabel exp;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable nameTable;
    private javax.swing.JButton start;
    // End of variables declaration//GEN-END:variables
}
