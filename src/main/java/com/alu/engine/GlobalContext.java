package com.alu.engine;

import com.alu.engine.windowing.Window;
import lombok.Getter;
import lombok.Setter;


public class GlobalContext {

    private static GlobalContext instance;

    @Getter
    @Setter
    private Window window;

    private GlobalContext() {
    }

    public static GlobalContext getInstance() {
        if (instance == null) {
            instance = new GlobalContext();
        }
        return instance;
    }
}
