package com.alu.engine.world;

public class World {

    public static final int SIZE = 200;
    public static final int AREA = World.SIZE * World.SIZE;
    public static final int VOLUME = World.AREA * World.SIZE;

    private final byte blocks[];

    public World() {
        blocks = new byte[VOLUME];

        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                for (int z = 0; z < SIZE; z++) {
                    if (y == 0) {
                        blocks[getBlockIndex(x, y, z)] = 1;
                    } else {
                        blocks[getBlockIndex(x, y, z)] = 0;
                    }
                }
            }
        }
    }

    public byte getBlock(final int x, final int y, final int z) {
        if (inBounds(x) && inBounds(y) && inBounds(z)) {
            return blocks[getBlockIndex(x, y, z)];
        }
        return 0;
    }

    public byte getBlockByIndex(final int index) {
        return blocks[index];
    }

    public int getBlockIndex(final int x, final int y, final int z) {
        return x + AREA * y + SIZE * z;
    }

    public boolean inBounds(final int coord) {
        return coord >= 0 && coord < SIZE;
    }
}
