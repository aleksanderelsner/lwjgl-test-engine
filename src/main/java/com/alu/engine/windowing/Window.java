package com.alu.engine.windowing;


import com.alu.engine.InputHandler;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private long handle;
    private int height, width;
    private final double[] cursorPosX, cursorPosY;

    public Window() {
        if (!glfwInit()) {
            glfwTerminate();
            throw new IllegalStateException("Failed to init GLFW");
        }

        height = 700;
        width = 1500;
        handle = glfwCreateWindow(width, height, "Window", NULL, NULL);

        glfwMakeContextCurrent(handle);
        GL.createCapabilities();
        glfwShowWindow(handle);

        final var inputHander = InputHandler.getInstance();
        glfwSetKeyCallback(handle, inputHander::handleKeyPress);
        glfwSetMouseButtonCallback(handle, inputHander::handleMousePress);

        cursorPosX = new double[1];
        cursorPosY = new double[1];

        disableCursor();
    }

    public void update() {
        if (InputHandler.getInstance().isKeyPressed(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(handle, true);
        }

        glfwGetCursorPos(handle, cursorPosX, cursorPosY);
        glfwSwapBuffers(handle);
        glfwPollEvents();
    }

    public void disableCursor() {
        glfwSetInputMode(handle, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }

    public int getAspectRatio() {
        return width / height;
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(handle);
    }

    public double cursorPosX() {
        return cursorPosX[0];
    }

    public double cursorPosY() {
        return cursorPosY[0];
    }
}
