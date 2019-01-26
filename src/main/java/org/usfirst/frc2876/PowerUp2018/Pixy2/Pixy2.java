package org.usfirst.frc2876.PowerUp2018.Pixy2;
import java.util.Arrays;

public class Pixy2 {
    Pixy2I2C i2c;
    public static final boolean PIXY_RESULT_OK = true;
    public static final boolean PIXY_RESULT_ERROR = false;
    public static final short PIXY_CHECKSUM_SYNC = (short)0xc1af;
    public static final short PIXY_NO_CHECKSUM_SYNC = (short)0xc1ae;

    private boolean m_cs;

    private Pixy2Request request;
    private Pixy2Response response;

    public Pixy2() {
        i2c = new Pixy2I2C("test", 0x54);
    }

    // https://stackoverflow.com/questions/736815/2-bytes-to-short-java
    public static short bytesToShort(byte upper, byte lower) {
        // return (((int) upper & 0xff) << 8) | ((int) lower & 0xff);
        return (short)(((upper & 0xFF) << 8) | (lower & 0xFF));
    }

    public boolean getSync() {
        // implement this here
        // https://github.com/charmedlabs/pixy2/blob/master/src/host/arduino/libraries/Pixy2/TPixy2.h#L169

        int i, j;
        boolean res;

        short start;
        byte[] buf = new byte[1];
        byte byteCur = 0;
        byte bytePrev = 0;
        // parse bytes until we find sync
        for (i = j = 0; true; i++) {
            //System.out.format("sync: %02X %02X\n", bytePrev, byteCur);
            res = i2c.recv(buf);
            if (res == PIXY_RESULT_OK) {
                byteCur = buf[0];
                // since we're using little endian, previous byte is least significant byte
                // start = cprev;
                // current byte is most significant byte
                // start |= c << 8;
                start = bytesToShort(byteCur, bytePrev);

                //System.out.format("sync: %02X %02X\n", bytePrev, byteCur);
                System.out.format("sync: %02X %02X start: %02X %02X %02X\n", bytePrev, byteCur, start, PIXY_CHECKSUM_SYNC, PIXY_NO_CHECKSUM_SYNC);
                System.out.println("start " + (short)start + " == " + PIXY_CHECKSUM_SYNC);
                
                bytePrev = byteCur;
                // cprev = c;
                if (start == PIXY_CHECKSUM_SYNC) {
                    m_cs = true;
                    return PIXY_RESULT_OK;
                }
                if (start == PIXY_NO_CHECKSUM_SYNC) {
                    m_cs = false;
                    return PIXY_RESULT_OK;
                }
            }
            // If we've read some bytes and no sync, then wait and try again.
            // And do that several more times before we give up.
            // Pixy guarantees to respond within 100us.
            if (i >= 4) {
                if (j >= 4) {
                    return PIXY_RESULT_ERROR;
                }
                // TODO add a Thread.sleep here
                // delayMicroseconds(25);
                j++;
                i = 0;
            }
        }
        // return false;
    }

    public byte[] recv() {
        // https://github.com/charmedlabs/pixy2/blob/master/src/host/arduino/libraries/Pixy2/TPixy2.h#L217
        //int csCalc, csSerial;
        boolean res;

        res = getSync();
        if (res != PIXY_RESULT_OK) {
            System.out.println("recv failed to get sync");
            return null;
        }
        byte m_type;
        byte m_length;
        byte[] payload;
        if (m_cs) {
            byte[] buf = new byte[4];
            res = i2c.recv(buf);
            if (res != PIXY_RESULT_OK) {
                return null;
            }
            m_type = buf[0];
            m_length = buf[1];

            // csSerial = *(uint16_t *)&m_buf[2];

            payload = new byte[m_length];
            res = i2c.recv(payload);
            if (res != PIXY_RESULT_OK) {
                return null;
            }
            // TODO calculate checksum
            // https://github.com/charmedlabs/pixy2/blob/master/src/host/arduino/libraries/Pixy2/Pixy2I2C.h#L56
            // add up payload bytes
            // if (csSerial!=csCalc) {
            // return PIXY_RESULT_CHECKSUM_ERROR;
            // }
        } else {
            byte[] buf = new byte[2];
            res = i2c.recv(buf);
            if (res != PIXY_RESULT_OK) {
                return null;
            }
            m_type = buf[0];
            m_length = buf[1];

            payload = new byte[m_length];
            res = i2c.recv(payload);
            if (res != PIXY_RESULT_OK) {
                return null;
            }
        }
        return payload;
    }

    public boolean send(byte type, byte[] payload) {
        // https://github.com/charmedlabs/pixy2/blob/master/src/host/arduino/libraries/Pixy2/TPixy2.h#L266
        int len = 0;
        if (payload != null) {
            len = payload.length;
        }
        byte[] header = { (byte) 0xAE, (byte) 0xC1, type, (byte)len };
        
        // https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#copyOf-byte:A-int-
        byte[] buf = Arrays.copyOf(header, header.length + len);
        if (payload != null) {
           // https://docs.oracle.com/javase/8/docs/api/java/lang/System.html#arraycopy-java.lang.Object-int-java.lang.Object-int-int-
            System.arraycopy(payload, 0, buf, header.length, len);
        }
        Pixy2I2C.printBytes("Send:", buf);
        return i2c.send(buf);
    }

    public void version() {
        byte[] payload = null;
        if (send((byte)0x0E, payload)) {
            byte[] response = recv();
            Pixy2I2C.printBytes("version rawbytes", response);
            Pixy2Version v = new Pixy2Version(response);
            System.out.println("Version:");
            v.print();
        }
    }

    public void version2() {
        Pixy2Version v = new Pixy2Version(i2c);
        v.print();
    }
    public void setLed() {
        byte r=1;
        byte g=1;
        byte b=1;
        
        Pixy2SetLED sl = new Pixy2SetLED(i2c, r, g, b);
    }

    public void getVectors() {
        Pixy2GetMainFeatures mf = new Pixy2GetMainFeatures(i2c);
        
    }
}