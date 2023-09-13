package com.alu.engine.utils;

import java.io.IOException;
import java.io.InputStream;

public class ResourceUtil {

    private ResourceUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String getResourceAsString(final String path) {
        return new String(getResourceAsByteArray(path));
    }

    public static byte[] getResourceAsByteArray(final String path) {
        try {
            return getResourceAInputStream(path).readAllBytes();
        } catch (IOException e) {
            throw new ResourceException("Failed to read bytes from resource: " + path, e);
        }
    }

    public static InputStream getResourceAInputStream(final String path) {
        return ResourceUtil.class.getClassLoader().getResourceAsStream(path);
    }

    private static class ResourceException extends RuntimeException {
        public ResourceException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}
