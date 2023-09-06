package com.alu.engine.rendering;

import static com.alu.engine.utils.ResourceUtil.getResourceAsString;
import static org.lwjgl.opengl.GL46.*;

public class Shader {

    private final String vertexSource, fragmentSource, name;
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
}
