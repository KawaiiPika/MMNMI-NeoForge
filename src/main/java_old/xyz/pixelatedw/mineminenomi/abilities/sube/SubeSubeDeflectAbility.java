package xyz.pixelatedw.mineminenomi.abilities.sube;

import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SubeSubeDeflectAbility extends Ability {
   private static final int HOLD_TIME = 240;
   private static final int MIN_COOLDOWN = 80;
   private static final int MAX_COOLDOWN = 200;
   public static final RegistryObject<AbilityCore<SubeSubeDeflectAbility>> INSTANCE = ModRegistry.registerAbility("sube_sube_deflect", "Sube Sube Deflect", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Temporarily makes the user immune to physical attacks, either by weapon or by hand to hand combat, as those attacks would just slip off their body.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SubeSubeDeflectAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(80.0F, 200.0F), ContinuousComponent.getTooltip(240.0F)).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addEndEvent(this::endContinuityEvent);
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::damageTakenEvent);

   public SubeSubeDeflectAbility(AbilityCore<SubeSubeDeflectAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.damageTakenComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 240.0F);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      float cooldown = 80.0F + this.continuousComponent.getContinueTime() / 2.0F;
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   private float damageTakenEvent(LivingEntity user, IAbility ability, DamageSource damageSource, float damage) {
      if (!this.isContinuous()) {
         return damage;
      } else {
         IDamageSourceHandler handler = IDamageSourceHandler.getHandler(damageSource);
         boolean isUnavoidable = handler.isUnavoidable();
         boolean isExplosionOrShockwave = handler.hasElement(SourceElement.EXPLOSION) || handler.hasElement(SourceElement.SHOCKWAVE);
         if (!isUnavoidable && !isExplosionOrShockwave) {
            boolean shouldBlock = false;
            if (!damageSource.m_276093_(DamageTypes.f_268566_) && !damageSource.m_276093_(DamageTypes.f_268464_) && !damageSource.m_269533_(DamageTypeTags.f_268524_)) {
               if (handler.hasType(SourceType.PHYSICAL)) {
                  shouldBlock = true;
               }
            } else {
               shouldBlock = true;
            }

            return shouldBlock ? 0.0F : damage;
         } else {
            return damage;
         }
      }
   }
}
