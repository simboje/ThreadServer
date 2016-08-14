/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Simba
 */
class ThreadSocket extends Thread
{
    private Socket socket=null;
    public ThreadSocket(Socket accept) 
    {
        this.socket=accept;
    }
    
    @Override
    public void run()
    {
        while(true)
        {
            try (
                    BufferedReader bReader=new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                    PrintWriter pWriter = new PrintWriter(this.socket.getOutputStream(),true);      // autoflush set to true
                )
            {
                while(true)
                {
                    String socketInput=null;
                    while((socketInput=bReader.readLine())!=null)
                    {
                        pWriter.println("Server - primljeno:"+socketInput);         // only methods ending with ln (like println) support autoflush, methods like write will not autoflush
                        // in case of emergency call pWriter.flush() here :)
                        // if flush() method is called here, while pWriter is not set to autoflush, it will work the same way as in case of autoflush=true
                        System.out.println("Primljena poruka x: "+socketInput);
                    }
                    System.out.println("Prosao while socket read");
                }
            }
            catch (IOException ex)
            {
                System.out.println("IOException fired in thread.");
                break;
            }
        }
    }
    
}
