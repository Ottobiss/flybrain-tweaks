package otto.flybrain.tweaks.frames;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.gameevent.GameEvent;
import otto.flybrain.tweaks.mixin.ItemFrameAccessor;

public class InvisibleFrames {
    public static void register() {
            UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
                if (!(world instanceof ServerLevel serverLevel)) {
                    return InteractionResult.PASS;
                } else if (hand == InteractionHand.OFF_HAND) {
                    return InteractionResult.PASS;
                } 
                
                if (!(entity instanceof ItemFrame frame)) {
                    return InteractionResult.PASS;
                }
                if (((ItemFrameAccessor) frame).isFixed()) {
                    return InteractionResult.PASS;
                }
                if (!player.isSecondaryUseActive()) {
                    return InteractionResult.PASS;
                }

                ItemStack heldItem = player.getItemInHand(hand);
                if (!heldItem.is(Items.SHEARS)) {
                    return InteractionResult.PASS;
                }
                if (frame.getItem().isEmpty()) {
                    return InteractionResult.PASS;
                }
                
                frame.setInvisible(!frame.isInvisible());
                world.playSound(null, frame.getX(), frame.getY(), frame.getZ(), SoundEvents.SHEARS_SNIP, frame.getSoundSource(), 1.0F, 1.0F);
                serverLevel.sendParticles(ParticleTypes.CLOUD,  frame.getX(), frame.getY(), frame.getZ(), 10, 0.2, 0.2, 0.2, 0.02);

                heldItem.hurtAndBreak(1, player, hand);
                frame.gameEvent(GameEvent.BLOCK_CHANGE, player);
                return InteractionResult.SUCCESS;
            });
    }
}
