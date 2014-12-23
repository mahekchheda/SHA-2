/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sha3;


/**
 *
 * @author Mahek Chheda
 */
public class NewJFrame extends javax.swing.JFrame {

     static String s[][];
    String z,p[];
    int r=1024,c=576,w=64;
    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
         s= new String[5][5];
        initComponents();
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
        System.out.println(" Input after padding"+inp);
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
                  NewJFrame. s[i][j]=""+p[i+5*j];
               else
                      NewJFrame. s[i][j]=t;
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
    void theta2()
    {
        int temp1,temp2;
        String C[]=new String[5];
        String d[]=new String[5];
        for(int i=0;i<5;i++)
        {
            temp1=(i-1)<0?4:(i-1);
            temp2=(i+1)%5;
            C[temp1]=xor(NewJFrame.s[temp1][0],xor(NewJFrame.s[temp1][1],xor(NewJFrame.s[temp1][2],xor(NewJFrame.s[temp1][3],(NewJFrame.s[temp1][4])))));
            C[temp2]=xor(NewJFrame.s[temp2][0],xor(NewJFrame.s[temp2][1],xor(NewJFrame.s[temp2][2],xor(NewJFrame.s[temp2][3],(NewJFrame.s[temp2][4])))));
            d[i]=xor(C[temp1%5],rot(C[temp2]));
            for(int j=0;j<5;j++)
                NewJFrame.s[i][j]=xor(NewJFrame.s[i][j],d[i]);
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
                b[j][temp]=rot2(NewJFrame.s[i][j],ri[i][j]);
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
                NewJFrame.s[i][j]=xor(b[i][j],(and(b[u][j],not(b[t][j]))));
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
        NewJFrame.s[0][0]=xor(NewJFrame.s[0][0],rc[i]);
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
       jTextArea2.setText(res);
       System.out.println(res);
    }
    String ascii(String a)
    {
        int m;
        String res="";
        //System.out.println("Z---"+(a.length()/8));
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


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Plain Text");

        jScrollPane1.setToolTipText("Enter Message");

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Hash Message");

        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Calibri", 3, 14)); // NOI18N
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jButton1.setFont(new java.awt.Font("Lucida Console", 0, 14)); // NOI18N
        jButton1.setText("Run SHA-3");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Lucida Console", 0, 14)); // NOI18N
        jButton2.setText("Reset");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Lucida Console", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("SHA-3");

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Error");

        jLabel5.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        jLabel5.setText("Note: Some characters might not be supported in this window so you may check it on cmd for exact answer");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(63, 63, 63)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(218, 218, 218)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2))
                        .addGap(19, 19, 19))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jLabel5)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
     try
    {
        jTextField1.setText("");
        String  inp=  Padding(jTextArea1.getText());
        System.out.println("after xoring with padding");
        //Absorbing phase
        p=new String[inp.length()/64];
        //String r=sh.preprocessing(inp);
        partition(inp);
        //putting p into s
        absorb();
        
        // Squeezing phase
       squeeze();
    }
    catch(Exception e)
     {
         jTextField1.setText("Invalid pass of arguments");
         
     }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        jTextArea1.setText("");
        jTextArea2.setText("");
        jTextField1.setText("");
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
