package com.caplin.upgradetool;

public class FilenameDecoder {
    public String decode(String encoded) {
        int len;
        String key = "";
        char code;

        len = encoded.length();

        for (int i = 0; i < len; i++) {
            if (encoded.charAt(i) != '%') {
                key += encoded.charAt(i);
            } else {
                if (len < i + 2) {
                    throw new IllegalArgumentException("Unable to decode <" + encoded + ">");
                }

                int decodedChar = 0;
                code = encoded.charAt(++i); //skip % char
                code -= code >= 'A' ? 'A' - 10 : '0';
                decodedChar = code << 4;

                code = encoded.charAt(++i);
                code -= code >= 'A' ? 'A' - 10 : '0';
                key += (char)(decodedChar + code);
            }
        }
        return key;
    }
}
