package org.usfirst.frc2876.PowerUp2018.Pixy2;

public class Pixy2Packet {
    
    //byte[] response;

    // public byte[] getBytes() {
    //     return response;
    // }
    public short cvt(byte upper, byte lower) {
        //return (((int) upper & 0xff) << 8) | ((int) lower & 0xff);
        //return (short)(((short)((short) upper & (short)0xff) << 8) | (short)((short) lower & (short)0xff));
        return (short)(((upper & 0xFF) << 8) | (lower & 0xFF));
    }

    /* 
    public boolean get() {
        boolean status = packet.send();
        if (status) {
            status = packet.recv();
            if (status) {
                rawBytes = packet.getBytes();
            }
            return status;
        }
        return status;
    } */
}