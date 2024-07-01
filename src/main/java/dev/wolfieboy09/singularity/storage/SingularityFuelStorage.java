package dev.wolfieboy09.singularity.storage;

import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;

// I took EnergyStorage, and changed it to fuel, so thanks forge!!
public class SingularityFuelStorage {
    protected int fuel;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    /**
     * @param capacity Maximum amount of fuel
     * @apiNote maxReceive and maxExtract will be set to what capacity is
     */
    public SingularityFuelStorage(int capacity) { this(capacity, capacity, capacity, 0); }

    /**
     * @param capacity Maximum amount of fuel
     * @param maxTransfer The maximum transfer rate (receive and extract)
     */
    public SingularityFuelStorage(int capacity, int maxTransfer) { this(capacity, maxTransfer, maxTransfer, 0); }

    /**
     * @param capacity Maximum amount of fuel
     * @param maxReceive Maximum amount of fuel that can go in at once
     * @param maxExtract Maximum amount of fuel that can exit at once
     */
    public SingularityFuelStorage(int capacity, int maxReceive, int maxExtract) { this(capacity, maxReceive, maxExtract, 0); }

    /**
     * @param capacity Maximum amount of fuel
     * @param maxReceive Maximum amount of fuel that can go in at once
     * @param maxExtract Maximum amount of fuel that can exit at once
     * @param fuel How much fuel is given at first
     */
    public SingularityFuelStorage(int capacity, int maxReceive, int maxExtract, int fuel) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.fuel = Math.max(0, Math.min(capacity, fuel));
    }

    /**
     * @param maxReceive Maximum amount of fuel that can go in at once
     * @param simulate bro I don't know what "simulate" is
     * @return int
     */
    public int receiveFuel(int maxReceive, boolean simulate) {
        if (!this.canReceive()) {
            return 0;
        } else {
            int fuelReceived = Math.min(this.capacity - this.fuel, Math.min(this.maxReceive, maxReceive));
            if (!simulate) {
                this.fuel += fuelReceived;
            }
            return fuelReceived;
        }
    }

    /**
     * @param maxExtract Maximum amount of fuel that can exit at once
     * @param simulate Weather to simulate it or not
     * @return int
     */
    public int extractFuel(int maxExtract, boolean simulate) {
        if (!this.canExtract()) {
            return 0;
        } else {
            int fuelExtracted = Math.min(this.fuel, Math.min(this.maxExtract, maxExtract));
            if (!simulate) this.fuel -= fuelExtracted;
            return fuelExtracted;
        }
    }

    /**
     * @return Amount of fuel stored in current instance
     */
    public int getFuelStored() { return this.fuel; }

    /**
     * @return Capacity of fuel in current instance
     */
    public int getMaxFuelStored() { return this.capacity; }

    /**
     * @return Weather or not fuel can be extracted
     */
    public boolean canExtract() { return this.maxExtract > 0; }

    /**
     * @return Weather or not fuel can be received
     */
    public boolean canReceive() { return this.maxReceive > 0; }

    /**
     * @return Tag
     */
    public Tag serializeNBT() { return IntTag.valueOf(this.getFuelStored()); }

    /**
     * @param nbt Tag NBT
     */
    public void deserializeNBT(Tag nbt) {
        if (nbt instanceof IntTag intNbt) {
            this.fuel = intNbt.getAsInt();
        } else {
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        }
    }
}
