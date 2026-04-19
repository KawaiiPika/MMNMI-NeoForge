package xyz.pixelatedw.mineminenomi.abilities.nikyu;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class HanpatsuAbility extends PunchAbility {
   private static final int COOLDOWN = 100;
   public static final RegistryObject<AbilityCore<HanpatsuAbility>> INSTANCE = ModRegistry.registerAbility("hanpatsu", "Hanpatsu", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Anyone the user punches gets sent flying far into the air", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, HanpatsuAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceType(SourceType.FIST).build("mineminenomi");
   });

   public HanpatsuAbility(AbilityCore<HanpatsuAbility> core) {
      super(core);
   }

   public float getPunchDamage() {
      return 0.0F;
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      double xPower = WyHelper.randomDouble() * (double)400.0F;
      if (xPower >= (double)0.0F) {
         xPower = Mth.m_14008_(xPower, (double)200.0F, (double)400.0F);
      } else {
         xPower = Mth.m_14008_(xPower, (double)-400.0F, (double)-200.0F);
      }

      double zPower = WyHelper.randomDouble() * (double)400.0F;
      if (zPower >= (double)0.0F) {
         zPower = Mth.m_14008_(zPower, (double)200.0F, (double)400.0F);
      } else {
         zPower = Mth.m_14008_(zPower, (double)-400.0F, (double)-200.0F);
      }

      target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.LAUNCHED.get(), 1200, 0));
      AbilityHelper.setDeltaMovement(target, xPower, (double)10.0F, zPower);
      target.f_19789_ = 0.0F;
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.PAD_HO_SFX.get(), SoundSource.PLAYERS, 2.0F, 0.75F);
      this.continuousComponent.stopContinuity(entity);
      return true;
   }

   public int getUseLimit() {
      return 1;
   }

   public float getPunchCooldown() {
      return 100.0F;
   }
}
