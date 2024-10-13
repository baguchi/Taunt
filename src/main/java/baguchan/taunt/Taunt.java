package baguchan.taunt;

import baguchan.taunt.registry.Taunts;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public class Taunt {
    public static final Codec<Taunt> DIRECT_CODEC = RecordCodecBuilder.create(
            p_332779_ -> p_332779_.group(
                            ResourceLocation.CODEC.fieldOf("taunt_location").forGetter(p_335261_ -> p_335261_.tauntLocation)
                    )
                    .apply(p_332779_, Taunt::new)
    );
    public static final Codec<Holder<Taunt>> CODEC = RegistryFileCodec.create(Taunts.TAUNT_VARIANT_REGISTRY_KEY, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<Taunt>> STREAM_CODEC = ByteBufCodecs.holderRegistry(Taunts.TAUNT_VARIANT_REGISTRY_KEY);
    private final ResourceLocation tauntLocation;

    public Taunt(ResourceLocation tauntLocation) {
        this.tauntLocation = tauntLocation;;
    }

    public ResourceLocation tauntLocation() {
        return this.tauntLocation;
    }

    @Override
    public boolean equals(Object p_332811_) {
        if (p_332811_ == this) {
            return true;
        } else {
            return !(p_332811_ instanceof Taunt wolfvariant)
                    ? false
                    : Objects.equals(this.tauntLocation, wolfvariant.tauntLocation);
        }
    }

    @Override
    public int hashCode() {
        int i = 1;
        return 31 * i + this.tauntLocation.hashCode();
    }
}