package xyz.pixelatedw.mineminenomi.abilities.sabi;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
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
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class RustTouchAbility extends PunchAbility {
   private static final float COOLDOWN = 300.0F;
   public static final RegistryObject<AbilityCore<RustTouchAbility>> INSTANCE = ModRegistry.registerAbility("rust_touch", "Rust Touch", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Rusts the enemy and the items equipped on them", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, RustTouchAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private static final AbilityOverlay OVERLAY;
   private final SkinOverlayComponent skinOverlayComponent;

   public RustTouchAbility(AbilityCore<RustTouchAbility> core) {
      super(core);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.addComponents(new AbilityComponent[]{this.skinOverlayComponent});
      this.continuousComponent.addStartEvent(100, this::startContinuityEvent);
      this.continuousComponent.addEndEvent(100, this::endContinuityEvent);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.showAll(entity);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.hideAll(entity);
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      target.m_7292_(new MobEffectInstance(MobEffects.f_19615_, 160, 2));
      target.m_7292_(new MobEffectInstance(MobEffects.f_19599_, 160, 1));
      target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 160, 1));

      for(ItemStack stack : ItemsHelper.getAllInventoryItems(target)) {
         if (!stack.m_41619_() && stack.m_204117_(ModTags.Items.RUSTY)) {
            if (stack.m_41763_()) {
               stack.m_41622_(stack.m_41776_() / 3 + 1 + 1, target, (e) -> e.m_21166_(EquipmentSlot.MAINHAND));
            } else {
               stack.m_41774_(1 + this.random.nextInt(4));
            }
         }
      }

      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.RUST_TOUCH.get(), entity, target.m_20185_(), target.m_20186_(), target.m_20189_());
      return true;
   }

   public int getUseLimit() {
      return 1;
   }

   public float getPunchDamage() {
      return 10.0F;
   }

   public float getPunchCooldown() {
      return 300.0F;
   }

   static {
      OVERLAY = (new AbilityOverlay.Builder()).setOverlayPart(AbilityOverlay.OverlayPart.LIMB).setTexture(ModResources.RUST_TOUCH_ARM).build();
   }
}
