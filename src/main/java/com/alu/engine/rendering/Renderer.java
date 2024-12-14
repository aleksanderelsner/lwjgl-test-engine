package com.alu.engine.rendering;

import com.alu.engine.GlobalContext;
import com.alu.engine.Player;
import com.alu.engine.world.World;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static com.alu.engine.utils.Vector.add;
import static org.joml.Math.toRadians;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Renderer {

    private static final int MAX_VERTICES = 100_000;
    private FloatBuffer vertexBuffer;
    private int vertexCount = 0;

    private Player player;
    private GlobalContext context;
    private boolean initialised;
    private World world;

    public Renderer() {
        context = GlobalContext.getInstance();
        player = new Player();
    }

    public void init() {
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        //glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        //glCullFace(GL_FRONT);

        vertexBuffer = BufferUtils.createFloatBuffer(MAX_VERTICES * 3);
        world = new World();
        initialised = true;
    }

    public void render(final float deltaTime) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        if (!initialised) {
            init();
        }
        player.update(deltaTime);

        //shaders
        final var vertexShader = new Shader("default", GL_VERTEX_SHADER);
        final var fragmentShader = new Shader("default", GL_FRAGMENT_SHADER);
        final var program = new ShaderProgram(vertexShader, fragmentShader);
        program.use();

        final var vao = glGenVertexArrays();
        glBindVertexArray(vao);

        final var vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        final var model = new Matrix4f();
        final var view = new Matrix4f().lookAt(player.getPosition(), add(player.getPosition(), player.getFront()), player.getUp());
        final var projection = new Matrix4f().perspective(toRadians(60f), context.getWindow().getAspectRatio(), 0.1f, 100f);

        program.uploadUniform("model", model);
        program.uploadUniform("view", view);
        program.uploadUniform("projection", projection);

        final var ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        processVertexData(world);
        flush();
    }

    void processVertexData(final World world) {
        for (int x = 0; x < World.SIZE; x++) {
            for (int y = 0; y < World.SIZE; y++) {
                for (int z = 0; z < World.SIZE; z++) {
                    // TOP FACE
                    if (world.getBlock(x, y + 1, z) == 0) {
                        addVertex(x - 1f, y + 1f, z - 1f);
                        addVertex(x - 1f, y + 1f, z + 1f);
                        addVertex(x + 1f, y + 1f, z + 1f);
                        addVertex(x + 1f, y + 1f, z - 1f);
                    }

                    // BOTTOM FACE
                    if (world.getBlock(x, y - 1, z) == 0) {
                        addVertex(x - 1f, y - 1f, z - 1f);
                        addVertex(x + 1f, y - 1f, z - 1f);
                        addVertex(x + 1f, y - 1f, z + 1f);
                        addVertex(x - 1f, y - 1f, z + 1f);
                    }

                    // FRONT FACE
                    if (world.getBlock(x - 1, y, z) == 0) {
                        addVertex(x - 1f, y + 1f, z - 1f);
                        addVertex(x - 1f, y - 1f, z - 1f);
                        addVertex(x - 1f, y - 1f, z + 1f);
                        addVertex(x - 1f, y + 1f, z + 1f);
                    }

                    // BACK FACE
                    if (world.getBlock(x + 1, y, z) == 0) {
                        addVertex(x + 1f, y + 1f, z + 1f);
                        addVertex(x + 1f, y - 1f, z + 1f);
                        addVertex(x + 1f, y - 1f, z - 1f);
                        addVertex(x + 1f, y + 1f, z - 1f);
                    }

                    // LEFT FACE
                    if (world.getBlock(x, y, z - 1) == 0) {
                        addVertex(x + 1f, y - 1f, z - 1f);
                        addVertex(x - 1f, y - 1f, z - 1f);
                        addVertex(x - 1f, y + 1f, z - 1f);
                        addVertex(x + 1f, y + 1f, z - 1f);
                    }

                    // RIGHT FACE
                    if (world.getBlock(x, y, z + 1) == 0) {
                        addVertex(x + 1f, y + 1f, z + 1f);
                        addVertex(x - 1f, y + 1f, z + 1f);
                        addVertex(x - 1f, y - 1f, z + 1f);
                        addVertex(x + 1f, y - 1f, z + 1f);
                    }
                }
            }
        }
    }

    void flush() {
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer.flip(), GL_STATIC_DRAW);
        glDrawArrays(GL_QUADS, 0, vertexBuffer.limit());

        vertexBuffer.clear();
    }

    public void addVertex(float x, float y, float z) {
        if (vertexCount == MAX_VERTICES) {
            flush();
            vertexCount = 0;
        }

        vertexBuffer.put(x);
        vertexBuffer.put(y);
        vertexBuffer.put(z);

        vertexCount++;
    }
}
