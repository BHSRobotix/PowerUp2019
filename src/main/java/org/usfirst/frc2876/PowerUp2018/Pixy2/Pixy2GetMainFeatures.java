
package org.usfirst.frc2876.PowerUp2018.Pixy2;

public class Pixy2GetMainFeatures {

    byte[] rawBytes;
    Pixy2Request request;
    Pixy2Response response;
    Pixy2Vector[] vectors;

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
            try {
                rawBytes = response.recv();
            } catch (Pixy2Exception ex) {
                System.out.println(ex);
                ex.printStackTrace();
                return;
            }
            if (response.m_type != responseType) {
                // TODO throw pixy exception
            }
            // TODO verify checksum?
            parseResponse();
        }
    }


    private void parseResponse() {
        // see https://docs.pixycam.com/wiki/doku.php?id=wiki:v2:protocol_reference#getmainfeatures-features-wait
        if (rawBytes.length == 0) {
            return;
        }
        Pixy2.printBytes("getMainFeatures reply:", rawBytes); 
        //getMainFeatures reply:: 01 06 2C 07 3A 0D AB 04

        if(rawBytes[0] == LINE_VECTOR){
            //https://github.com/charmedlabs/pixy2/blob/master/src/host/arduino/libraries/Pixy2/Pixy2Line.h#L180
            int numVectors = rawBytes[1]/VECTOR_LENGTH;
            vectors = new Pixy2Vector[numVectors];
            for(int i = 0; i < numVectors; i++){
                byte[] buf = new byte[VECTOR_LENGTH];
                int offset = (VECTOR_LENGTH * i) + 2;
                System.arraycopy(rawBytes, offset, buf, 0, VECTOR_LENGTH);
                Pixy2Vector v = new Pixy2Vector(buf);
                vectors[i] = v;
                v.print();
            }
        }
        
    }

}