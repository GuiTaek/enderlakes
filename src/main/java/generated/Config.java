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

    private final Option<java.lang.Integer> nrLakes = this.optionForKey(this.keys.nrLakes);
    private final Option<java.lang.Integer> powerDistance = this.optionForKey(this.keys.powerDistance);
    private final Option<java.lang.String> mySampleString = this.optionForKey(this.keys.mySampleString);
    private final Option<java.util.List<java.lang.Integer>> CycleWeights = this.optionForKey(this.keys.CycleWeights);
    private final Option<java.lang.Integer> minimumDistance = this.optionForKey(this.keys.minimumDistance);

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

    public int nrLakes() {
        return nrLakes.value();
    }

    public void nrLakes(int value) {
        nrLakes.set(value);
    }

    public int powerDistance() {
        return powerDistance.value();
    }

    public void powerDistance(int value) {
        powerDistance.set(value);
    }

    public java.lang.String mySampleString() {
        return mySampleString.value();
    }

    public void mySampleString(java.lang.String value) {
        mySampleString.set(value);
    }

    public java.util.List<java.lang.Integer> CycleWeights() {
        return CycleWeights.value();
    }

    public void CycleWeights(java.util.List<java.lang.Integer> value) {
        CycleWeights.set(value);
    }

    public int minimumDistance() {
        return minimumDistance.value();
    }

    public void minimumDistance(int value) {
        minimumDistance.set(value);
    }


    public static class Keys {
        public final Option.Key nrLakes = new Option.Key("nrLakes");
        public final Option.Key powerDistance = new Option.Key("powerDistance");
        public final Option.Key mySampleString = new Option.Key("mySampleString");
        public final Option.Key CycleWeights = new Option.Key("CycleWeights");
        public final Option.Key minimumDistance = new Option.Key("minimumDistance");
    }
}

