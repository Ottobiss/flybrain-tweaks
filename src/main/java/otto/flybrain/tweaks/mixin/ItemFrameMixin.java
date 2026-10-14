package otto.flybrain.tweaks.mixin;

import net.minecraft.world.entity.decoration.ItemFrame;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemFrame.class)
public class ItemFrameMixin {
	@Inject(at = @At("TAIL"), method = "setItem(Lnet/minecraft/world/item/ItemStack;Z)V")
	private void showItemFrame(CallbackInfo info) {
		ItemFrame frame = (ItemFrame) (Object) this;
		if (frame.getItem().isEmpty()) {
			frame.setInvisible(false);
		}
	}
}