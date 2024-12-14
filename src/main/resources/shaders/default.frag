#version 330 core

in vec3 pos;
in vec4 gl_FragCoord;

out vec4 FragColor;

void main() {
    if (pos.x == gl_FragCoord.x) {
        FragColor = vec4(0, 0, 0, 1);
    } else {
        FragColor = vec4(pos, 1.0f);
    }
}