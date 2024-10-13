package baguchan.taunt.network;

import bagu_chan.bagus_lib.util.client.AnimationUtil;
import baguchan.taunt.Taunt;
import baguchan.taunt.TauntMod;
import baguchan.taunt.registry.ModAttachments;
import baguchan.taunt.registry.Taunts;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

import java.util.Optional;

public class TauntPacket implements CustomPacketPayload, IPayloadHandler<TauntPacket> {

    public static final StreamCodec<FriendlyByteBuf, TauntPacket> STREAM_CODEC = CustomPacketPayload.codec(
            TauntPacket::write, TauntPacket::new
    );
    public static final Type<TauntPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(TauntMod.MODID, "taunt"));


    public TauntPacket() {
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void write(FriendlyByteBuf buffer) {
    }

    public TauntPacket(FriendlyByteBuf buffer) {
        this();
    }

    public void handle(TauntPacket message, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            Optional<Holder.Reference<Taunt>> taunt = TauntMod.registryAccess().registryOrThrow(Taunts.TAUNT_VARIANT_REGISTRY_KEY).getRandom(player.getRandom());
            if(taunt.isPresent()) {
                player.getData(ModAttachments.TAUNT).setActionTick(20);
                player.getData(ModAttachments.TAUNT).syncAction(player, taunt.get().value().tauntLocation());
                AnimationUtil.sendAnimation(player, taunt.get().value().tauntLocation());

                if(player.level() instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer){
                    serverLevel.sendParticles(ParticleTypes.FLASH, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), 1, 0, 0, 0, 1.0F);
                }
            }
        });
    }
}