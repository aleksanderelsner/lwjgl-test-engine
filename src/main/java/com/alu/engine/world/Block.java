package com.alu.engine.world;

import lombok.Getter;

@Getter
public class Block {

    private Type type;

    public Block(final Type type) {
        this.type = type;
    }

    public enum Type {
        AIR,
        STONE
    }
}
