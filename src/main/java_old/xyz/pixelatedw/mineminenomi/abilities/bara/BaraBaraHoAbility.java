package xyz.pixelatedw.mineminenomi.abilities.bara;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bara.BaraBaraHoProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BaraBaraHoAbility extends Ability {
   private static final int COOLDOWN = 80;
   public static final RegistryObject<AbilityCore<BaraBaraHoAbility>> INSTANCE = ModRegistry.registerAbility("bara_bara_ho", "Bara Bara Ho", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Launches the user's fist towards the enemy, if the user holds a weapon in hand this will increase the fist's damage as well.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BaraBaraHoAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(80.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final MorphComponent morphComponent = new MorphComponent(this);
   private int morphTick;

   public BaraBaraHoAbility(AbilityCore<BaraBaraHoAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.projectileComponent, this.morphComponent});
      this.addCanUseCheck(BaraHelper::hasLimbs);
      this.addTickEvent(this::tickEvent);
      this.addUseEvent(this::useEvent);
   }

   private void tickEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_ && this.morphComponent.isMorphed()) {
         if (this.morphTick > 0) {
            --this.morphTick;
         } else {
            this.morphComponent.stopMorph(entity);
         }
      }

   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity, 2.0F, 1.0F);
      int projectileLife = this.projectileComponent.getShotProjectile().getLife();
      this.morphComponent.startMorph(entity, (MorphInfo)ModMorphs.BARA_HO.get());
      this.morphTick = projectileLife;
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.NO_HANDS.get(), projectileLife, 0));
      this.cooldownComponent.startCooldown(entity, 80.0F);
   }

   private BaraBaraHoProjectile createProjectile(LivingEntity entity) {
      BaraBaraHoProjectile proj = new BaraBaraHoProjectile(entity.m_9236_(), entity, this);
      ItemStack stack = entity.m_21205_();
      float extraDamage = 0.0F;
      if (ItemsHelper.isSword(stack)) {
         extraDamage = ItemsHelper.getItemDamage(stack);
      }

      proj.setDamage(proj.getDamage() + extraDamage);
      return proj;
   }
}
