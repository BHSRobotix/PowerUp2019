package org.usfirst.frc2876.PowerUp2018.utilities;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pixy2I2C {
    String name;
    I2C i2c;
    Port port = Port.kOnboard;
    PixyException pExc;
    String print;

    // Max bytes to send across wire for each write
    public static final int MAX_BYTES_SEND = 16;

    private static final byte[] GET_VERSION = new byte[] {
        (byte)0xAE, (byte)0xc1,
        0x0e,
        0
    };

    private static final byte[] LED_ON = new byte[] {
        (byte)0xAE, (byte)0xc1,
        0x16,
        0x02,
        0,
        0x01
    };
    private static final byte[] LED_GREEN = new byte[] {
        (byte)0xAE, (byte)0xc1,
        0x14,
        0x03,
        (byte)0xff,
        (byte)0xff,  //green doesnt work for some reason
        (byte)0xff
    };
    private static final byte[] LED_OFF = new byte[] {
        (byte)0xAE, (byte)0xc1,
        0x16,
        0x02,
        0,
        0
    };

    public Pixy2I2C(String id, int address) {
        i2c = new I2C(port, address);
        //pExc = argPixyException;
        name = "Pixy_" + id;
    }

    // This method parses raw data from the pixy into readable integers
    public int cvt(byte upper, byte lower) {
        return (((int) upper & 0xff) << 8) | ((int) lower & 0xff);
    }

    public boolean recv(byte[] buf) {
        try {
            return !i2c.readOnly(buf, buf.length);
        } catch (RuntimeException e) {
            //SmartDashboard.putString(name + "Status", e.toString());
            System.out.println(name + "  " + e);
            return false;
        }
    }

    public void sendBB(byte[] buf) {
        int packet_length;
        for (int i=0; i<buf.length; i+=MAX_BYTES_SEND) {
            if (buf.length < MAX_BYTES_SEND) {
                packet_length = buf.length-1;
            } else {
                packet_length = MAX_BYTES_SEND;
            }
            ByteBuffer bb = ByteBuffer.wrap(buf, i, packet_length);
            i2c.writeBulk(bb, packet_length);
            
        }

    }

    public boolean send(byte[] buf) {
        return !i2c.writeBulk(buf);
    }

    public static void printBytes(byte[] b) {
        System.out.println("start bytes");
        for (int i=0; i<b.length; i++) {
            System.out.format("%02X ", b[i]);
        }
        System.out.println("end bytes");
    }

    public void version( ){
        /*
            https://docs.pixycam.com/wiki/doku.php?id=wiki:v2:porting_guide#the-serial-protocol

            Write some code that calls send() to send version cmd to pixy2
            Then write some code that calls recv() to get response
            Parse the response and print out the version.
            The link above has example/steps to do this.

            Use printBytes to print out what is being sent and recv to
            help understand/debug problems.

            GET_VERSION defines the bytes you need to send.
        */
        System.out.println("Version Started");
        //byte[] testResp = new byte[1];
        //while(recv(testResp));

        printBytes(GET_VERSION);
        boolean t = send(GET_VERSION);
        System.out.println("send returned: " + t);

        byte[] resp = new byte[6+16];
        boolean b = recv(resp);
        System.out.println("recv returned: " + b);
        printBytes(resp);
    }


    public void ledOn( ){
        System.out.println("ledOn Started");
        //byte[] testResp = new byte[1];
        //while(recv(testResp));

        printBytes(LED_ON);
        boolean t = send(LED_ON);
        System.out.println("send returned: " + t);
        
        byte[] resp = new byte[6+16];
        boolean b = recv(resp);
        System.out.println("recv returned: " + b);
        printBytes(resp);
    }

    public void ledGreen( ){
        System.out.println("ledOn Started");
        //byte[] testResp = new byte[1];
        //while(recv(testResp));

        printBytes(LED_GREEN);
        boolean t = send(LED_GREEN);
        System.out.println("send returned: " + t);
        
        byte[] resp = new byte[6+16];
        boolean b = recv(resp);
        System.out.println("recv returned: " + b);
        printBytes(resp);
    }    
    public void ledOff( ){
        System.out.println("ledOff Started");
        //byte[] testResp = new byte[1];
        //while(recv(testResp));

        printBytes(LED_OFF);
        boolean t = send(LED_OFF);
        System.out.println("send returned: " + t);
        
        byte[] resp = new byte[6+16];
        boolean b = recv(resp);
        System.out.println("recv returned: " + b);
        printBytes(resp);
    }

}