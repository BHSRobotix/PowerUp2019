package org.usfirst.frc2876.PowerUp2018.Pixy2;

import java.util.Arrays;

public class Pixy2Version extends Pixy2Packet {

    short hardware;
    byte firmwareMajor;
    byte firmwareMinor;
    short firmwareBuild;
    String firmwareType;
    byte[] rawBytes;

    public Pixy2Version(byte[] rawBytes) {
        this.rawBytes = rawBytes;
        parseBytes();
    }

    byte type = 0x0E;

    private void parseBytes() {
        // 0x00 // first byte of hardware version (little endian -> least-significant
        // byte first)
        // 0x22 // second byte of hardware version
        // 0x03 // firmware major version number
        // 0x00 // firmware minor version number
        // 0x0a // first byte of firmware build number (little endian ->
        // least-significant byte first)
        // 0x00 // second byte of firmware build number
        // 0x67 // byte 0 of firmware type ASCII string
        // 0x65 // byte 1 of firmware type ASCII string
        // 0x6e // byte 2 of firmware type ASCII string
        // 0x65 // byte 3 of firmware type ASCII string
        // 0x72 // byte 4 of firmware type ASCII string
        // 0x61 // byte 5 of firmware type ASCII string
        // 0x6c // byte 6 of firmware type ASCII string
        // 0x00 // byte 7 of firmware type ASCII string
        // 0x00 // byte 8 of firmware type ASCII string
        // 0x00 // byte 9 of firmware type ASCII string

        hardware = cvt(rawBytes[1], rawBytes[0]);
        firmwareMajor = rawBytes[2];
        firmwareMinor = rawBytes[3];
        firmwareBuild = cvt(rawBytes[5], rawBytes[4]);
        firmwareType = new String(Arrays.copyOfRange(rawBytes, 6, rawBytes.length));
    }

    void print() {
        String str = String.format("hardware ver: 0x%x firmware ver: %d.%d.%d %s", hardware, firmwareMajor,
                firmwareMinor, firmwareBuild, firmwareType);
        System.out.println(str);
    }

}