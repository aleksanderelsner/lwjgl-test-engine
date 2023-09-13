package com.alu.engine;

import com.alu.engine.rendering.Shader;
import org.joml.Matrix4f;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.opengl.GL46.*;

public class HelloCubeScene implements Screen {

    private float[] vertices;

    @Override
    public void init() {
        vertices = new float[]{
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f, 0.5f, 0.0f
        };
    }

    @Override
    public void loop(double deltaTime) {
        final var vbo = glGenBuffers();
        final var vao = glGenVertexArrays();

        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0L);
        glEnableVertexAttribArray(0);


        var model = new Matrix4f();
        var projection = new Matrix4f().perspective(45, 800f/600f, 0.1f, 100f);

        final var shader = new Shader("default");
        shader.compile();
        shader.use();
        shader.uniformMat4f("model", model);

        var camX = (float) sin(glfwGetTime()) * 5f;
        var camZ = (float) cos(glfwGetTime()) * 5f;
        var view = new Matrix4f().lookAt(camX, 0f, camZ, 0f, 0f ,0f, 0f, 1f, 0f);
        shader.uniformMat4f("view", view);
        shader.uniformMat4f("projection", projection);

        glDrawArrays(GL_TRIANGLES, 0, 3);
    }
}
