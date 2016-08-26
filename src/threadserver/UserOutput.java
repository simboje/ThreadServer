/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadserver;

import java.io.PrintWriter;

/**
 *
 * @author Simba
 */
public class UserOutput 
{
    private PrintWriter writer;
    private String userName;
    
    public UserOutput(PrintWriter pw,String name)
    {
        writer = pw;
        userName = name;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public String getUserName() {
        return userName;
    }
    
    
}
