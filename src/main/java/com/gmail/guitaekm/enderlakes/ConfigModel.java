package com.gmail.guitaekm.enderlakes;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

@Modmenu(modId = "enderlakes")
@Config(name = "enderlakes", wrapperName = "Config")
public class ConfigModel {
    public int mySampleInt = 0;
    public String mySampleString = "hello world";
}
