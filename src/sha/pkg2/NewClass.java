/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sha.pkg2;

/**
 *
 * @author Mahek Chheda
 */
public class NewClass {
     void rccompute()
    {
        String rc[]={"0000000000000001","0000000000008082","800000000000808A","8000000080008000","000000000000808B",
                                "0000000080000001","8000000080008081","8000000000008009","000000000000008A","0000000000000088",
                                "0000000080008009","000000008000000A","000000008000808B","800000000000008B","8000000000008089",
                                "8000000000008003","8000000000008002","8000000000000080","000000000000800A","800000008000000A",
                                "8000000080008081","8000000000008080","0000000080000001","8000000080008008"};
    for(int i=0;i<24;i++)
    {
        rc[i]=hexToBin(rc[i]);
        System.out.println(rc[i]);
    }
    }
    String hexToBin(String hex){
    String bin = "";
    String binFragment = "";
    int iHex;
    //hex = hex.trim();
    //hex = hex.replaceFirst("0x", "");

    for(int i = 0; i < hex.length(); i++){
        iHex = Integer.parseInt(""+hex.charAt(i),16);
        binFragment = Integer.toBinaryString(iHex);

        while(binFragment.length() < 4){
            binFragment = "0" + binFragment;
        }
        bin += binFragment;
    }
    return bin;
}
 public static void main(String args[])
 {
     NewClass n=new NewClass();
     n.rccompute();
 }
}
