
package org.usfirst.frc2876.PowerUp2018.Pixy2;

import java.util.Arrays;

public class Pixy2SetLED {

    byte[] rawBytes;
    Pixy2Request request;
    Pixy2Response response;

    byte requestType = 20;
    byte responseType = 21;
    byte responseLength = 4;

    public Pixy2SetLED(Pixy2I2C i2c, byte r, byte g, byte b) {
        byte[] payload = {r,g,b};
        request = new Pixy2Request(requestType, payload);
        if (i2c.send(request.buf())) {
            response = new Pixy2Response(i2c);
            rawBytes = response.recv();
            if (response.m_type != responseType) {
                // TODO throw pixy exception
            }
            if (response.payload.length != responseLength) {
                // TODO throw pixy exception
            }
            // TODO verify checksum?
            parseResponse();
        }
    }


    private void parseResponse() {
        // see https://docs.pixycam.com/wiki/doku.php?id=wiki:v2:protocol_reference#setled-r-g-b

    }

    void print() {
        
    }

}