package dev.wolfieboy09.singularity.intergration.computercraft;

public interface EnergyUnit {
    int getEnergyCapacity();
    int getEnergyStored();
    boolean canReceive();
    boolean canExtract();
}
