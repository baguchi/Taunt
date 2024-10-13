package baguchan.taunt.registry;

import baguchan.taunt.TauntMod;
import baguchan.taunt.attachment.TauntAttachments;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, TauntMod.MODID);

    public static final Supplier<AttachmentType<TauntAttachments>> TAUNT = ATTACHMENT_TYPES.register(
            "taunt", () -> AttachmentType.builder(TauntAttachments::new).build());
}