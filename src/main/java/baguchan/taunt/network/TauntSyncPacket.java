package baguchan.taunt.network;

import baguchan.taunt.TauntMod;
import baguchan.taunt.registry.ModAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public class TauntSyncPacket implements CustomPacketPayload, IPayloadHandler<TauntSyncPacket> {

    public static final StreamCodec<FriendlyByteBuf, TauntSyncPacket> STREAM_CODEC = CustomPacketPayload.codec(
            TauntSyncPacket::write, TauntSyncPacket::new
    );
    public static final Type<TauntSyncPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(TauntMod.MODID, "taunt_sync"));

    public int entityID;
    public ResourceLocation taunt;
    public int tick;

    public TauntSyncPacket(int entityID, ResourceLocation taunt, int tick) {
        this.entityID = entityID;
        this.taunt = taunt;
        this.tick = tick;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeInt(this.entityID);
        buffer.writeResourceLocation(this.taunt);
        buffer.writeInt(this.tick);
    }

    public TauntSyncPacket(FriendlyByteBuf buffer) {
        this(buffer.readInt(), buffer.readResourceLocation(), buffer.readInt());
    }

    public void handle(TauntSyncPacket message, IPayloadContext context) {
        context.enqueueWork(() -> {
            Entity entity = Minecraft.getInstance().level.getEntity(message.entityID);
            if (entity != null) {
                entity.getData(ModAttachments.TAUNT).setAction(message.taunt);
                entity.getData(ModAttachments.TAUNT).setActionTick(message.tick);
                entity.playSound(SoundEvents.ANVIL_PLACE, 1.0F, 1.5F);
            }
        });
    }
}