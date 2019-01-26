
package org.usfirst.frc2876.PowerUp2018.Pixy2;

import java.util.Arrays;

public class Pixy2GetMainFeatures {

    byte[] rawBytes;
    Pixy2Request request;
    Pixy2Response response;

    public static final byte LINE_VECTOR = 1;
    public static final byte VECTOR_LENGTH = 6;

    byte requestType = 48;
    byte responseType = 49;

    public Pixy2GetMainFeatures(Pixy2I2C i2c) {
        //https://docs.pixycam.com/wiki/doku.php?id=wiki:v2:line_api
        //https://docs.pixycam.com/wiki/doku.php?id=wiki:v2:protocol_reference#getmainfeatures-features-wait
        byte[] payload = {0, 1};
        request = new Pixy2Request(requestType, payload);
        if (i2c.send(request.buf())) {
            response = new Pixy2Response(i2c);
            rawBytes = response.recv();
            if (response.m_type != responseType) {
                // TODO throw pixy exception
            }
            // TODO verify checksum?
            parseResponse();
        }
    }


    private void parseResponse() {
        // see https://docs.pixycam.com/wiki/doku.php?id=wiki:v2:protocol_reference#getmainfeatures-features-wait
        if(rawBytes[0] == 0){
            return;
        }
        if(rawBytes[0] == LINE_VECTOR){
            //https://github.com/charmedlabs/pixy2/blob/master/src/host/arduino/libraries/Pixy2/Pixy2Line.h#L180
            int numVectors = rawBytes[1]/VECTOR_LENGTH;
            for(int i = 0; i < numVectors; i++){
                byte[] buf = new byte[VECTOR_LENGTH];
                int offset = (VECTOR_LENGTH * numVectors) + 2;
                System.arraycopy(rawBytes, offset, buf, 0, VECTOR_LENGTH);
                Pixy2Vector v = new Pixy2Vector(buf);
                v.print();
            }
        }
        
    }

}