package baguchan.taunt.registry;

import baguchan.taunt.Taunt;
import baguchan.taunt.TauntMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class Taunts {
    public static final ResourceKey<Registry<Taunt>> TAUNT_VARIANT_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(TauntMod.MODID, "taunt"));
}
