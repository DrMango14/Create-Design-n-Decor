package dev.lopyluna.dndecor.mixins;

import com.simibubi.create.content.trains.display.FlapDisplayBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(targets = "com.simibubi.create.content.trains.display.FlapDisplayBlock$PlacementHelper")
public class FlapDisplayPlacementHelperMixin {
    //@Inject(method = "getItemPredicate()Ljava/util/function/Predicate;", at = @At(value = "HEAD"), cancellable = true)
    //public void getItemPredicate(CallbackInfoReturnable<Predicate<ItemStack>> cir) {
    //    cir.setReturnValue(i -> i.getItem() instanceof BlockItem && ((BlockItem) i.getItem()).getBlock() instanceof FlapDisplayBlock);
    //}
    //@Inject(method = "getStatePredicate()Ljava/util/function/Predicate;", at = @At(value = "HEAD"), cancellable = true)
    //public void getStatePredicate(CallbackInfoReturnable<Predicate<BlockState>> cir) {
    //    cir.setReturnValue(s -> s.getBlock() instanceof FlapDisplayBlock);
    //}
}
