package dev.wolfieboy09.singularity.blockentity.capabilities;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface IFuelStorage {
    int receiveEnergy(int var1, boolean var2);

    int extractEnergy(int var1, boolean var2);

    int getEnergyStored();

    int getMaxEnergyStored();

    boolean canExtract();

    boolean canReceive();

    void setFuel(int var1);

    int addFuel(int var1);

    int removeFuel(int var1);
}
