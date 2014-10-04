package bmrdbclient;

import java.util.logging.Level;
import java.util.logging.Logger;

public class bdClientGui extends javax.swing.JFrame {
    
    public bdClientGui() {
        initComponents();
        Pro = new SProcess();
        p = new Thread(new Runnable(){

            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(bdClientGui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("=====");
                    if(Pro.client != null ){
                        System.out.println("Tunggu pesan");
                        Pro.recvCmd();
                        Area.append(Pro.Pesan);
                    }
                }
            }
            
        });
        p.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Main = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        Host = new javax.swing.JLabel();
        THost = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        Port = new javax.swing.JLabel();
        TPort = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        Connect = new javax.swing.JButton();
        Disconnect = new javax.swing.JButton();
        status = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        CBox = new javax.swing.JComboBox();
        TField = new javax.swing.JTextField();
        Send = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Area = new javax.swing.JTextArea();
        status1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("bdjava Client");

        jPanel1.setLayout(new java.awt.GridLayout(2, 1));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Setting Connect"));
        jPanel3.setLayout(new java.awt.GridLayout(2, 1));

        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        Host.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Host.setText("Host : ");
        jPanel5.add(Host);
        jPanel5.add(THost);

        jPanel3.add(jPanel5);

        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        Port.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Port.setText("Port  : ");
        jPanel6.add(Port);

        TPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TPortActionPerformed(evt);
            }
        });
        jPanel6.add(TPort);

        jPanel3.add(jPanel6);

        jPanel1.add(jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Connect ?"));
        jPanel4.setLayout(new java.awt.GridLayout(3, 1));

        Connect.setText("Connect");
        Connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnectActionPerformed(evt);
            }
        });
        jPanel4.add(Connect);

        Disconnect.setText("Disconnect");
        Disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DisconnectActionPerformed(evt);
            }
        });
        jPanel4.add(Disconnect);

        status.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        status.setText("Status");
        jPanel4.add(status);

        jPanel1.add(jPanel4);

        Main.addTab("Conf", jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        CBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pesan", "Command", "Transfer" }));
        jPanel7.add(CBox);

        TField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFieldActionPerformed(evt);
            }
        });
        jPanel7.add(TField);

        Send.setText("Send");
        Send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendActionPerformed(evt);
            }
        });
        jPanel7.add(Send);

        jPanel2.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseMoved(evt);
            }
        });

        Area.setEditable(false);
        Area.setColumns(20);
        Area.setRows(5);
        jScrollPane1.setViewportView(Area);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        status1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        status1.setText("Status");
        jPanel2.add(status1, java.awt.BorderLayout.PAGE_END);

        Main.addTab("Main", jPanel2);

        getContentPane().add(Main, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void TPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TPortActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_TPortActionPerformed

private void SendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendActionPerformed
    int a = CBox.getSelectedIndex();
    this.Pro.setCmd(String.valueOf(a));
    this.Pro.sendCmd();
    String b = TField.getText();
    if(TField.getText().equals("")||TField.getText().equals(" ")||TField.getText().equals("   ")){
        status1.setText("Input Null");
    }else{
        if(a == 0 ){
            this.Pro.setCmd(b);
            this.Pro.sendCmd();
            Area.append("[ Pesan ]"+b+"\n");
            status1.setText("sukses");
        }else if(a == 1){
            this.Pro.setCmd(b);
            this.Pro.sendCmd();
            Area.append("[ Command ]"+b+"\n");
            status1.setText("sukses");
        }else if(a == 2){
            this.Pro.setCmd(b);
            this.Pro.sendCmd();
            this.Pro.sendBigFile();
            Area.append("[ Transfer ]"+b+"\n");
            status1.setText("sukses");
        }
    }
}//GEN-LAST:event_SendActionPerformed

private void TFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFieldActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_TFieldActionPerformed

    private void ConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConnectActionPerformed
        String host = THost.getText();
        String port = TPort.getText();
        if(host != null && port != null){
            Pro.setHost(host);
            Pro.setPort(Integer.parseInt(port));
            Pro.Process(host, Integer.parseInt(port));
            Pro.start();
        }
        status.setText("connected");
    }//GEN-LAST:event_ConnectActionPerformed

    private void DisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DisconnectActionPerformed
        if(this.Pro.client !=  null){
            this.Pro.closeSocket();
            //System.out.println("AA");
            status.setText(" Stop , already to connect");
        }
    }//GEN-LAST:event_DisconnectActionPerformed

    private void jScrollPane1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseMoved
        //this.Pro.recvCmd();
        //Area.append(this.Pro.Pesan);
    }//GEN-LAST:event_jScrollPane1MouseMoved

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
            java.util.logging.Logger.getLogger(bdClientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(bdClientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(bdClientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(bdClientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new bdClientGui().setVisible(true);
            }
        });
    }
    private Thread p = null;
    private SProcess Pro = null ; 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Area;
    private javax.swing.JComboBox CBox;
    private javax.swing.JButton Connect;
    private javax.swing.JButton Disconnect;
    private javax.swing.JLabel Host;
    private javax.swing.JTabbedPane Main;
    private javax.swing.JLabel Port;
    private javax.swing.JButton Send;
    private javax.swing.JTextField TField;
    private javax.swing.JTextField THost;
    private javax.swing.JTextField TPort;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel status;
    private javax.swing.JLabel status1;
    // End of variables declaration//GEN-END:variables
}
