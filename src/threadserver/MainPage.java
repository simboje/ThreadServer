
package threadserver;

import UserDataPackage.UserData;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

public class MainPage extends javax.swing.JFrame 
{
    String userName;
    int portNumber = 50000;
    ServerSocket serverSocket;
    ArrayList<UserOutput> printerList=new ArrayList();  // UserOutput class contains PrintWriter to specific user and String with username of that user
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
            jTextArea1.append("Error while saving PORT number value.\n"+ex.toString());
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
            @Override
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
                jTextField1.setText(portNumber+"");
                bread.close();
            }
            catch (IOException ex)
            {
                jTextArea1.append(ex.toString()+"\n");
            }
        }
        else
        {
            jTextArea1.append("Settings file does not exist. Using default port 50000.\n");
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
                        try
                        {
                        PreparedStatement statement     = conn.prepareStatement("INSERT INTO messages (username,datetime,message) VALUES(?,?,?)");
                        Calendar cal = Calendar.getInstance(); 
                        java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
                        statement.setString(1, myUser.getUserName());
                        statement.setTimestamp(2, timestamp);
                        statement.setString(3, socketInput);
                        int insertedRecordsCount = statement.executeUpdate();
                        }
                        catch (Exception ex)
                        {
                            jTextArea1.append(ex.toString()+"\n");
                        }
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
    UserData userData;
    static String connString = "jdbc:mysql://localhost:3306/chat_server";
    UserData dbUser;
    Connection conn;
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
                    ObjectInputStream ois = new ObjectInputStream(clientConn.getInputStream());
                    userData = (UserData) ois.readObject();
                    conn = DriverManager.getConnection(connString,"root","");
                    Statement stat = conn.createStatement();
                    
                    File f = new File(userData.name);
                    if(f.exists())
                    {
                        FileInputStream fis = new FileInputStream(f);
                        ObjectInputStream oisf = new ObjectInputStream(fis);
                        UserData userFromFile=(UserData) oisf.readObject();
                        oisf.close();
                        if(userFromFile==null)
                            jTextArea1.append("NULL 111");
                        ResultSet rset = stat.executeQuery("select * from users");
                        while(rset.next())
                        {
                            if(userFromFile.name.equals(rset.getString(2)))
                            {
                                jTextArea1.append("I found "+rset.getString(2));
                                dbUser = new UserData(rset.getString(2),rset.getBytes(3),userFromFile.key,"dummy",9999);
                                //jTextArea1.append(dbUser.pass+"     "+dbUser.key+"\n");
                                break;
                            }
                        }
                        
                        if(dbUser==null)
                            jTextArea1.append("NULL 222");
                        
                        Key keyFromFile = new SecretKeySpec(userFromFile.key, "DES");
                        Cipher decrypter = Cipher.getInstance("DES/ECB/PKCS5Padding");
                        decrypter.init(Cipher.DECRYPT_MODE, keyFromFile);
                        byte[] decryptedText = decrypter.doFinal(dbUser.pass);
                        String izfajla = new String(decryptedText);
                        
                        Key keyFromSocket = new SecretKeySpec(userData.key, "DES");
                        Cipher decrypter2 = Cipher.getInstance("DES/ECB/PKCS5Padding");
                        decrypter2.init(Cipher.DECRYPT_MODE, keyFromSocket);
                        byte[] decryptedText2 = decrypter2.doFinal(userData.pass);
                        String izsocketa = new String(decryptedText2);
                        
                        if(izfajla.equals(izsocketa))
                        {
                            jTextArea1.append("Succesfull login: "+userData.name);
                            
                            BufferedReader userNameReader = new BufferedReader(new InputStreamReader(clientConn.getInputStream()));
                            String un = userNameReader.readLine();
                            UserOutput user=new UserOutput(pWrite,un);
                            printerList.add(user);
                            pWrite.println(printerList.size());                                             // sends number of clients to new client so that he can start a for loop and receive all usernames
                            for(UserOutput uo:printerList)
                            {
                                pWrite.println(uo.getUserName());
                            }
                            Calendar cal = Calendar.getInstance(); 
                            java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
                            String stamp = timestamp.toString();
                            ResultSet mlist = stat.executeQuery("select * from messages");
                            while(mlist.next())
                            {
                                pWrite.println(mlist.getString(2)+"  "+mlist.getDate(3)+":  "+mlist.getString(4));
                            }
                            Thread clientHandler = new Thread(new ClientSupport(clientConn,user));
                            clientHandler.start();
                            jTextArea1.append("Client:  "+un+"   connected.\n");
                        }
                        else
                        {
                            jTextArea1.append("Password not good!!!");
                            jTextArea1.append("userFromFile.pass "+userFromFile.pass+"\n");
                            jTextArea1.append("userData.pass "+userData.pass+"\n");
                            ois.close();
                            pWrite.close();                            
                        }
                    }
                    else
                    {
                        
                        String query = "insert into users (username, password)" + " values (?, ?)";
                        PreparedStatement preparedStmt = conn.prepareStatement(query);
                        preparedStmt.setString (1, userData.name);
                        Blob blob = new javax.sql.rowset.serial.SerialBlob(userData.pass);
                        preparedStmt.setBlob(2, blob);
                        preparedStmt.execute();
            
                        FileOutputStream fw = new FileOutputStream(f);
                        ObjectOutputStream os = new ObjectOutputStream(fw);
                        os.writeObject(userData);
                        os.close();
                        
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
                        jTextArea1.append("Client:  "+un+"   connected.\n");
                    }
                            
                    
                }
            } 
            catch (IOException ex) 
            {
                jTextArea1.append(ex.toString()+"\n");
            } catch (ClassNotFoundException ex) 
            {
                jTextArea1.append(ex.toString()+"\n");
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                jTextArea1.append("ALG not good!!!");
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchPaddingException ex) {
                jTextArea1.append("PADDING not good!!!");
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
                jTextArea1.append("KEY not good!!!");
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalBlockSizeException ex) {
                jTextArea1.append("BLOCK not good!!!");
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                jTextArea1.append("BAD LAST not good!!!");
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (SQLException ex) 
            {
                jTextArea1.append("SQL exception not good!!!"+ex.toString());
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (Exception ex)
            {
                jTextArea1.append("LAST generic exception not good!!!"+ex.toString());
            }
        }
    
    }
}
