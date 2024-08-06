package dev.wolfieboy09.singularity.intergration.computercraft;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import dev.wolfieboy09.singularity.blockentity.entities.SolarPanelBlockEntity;
import dev.wolfieboy09.wolfieslib.api.annotations.NothingNullByDefault;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@NothingNullByDefault
public class SolarPanelPeripheral implements EnergyUnit, IPeripheral {
    private final SolarPanelBlockEntity solarPanel;

    public SolarPanelPeripheral(final SolarPanelBlockEntity solarPanel) {
        this.solarPanel = solarPanel;
    }

    @Override
    @LuaFunction(mainThread = true)
    public final int getEnergyCapacity() {
        return solarPanel.energyStorage().getEnergyStored();
    }

    @Override
    @LuaFunction(mainThread = true)
    public final int getEnergyStored() {
        return solarPanel.energyStorage().getEnergyStored();
    }

    @Override
    @LuaFunction(mainThread = true)
    public final boolean canReceive() {
        return solarPanel.energyStorage().canReceive();
    }

    @Override
    @LuaFunction(mainThread = true)
    public final boolean canExtract() {
        return solarPanel.energyStorage().canExtract();
    }

    @LuaFunction(mainThread = true)
    public final boolean canSeeSun() {
        return solarPanel.canSeeSun(Objects.requireNonNull(solarPanel.getLevel()), solarPanel.getBlockPos());
    }

    @LuaFunction(mainThread = true)
    public final float generationRate() {
        return solarPanel.generationRate();
    }

    @Override
    public String getType() {
        return "solarPanel";
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return false;
    }
}
