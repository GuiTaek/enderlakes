package com.gmail.guitaekm.enderlakes;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

import java.util.List;

@Modmenu(modId = "enderlakes")
@Config(name = "enderlakes", wrapperName = "Config")
public class ConfigModel {
    public int nrLakes = 7499981;
    public int powerDistance = 2;
    public List<Integer> cycleWeights = List.of(
            0, 5, 5, 3, 3, 1, 1
    );
    public int minimumDistance = 16;
}
