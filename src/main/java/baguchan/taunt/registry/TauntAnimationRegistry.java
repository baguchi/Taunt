package baguchan.taunt.registry;

import bagu_chan.bagus_lib.event.RegisterBagusAnimationEvents;
import baguchan.taunt.TauntMod;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = TauntMod.MODID)
public class TauntAnimationRegistry {
    @SubscribeEvent
    public static void registryAnimaiton(RegisterBagusAnimationEvents events){
        if(events.getEntity() instanceof Player) {
            events.getEntity().registryAccess().registryOrThrow(Taunts.TAUNT_VARIANT_REGISTRY_KEY).holders().forEach(taunt -> {
                events.addAnimationState(taunt.value().tauntLocation());
            });
        }
    }
}
