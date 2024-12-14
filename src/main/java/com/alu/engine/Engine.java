package com.alu.engine;


import com.alu.engine.rendering.Renderer;
import com.alu.engine.windowing.Window;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

public class Engine {

    private final GlobalContext context;
    private final InputHandler inputHandler;
    private final Renderer renderer;

    public Engine() {
        inputHandler = InputHandler.getInstance();
        context = GlobalContext.getInstance();
        context.setWindow(new Window());
        renderer = new Renderer();
    }

    public void run() {
        init();
        update();
    }

    private void init() {

    }

    private void update() {
        var deltaTime = 0.0f;
        var startTime = glfwGetTime();
        while (!context.getWindow().shouldClose()) {
            context.getWindow().update();
            inputHandler.update();

            renderer.render(deltaTime);

            final var endTime = glfwGetTime();
            deltaTime = (float) (endTime - startTime);
            startTime = endTime;
        }
        glfwTerminate();
    }
}