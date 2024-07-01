package dev.wolfieboy09.singularity.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class SingularityCapabilities {
    public static final Capability<IFuelStorage> FUEL = CapabilityManager.get(new CapabilityToken<>() {});
    public SingularityCapabilities() {}
}