package com.example.util;

public class RespUtil {

    private RespUtil() {}
    
    private static final String CRLF = "\r\n";
    private static final char PLUS = '+';
    private static final char DOLLAR = '$';

    public static String encode(String data) {
        return "+OK"+data+"\r\n";
    }
    
    public static String decode(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }

        String cmd = new String(bytes);

        if (isSimpleString(cmd)) {
            return decodeSimpleString(cmd);
        } else if (isBulkString(cmd)) {
            return decodeBulkString(cmd);
        }

        return "";
    }

    private static boolean isSimpleString(String cmd) {
        if (cmd == null || cmd.isEmpty()) {
            return false;
        }

        return cmd.charAt(0) == PLUS;
    }
    
    private static String decodeSimpleString(String cmd) {
        if (cmd == null || cmd.isEmpty()) {
            return "";
        }
        
        int crlfIndex = cmd.indexOf(CRLF);
        if (crlfIndex == -1) {
            return "";
        }
        
        return cmd.substring(3, crlfIndex);
    }

    private static boolean isBulkString(String cmd) {
        if (cmd == null || cmd.isEmpty()) {
            return false;
        }

        if (cmd.charAt(0) != DOLLAR) {
            return false;
        }

        int crlfFirstIndex = cmd.indexOf(CRLF);
        int crlfLastIndex = cmd.lastIndexOf(CRLF);
        if (crlfFirstIndex == -1) {
            return false;
        }

        String value = cmd.substring(crlfFirstIndex + 1, crlfLastIndex);
        return value.length() == Character.getNumericValue(cmd.charAt(1));
    }

    private static String decodeBulkString(String cmd) {
        if (cmd == null || cmd.isEmpty()) {
            return "";
        }

        int crlfIndex = cmd.indexOf(CRLF);
        if (crlfIndex == -1) {
            return "";
        }

        return cmd.substring(2, crlfIndex);
    }
}
