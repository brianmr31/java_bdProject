
package bmrbd;

import java.util.logging.Level;
import java.util.logging.Logger;

public class bdServerGui extends javax.swing.JFrame {
    /** Creates new form bdServerGui */
    public bdServerGui() {
        initComponents();
        Pro = new SProcess();
        Pro.SetUp();
        p = new Thread(new Runnable(){
            @Override
            public void run() {
                while(true){
                    try {
                       Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(bdServerGui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println(Pro.running);
                    if(Pro.running != false){
                        System.out.println("=> Pesan diterima ");
                        Pro.recvCmd();
                        int a = Integer.parseInt(Pro.Pesan);
                        if(a == 0){
                            Pro.recvCmd();
                            Area.append("[ Pesannya ]"+Pro.Pesan+"\n");
                            LogArea.append("[ Pesannya ]"+Pro.Pesan+"\n");
                        }else if(a == 1){
                            Pro.recvCmd();
                            Pro.execCMD();
                            Pro.processOutputCmd();
                            Pro.setCmd("9");
                            Pro.sendCmd();
                            Pro.sendCmdOut();
                            Area.append("[ Commandnya ]"+Pro.cmdOut+"\n");
                            LogArea.append("[ Commandnya ]"+Pro.cmdOut+"\n");
                            Pro.showCmd();
                        }else if(a == 2){
                            Pro.recvCmd();
                            Pro.recvBigFile(Pro.Pesan);
                            Area.append("[ Transfernya ]"+Pro.Pesan+"\n");
                            LogArea.append("[ Transfernya ]"+Pro.Pesan+"\n");
                        }
                        
                    }
                }
            }
        });
        
        p.setDaemon(true);
        p.start();
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        Gport = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        status = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        Cbox = new javax.swing.JComboBox();
        command = new javax.swing.JTextField();
        send = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Area = new javax.swing.JTextArea();
        status1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        LogArea = new javax.swing.JTextArea();
        jPanel7 = new javax.swing.JPanel();
        save = new javax.swing.JButton();
        status2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("bdJav Server");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel1.setLayout(new java.awt.GridLayout(0, 1));

        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Port ");
        jPanel4.add(jLabel2);

        Gport.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Gport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GportActionPerformed(evt);
            }
        });
        jPanel4.add(Gport);

        jPanel1.add(jPanel4);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Server"));
        jPanel5.setLayout(new java.awt.GridLayout(3, 0, 0, 1));

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton1);

        jButton2.setText("Stop");
        jButton2.setToolTipText("klik it to stop listen port");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton2);

        status.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        status.setText("Status");
        status.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseMoved(evt);
            }
        });
        jPanel5.add(status);

        jPanel1.add(jPanel5);

        jTabbedPane1.addTab("Conf", jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        Cbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pesan", "Transfer" }));
        Cbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CboxActionPerformed(evt);
            }
        });
        jPanel6.add(Cbox);

        command.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commandActionPerformed(evt);
            }
        });
        jPanel6.add(command);

        send.setText("send");
        send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendActionPerformed(evt);
            }
        });
        jPanel6.add(send);

        jPanel2.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        Area.setEditable(false);
        Area.setColumns(20);
        Area.setRows(5);
        Area.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                AreaMouseMoved(evt);
            }
        });
        jScrollPane1.setViewportView(Area);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        status1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        status1.setText("status");
        jPanel2.add(status1, java.awt.BorderLayout.PAGE_END);

        jTabbedPane1.addTab("Main", jPanel2);

        jPanel3.setLayout(new java.awt.BorderLayout());

        LogArea.setEditable(false);
        LogArea.setColumns(20);
        LogArea.setRows(5);
        jScrollPane2.setViewportView(LogArea);

        jPanel3.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel7.setLayout(new java.awt.GridLayout(2, 1));

        save.setText("Save");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        jPanel7.add(save);

        status2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        status2.setText("Status");
        jPanel7.add(status2);

        jPanel3.add(jPanel7, java.awt.BorderLayout.PAGE_END);

        jTabbedPane1.addTab("Log", jPanel3);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
   if(Pro.isAlive() == false){
        int port = Integer.parseInt(Gport.getText());
        Pro.setPort(port);
        Pro.Process();
        Pro.start();
        nilai = true ;
        status.setText("Wait Client");
   }
}//GEN-LAST:event_jButton1ActionPerformed

private void GportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GportActionPerformed
    
}//GEN-LAST:event_GportActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    if(Pro.Sc != null || Pro.SServer != null ){
        //System.out.println("akan Ditutup");
        Pro.closeServer();
        //this.Pro.stop();
        status.setText(" Stop , already to connect");
        Pro.SServer = null ;
        Pro.Sc = null ;
    }
}//GEN-LAST:event_jButton2ActionPerformed

private void jTabbedPane1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseMoved
    if(Pro.Sc != null){
            status.setText("Connected");
            Pro.stop();
        }
}//GEN-LAST:event_jTabbedPane1MouseMoved

private void CboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CboxActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_CboxActionPerformed

private void sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendActionPerformed
    
    int sel = Cbox.getSelectedIndex();
    this.Pro.setCmd(String.valueOf(sel));
    this.Pro.sendCmd();
    String S= command.getText();
    if(command.getText().equals("")||command.getText().equals(" ")||command.getText().equals("   ")){
        status1.setText("Input Null");
    }else{
        if(sel == 0 ){
            Pro.setCmd(S);
            Pro.sendCmd();
            Area.append("[ Pesan ]"+S+"\n");
            LogArea.append("[ Pesan ]"+S+"\n");
            status1.setText("sukses");
        }else if(sel == 1){
            Pro.setCmd(S);
            Pro.sendCmd();
            Pro.sendBigFile();
            Area.append("[ Transfer ]"+S+"\n");
            LogArea.append("[ Transfer ]"+S+"\n");
            status1.setText("sukses");
        }
    }
    
}//GEN-LAST:event_sendActionPerformed

private void commandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commandActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_commandActionPerformed

private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
    save.setText("sukses");
}//GEN-LAST:event_saveActionPerformed

    private void AreaMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AreaMouseMoved
        //Pro.recvCmd();
        //Area.append("[ Transfer ]"+Pro.Pesan+"\n");
        //LogArea.append("[ Transfer ]"+Pro.Pesan+"\n");
    }//GEN-LAST:event_AreaMouseMoved

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
            java.util.logging.Logger.getLogger(bdServerGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(bdServerGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(bdServerGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(bdServerGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new bdServerGui().setVisible(true);
                
            }
        });
        
    }
    private final SProcess Pro  ;
    private Thread p ;
    private Boolean nilai = false ;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Area;
    private javax.swing.JComboBox Cbox;
    private javax.swing.JTextField Gport;
    private javax.swing.JTextArea LogArea;
    private javax.swing.JTextField command;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton save;
    private javax.swing.JButton send;
    private javax.swing.JLabel status;
    private javax.swing.JLabel status1;
    private javax.swing.JLabel status2;
    // End of variables declaration//GEN-END:variables
}
