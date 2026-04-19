package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro;

import java.awt.Color;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class ElectroVisualProjectile extends NuHorizontalLightningEntity {
   public static final Color COLOR = WyHelper.hexToRGB("#71A3F055");

   public ElectroVisualProjectile(EntityType<? extends ElectroVisualProjectile> type, Level world) {
      super(type, world);
   }

   public ElectroVisualProjectile(Level world, LivingEntity thrower, IAbility ability, float boltSize) {
      super((EntityType)ModProjectiles.ELECTRO_VISUAL.get(), thrower, boltSize, 8.0F, ability);
      this.m_146922_((float)WyHelper.randomWithRange(0, 360));
      this.m_146926_((float)WyHelper.randomWithRange(-20, 20));
      this.setColor(COLOR);
      this.setAngle(60);
      this.setMaxLife(20);
      this.setDamage(0.0F);
      this.setSize(boltSize / 600.0F);
      this.setBranches((int)WyHelper.randomWithRange(1, 3));
      this.setSegments((int)((double)boltSize * 0.6));
   }
}
