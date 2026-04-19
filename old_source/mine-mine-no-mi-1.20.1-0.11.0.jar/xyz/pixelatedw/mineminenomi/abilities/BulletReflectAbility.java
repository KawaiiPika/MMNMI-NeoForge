package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BulletReflectAbility extends PassiveAbility {
   public static final RegistryObject<AbilityCore<BulletReflectAbility>> INSTANCE = ModRegistry.registerAbility("bullet_reflect", "Bullet Reflect", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Reflects bullets", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, BulletReflectAbility::new)).addDescriptionLine(desc).setHidden().build("mineminenomi");
   });
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::onDamageTaken);

   public BulletReflectAbility(AbilityCore<BulletReflectAbility> ability) {
      super(ability);
      this.addComponents(new AbilityComponent[]{this.damageTakenComponent});
   }

   private float onDamageTaken(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      IDamageSourceHandler handler = IDamageSourceHandler.getHandler(damageSource);
      boolean isBullet = handler.hasType(SourceType.PHYSICAL) && handler.hasType(SourceType.PROJECTILE) && handler.hasType(SourceType.BLUNT);
      if (isBullet) {
         Entity var8 = damageSource.m_7640_();
         if (var8 instanceof Projectile) {
            Projectile proj = (Projectile)var8;
            proj.f_19797_ = 0;
            proj.m_5602_(entity);
            AbilityHelper.setDeltaMovement(proj, -proj.m_20184_().f_82479_, -proj.m_20184_().f_82480_, -proj.m_20184_().f_82481_);
         }

         return 0.0F;
      } else {
         return damage;
      }
   }
}
