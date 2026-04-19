package xyz.pixelatedw.mineminenomi.abilities.chiyu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HealComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class HealingTouchAbility extends PunchAbility {
   private static final float COOLDOWN = 200.0F;
   private static final float HEAL_AMOUNT = 20.0F;
   public static final RegistryObject<AbilityCore<HealingTouchAbility>> INSTANCE = ModRegistry.registerAbility("healing_touch", "Healing Touch", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Touch the target to heal them", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, HealingTouchAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), HealComponent.getTooltip(20.0F)).setSourceType(SourceType.FIST, SourceType.FRIENDLY).build("mineminenomi");
   });
   private final HealComponent healComponent = new HealComponent(this);

   public HealingTouchAbility(AbilityCore<HealingTouchAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.healComponent});
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      this.healComponent.healTarget(entity, target, 20.0F);
      target.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 400, 1));
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.HEALING_TOUCH.get(), entity, target.m_20185_(), target.m_20186_(), target.m_20189_());
      this.increaseUses();
      return true;
   }

   public float getPunchCooldown() {
      return 200.0F;
   }

   public int getUseLimit() {
      return 1;
   }

   public boolean isParallel() {
      return true;
   }
}
