package baguchan.taunt.attachment;

import baguchan.taunt.network.TauntSyncPacket;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.UnknownNullability;

public class TauntAttachments  {
     private int actionTick;
    private ResourceLocation action;

    public void setActionTick(int actionTick) {
        this.actionTick = actionTick;
    }

    public int getActionTick() {
        return actionTick;
    }

    public void setAction(ResourceLocation action) {
        this.action = action;
    }

    public void syncAction(Entity entity, ResourceLocation action) {
        this.action = action;
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, new TauntSyncPacket(entity.getId(), action));
    }

    public ResourceLocation getAction() {
        return action;
    }
}