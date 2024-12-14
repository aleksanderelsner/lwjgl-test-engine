#version 330 core

layout (location = 0) in vec3 vPos;

out vec3 pos;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main() {
    pos = vPos;
    gl_Position = projection * view * model * vec4(vPos, 1.0);
}