package com.alu.engine.rendering;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import static com.alu.engine.utils.ResourceUtil.getResourceAsString;
import static org.lwjgl.opengl.GL46.*;

public class Shader {

    private final String vertexSource;
    private final String fragmentSource;
    private final String name;
    private int program;

    public Shader(String shaderName) {
        this.name = shaderName;
        vertexSource = getResourceAsString("shaders/" + shaderName + "/vertex.glsl");
        fragmentSource = getResourceAsString("shaders/" + shaderName + "/fragment.glsl");
    }

    public void compile() {
        final var vertex = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertex, vertexSource);
        glCompileShader(vertex);

        if (glGetShaderi(vertex, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new IllegalStateException(String.format("Failed to compile vertex shader '%s': %s",
                    name, glGetShaderInfoLog(vertex)));
        }

        final var fragment = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragment, fragmentSource);
        glCompileShader(fragment);

        if (glGetShaderi(fragment, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new IllegalStateException(String.format("Failed to compile fragment shader '%s': %s",
                    name, glGetShaderInfoLog(fragment)));
        }

        program = glCreateProgram();
        glAttachShader(program, vertex);
        glAttachShader(program, fragment);
        glLinkProgram(program);

        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            throw new IllegalStateException(String.format("Failed to compile fragment shader '%s': %s",
                    name, glGetProgramInfoLog(fragment)));
        }

        glDeleteShader(vertex);
        glDeleteShader(fragment);
    }

    public void use() {
        glUseProgram(program);
    }

    public void detach() {
        glUseProgram(0);
    }

    public void uniformMat4f(final String name, final Matrix4f mat4f) {
        final var loc = glGetUniformLocation(program, name);
        final var buffer = BufferUtils.createFloatBuffer(16);
        glUniformMatrix4fv(loc, false, mat4f.get(buffer));
    }
}
