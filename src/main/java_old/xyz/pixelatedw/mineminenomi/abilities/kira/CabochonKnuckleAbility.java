package xyz.pixelatedw.mineminenomi.abilities.kira;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class CabochonKnuckleAbility extends PunchAbility {
   private static final AbilityOverlay OVERLAY;
   private static final float COOLDOWN = 160.0F;
   public static final RegistryObject<AbilityCore<CabochonKnuckleAbility>> INSTANCE;
   private final SkinOverlayComponent skinOverlayComponent;

   public CabochonKnuckleAbility(AbilityCore<CabochonKnuckleAbility> core) {
      super(core);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.addComponents(new AbilityComponent[]{this.skinOverlayComponent});
      this.continuousComponent.addStartEvent(100, this::onContinuityStart);
      this.continuousComponent.addEndEvent(100, this::onContinuityEnd);
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.showAll(entity);
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.hideAll(entity);
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      Vec3 speed = entity.m_20154_().m_82542_((double)3.0F, (double)1.0F, (double)3.0F);
      AbilityHelper.setDeltaMovement(target, target.m_20184_().m_82520_(speed.f_82479_, (double)0.5F, speed.f_82481_));
      target.m_6853_(false);
      return true;
   }

   public boolean isParallel() {
      return true;
   }

   public float getPunchDamage() {
      return 15.0F;
   }

   public float getPunchCooldown() {
      return 160.0F;
   }

   public int getUseLimit() {
      return 1;
   }

   static {
      OVERLAY = (new AbilityOverlay.Builder()).setTexture(ModResources.DIAMOND_BODY).setOverlayPart(AbilityOverlay.OverlayPart.LIMB).build();
      INSTANCE = ModRegistry.registerAbility("cabochon_knuckle", "Cabochon Knuckle", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Covers the user's punch in a diamond coating, dealing damage and slight knockback.", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, CabochonKnuckleAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(160.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
      });
   }
}
