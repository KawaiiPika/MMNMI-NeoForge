package xyz.pixelatedw.mineminenomi.abilities.kage;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KageKakumeiAbility extends PunchAbility {
   private static final int SHADOWS_REQUIRED = 20;
   private static final float COOLDOWN = 200.0F;
   public static final RegistryObject<AbilityCore<KageKakumeiAbility>> INSTANCE = ModRegistry.registerAbility("kage_kakumei", "Kage Kakumei", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("By hitting another entity the user impales %s shadows at once into them boosting their physical abilities", new Object[]{MentionHelper.mentionText(20)}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, KageKakumeiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ContinuousComponent.getTooltip()).setSourceType(SourceType.FRIENDLY).build("mineminenomi");
   });

   public KageKakumeiAbility(AbilityCore<KageKakumeiAbility> core) {
      super(core);
      this.addUseEvent(100, (entity, abl) -> KageHelper.hasEnoughShadows(entity, abl, 20));
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      target.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 400, 0));
      target.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 400, 0));
      target.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 400, 0));
      target.m_7292_(new MobEffectInstance(MobEffects.f_19616_, 400, 0));
      target.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 400, 0));
      KageHelper.removeShadows(entity, 20);
      return true;
   }

   public float getPunchCooldown() {
      return 200.0F;
   }

   public int getUseLimit() {
      return 1;
   }
}
