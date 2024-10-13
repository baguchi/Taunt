package baguchan.taunt;

import bagu_chan.bagus_lib.util.client.AnimationUtil;
import baguchan.taunt.attachment.TauntAttachments;
import baguchan.taunt.registry.ModAttachments;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = TauntMod.MODID)
public class CommonEvents {
    @SubscribeEvent
    public static void tickEvent(EntityTickEvent.Post event) {
        TauntAttachments tauntAttachments = event.getEntity().getData(ModAttachments.TAUNT);
        if (tauntAttachments.getActionTick() > 0) {
            event.getEntity().setDeltaMovement(0, 0, 0);
            tauntAttachments.setActionTick(tauntAttachments.getActionTick() - 1);
            if (tauntAttachments.getActionTick() == 0) {
                if (!event.getEntity().level().isClientSide()) {
                    AnimationUtil.sendStopAnimation(event.getEntity(), tauntAttachments.getAction());
                }
            }
        }
    }

    @SubscribeEvent
    public static void attackEvent(AttackEntityEvent event) {
        TauntAttachments tauntAttachments = event.getEntity().getData(ModAttachments.TAUNT);
        if (tauntAttachments.getActionTick() > 0) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void clickBlockEvent(PlayerInteractEvent.RightClickBlock event) {
        TauntAttachments tauntAttachments = event.getEntity().getData(ModAttachments.TAUNT);
        if (tauntAttachments.getActionTick() > 0) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void clickItemEvent(PlayerInteractEvent.RightClickItem event) {
        TauntAttachments tauntAttachments = event.getEntity().getData(ModAttachments.TAUNT);
        if (tauntAttachments.getActionTick() > 0) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onKnockBack(LivingKnockBackEvent event) {
        TauntAttachments taunt = event.getEntity().getData(ModAttachments.TAUNT);
        if (taunt != null && taunt.getActionTick() > 0) {
            event.setStrength(0F);
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void onProjectile(ProjectileImpactEvent event) {
        if (event.getRayTraceResult() instanceof EntityHitResult hitResult) {
            TauntAttachments taunt = hitResult.getEntity().getData(ModAttachments.TAUNT);
            if (taunt != null && taunt.getActionTick() > 0) {
                event.setCanceled(true);
                event.getProjectile().deflect(ProjectileDeflection.REVERSE, event.getProjectile().getOwner(), event.getProjectile().getOwner(), true);
            }
        }
    }

    @SubscribeEvent
    public static void onHurt(LivingIncomingDamageEvent event) {
        TauntAttachments taunt = event.getEntity().getData(ModAttachments.TAUNT);
        if (taunt != null && taunt.getActionTick() > 0 && event.getSource().isDirect() && event.getSource().getDirectEntity() != null) {
            event.setAmount(0);
            event.setCanceled(true);
        }
    }
}
