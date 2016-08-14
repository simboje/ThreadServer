
package threadserver;

import java.io.IOException;
import java.net.ServerSocket;

public class ThreadServer {

    public static void main(String[] args) 
    {
        int portNumber = 6999;
        
        try(ServerSocket serverSocket=new ServerSocket(portNumber))
        {
            while(true)
            {
                try
                {
                    new ThreadSocket(serverSocket.accept()).start();
                }
                catch(Exception ex)
                {
                    System.out.println(ex.toString());
                }
            }
        }
        catch(IOException ex)
        {
            System.out.println(ex.toString());
        }
    }
    
}
