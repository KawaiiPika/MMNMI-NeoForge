package xyz.pixelatedw.mineminenomi.abilities.netsu;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
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
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class NetsuEnhancementAbility extends PunchAbility {
   private static final int HOLD_TIME = 800;
   private static final int MIN_COOLDOWN = 100;
   private static final int MAX_COOLDOWN = 500;
   public static final RegistryObject<AbilityCore<NetsuEnhancementAbility>> INSTANCE = ModRegistry.registerAbility("netsu_enhancement", "Netsu Enhancement", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Increases the user's attacks and body functions through heat.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, NetsuEnhancementAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F, 500.0F), ContinuousComponent.getTooltip(800.0F), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private static final AbilityAttributeModifier MULTIPLIER;
   private static final AbilityOverlay OVERLAY;
   private final SkinOverlayComponent skinOverlayComponent;

   public NetsuEnhancementAbility(AbilityCore<NetsuEnhancementAbility> core) {
      super(core);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.addComponents(new AbilityComponent[]{this.skinOverlayComponent});
      this.clearUseChecks();
      this.statsComponent.addAttributeModifier(Attributes.f_22283_, MULTIPLIER);
      this.continuousComponent.addStartEvent(this::startContinuityEvent);
      this.continuousComponent.addTickEvent(this::duringContinuityEvent);
      this.continuousComponent.addEndEvent(this::endContinuityEvent);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.showAll(entity);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_ && this.continuousComponent.getContinueTime() % 5.0F == 0.0F) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.NETSU_ENCHANTMENT.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.hideAll(entity);
      float cooldown = 100.0F + this.continuousComponent.getContinueTime() / 2.0F;
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   public float getPunchDamage() {
      return 10.0F;
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      AbilityHelper.setSecondsOnFireBy(target, 5, entity);
      return true;
   }

   public boolean isParallel() {
      return true;
   }

   public int getUseLimit() {
      return -1;
   }

   public float getPunchCooldown() {
      return -1.0F;
   }

   static {
      MULTIPLIER = new AbilityAttributeModifier(UUID.fromString("efa08cbd-57e5-478f-b15c-6295eb1b375e"), INSTANCE, "Netsu Enhancement Modifier", (double)0.25F, Operation.MULTIPLY_BASE);
      OVERLAY = (new AbilityOverlay.Builder()).setRenderType(AbilityOverlay.RenderType.ENERGY).setColor(WyHelper.hexToRGB("#962A2AAA")).build();
   }
}
