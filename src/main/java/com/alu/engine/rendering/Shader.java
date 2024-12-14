package com.alu.engine.rendering;

import lombok.Getter;

import java.util.Map;

import static com.alu.engine.utils.ResourceUtil.getResourceAsString;
import static org.lwjgl.opengl.GL20.*;

public class Shader {

    @Getter
    private int id;
    @Getter
    private boolean compiled;
    private final int type;
    private final String source;

    private final static Map<Integer, String> typeToExtension = Map.of(
            GL_VERTEX_SHADER, "vert",
            GL_FRAGMENT_SHADER, "frag"
    );

    public Shader(final String name, final int type) {
        final var fileExt = typeToExtension.get(type);
        if (fileExt == null) {
            throw new RuntimeException("Invalid shader type" + type);
        }

        this.source = getResourceAsString("shaders/%s.%s".formatted(name, fileExt));
        this.type = type;
    }

    public int compile() {
        compiled = true;
        id = glCreateShader(type);

        glShaderSource(id, source);
        glCompileShader(id);

        final var status = glGetShaderi(id, GL_COMPILE_STATUS);

        if (status == GL_FALSE) {
            final var length = glGetShaderi(id, GL_INFO_LOG_LENGTH);
            final var infoLog = glGetShaderInfoLog(id, length);
            throw new RuntimeException(infoLog);
        }

        return id;
    }
}
