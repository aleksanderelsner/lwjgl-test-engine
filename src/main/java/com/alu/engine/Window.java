package com.alu.engine;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private final long window;
    private final Screen screen;

    public Window() {
        if (!glfwInit()) {
            glfwTerminate();
            throw new IllegalStateException("Failed to initalise glfw");
        }
        window = glfwCreateWindow(1500, 750, "Window", NULL, NULL);
        screen = new HelloTriangleScreen();
    }

    public void start() {
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glfwShowWindow(window);
        screen.init();
        while (!glfwWindowShouldClose(window)) {
            glfwSwapBuffers(window);
            glfwPollEvents();
            screen.loop();
        }
        glfwTerminate();
    }
}
