package com.gmail.guitaekm.enderlakes;

import blue.endless.jankson.Jankson;
import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.util.Observable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Config extends ConfigWrapper<com.gmail.guitaekm.enderlakes.ConfigModel> {

    public final Keys keys = new Keys();

    private final Option<java.lang.Integer> mySampleInt = this.optionForKey(this.keys.mySampleInt);
    private final Option<java.lang.String> mySampleString = this.optionForKey(this.keys.mySampleString);

    private Config() {
        super(com.gmail.guitaekm.enderlakes.ConfigModel.class);
    }

    private Config(Consumer<Jankson.Builder> janksonBuilder) {
        super(com.gmail.guitaekm.enderlakes.ConfigModel.class, janksonBuilder);
    }

    public static Config createAndLoad() {
        var wrapper = new Config();
        wrapper.load();
        return wrapper;
    }

    public static Config createAndLoad(Consumer<Jankson.Builder> janksonBuilder) {
        var wrapper = new Config(janksonBuilder);
        wrapper.load();
        return wrapper;
    }

    public int mySampleInt() {
        return mySampleInt.value();
    }

    public void mySampleInt(int value) {
        mySampleInt.set(value);
    }

    public java.lang.String mySampleString() {
        return mySampleString.value();
    }

    public void mySampleString(java.lang.String value) {
        mySampleString.set(value);
    }


    public static class Keys {
        public final Option.Key mySampleInt = new Option.Key("mySampleInt");
        public final Option.Key mySampleString = new Option.Key("mySampleString");
    }
}

