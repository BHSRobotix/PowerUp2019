package org.usfirst.frc2876.PowerUp2018.Pixy2;

public class Pixy2Response {

    public static final boolean PIXY_RESULT_OK = true;
    public static final boolean PIXY_RESULT_ERROR = false;
    public static final short PIXY_CHECKSUM_SYNC = (short)0xc1af;
    public static final short PIXY_NO_CHECKSUM_SYNC = (short)0xc1ae;

    Pixy2I2C i2c;
    boolean m_cs = false;
    public byte m_type;
    byte m_length;
    public byte[] payload;

    public Pixy2Response(Pixy2I2C i2c) {
        this.i2c = i2c;
    }

    public Pixy2Response() {
        this.i2c = null;
    }

    private boolean getSync() {
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
            // System.out.format("sync: %02X %02X\n", bytePrev, byteCur);
            res = i2c.recv(buf);
            if (res == PIXY_RESULT_OK) {
                byteCur = buf[0];
                // since we're using little endian, previous byte is least significant byte
                // start = cprev;
                // current byte is most significant byte
                // start |= c << 8;
                start = Pixy2.bytesToShort(byteCur, bytePrev);

                // System.out.format("sync: %02X %02X\n", bytePrev, byteCur);
                System.out.format("sync: %02X %02X start: %02X %02X %02X\n", bytePrev, byteCur, start,
                        PIXY_CHECKSUM_SYNC, PIXY_NO_CHECKSUM_SYNC);
                System.out.println("start " + (short) start + " == " + PIXY_CHECKSUM_SYNC);

                bytePrev = byteCur;
                // cprev = c;
                if (start == PIXY_CHECKSUM_SYNC) {
                    m_cs = true;
                    return true;
                }
                if (start == PIXY_NO_CHECKSUM_SYNC) {
                    m_cs = false;
                    return false;
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
        // int csCalc, csSerial;
        boolean res;

        res = getSync();
        if (res != PIXY_RESULT_OK) {
            System.out.println("recv failed to get sync");
            return null;
        }
       
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
}
