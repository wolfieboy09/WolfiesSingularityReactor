package dev.wolfieboy09.singularity.api.storage;

import net.minecraftforge.energy.EnergyStorage;

public abstract class SingularityEnergyStorage extends EnergyStorage {
    /**
     * @param capacity The max capacity of the entity
     */
    public SingularityEnergyStorage(int capacity) { super(capacity); }

    /**
     * @param capacity The max capacity of the entity
     * @param maxTransfer The maximum transfer rate (receive and extract)
     */
    public SingularityEnergyStorage(int capacity, int maxTransfer) { super(capacity, maxTransfer); }

    /**
     * @param capacity The maximum capacity of the entity
     * @param maxReceive How much energy can be received at once
     * @param maxExtract How much energy can exit at once
     */
    public SingularityEnergyStorage(int capacity, int maxReceive, int maxExtract) { super(capacity, maxReceive, maxExtract); }

    /**
     * @param capacity The max capacity of the entity
     * @param maxReceive How much energy can be received at once
     * @param maxExtract How much energy can exit at once
     * @param energy How much energy it has on the start
     */
    public SingularityEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) { super(capacity, maxReceive, maxExtract, energy); }

    /**
     * @param energy Sets the energy given
     * @implNote If the energy is less than zero, sets the value to 0
     */
    public void setEnergy(int energy) {
        if(energy < 0)
            energy = 0;
        if(energy > this.capacity)
            energy = this.capacity;
        this.energy = energy;
    }

    /**
     * @param energy Amount of energy to add
     */
    public void addEnergy(int energy) {
        setEnergy(this.energy + energy);
    }

    /**
     * @param energy Amount of energy to remove
     */
    public void removeEnergy(int energy) {
        setEnergy(this.energy - energy);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int receiveEnergy = super.receiveEnergy(maxReceive, simulate);
        if (receiveEnergy != 0) {
            onEnergyChanged();
        }
        return receiveEnergy;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extractedEnergy = super.extractEnergy(maxExtract, simulate);
        if (extractedEnergy != 0) {
            onEnergyChanged();
        }
        return extractedEnergy;
    }


    public abstract void onEnergyChanged();
}
