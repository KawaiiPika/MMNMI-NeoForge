package xyz.pixelatedw.mineminenomi.abilities.bomu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ZenshinKibakuAbility extends Ability {
   private static final float CHARGE_TIME = 100.0F;
   private static final float MIN_COOLDOWN = 40.0F;
   private static final float MIAX_COOLDOWN = 140.0F;
   public static final RegistryObject<AbilityCore<ZenshinKibakuAbility>> INSTANCE = ModRegistry.registerAbility("zenshin_kibaku", "Zenshin Kibaku", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user creates a massive explosion from their body", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, ZenshinKibakuAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(40.0F, 140.0F), ChargeComponent.getTooltip(100.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceElement(SourceElement.EXPLOSION).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this, (comp) -> (double)comp.getChargePercentage() > 0.1)).addTickEvent(100, this::tickChargeEvent).addEndEvent(100, this::endChargeEvent);
   private final StackComponent stackComponent = new StackComponent(this);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);
   private int previousPower;

   public ZenshinKibakuAbility(AbilityCore<ZenshinKibakuAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.stackComponent, this.explosionComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 100.0F);
   }

   private void tickChargeEvent(LivingEntity entity, IAbility ability) {
      int power = (int)(this.chargeComponent.getChargePercentage() * 10.0F);
      if (power != this.previousPower) {
         this.stackComponent.setStacks(entity, this, power);
         this.previousPower = power;
      }

   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      float power = this.chargeComponent.getChargePercentage() * 10.0F;
      float cooldown = this.chargeComponent.getChargeTime() + 40.0F;
      if (!entity.m_9236_().f_46443_) {
         AbilityExplosion explosion = this.explosionComponent.createExplosion(entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), power);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), new CommonExplosionParticleEffect.Details((double)power));
         explosion.setStaticDamage(power * 12.0F);
         explosion.m_46061_();
      }

      this.stackComponent.setStacks(entity, ability, 0);
      this.cooldownComponent.startCooldown(entity, cooldown);
   }
}
