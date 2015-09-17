/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package radar;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.DataBufferByte;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author ASN
 */
public class loadHarbour {
    int mapSize;
    String harbour, heightmap, depthmap;
    int IMAGESIZE = 1000;
    public int[][] array2D,vArray2D;
    boolean noHarbour = false;
    
    /**
     *
     * @param val
     */
    public void loadHarbour(int val){ 
       
        try{
            switch(val){
                case 0:
                    heightmap = "GHS.bmp"; //load Colombo Harbour
                    depthmap = "GHS.bmp";
                    harbour = "Colombo";
                    mapSize = 20000;
                    System.out.println("Colombo - "+mapSize);
                    break;
                case 1: 
                    heightmap = "GHR.bmp"; //load Galle Harbour
                    depthmap = "GHS.bmp";
                    harbour = "Galle";
                    mapSize = 20000;
                    System.out.println("Galle - "+mapSize);
                    break;
                case 2:        
                    heightmap = "VHR.bmp"; //load Hambantota Harbour
                    depthmap = "VHS.bmp";
                    harbour = "Hambantota";
                    mapSize = 20000;
                    System.out.println("Virtual - "+mapSize);
                    break;
                case 3:
                    heightmap = "THR.bmp"; //load Trinco Harbour
                    depthmap = "THS.bmp";
                    harbour = "Trinco";
                    mapSize = 40000;
                    System.out.println("Trinco - "+mapSize);
                    break;
                default:
                    noHarbour = true;
                    System.out.println("Invalid Harbour !! "+mapSize);
                    break;
            }        
            if(!noHarbour){
                seeBMPImage(heightmap); 
            }
        } catch (Exception ex) {
            Logger.getLogger(loadHarbour.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void seeBMPImage(String BMPFileName) {        
        try {
            BufferedImage image = ImageIO.read(new File("MAP/"+BMPFileName));
            array2D = new int[image.getWidth()][image.getHeight()];
            
            //array2D = convertTo2DWithoutUsingGetRGB(image);
            for (int xPixel = 0; xPixel < image.getWidth(); xPixel++){
                for (int yPixel = 0; yPixel < image.getHeight(); yPixel++){
                    int color = image.getRGB(xPixel, yPixel); 
                    array2D[xPixel][yPixel] = (byte)(color);//color;
                   // System.out.println(array2D[xPixel][yPixel] +"");
                }
                
            }
            vArray2D=findVessels(new File("C:/Users/Lochana/Desktop/radar_New (1)/radar_New/src/radar/vessels.txt"));
            // store vessel locations in array2D
            
            for(int y=0; y<3;y++){
                int x=0;
                int vX =(vArray2D[x][y]/20);
                int vY =(vArray2D[x+1][y]/20);

                if ((vArray2D[x][y] %20)!= 0){
                    vX= vX+1;
                }
                if ((vArray2D[x+1][y] %20)!= 0){
                    vY =+1;
                }
                
                System.out.println("-------------"+vX+" "+vY);

                array2D[vX][(vArray2D[x+1][y]/20)]=10;


            }
                
            
            BufferedImage theImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            for(int x = 0; x<image.getWidth(); x++){
                for(int y = 0; y<image.getHeight(); y++){
                    int value = image.getRGB(x, y);//array2D[x][y]; 
                    theImage.setRGB(x, y, value);
                }
            }
            File outputfile = new File("gray.bmp");
            ImageIO.write(theImage, "bmp", outputfile);
//            image = ImageIO.read(new File("gray.bmp"));
//            array2D = new int[image.getWidth()][image.getHeight()];
//            for (int xPixel = 0; xPixel < image.getWidth(); xPixel++){
//                for (int yPixel = 0; yPixel < image.getHeight(); yPixel++){
//                    int color = image.getRGB(xPixel, yPixel); 
//                    array2D[xPixel][yPixel] = (byte)color;// (byte)(color);
//                    //System.out.print(array2D[xPixel][yPixel]+" ");
//                }
//                //System.out.println("");
//            }
            
//            circularSearch ser = new circularSearch();
//            ser.circularSearch(array2D);
//            ser.setVisible(true);
            radarView radar = new radarView(array2D);
            radar.setVisible(true);
//            
        } catch (IOException ex) {
            Logger.getLogger(loadHarbour.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int[][] findVessels(File f) {
        int x=0,y=0;
        int[][] array = new int[2][3];;
        String line,v[];
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            while ((line =br.readLine()) != null) {
                //i++;
                v= line.split(",");
                array[x][y]= Integer.parseInt(v[0]);

                x++;
                array[x][y]= Integer.parseInt(v[1]);

                y++;
                x--;

	    }
           
            //vX = Integer.parseInt(br.readLine())

        } catch (FileNotFoundException ex) {
            Logger.getLogger(loadHarbour.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(loadHarbour.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        return array;
    }
    
    private static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {

      final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
      final int width = image.getWidth();
      final int height = image.getHeight();
      final boolean hasAlphaChannel = image.getAlphaRaster() != null;

      int[][] array2D = new int[height][width];
      if (hasAlphaChannel) {
         final int pixelLength = 4;
         for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
            int argb = 0;
            argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
            argb += ((int) pixels[pixel + 1] & 0xff); // blue
            argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
            argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
            array2D[row][col] = argb;
            col++;
            if (col == width) {
               col = 0;
               row++;
            }
         }
      } else {
         final int pixelLength = 3;
         for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
            int argb = 0;
            argb += -16777216; // 255 alpha
            argb += ((int) pixels[pixel] & 0xff); // blue
            argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
            argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
            array2D[row][col] = argb;
            col++;
            if (col == width) {
               col = 0;
               row++;
            }
         }
      }

      return array2D;
   }

    
    
}
