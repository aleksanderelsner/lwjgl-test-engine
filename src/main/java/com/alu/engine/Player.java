package com.alu.engine;

import lombok.Getter;
import org.joml.Vector3f;

import static com.alu.engine.utils.Vector.*;
import static java.lang.Math.*;
import static org.lwjgl.glfw.GLFW.*;

public class Player {

    @Getter
    private Vector3f position, front, up;
    private InputHandler inputHandler;
    private float speed;
    private double pitch, yaw;

    public Player() {
        position = new Vector3f(0, 0, 0);
        front = new Vector3f(0, 0, -1);
        up = new Vector3f(0, 1, 0);

        speed = 10.5f;
        pitch = 0;
        yaw = 0;

        inputHandler = InputHandler.getInstance();
    }

    public void update(final float deltaTime) {
        handleMovement(deltaTime);
    }

    public Vector3f getRight() {
        return cross(front, up).normalize();
    }

    public void handleMovement(final float deltaTime) {
        final var posY = position.y;
        final var speed = this.speed * deltaTime;
        if (inputHandler.isKeyPressed(GLFW_KEY_W)) {
            position.add(mul(normalize(front), speed));
        }
        if (inputHandler.isKeyPressed(GLFW_KEY_S)) {
            position.sub(mul(normalize(front), speed));
        }
        if (inputHandler.isKeyPressed(GLFW_KEY_A)) {
            position.sub(mul(getRight(), speed));
        }
        if (inputHandler.isKeyPressed(GLFW_KEY_D)) {
            position.add(mul(getRight(), speed));
        }
        position.y = posY;
        if (inputHandler.isKeyPressed(GLFW_KEY_SPACE)) {
            position.y += speed;
        }
        if (inputHandler.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            position.y -= speed;
        }
        System.out.println("Position " + position);
        if (inputHandler.isMouseMoving()) {
            yaw += inputHandler.deltaMousePosX() * deltaTime;
            pitch -= inputHandler.deltaMousePosY() * deltaTime;

            pitch = clamp(pitch, -89, 89);

            final var x = (float) (cos(toRadians(yaw)) * cos(toRadians(pitch)));
            final var y = (float) (sin(toRadians(pitch)));
            final var z = (float) (sin(toRadians(yaw)) * cos(toRadians(pitch)));

            front = new Vector3f(x, y, z).normalize();
        }
    }
}
