package cc.i9mc.uhc.utils;

import cc.i9mc.uhc.common.Biome;
import net.minecraft.server.v1_8_R3.BiomeBase;

import java.lang.reflect.Field;
import java.util.Arrays;

public class BiomeReplacerUtil {
    public BiomeBase[] copy;
    public BiomeBase[] biomes;

    public BiomeReplacerUtil() {
        try {
            Field biomeF = BiomeBase.class.getDeclaredField("biomes");
            biomeF.setAccessible(true);
            biomes = (BiomeBase[]) biomeF.get(null);
            copy = Arrays.copyOf(biomes, biomes.length);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void swap(Biome from, Biome to) {
        biomes[from.getId()] = copy[to.getId()];
    }

    public void swap(Biome to) {
        for (int i = 0; i < biomes.length; i++) {
            if (i != to.getId() && biomes[i] != null)
                biomes[i] = copy[to.getId()];
        }
    }
}
