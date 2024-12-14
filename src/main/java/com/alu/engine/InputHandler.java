package com.alu.engine;

import lombok.Getter;
import org.lwjgl.glfw.GLFW;

import java.util.HashSet;
import java.util.Set;

public class InputHandler {

    private static InputHandler instance;
    private final Set<Integer> pressedKeyboardKeys;
    private final Set<Integer> pressedMouseKeys;
    @Getter
    private double mousePosX, mousePosY, lastMousePosX, lastMousePosY;
    @Getter
    private boolean mouseMoving;

    private InputHandler() {
        pressedKeyboardKeys = new HashSet<>();
        pressedMouseKeys = new HashSet<>();
    }

    public static InputHandler getInstance() {
        if (instance == null) {
            instance = new InputHandler();
        }
        return instance;
    }

    public void update() {
        handleCursorMovement();
    }

    public void handleCursorMovement() {
        final var window = GlobalContext.getInstance().getWindow();
        if (window == null) {
            throw new RuntimeException("No window specified in the global context");
        }

        final var x = window.cursorPosX();
        final var y = window.cursorPosY();

        mouseMoving = mousePosX != x && mousePosY != y;
        if (!mouseMoving) {
            return;
        }

        lastMousePosX = mousePosX;
        lastMousePosY = mousePosY;

        mousePosX = x;
        mousePosY = y;
    }

    public void handleKeyPress(final long window,
                               final int key,
                               final int scancode,
                               final int action,
                               final int mods) {
        if (action == GLFW.GLFW_PRESS) {
            pressedKeyboardKeys.add(key);
        }
        if (action == GLFW.GLFW_RELEASE) {
            pressedKeyboardKeys.remove(key);
        }
    }

    public void handleMousePress(final long window,
                                 final int button,
                                 final int action,
                                 final int mods) {
        if (action == GLFW.GLFW_PRESS) {
            pressedMouseKeys.add(button);
        }
        if (action == GLFW.GLFW_RELEASE) {
            pressedMouseKeys.remove(button);
        }
    }

    public double deltaMousePosX() {
        return mousePosX - lastMousePosX;
    }

    public double deltaMousePosY() {
        return mousePosY - lastMousePosY;
    }

    public boolean isKeyPressed(final int keys) {
        return pressedKeyboardKeys.contains(keys);
    }
}