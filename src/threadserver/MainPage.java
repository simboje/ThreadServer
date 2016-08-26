
package threadserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class MainPage extends javax.swing.JFrame 
{
    String userName;
    int portNumber = 6999;
    ServerSocket serverSocket;
    ArrayList<UserOutput> printerList=new ArrayList();
    public MainPage() 
    {
        initComponents();
        readPortNumber();
        Thread mainPortThread = new Thread(new mainPortListener());
        mainPortThread.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField1.setText("6999");

        jLabel1.setText("Port to listen");

        jButton1.setText("Save and change port");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(0, 122, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try (FileWriter fwrite = new FileWriter("settings.set")) 
        {
            portNumber=Integer.parseInt(jTextField1.getText());
            fwrite.write(portNumber+"");
            Thread mainPortThread = new Thread(new mainPortListener());
            mainPortThread.start();
        } 
        catch (IOException ex) 
        {
            jTextArea1.append("Error while saving PORT number value.\n");
        }    
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    private void readPortNumber() 
    {
        File f=new File("settings.set");
        if(f.exists())
        {
            try
            {
                BufferedReader bread = new BufferedReader(new FileReader("settings.set"));
                portNumber = Integer.parseInt(bread.readLine());
                bread.close();
            }
            catch (IOException ex)
            {
                jTextArea1.append(ex.toString()+"\n");
            }
        }
        else
        {
            jTextArea1.append("Settings file does not exist. Using default port 6999.\n");
        }
    }
  

    private class ClientSupport implements Runnable 
    {
        Socket mySocket;
        UserOutput myUser;
        public ClientSupport(Socket socket, UserOutput uo) 
        {
            mySocket = socket;
            myUser=uo;
        }

        @Override
        public void run() 
        {
            while(true)
            {
                try 
                {
                    BufferedReader readClient = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
                    String socketInput=null;
                    while((socketInput=readClient.readLine())!=null)
                    {
                        jTextArea1.append(socketInput+"\n");
                        broadcastToClients(socketInput);
                    }
                } 
                catch (IOException ex) 
                {
                    
                }
                finally
                {
                    jTextArea1.append("Client disconnected: "+myUser.getUserName()+"\n");
                    printerList.remove(myUser);
                    break;
                }
            }
        }

        private void broadcastToClients(String socketInput) 
        {
            for(UserOutput pw:printerList)
            {
                pw.getWriter().println(socketInput);
            }
        }
    }
    
    class mainPortListener implements Runnable 
    {

        public mainPortListener() 
        {        }

        @Override
        public void run() 
        {
           try 
            {
                serverSocket = new ServerSocket(portNumber);
                while(true)
                {
                    Socket clientConn = serverSocket.accept();
                    PrintWriter pWrite = new PrintWriter(clientConn.getOutputStream(),true);      // autoflush set to true
                    BufferedReader userNameReader = new BufferedReader(new InputStreamReader(clientConn.getInputStream()));
                    String un = userNameReader.readLine();
                    UserOutput user=new UserOutput(pWrite,un);
                    printerList.add(user);
                    pWrite.println(printerList.size());                                             // sends number of clients to new client so that he can start a for loop and receive all usernames
                    for(UserOutput uo:printerList)
                    {
                        pWrite.println(uo.getUserName());
                    }
                        
                    Thread clientHandler = new Thread(new ClientSupport(clientConn,user));
                    clientHandler.start();
                    jTextArea1.append("Client:"+un+" connected.\n");
                }
            } 
            catch (IOException ex) 
            {
                
            } 
        }
    
    }
}
