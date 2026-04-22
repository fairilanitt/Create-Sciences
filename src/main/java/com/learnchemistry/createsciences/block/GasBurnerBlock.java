package com.learnchemistry.createsciences.block;

import com.learnchemistry.createsciences.chemistry.reaction.HeatLevel;
import net.minecraft.world.level.block.Block;

public class GasBurnerBlock extends Block {
    public GasBurnerBlock(Properties properties) {
        super(properties);
    }

    public static boolean generatesHeatAtRest() {
        return true;
    }

    public static HeatLevel burnerHeatLevel() {
        return HeatLevel.BURNER;
    }

    public boolean generatesHeat() {
        return generatesHeatAtRest();
    }

    public HeatLevel heatLevel() {
        return burnerHeatLevel();
    }
}
