package dev.wolfieboy09.singularity.storage;

import dev.wolfieboy09.singularity.blockentity.capabilities.IFuelStorage;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;


public class FuelStorage implements IFuelStorage, INBTSerializable<Tag> {
    protected int fuel;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    /**
     * @param capacity Maximum amount of fuel
     * @apiNote maxReceive and maxExtract will be set to what capacity is
     */
    public FuelStorage(int capacity) { this(capacity, capacity, capacity, 0); }

    /**
     * @param capacity Maximum amount of fuel
     * @param maxTransfer The maximum transfer rate (receive and extract)
     */
    public FuelStorage(int capacity, int maxTransfer) { this(capacity, maxTransfer, maxTransfer, 0); }

    /**
     * @param capacity Maximum amount of fuel
     * @param maxReceive Maximum amount of fuel that can go in at once
     * @param maxExtract Maximum amount of fuel that can exit at once
     */
    public FuelStorage(int capacity, int maxReceive, int maxExtract) { this(capacity, maxReceive, maxExtract, 0); }

    /**
     * @param capacity Maximum amount of fuel
     * @param maxReceive Maximum amount of fuel that can go in at once
     * @param maxExtract Maximum amount of fuel that can exit at once
     * @param fuel How much fuel is given at first
     */
    public FuelStorage(int capacity, int maxReceive, int maxExtract, int fuel) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.fuel = Math.max(0, Math.min(capacity, fuel));
    }


    /**
     * @param maxReceive Maximum amount of fuel that can go in at once
     * @param simulate Weather to simulate or not
     * @return int
     */
    @Override
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
    @Override
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
    @Override
    public int getFuelStored() {
        return this.fuel;
    }

    /**
     * @return Amount of fuel stored in current instance
     */
    @Override
    public int getMaxFuelStored() {
        return this.capacity;
    }

    /**
     * @return Weather or not fuel can be extracted
     */
    @Override
    public boolean canExtract() {
        return this.maxExtract > 0;
    }

    /**
     * @return Weather or not fuel can be received
     */
    @Override
    public boolean canReceive() {
        return this.maxReceive > 0;
    }

    /**
     * @return Tag
     */
    @Override
    public Tag serializeNBT() {
        return IntTag.valueOf(this.getFuelStored());
    }

    /**
     * @param nbt NBT data
     */
    @Override
    public void deserializeNBT(Tag nbt) {
        if (nbt instanceof IntTag intNbt) {
            this.fuel = intNbt.getAsInt();
        } else {
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        }
    }

    /**
     * @param fuel Sets the energy given
     */
    @Override
    public void setFuel(int fuel) {
        if(fuel < 0) fuel = 0;
        if(fuel > this.capacity) fuel = this.capacity;
        this.fuel = fuel;
    }

    /**
     * @param fuel Amount of energy to add
     */
    @Override
    public void addFuel(int fuel) {
        setFuel(this.fuel + fuel);
    }

    /**
     * @param fuel Amount of energy to remove
     */
    @Override
    public void removeFuel(int fuel) {
        setFuel(this.fuel - fuel);
    }
}
