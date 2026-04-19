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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ImpactDialItem extends BlockItem {
   public ImpactDialItem(Block block) {
      super(block, (new Item.Properties()).m_41487_(16));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      if (!world.f_46443_) {
         player.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 2, 4));
         AbilityExplosion explosion = new AbilityExplosion(player, (IAbility)null, (double)player.m_20183_().m_123341_(), (double)player.m_20183_().m_123342_(), (double)player.m_20183_().m_123343_(), 3.0F);
         explosion.setStaticDamage(5.0F);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION2);
         explosion.m_46061_();
         player.m_36335_().m_41524_(this, 10);
         itemstack.m_41774_(1);
      }

      return InteractionResultHolder.m_19090_(itemstack);
   }
}
