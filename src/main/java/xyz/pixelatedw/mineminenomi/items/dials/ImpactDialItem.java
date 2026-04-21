package xyz.pixelatedw.mineminenomi.items.dials;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
// import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
// // import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
// // import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ImpactDialItem extends BlockItem {
   public ImpactDialItem(Block block) {
      super(block, (new Item.Properties()).stacksTo(16));
   }

   public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      if (!world.isClientSide()) {
         player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2, 4));
         // AbilityExplosion explosion = new AbilityExplosion(player, (IAbility)null, (double)player.blockPosition().getX(), (double)player.blockPosition().getY(), (double)player.blockPosition().getZ(), 3.0F);
         // explosion.setStaticDamage(5.0F);
         // // explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION2);
         // explosion.explode();
         player.getCooldowns().addCooldown(this, 10);
         itemstack.shrink(1);
      }

      return InteractionResultHolder.sidedSuccess(itemstack, world.isClientSide());
   }
}
