package com.alu.engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private final long window;

    public Window() {
        if (!glfwInit()) {
            glfwTerminate();
            throw new IllegalStateException("Failed to initalise glfw");
        }
        window = glfwCreateWindow(300, 300, "Window", NULL, NULL);
    }

    public void start() {
        glfwMakeContextCurrent(window);
        glfwShowWindow(window);
        while (!glfwWindowShouldClose(window)) {
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
        glfwTerminate();
    }
}
