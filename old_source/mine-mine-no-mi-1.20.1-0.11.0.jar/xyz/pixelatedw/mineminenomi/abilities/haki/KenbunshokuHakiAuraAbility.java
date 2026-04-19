package xyz.pixelatedw.mineminenomi.abilities.haki;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class KenbunshokuHakiAuraAbility extends Ability {
   public static final RegistryObject<AbilityCore<KenbunshokuHakiAuraAbility>> INSTANCE = ModRegistry.registerAbility("kenbunshoku_haki_aura", "Kenbunshoku Haki: Aura", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Uses Observation Haki to see the auras of all nearby creatures, differentiated by colors.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.HAKI, KenbunshokuHakiAuraAbility::new)).addDescriptionLine(desc).setUnlockCheck(KenbunshokuHakiAuraAbility::canUnlock).build("mineminenomi");
   });
   private static final int HOLD_TIME = 2400;
   private static final int MIN_COOLDOWN = 60;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::startContinuityEvent).addEndEvent(this::endContinuityEvent);

   public KenbunshokuHakiAuraAbility(AbilityCore<KenbunshokuHakiAuraAbility> core) {
      super(core);
      this.setOGCD();
      this.addComponents(new AbilityComponent[]{this.continuousComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 2400.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.KENBUNSHOKU_HAKI_ON_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.KENBUNSHOKU_HAKI_OFF.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
      float cooldown = Math.max(60.0F, this.continuousComponent.getContinueTime());
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   private static boolean canUnlock(LivingEntity user) {
      IHakiData hakiProps = (IHakiData)HakiCapability.get(user).orElse((Object)null);
      IEntityStats statsProps = (IEntityStats)EntityStatsCapability.get(user).orElse((Object)null);
      if (hakiProps != null && statsProps != null) {
         return statsProps.getDoriki() > (double)1500.0F && (double)hakiProps.getKenbunshokuHakiExp() > HakiHelper.getKenbunshokuAuraExpNeeded(user);
      } else {
         return false;
      }
   }
}
