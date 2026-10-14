package otto.flybrain.tweaks.mixin;

import net.minecraft.world.entity.decoration.ItemFrame;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemFrame.class)
public interface ItemFrameAccessor {
    @Accessor("fixed") boolean isFixed();
}
