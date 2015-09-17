/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package radar;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author CRD 1
 */
public class Locator {
    int id;
    float x;
    float y;

    public Locator(char[] buffer) {
        id = getId(buffer);
        x = getX(buffer);
        y = getY(buffer);
    }

    public int getId(char[] buf) {

        byte bytes[] = new byte[4];
        System.out.println("buf...."+buf[9]);
        System.out.println("buf...."+buf[10]);
        System.out.println("buf...."+buf[11]);
        System.out.println("buf...."+buf[12]);
        bytes[0] = (byte) buf[0];
        bytes[1] = (byte) buf[1];
        bytes[2] = (byte) buf[2];
        bytes[3] = (byte) buf[3];

        ByteBuffer result = ByteBuffer.wrap(bytes);
        result.order(ByteOrder.LITTLE_ENDIAN);
        System.out.println(" *** "+result.toString());
        return result.getInt();
    }

    public float getX(char[] buf) {
        byte bytes[]=new String(buf).getBytes();
        byte bytesF[] = new byte[4];
        bytesF[0] = bytes[4];
        bytesF[1] = bytes[5];
        bytesF[2] = bytes[6];
        bytesF[3] = bytes[7];
        ByteBuffer result = ByteBuffer.wrap(bytes);
        result.order(ByteOrder.LITTLE_ENDIAN);

        return result.getFloat();
    }

    public float getY(char[] buf) {

        byte bytes[] = new byte[4];

        bytes[0] = (byte) buf[8];
        bytes[1] = (byte) buf[9];
        bytes[2] = (byte) buf[10];
        bytes[3] = (byte) buf[11];

        ByteBuffer result = ByteBuffer.wrap(bytes);
        result.order(ByteOrder.LITTLE_ENDIAN);

        return result.getFloat();
    }
    
}
