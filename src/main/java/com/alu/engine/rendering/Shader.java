package com.alu.engine.rendering;

import static com.alu.engine.utils.ResourceUtil.getResourceAsString;

public class Shader {

    final String vertexSource;
    final String fragmentSource;

    public Shader(String shaderName) {
        vertexSource = getResourceAsString("shaders/" + shaderName + "/vertex.glsl");
        fragmentSource = getResourceAsString("shaders/" + shaderName + "/vertex.glsl");
    }

}
