package xyz.pixelatedw.mineminenomi.abilities.noro;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class KyubiRushAbility extends PunchAbility {
   private static final float COOLDOWN = 100.0F;
   private static final float MIN_DAMAGE = 1.0F;
   private static final float MAX_DAMAGE = 100.0F;
   private static final AbilityDescriptionLine.IDescriptionLine STATS_TOOLTIP = (entity, ability) -> {
      Component attackAttrName = Component.m_237115_(Attributes.f_22281_.m_22087_());
      Component attackStatText = (new AbilityStat.Builder(attackAttrName, 1.0F, 100.0F)).build().getStatDescription(2);
      StringBuilder sb = new StringBuilder();
      sb.append("§a" + ModI18nAbilities.DESCRIPTION_STAT_NAME_STATS.getString() + "§r\n");
      sb.append(attackStatText.getString());
      return Component.m_237113_(sb.toString());
   };
   public static final RegistryObject<AbilityCore<KyubiRushAbility>> INSTANCE = ModRegistry.registerAbility("kyubi_rush", "Kyubi Rush", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("While the opponent is slowed, the user delivers a series of punches, which hits the opponent all at once (a stronger slowness effect causes more damage)", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, KyubiRushAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F), STATS_TOOLTIP).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);

   public KyubiRushAbility(AbilityCore<KyubiRushAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.dealDamageComponent});
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      float damageFromSlowness = 0.0F;
      if (target.m_21023_((MobEffect)ModEffects.NORO_SLOWNESS.get())) {
         damageFromSlowness = Math.min((float)target.m_21124_((MobEffect)ModEffects.NORO_SLOWNESS.get()).m_19557_() / 15.0F, 100.0F);
         int newTime = target.m_21124_((MobEffect)ModEffects.NORO_SLOWNESS.get()).m_19557_() / 2;
         int newAmplifier = Math.min(target.m_21124_((MobEffect)ModEffects.NORO_SLOWNESS.get()).m_19564_() - 2, 0);
         target.m_21195_((MobEffect)ModEffects.NORO_SLOWNESS.get());
         target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.NORO_SLOWNESS.get(), newTime, newAmplifier));
      } else {
         damageFromSlowness = 1.0F;
      }

      WyHelper.spawnDamageIndicatorParticles(entity.m_9236_(), target, Math.round(damageFromSlowness));
      this.dealDamageComponent.hurtTarget(entity, target, damageFromSlowness, source);
      return true;
   }

   public int getUseLimit() {
      return 1;
   }

   public float getPunchCooldown() {
      return 100.0F;
   }
}
