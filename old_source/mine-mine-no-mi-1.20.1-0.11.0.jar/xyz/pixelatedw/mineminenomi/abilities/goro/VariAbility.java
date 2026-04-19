package xyz.pixelatedw.mineminenomi.abilities.goro;

import java.awt.Color;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.math.VectorHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.entities.LightningDischargeEntity;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class VariAbility extends Ability {
   private static final ResourceLocation DEFAULT_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/vari.png");
   private static final ResourceLocation ALT_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/alts/vari.png");
   private static final Component ONE_MILLION_V_VARI_NAME;
   private static final Component TWENTY_MILLION_V_VARI_NAME;
   private static final Component ONE_HUNDRED_MILLION_V_VARI_NAME;
   private static final Component TWO_HUNDRED_MILLION_V_VARI_NAME;
   private static final int CHARGE_TIME = 20;
   public static final RegistryObject<AbilityCore<VariAbility>> INSTANCE;
   private final ChargeComponent chargeComponent = (new ChargeComponent(this, true)).addStartEvent(100, this::onChargeStart).addTickEvent(this::onChargeTick).addEndEvent(this::onChargeEnd);
   private final AltModeComponent<Mode> altModeComponent;
   private final DealDamageComponent dealDamageComponent;
   private final AnimationComponent animationComponent;
   private final Interval dischargeInterval;

   public VariAbility(AbilityCore<VariAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<Mode>(this, Mode.class, VariAbility.Mode.ONE_MILLION_V)).addChangeModeEvent(this::onAltModeChange);
      this.dealDamageComponent = new DealDamageComponent(this);
      this.animationComponent = new AnimationComponent(this);
      this.dischargeInterval = new Interval(5);
      this.setDisplayIcon(DEFAULT_ICON);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.altModeComponent, this.dealDamageComponent, this.animationComponent});
      this.addUseEvent(this::onUseEvent);
      this.addEquipEvent(this::equipEvent);
   }

   public void equipEvent(LivingEntity entity, IAbility ability) {
      this.setDisplayIcon(DEFAULT_ICON);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 20.0F);
   }

   private void onChargeStart(LivingEntity entity, IAbility ability) {
      this.dischargeInterval.restartIntervalToZero();
      this.animationComponent.start(entity, ModAnimations.POINT_ARMS);
   }

   private void onChargeTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.dischargeInterval.canTick()) {
            Vec3 pos = VectorHelper.getRelativeOffset(entity.m_20182_(), entity.f_20883_, new Vec3((double)0.0F, 1.15, 0.8));
            float multi = 1.0F;
            if (((MorphInfo)ModMorphs.VOLT_AMARU.get()).isActive(entity)) {
               multi += 0.25F;
            }

            LightningDischargeEntity discharge = new LightningDischargeEntity(entity, pos.f_82479_, pos.f_82480_, pos.f_82481_, entity.m_146908_(), entity.m_146909_());
            discharge.setAliveTicks(5);
            discharge.setLightningLength(0.2F * multi);
            discharge.setColor(Color.WHITE);
            discharge.setOutlineColor(ClientConfig.isGoroBlue() ? ElThorAbility.BLUE_THUNDER : ElThorAbility.YELLOW_THUNDER);
            discharge.setDetails(20);
            discharge.setDensity(40);
            discharge.setSize(0.2F * multi);
            discharge.setSkipSegments(1);
            entity.m_9236_().m_7967_(discharge);
         }

      }
   }

   private void onChargeEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         float multi = 1.0F;
         if (((MorphInfo)ModMorphs.VOLT_AMARU.get()).isActive(entity)) {
            multi += 0.25F;
         }

         DamageSource ablSource = this.dealDamageComponent.getDamageSource(entity);
         IDamageSourceHandler handler = IDamageSourceHandler.getHandler(ablSource);
         handler.addAbilityPiercing(0.75F, this.getCore());
         handler.setUnavoidable();
         float radius = 12.0F * this.chargeComponent.getChargePercentage();
         Vec3 pos = VectorHelper.getRelativeOffset(entity.m_20182_(), entity.f_20883_, new Vec3((double)0.5F, 1.15, 0.8));
         LightningDischargeEntity discharge = new LightningDischargeEntity(entity, pos.f_82479_, pos.f_82480_, pos.f_82481_, entity.m_146908_(), entity.m_146909_());
         discharge.setAliveTicks(15);
         discharge.setLightningLength(radius / 2.0F * multi);
         discharge.setColor(Color.WHITE);
         discharge.setOutlineColor(ClientConfig.isGoroBlue() ? ElThorAbility.BLUE_THUNDER : ElThorAbility.YELLOW_THUNDER);
         discharge.setDetails(20);
         discharge.setDensity(40);
         discharge.setSize(radius / 2.0F * multi);
         discharge.setSkipSegments(1);
         entity.m_9236_().m_7967_(discharge);
         List<LivingEntity> targets = WyHelper.<LivingEntity>getNearbyEntities(pos, entity.m_9236_(), (double)radius, ModEntityPredicates.getEnemyFactions(entity), LivingEntity.class);
         targets.remove(entity);
         Mode currentMode = this.altModeComponent.getCurrentMode();

         for(LivingEntity target : targets) {
            float damage = (float)((double)(currentMode.getDamage() * this.chargeComponent.getChargePercentage()) * ((double)1.0F - pos.m_82554_(target.m_20182_()) / (double)(radius * 3.0F)));
            if (this.dealDamageComponent.hurtTarget(entity, target, damage * multi, ablSource)) {
               target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.PARALYSIS.get(), 10, 0, false, false, true));
            }
         }

         this.animationComponent.stop(entity);
         this.cooldownComponent.startCooldown(entity, currentMode.getCooldown());
      }
   }

   private boolean onAltModeChange(LivingEntity entity, IAbility ability, Mode mode) {
      this.setDisplayName(mode.getDisplayName());
      return true;
   }

   static {
      ONE_MILLION_V_VARI_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "1_million_vari", "1 Million Vari"));
      TWENTY_MILLION_V_VARI_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "20_million_vari", "20 Million Vari"));
      ONE_HUNDRED_MILLION_V_VARI_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "100_million_vari", "100 Million Vari"));
      TWO_HUNDRED_MILLION_V_VARI_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "200_million_vari", "200 Million Vari"));
      INSTANCE = ModRegistry.registerAbility("vari", "Vari", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("A basic move where the user discharges variable amounts of electricity", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, VariAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, (e, a) -> ONE_MILLION_V_VARI_NAME.m_6881_().m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GREEN)), (e, a) -> CooldownComponent.getTooltip(VariAbility.Mode.ONE_MILLION_V.cooldown).expand(e, a), ChargeComponent.getTooltip(20.0F)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, (e, a) -> TWENTY_MILLION_V_VARI_NAME.m_6881_().m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GREEN)), (e, a) -> CooldownComponent.getTooltip(VariAbility.Mode.TWENTY_MILLION_V.cooldown).expand(e, a), ChargeComponent.getTooltip(20.0F)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, (e, a) -> ONE_HUNDRED_MILLION_V_VARI_NAME.m_6881_().m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GREEN)), (e, a) -> CooldownComponent.getTooltip(VariAbility.Mode.ONE_HUNDRED_MILLION_V.cooldown).expand(e, a), ChargeComponent.getTooltip(20.0F)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, (e, a) -> TWO_HUNDRED_MILLION_V_VARI_NAME.m_6881_().m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GREEN)), (e, a) -> CooldownComponent.getTooltip(VariAbility.Mode.TWO_HUNDRED_MILLION_V.cooldown).expand(e, a), ChargeComponent.getTooltip(20.0F)).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.LIGHTNING).setIcon(DEFAULT_ICON).build("mineminenomi");
      });
   }

   public static enum Mode {
      ONE_MILLION_V(VariAbility.ONE_MILLION_V_VARI_NAME, 10.0F, 200.0F),
      TWENTY_MILLION_V(VariAbility.TWENTY_MILLION_V_VARI_NAME, 35.0F, 300.0F),
      ONE_HUNDRED_MILLION_V(VariAbility.ONE_HUNDRED_MILLION_V_VARI_NAME, 50.0F, 500.0F),
      TWO_HUNDRED_MILLION_V(VariAbility.TWO_HUNDRED_MILLION_V_VARI_NAME, 65.0F, 600.0F);

      private Component displayName;
      private float damage;
      private float cooldown;

      private Mode(Component displayName, float damage, float cooldown) {
         this.displayName = displayName;
         this.damage = damage;
         this.cooldown = cooldown;
      }

      public Component getDisplayName() {
         return this.displayName;
      }

      public float getDamage() {
         return this.damage;
      }

      public float getCooldown() {
         return this.cooldown;
      }

      // $FF: synthetic method
      private static Mode[] $values() {
         return new Mode[]{ONE_MILLION_V, TWENTY_MILLION_V, ONE_HUNDRED_MILLION_V, TWO_HUNDRED_MILLION_V};
      }
   }
}
