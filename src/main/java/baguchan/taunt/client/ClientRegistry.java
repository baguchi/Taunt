package baguchan.taunt.client;

import baguchan.taunt.TauntMod;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = TauntMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegistry {
    public static final KeyMapping taunt= new KeyMapping("key.taunt.taunt", InputConstants.KEY_R, "key.categories.movement");
    @SubscribeEvent
    public static void registerMapping(RegisterKeyMappingsEvent event){
        event.register(taunt);
    }
}
