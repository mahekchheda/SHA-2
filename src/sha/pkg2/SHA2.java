/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sha.pkg2;
import java.util.*;
/**
 *
 * @author Mahek Chheda
 */
public class SHA2 {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         Scanner sc=new Scanner(System.in);
        SHA2 s=new SHA2();
        System.out.println(s.HexToBinary("1"));
    }
   String HexToBinary(String Hex)
{
    int i = Integer.parseInt(Hex);
    String Bin = Integer.toBinaryString(i);
    String s="";
    for(int j=0;j<8-Bin.length();j++)
    {
        s+="0";
    }
    s=rot(s+Bin);
     return s;
}
   String rot(String s)
    {
       String x=s.charAt(s.length()-1)+s.substring(0, s.length()-1);
       System.out.println(x.length());
        return x;
    }
}
