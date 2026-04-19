package xyz.pixelatedw.mineminenomi.mixins;

import java.util.List;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import xyz.pixelatedw.mineminenomi.handlers.entity.KairosekiCoatingHandler;

@Mixin({BoatItem.class})
public class BoatItemMixin {
   @Inject(
      method = {"use"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z",
   shift = Shift.AFTER
)},
      locals = LocalCapture.CAPTURE_FAILEXCEPTION
   )
   public void mineminenomi$use(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir, ItemStack _stack, HitResult _hitResult, Vec3 _vec, double _d, List<Entity> _list, Boat boat) {
      KairosekiCoatingHandler.applyExistingKairosekiCoatingFromItem(player, hand, boat);
   }

   @Inject(
      method = {"use"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z",
   shift = Shift.AFTER
)},
      locals = LocalCapture.CAPTURE_FAILSOFT
   )
   public void mineminenomi$arclight$use(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir, ItemStack _stack, BlockHitResult _hitResult, Vec3 _vec, double _d, List<Entity> _list, Boat boat) {
      KairosekiCoatingHandler.applyExistingKairosekiCoatingFromItem(player, hand, boat);
   }
}
