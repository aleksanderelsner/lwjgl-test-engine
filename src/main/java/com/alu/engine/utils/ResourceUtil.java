package com.alu.engine.utils;

import java.io.IOException;
import java.io.InputStream;

public class ResourceUtil {

    public static String getResourceAsString(final String path) {
        return new String(getResourceAsByteArray(path));
    }

    public static byte[] getResourceAsByteArray(final String path) {
        try {
            return getResourceAsStream(path).readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read bytes from resource: " + path);
        }
    }

    public static InputStream getResourceAsStream(final String path) {
        return ResourceUtil.class.getClassLoader().getResourceAsStream(path);
    }
}
