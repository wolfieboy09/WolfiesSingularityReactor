package dev.wolfieboy09.singularity.blockentity.capabilities;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface IFuelStorage {
    int receiveFuel(int maxReceive, boolean simulate);

    int extractFuel(int maxExtract, boolean simulate);

    int getFuelStored();

    int getMaxFuelStored();

    boolean canExtract();

    boolean canReceive();

    void setFuel(int fuel);

    void addFuel(int fuel);

    void removeFuel(int fuel);
}
