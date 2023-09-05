package com.alu.engine;

import static org.lwjgl.opengl.GL46.*;

public class HelloTriangleScene implements Scene {

    float[] vertices;

    @Override
    public void init() {
        vertices = new float[] {
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f,  0.5f, 0.0f
        };
    }

    @Override
    public void loop() {
        final var vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
    }
}
