package xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.WeatherBallKind;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.ThunderLanceProjectile;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ThunderLanceTempo extends ChargedTempoAbility {
   public static final float DAMAGE = 60.0F;
   private static final int CHARGE_TIME = 160;
   private static final float MAX_DISTANCE = 80.0F;
   public static final AbilityDescriptionLine.IDescriptionLine CUSTOM_CHARGE_TIME = (entity, ability) -> !ItemsHelper.isClimaTact(entity.m_21205_()) ? null : ChargeComponent.getTooltip(160.0F).expand(entity, ability);
   public static final RegistryObject<AbilityCore<ThunderLanceTempo>> INSTANCE = ModRegistry.registerAbility("thunder_lance_tempo", "Thunder Lance Tempo", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates a lighting bolt that goes directly to the area the user is pointing at, exploding on impact and hurting entities in its path", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, AbilityType.PASSIVE, ThunderLanceTempo::new)).setIcon(ModResources.TEMPO_ICON).addDescriptionLine(CHARGED_TEMPO_DESCRIPTION).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CUSTOM_CHARGE_TIME, DealDamageComponent.getTooltip(60.0F)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.LIGHTNING).setNodeFactories(ThunderLanceTempo::createNode).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addTickEvent(100, this::duringChargeEvent).addEndEvent(100, this::endChargeEvent);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private Vec3 pos;

   public ThunderLanceTempo(AbilityCore<ThunderLanceTempo> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.dealDamageComponent, this.projectileComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresClimaTact);
   }

   public WeatherBallKind[] getTempoOrder() {
      return new WeatherBallKind[]{WeatherBallKind.THUNDER, WeatherBallKind.THUNDER, WeatherBallKind.THUNDER};
   }

   public void useTempo(LivingEntity entity) {
      if (!entity.m_21205_().m_41619_() && !this.chargeComponent.isCharging()) {
         this.chargeComponent.startCharging(entity, 160.0F);
      }
   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      BlockHitResult mop = WyHelper.rayTraceBlocks(entity, (double)80.0F);
      if (mop.m_6662_().equals(Type.BLOCK)) {
         this.pos = mop.m_82450_();
         double i = this.pos.f_82479_;
         double j = this.pos.f_82480_;
         double k = this.pos.f_82481_;
         if (entity.f_19797_ % 2 == 0) {
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.THUNDER_LANCE.get(), entity, i, j, k);
         }
      }

   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         ((ServerLevel)entity.m_9236_()).m_7726_().m_8394_(entity, new ClientboundAnimatePacket(entity, 0));
      }

      this.projectileComponent.shoot(entity);
   }

   private ThunderLanceProjectile createProjectile(LivingEntity entity) {
      return new ThunderLanceProjectile(entity.m_9236_(), entity, this);
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-17.0F, 2.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.ART_OF_WEATHER.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)ThunderboltTempo.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
