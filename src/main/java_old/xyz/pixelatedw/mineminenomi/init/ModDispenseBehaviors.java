package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import xyz.pixelatedw.mineminenomi.entities.NetEntity;

public class ModDispenseBehaviors {
   public static void init() {
      DispenserBlock.m_52672_((ItemLike)ModItems.ROPE_NET.get(), new AbstractProjectileDispenseBehavior() {
         protected Projectile m_6895_(Level level, Position position, ItemStack stack) {
            NetEntity netEntity = NetEntity.createNormalNet(level, (LivingEntity)null);
            netEntity.m_6034_(position.m_7096_(), position.m_7098_(), position.m_7094_());
            return netEntity;
         }
      });
      DispenserBlock.m_52672_((ItemLike)ModItems.KAIROSEKI_NET.get(), new AbstractProjectileDispenseBehavior() {
         protected Projectile m_6895_(Level level, Position position, ItemStack stack) {
            NetEntity netEntity = NetEntity.createKairosekiNet(level, (LivingEntity)null);
            netEntity.m_6034_(position.m_7096_(), position.m_7098_(), position.m_7094_());
            return netEntity;
         }
      });
   }
}
