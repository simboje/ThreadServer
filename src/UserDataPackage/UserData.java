/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserDataPackage;

import java.io.Serializable;

/**
 *
 * @author Simba
 */
public class UserData implements Serializable
{
    public static final long serialVersionUID = 42L;
    public String name;
    public byte[] pass;
    public byte[] key;
    public String hostName;
    public int portNumber;
    public UserData(String un,byte[] p,byte[] k,String hname,int pnumber)
    {
        name=un;
        pass=p;
        key = k;
        hostName=hname;
        portNumber=pnumber;
    }    
}
