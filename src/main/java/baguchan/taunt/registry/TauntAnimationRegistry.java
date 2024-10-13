package baguchan.taunt.registry;

import bagu_chan.bagus_lib.event.RegisterBagusAnimationEvents;
import baguchan.taunt.TauntMod;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = TauntMod.MODID)
public class TauntAnimationRegistry {
    public static void registryAnimaiton(RegisterBagusAnimationEvents events){
        if(events.getEntity() instanceof Player) {
            TauntMod.registryAccess().registryOrThrow(Taunts.TAUNT_VARIANT_REGISTRY_KEY).holders().forEach(taunt -> {
                events.addAnimationState(taunt.value().tauntLocation());
            });
        }
    }
}
