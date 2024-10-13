package baguchan.taunt;

import baguchan.taunt.network.TauntPacket;
import baguchan.taunt.network.TauntSyncPacket;
import baguchan.taunt.registry.ModAttachments;
import baguchan.taunt.registry.Taunts;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.util.thread.EffectiveSide;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

@Mod(TauntMod.MODID)
public class TauntMod {
    public static final String MODID = "taunt";

    public static final String NETWORK_PROTOCOL = "2";


    public TauntMod(ModContainer modContainer, IEventBus modBus) {

        IEventBus forgeBus = NeoForge.EVENT_BUS;

        ModAttachments.ATTACHMENT_TYPES.register(modBus);
        modBus.addListener(DataPackRegistryEvent.NewRegistry.class, event -> event.dataPackRegistry(Taunts.TAUNT_VARIANT_REGISTRY_KEY, Taunt.DIRECT_CODEC, Taunt.DIRECT_CODEC));

        modBus.addListener(this::setup);
        modBus.addListener(this::setupPackets);
    }


    public void setupPackets(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0").optional();
        registrar.playBidirectional(TauntPacket.TYPE, TauntPacket.STREAM_CODEC, (handler, payload) -> handler.handle(handler, payload));
        registrar.playBidirectional(TauntSyncPacket.TYPE, TauntSyncPacket.STREAM_CODEC, (handler, payload) -> handler.handle(handler, payload));
    }

    public void setup(FMLCommonSetupEvent event) {

    }


    public static RegistryAccess registryAccess() {
        if (EffectiveSide.get().isServer()) {
            return ServerLifecycleHooks.getCurrentServer().registryAccess();
        }
        return Minecraft.getInstance().getConnection().registryAccess();
    }
}
