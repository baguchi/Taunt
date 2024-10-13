package baguchan.taunt.network;

import bagu_chan.bagus_lib.util.client.AnimationUtil;
import baguchan.taunt.Taunt;
import baguchan.taunt.TauntMod;
import baguchan.taunt.attachment.TauntAttachments;
import baguchan.taunt.registry.ModAttachments;
import baguchan.taunt.registry.Taunts;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

import java.util.Optional;

public class TauntSyncPacket implements CustomPacketPayload, IPayloadHandler<TauntSyncPacket> {

    public static final StreamCodec<FriendlyByteBuf, TauntSyncPacket> STREAM_CODEC = CustomPacketPayload.codec(
            TauntSyncPacket::write, TauntSyncPacket::new
    );
    public static final Type<TauntSyncPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(TauntMod.MODID, "taunt"));

    public int entityID;
    public ResourceLocation taunt;

    public TauntSyncPacket(int entityID, ResourceLocation taunt) {
        this.entityID = entityID;
        this.taunt = taunt;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeInt(this.entityID);
        buffer.writeResourceLocation(this.taunt);
    }

    public TauntSyncPacket(FriendlyByteBuf buffer) {
        this(buffer.readInt(), buffer.readResourceLocation());
    }

    public void handle(TauntSyncPacket message, IPayloadContext context) {
        context.enqueueWork(() -> {
            Entity entity = Minecraft.getInstance().level.getEntity(message.entityID);
            if (entity != null) {
                entity.getData(ModAttachments.TAUNT).setAction(message.taunt);
            }
        });
    }
}