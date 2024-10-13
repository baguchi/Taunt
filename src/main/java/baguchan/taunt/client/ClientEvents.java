package baguchan.taunt.client;

import bagu_chan.bagus_lib.api.client.IRootModel;
import bagu_chan.bagus_lib.client.event.BagusModelEvent;
import baguchan.taunt.TauntMod;
import baguchan.taunt.network.TauntPacket;
import baguchan.taunt.registry.ModAttachments;
import baguchan.taunt.registry.ModKeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.entity.animation.json.AnimationLoader;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = TauntMod.MODID, value = Dist.CLIENT)
public class ClientEvents {
    public static int tauntTick;
    @SubscribeEvent
    public static void onKeyPush(ClientTickEvent.Pre event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null)
            return;
        if (tauntTick <= 0 && ModKeyMapping.taunt.isDown()) {
            PacketDistributor.sendToServer(new TauntPacket());
            tauntTick = 12;
        }else {
            if (tauntTick > 0){
                tauntTick--;
            }
        }
    }

    @SubscribeEvent
    public static void animationPostEvent(BagusModelEvent.PostAnimate bagusModelEvent) {
        Entity entity = bagusModelEvent.getEntity();
        IRootModel rootModel = bagusModelEvent.getRootModel();
        if (entity instanceof LivingEntity livingEntity) {
            if (bagusModelEvent.isSupportedAnimateModel() && bagusModelEvent.getEntity().getData(ModAttachments.TAUNT).getActionTick() > 0) {
                rootModel.getBagusRoot().getAllParts().forEach(ModelPart::resetPose);
                rootModel.applyStaticBagu(AnimationLoader.INSTANCE.getAnimationHolder(entity.getData(ModAttachments.TAUNT).getAction()));
            }

        }
    }
}
