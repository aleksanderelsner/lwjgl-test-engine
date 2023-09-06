package com.alu.engine;

import com.alu.engine.rendering.Shader;

import static org.lwjgl.opengl.GL46.*;

public class HelloTriangleScreen implements Screen {

    float[] vertices;

    @Override
    public void init() {
        vertices = new float[]{
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f, 0.5f, 0.0f
        };
    }

    @Override
    public void loop() {
        final var vbo = glGenBuffers();
        final var vao = glGenVertexArrays();

        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0L);
        glEnableVertexAttribArray(0);

        final var shader = new Shader("default");
        shader.compile();
        shader.use();
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }
}
