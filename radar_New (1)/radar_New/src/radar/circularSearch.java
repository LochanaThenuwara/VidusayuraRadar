/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package radar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author ASN
 */
public class circularSearch extends javax.swing.JFrame {

    int scaledX, scaledY, posX, posY, qtr, ox, oy, gap=0; 
    int[][] array2D;
    int[][] array2DCopy;
    int current,currentX,currentY;
    boolean round = true;
    BufferedImage theImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);  
    
    
          
    public void circularSearch(int[][] array2D) throws IOException{
        this.array2D = array2D;
        this.setSize(500, 500);
        this.setVisible(true);
        this.setBackground(Color.BLACK);
        
        while(true){
            array2DCopy = new int[array2D.length][array2D.length];
            qtr = 0; 
            fetchOwnShipPos();
            if(scaledX>249 && scaledX<750 && scaledY>249 && scaledY<750){
                if(qtr == 0){
                    gap=0;          
                    for(int i = scaledX-250; i<scaledX+251; i++){
                        ox = i;
                        oy = scaledY-250;
                        currentX=0;
                        currentY=0;
                        current=0;
                        findLine(ox, oy, scaledX, scaledY, 0);
                        array2DCopy[currentX][currentY] = current;
                        paint(this.getGraphics());                        
                    }
                    qtr=1;
                }
                if(qtr == 1){
                    gap=0;
                    for(int i=scaledY-250;i<scaledY+251;i++){
                        ox=scaledX+250;
                        oy=i;
                        currentX=0;
                        currentY=0;
                        current=0;
                        findLine(ox, oy, scaledX, scaledY, 0);
                        array2DCopy[currentX][currentY] = current;
                        paint(this.getGraphics());
                    }
                    qtr=2;
                }
                if(qtr == 2){
                    gap=0;
                    for(int i=scaledX+250;i>scaledX-251;i--){
                        ox=i;
                        oy=scaledY+250;
                        currentX=0;
                        currentY=0;
                        current=0;
                        findLine(ox, oy, scaledX, scaledY, 0);
                        array2DCopy[currentX][currentY] = current;
                        paint(this.getGraphics());
                    }
                    qtr =3;
                }
                if(qtr == 3){
                    gap=0;
                    for(int i=scaledY+250;i>scaledY-251;i--){
                        ox=scaledX-250;
                        oy=i;
                        currentX=0;
                        currentY=0;
                        current=0;
                        findLine(ox, oy, scaledX, scaledY, 0);
                        array2DCopy[currentX][currentY] = current;
                        paint(this.getGraphics());
                    }
                }
            }
            round = !round;
        }    
    }

    private void fetchOwnShipPos() {
        scaledX = 250+posX;
        scaledY = 250+posY;
        posX += 25;
        posY += 25;
        for(int x = 0; x<1000; x++){            
            for(int y = 0; y<1000; y++){
                array2DCopy[x][y] = 0;
            }
        }
        //socket
        
//        try {
//            Socket echoSocket = new Socket("10.22.196.220", 1238);
//            PrintWriter out =   new PrintWriter(echoSocket.getOutputStream(), true);
//            BufferedReader in =  new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
//            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
//            String userInput;
//            while ((userInput = stdIn.readLine()) != null) {
//                out.println(userInput);
//                System.out.println("echo: " + in.readLine());
//            }
//        }catch(Exception e){
//        }
    }
    
    public void findLine(int x0, int y0, int x1, int y1, int type){
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0); 
        int sx = x0 < x1 ? 1 : -1; 
        int sy = y0 < y1 ? 1 : -1;  
        int err = dx-dy;
        int e2; 
        
        while (true){
            if(type==0){
                searchMatrix(x0,y0);
            }else{
                printMatrix(x0,y0);
            }
            if (x0 == x1 && y0 == y1) 
                break; 
            e2 = 2 * err;
            if (e2 > -dy){
                err = err - dy;
                x0 = x0 + sx;
            } 
            if (e2 < dx){
                err = err + dx;
                y0 = y0 + sy;
            }
        }
    }
    
    private void searchMatrix(int x, int y) {    
       /* array2DCopy[x][y] = array2D[x][y];
        Graphics g = this.getGraphics();
        if(round){
            if(array2DCopy[x][y]==5){
                g.setColor(Color.yellow);
                g.drawRect(x, y, 1, 1);
            }else if(array2DCopy[x][y]==0){
                g.setColor(Color.pink);
                g.drawRect(x, y, 1, 1);
            }else{
                g.setColor(Color.green);
                g.drawRect(x, y, 1, 1);
            } 
            
        }else{
            if(array2DCopy[x][y]==5){
                g.setColor(Color.blue);
                g.drawRect(x, y, 1, 1);
            }else if(array2DCopy[x][y]==0){
                g.setColor(Color.black);
                g.drawRect(x, y, 1, 1);
            }else{
                g.setColor(Color.red);
                g.drawRect(x, y, 1, 1);
            } 
        }*/
        if(array2D[x][y]>current){
            current = array2D[x][y];
            currentX = x;
            currentY = y;
        }
    }

