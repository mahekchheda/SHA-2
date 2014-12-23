/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sha3;
import java.util.*;
/**
 *
 * @author Mahek Chheda
 */
public class sha3 {
   static String s[][];
    String z,p[];
    int r,c,w;
    sha3( )
    {
        s= new String[5][5];
    }
    
    /**
     *
     * @param args
     */
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        sha3 sh=new sha3();
         System.out.println("Enter the plain text");
        String inp=sc.nextLine();
        System.out.println("Enter value of r, c & w");
        sh.r=sc.nextInt();
        sh.c=sc.nextInt();
       sh.w=sc.nextInt();
        inp=  sh.Padding(inp);
        //System.out.println("after xoring with padding");
        //Absorbing phase
        sh.p=new String[inp.length()/64];
        //String r=sh.preprocessing(inp);
        sh.partition(inp);
        //putting p into s
        sh.absorb();
        
        // Squeezing phase
       sh.squeeze();
      //  sh.ascii(Z);
    }
    String Padding(String inp)                      // p = m+00s
    {
        inp=BinString(inp);
        int a=inp.length();
        for(int x=0;(a+x)%r!=0;x++)
        {
            if(x==0)
            {
                inp+="1";
            }
            else
            inp+="0";
        }
       //System.out.println(" Input after padding"+inp);
        inp=inp.substring(0,(inp.length()-1))+'1';
        return inp;
    }
    String BinString(String inp)        //converting ascii to binary
    {
        String si="";
        for(int i=0;i<inp.length();i++)
        {
            int x=inp.charAt(i);
            String temp=Integer.toBinaryString(x);
            for(int j=0;j<(8-temp.length());j++)
            {
                temp="0"+temp;
            }
            si+=temp;
        }
       // System.out.println(" Bin equivalent of String"+si);
        return si;
    }
    void partition(String inp)            //partitioning P
    {
        for(int i=0;i<r/w;i++)
            p[i]=inp.substring(i*64, (i*64+64));
      //  System.out.println("No of padding"+p.length);
        //for(int j=0;j<p.length;j++)
          //  System.out.println("P"+j+p[j]);
    }
   
    String xor(String a, String b)
    {
        String res="";
       int i=0;
        if(a.length()>b.length())
        {
            res+=a.substring(i, a.length()-1);
            i=a.length();
        }
        else if (a.length()==b.length())
        {
            i=a.length();
        }
        else
         {
             i=b.length();
             res+=b.substring(i, b.length());
         }
        for(int j=i-1;j>=0;j--)
        {
           // System.out.println("value of j"+j);
            if(a.charAt(j)!=b.charAt(j))
                res="1"+res;
            else
                res='0'+res;
        }
       // System.out.println("Xor result length"+res.length())
        //System.out.println("M="+m);
        return res;
    }

    String preprocessing(String inp)
   {
        String x=inp.substring(0,r);
        for(int i=0;i<inp.length()&&inp.length()>r;i++)
        {
            if(x.charAt(i%r)!=inp.charAt(i))
                x+="1";
             else
                x+="0";
        }
       // System.out.println("XORing 1024 blocks");
        //System.out.println(x);
        return x;
    }
    void absorb()
    {
        String t="";
        for(int i=0;i<64;i++)
            t=t+"0";
        for(int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++)
            {
               if((i+5*j)<(r/64))
                  sha3. s[i][j]=""+p[i+5*j];
               else
                      sha3. s[i][j]=t;
            }
        }
       keccak_round();
    }
    String rot(String s)
    {
       String x=s.charAt(63)+s.substring(0, 63);
      // System.out.println(x.length());
        return x;
    }
    String rot2(String s,int x)
    {
       for(int i=0;i<x;i++)
           s=rot(s);
        return s;
    }
    String and(String a, String b)
    {
        String res="";
        for(int i=0;i<64;i++)
        {
            if(a.charAt(i)=='1'&&b.charAt(i)=='1')
                res+='1';
            else
                res+='0';
        }
        return res;
    }
    String not(String a)
    {
        String res="";
        for(int i=0;i<64;i++)
        {
            if(a.charAt(i)=='1')
                res+='0';
            else
                res+='1';
        }
        return res;
    }
    void keccak_round()
    {
        for(int k=0;k<24;k++)
        {
            theta2();
            String b[][]=rho();
            chi(b);
            iota(k);
        }
    }
    void theta()
    {
        int x,y;
        for(int i=0;i<5;i++)
        {
            x=i-1;
            y=(i+1)%5;
            if(x<0)
                x=4;
            for(int j=0;j<5;j++)
            {
               sha3. s[i][j]=xor(sha3.s[i][j],xor(sha3.s[x][j],rot(sha3.s[y][j])));  
            }
        }
        // display(s);
    }
    void theta2()
    {
        int temp1,temp2;
        String C[]=new String[5];
        String d[]=new String[5];
        for(int i=0;i<5;i++)
        {
            temp1=(i-1)<0?4:(i-1);
            temp2=(i+1)%5;
            C[temp1]=xor(sha3.s[temp1][0],xor(sha3.s[temp1][1],xor(sha3.s[temp1][2],xor(sha3.s[temp1][3],(sha3.s[temp1][4])))));
            C[temp2]=xor(sha3.s[temp2][0],xor(sha3.s[temp2][1],xor(sha3.s[temp2][2],xor(sha3.s[temp2][3],(sha3.s[temp2][4])))));
            d[i]=xor(C[temp1%5],rot(C[temp2]));
            for(int j=0;j<5;j++)
                sha3.s[i][j]=xor(sha3.s[i][j],d[i]);
        }
       // display(s);
    }
    String [][] rho()
    {
        String b[][]=new String[5][5];
         int ri[][]={{0,36,3,41,18},{1,44,10,45,2},{62,6,43,15,61},{28,55,25,21,56},{27,20,39,8,14}};
        int temp;
        for(int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++)
            {
                temp=(2*i+3*j)%5;
                b[j][temp]=rot2(sha3.s[i][j],ri[i][j]);
            }
        }
       // display(b);
        return b;
    }
    void chi(String b[][])
    {
        int t,u;
        for(int i=0;i<5;i++)
        {
            t=(i+1)%5;
            u=(i+2)%5;
            for(int j=0;j<5;j++)
            {
                sha3.s[i][j]=xor(b[i][j],(and(b[u][j],not(b[t][j]))));
            }
        }
       // display(s);
    }
    String [] rccompute()
    {
        String rc[]={"0000000000000001","0000000000008082","800000000000808A","8000000080008000","000000000000808B",
                                "0000000080000001","8000000080008081","8000000000008009","000000000000008A","0000000000000088",
                                "0000000080008009","000000008000000A","000000008000808B","800000000000008B","8000000000008089",
                                "8000000000008003","8000000000008002","8000000000000080","000000000000800A","800000008000000A",
                                "8000000080008081","8000000000008080","0000000080000001","8000000080008008"};
    for(int i=0;i<24;i++)
    {
        rc[i]=hexToBin(rc[i]);
    }
    return rc;
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
    void iota(int i)
    {
        String rc[]=rccompute();
        sha3.s[0][0]=xor(sha3.s[0][0],rc[i]);
    }
    void squeeze()
    {
         String Z="";
        for(int i=0;i<5;i++)
            for(int j=0;j<5;j++)
            {
                 if((i+5*j)<(r/64))
                {
                    Z=Z+s[i][j];
                }
            }
        String res=ascii(Z.substring(0, c/2));
        System.out.println("Hash code:::"+res);
    }
    String ascii(String a)
    {
        int m;
        String res="";
        System.out.println("Z---"+(a.length()/8));
        String l[]= new String [a.length()/8];
        for(int i=0;i<a.length()/8;i++)
            l[i]=a.substring(i*8,( i*8+8));
        for(int i=0;i<l.length;i++)
        {
            m=bintodecimal(l[i]);
            res+=(char)m;
        }
        return res;
    }
    int bintodecimal( String m)
    {
        int temp=0;
        for(int i=1;i<8;i++)
        {
            if(m.charAt(i)=='1')
            {
                temp+=Math.pow(2,7-i);
            }
        }
        return temp;
    }
}

