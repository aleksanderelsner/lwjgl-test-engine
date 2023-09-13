package com.alu.engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Player {

    private Vector3f pos;
    private Vector3f direction;
    private Vector3f target;
    private Vector3f up;
    private Vector3f right;


    public Player() {
        this.pos = new Vector3f(0f, 0f, 3f);
        this.target = new Vector3f();
        this.direction = new Vector3f(this.pos).sub(this.target).normalize();

        final var upDir = new Vector3f(0, 1f, 0f);
        this.right = upDir.cross(direction).normalize();
        this.up = new Vector3f(direction).cross(right);
    }

    public Matrix4f getView() {
        return new Matrix4f().lookAt(pos, new Vector3f(pos).add(target), up);
    }
}