//    private void printSearch() {
//      try {
//            for(int x = 0; x<500; x++){
//                for(int y = 0; y<500; y++){
//                    int value = array2DCopy[x][y];
//                    if(value>0){
//                        Color c = new Color(0, 255, 0, 255);                       
//                        theImage.setRGB(x,y,c.getRGB());
//                    }else{
//                        theImage.setRGB(x,y,0);
//                    }
//                    System.out.println("************");
//                }
//                System.out.println("&&&&&&&&&&&&&&&&&&&&"+ x);
//            }
//            File outputfile = new File("outImg.bmp");
//            ImageIO.write(theImage, "png", outputfile);
//            Image image = ImageIO.read(outputfile);
//            JLabel label = new JLabel(new ImageIcon(image));
//            this.getContentPane().add(label, BorderLayout.CENTER);
//            this.pack();
//            this.setVisible(true);
//
//        } catch (IOException ex) {
//            Logger.getLogger(circularSearch.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    @Override
    public void paint(Graphics g) {         
        Color c = new Color(0, 255, 0, 255); 
        g.setColor(c);
        switch(qtr){
            case(0):
                g.drawLine(250, 250, gap, 0);
                if(gap>0){
                    g.setColor(Color.BLACK);
                    g.drawLine(250, 250, gap-1, 0);
                    findLine(250, 250, gap-1, 0, 1);
                }
                break;
            case(1):
                g.drawLine(250, 250, 500, gap);
                if(gap>0){
                    g.setColor(Color.BLACK);
                    g.drawLine(250, 250,500, gap-1);
                    findLine(250, 250,500, gap-1, 1);
                }
                break;
            case(2):
                g.drawLine(250, 250, 500-gap, 500);     
                
                if(gap>0){
                    g.setColor(Color.BLACK);
                    g.drawLine(250, 250, 501-gap, 500);
                    findLine(250, 250, 501-gap, 500, 1);
                }
                break;
            case(3):
                g.drawLine(250, 250, 0, 500-gap);       
                
                if(gap>0){
                    g.setColor(Color.BLACK);
                    g.drawLine(250, 250, 0, 501-gap);
                    findLine(250, 250, 0, 501-gap, 1);
                }
                break;
            default:
                break;
        }
        gap+=1;
               
//        if(qtr==0){
//            g.drawLine(250, 250, gap, 0);           
//            if(gap>0){
//                 g.setColor(Color.BLACK);
//                 g.drawLine(250, 250, gap-1, 0);
//            }
//            gap+=1;
//        }else if(qtr==1){
//            g.drawLine(250, 250, 500, gap);
//            if(gap>0){
//                g.setColor(Color.BLACK);
//                g.drawLine(250, 250,500, gap-1);
//            }
//            gap+=1;
//        }else if(qtr==2){
//            g.drawLine(250, 250, 500-gap, 500);
//            if(gap>0){
//                g.setColor(Color.BLACK);
//                g.drawLine(250, 250, 501-gap, 500);
//            }
//            gap+=1;
//        }else if(qtr==3){
//            g.drawLine(250, 250, 0, 500-gap);
//            if(gap>0){
//                g.setColor(Color.BLACK);
//                g.drawLine(250, 250, 0, 501-gap);
//            }
//            gap+=1;
//        }
        
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Radar");
        setAlwaysOnTop(true);
        setPreferredSize(new java.awt.Dimension(500, 500));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 513, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 511, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        this.dispose();
       
    }//GEN-LAST:event_formWindowClosing

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_formWindowClosed

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
            java.util.logging.Logger.getLogger(circularSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(circularSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(circularSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(circularSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new circularSearch().setVisible(true);                    
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

    private void printMatrix(int x0, int y0) {
        int value = array2DCopy[x0][y0];
        Color c = new Color(0, 255, 0, 255); 
        Graphics g = this.getGraphics();
        g.setColor(c);   
        if(value>0)
            g.drawRect(x0, y0, 1, 1);
        System.out.println("x0- "+x0+"y0-"+y0);
    }

    
}
