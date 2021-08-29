package cc.i9mc.uhc.common;

import cc.i9mc.uhc.UHC;
import cc.i9mc.uhc.utils.BiomeReplacerUtil;

public class Swap {
    private String from;
    private String to;

    public Swap(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public void execute() {
        BiomeReplacerUtil biomeReplacerUtil = UHC.getInstance().getBiomeReplacerUtil();
        biomeReplacerUtil.swap(Biome.valueOf(from), Biome.valueOf(to));
    }
}