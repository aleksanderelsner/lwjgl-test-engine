package com.alu.engine.rendering;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {

    private int id;
    private Shader[] shaders;
    private boolean currentlyUsed;

    public ShaderProgram() {
        this.id = glCreateProgram();
    }

    public ShaderProgram(final Shader... shaders) {
        this.id = glCreateProgram();
        attachShaders(shaders);
    }

    public void attachShaders(final Shader... shaders) {
        this.shaders = shaders;
        for (final var shader : shaders) {
            if (!shader.isCompiled()) {
                shader.compile();
            }
            glAttachShader(id, shader.getId());
        }
    }

    public void use() {
        if (currentlyUsed) {
            return;
        }
        currentlyUsed = true;
        glLinkProgram(id);

        final var status = glGetProgrami(id, GL_LINK_STATUS);

        if (status == GL_FALSE) {
            final var length = glGetProgrami(id, GL_INFO_LOG_LENGTH);
            final var infoLog = glGetProgramInfoLog(id, length);

            throw new RuntimeException(infoLog);
        }
        glUseProgram(id);
    }

    public void detach() {
        currentlyUsed = false;
        glUseProgram(0);
    }

    public void uploadUniform(final String uniform, final Object value) {
        final var location = glGetUniformLocation(id, uniform);

        switch (value) {
            case Matrix4f mat4f -> uploadMatrix4f(location, mat4f);
            default -> throw new IllegalStateException("Unexpected value: " + value);
        }
    }

    private void uploadMatrix4f(final int location, final Matrix4f mat4f) {
        final var buffer = BufferUtils.createFloatBuffer(16);
        mat4f.get(buffer);
        glUniformMatrix4fv(location, false, buffer);
    }
}
