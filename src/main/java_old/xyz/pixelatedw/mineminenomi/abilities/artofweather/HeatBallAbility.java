package xyz.pixelatedw.mineminenomi.abilities.artofweather;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.WeatherBallKind;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.HeatBallProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.WeatherBallProjectile;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class HeatBallAbility extends WeatherBallAbility {
   public static final RegistryObject<AbilityCore<HeatBallAbility>> INSTANCE = ModRegistry.registerAbility("heat_ball", "Heat Ball", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Launch a Heat Ball from your Clima Tact to use for different Tempos", (Object)null), ImmutablePair.of("§aSHIFT-USE§r: Loads the ball into the clima tact", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, HeatBallAbility::new)).addDescriptionLine(desc).setSourceElement(SourceElement.FIRE).setSourceHakiNature(SourceHakiNature.IMBUING).setNodeFactories(HeatBallAbility::createNode).build("mineminenomi");
   });

   public HeatBallAbility(AbilityCore<HeatBallAbility> core) {
      super(core);
   }

   public WeatherBallKind getKind() {
      return WeatherBallKind.HEAT;
   }

   public ParticleEffect<?> getParticleEffect() {
      return (ParticleEffect)ModParticleEffects.HEAT_BALL_CHARGE.get();
   }

   public WeatherBallProjectile getWeatherBallEntity(LivingEntity entity, Vec3 lookVec) {
      HeatBallProjectile proj = new HeatBallProjectile(entity.m_9236_(), entity, this);
      proj.m_7678_(lookVec.m_7096_(), entity.m_20186_() + (double)entity.m_20192_() - (double)0.5F, lookVec.m_7094_(), entity.m_146908_(), entity.m_146909_());
      AbilityHelper.setDeltaMovement(proj, (double)0.0F, 0.3, (double)0.0F);
      return proj;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-12.0F, 1.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.ART_OF_WEATHER.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
