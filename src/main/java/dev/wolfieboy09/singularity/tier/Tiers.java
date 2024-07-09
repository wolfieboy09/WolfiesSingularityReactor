package dev.wolfieboy09.singularity.tier;


import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;

public enum Tiers {
    STARTER(Rarity.create("starter", ChatFormatting.WHITE)),
    NEOPHYTE(Rarity.create("neophyte", ChatFormatting.DARK_GREEN)),
    VANGUARD(Rarity.create("vanguard", ChatFormatting.YELLOW)),
    ARCANIST(Rarity.create("arcanist", ChatFormatting.RED)),
    PARAGON(Rarity.create("paragon", ChatFormatting.DARK_BLUE)),
    EXEMPLAR(Rarity.create("exemplar", ChatFormatting.DARK_PURPLE));

    Tiers(Rarity rarity) {}
}