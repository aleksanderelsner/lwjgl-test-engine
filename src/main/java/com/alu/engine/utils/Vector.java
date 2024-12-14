package com.alu.engine.utils;

import org.joml.Vector3f;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class Vector {

    private Vector() {
        // util
        throw new IllegalStateException("Util class");
    }

    /**
     * New vector with result of a x b
     */
    public static Vector3f cross(final Vector3f a, final Vector3f b) {
        return use(b, a::cross);
    }

    /**
     * New vector with result of a + b
     */
    public static Vector3f add(final Vector3f a, final Vector3f b) {
        return use(b, a::add);
    }

    /**
     * New vector with result of a - b
     */
    public static Vector3f sub(final Vector3f a, final Vector3f b) {
        return use(b, a::sub);
    }

    public static Vector3f mul(final Vector3f vec3f, final float scalar) {
        return use(scalar, vec3f::mul);
    }

    public static Vector3f normalize(final Vector3f vec3f) {
        return use(vec3f::normalize);
    }

    private static Vector3f use(final UnaryOperator<Vector3f> function) {
        final var result = new Vector3f();
        function.apply(result);
        return result;
    }

    private static Vector3f use(final float b, final BiFunction<Float, Vector3f, Vector3f> function) {
        final var result = new Vector3f();
        function.apply(b, result);
        return result;
    }

    private static Vector3f use(final Vector3f b, final BinaryOperator<Vector3f> function) {
        final var result = new Vector3f();
        function.apply(b, result);
        return result;
    }
}
