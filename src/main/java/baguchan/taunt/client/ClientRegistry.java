package baguchan.taunt.client;

import baguchan.taunt.TauntMod;
import baguchan.taunt.registry.ModKeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = TauntMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegistry {
    @SubscribeEvent
    public static void registerMapping(RegisterKeyMappingsEvent event){
        event.register(ModKeyMapping.taunt);
    }
}
