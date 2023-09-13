package com.alu.engine;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private final long windowHandle;
    private final Screen screen;

    public Window() {
        if (!glfwInit()) {
            glfwTerminate();
            throw new IllegalStateException("Failed to initalise glfw");
        }
        windowHandle = glfwCreateWindow(1500, 750, "Window", NULL, NULL);
        screen = new HelloCubeScene();
    }

    public void start() {
        glfwMakeContextCurrent(windowHandle);
        GL.createCapabilities();
        glfwShowWindow(windowHandle);
        screen.init();

        double frameStart = glfwGetTime();
        double frameFinish;
        double deltaTime = 0;
        while (!glfwWindowShouldClose(windowHandle)) {
            
            glClearColor(0.5f, 0.2f, 0.1f, 1f);
            glClear(GL_COLOR_BUFFER_BIT);

            screen.loop(deltaTime);

            glfwSwapBuffers(windowHandle);
            glfwPollEvents();
            frameFinish = glfwGetTime();
            deltaTime = frameFinish - frameStart;
            frameStart = frameFinish;
        }
        glfwTerminate();
    }
}
