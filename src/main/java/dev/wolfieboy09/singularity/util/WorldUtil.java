package dev.wolfieboy09.singularity.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class WorldUtil {
    public static boolean canSeeSun(Level world, BlockPos pos) {
        return world != null && world.dimensionType().hasSkyLight() && world.getSkyDarken() < 4 && world.canSeeSky(pos);
    }
}
