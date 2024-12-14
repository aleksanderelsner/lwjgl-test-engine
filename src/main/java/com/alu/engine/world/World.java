package com.alu.engine.world;

public class World {

    public static final int SIZE = 20;
    public static final int AREA = World.SIZE * World.SIZE;
    public static final int VOLUME = World.AREA * World.SIZE;

    private final byte blocks[];

    public World() {
        blocks = new byte[VOLUME];

        for (int i = 0; i < VOLUME; i++) {
            blocks[i] = 1;
        }
    }

    public byte getBlock(final int x, final int y, final int z) {
        if (inBounds(x) && inBounds(y) && inBounds(z)) {
            return blocks[getBlockIndex(x, y, z)];
        }
        return 0;
    }

    public int getBlockIndex(final int x, final int y, final int z) {
        return x + AREA * y + SIZE * z;
    }

    public boolean inBounds(final int coord) {
        return coord >= 0 && coord < SIZE;
    }
}
