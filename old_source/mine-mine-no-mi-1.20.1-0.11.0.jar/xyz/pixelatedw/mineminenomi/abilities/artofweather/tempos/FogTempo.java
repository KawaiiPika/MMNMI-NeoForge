package xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.WeatherBallKind;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.clouds.MirageTempoCloudEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class FogTempo extends ChargedTempoAbility {
   public static final RegistryObject<AbilityCore<FogTempo>> INSTANCE = ModRegistry.registerAbility("fog_tempo", "Fog Tempo", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates a thick fog at the user's position, blinding all nearby enemies as well", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, AbilityType.PASSIVE, FogTempo::new)).setIcon(ModResources.TEMPO_ICON).addDescriptionLine(CHARGED_TEMPO_DESCRIPTION).addDescriptionLine(desc).setNodeFactories(FogTempo::createNode).build("mineminenomi");
   });

   public FogTempo(AbilityCore<FogTempo> core) {
      super(core);
   }

   public WeatherBallKind[] getTempoOrder() {
      return new WeatherBallKind[]{WeatherBallKind.HEAT, WeatherBallKind.HEAT, WeatherBallKind.COOL};
   }

   public void useTempo(LivingEntity entity) {
      MirageTempoCloudEntity smokeCloud = new MirageTempoCloudEntity(entity.m_9236_(), entity);
      smokeCloud.setLife(300);
      smokeCloud.m_7678_(entity.m_20185_(), entity.m_20186_() + (double)1.0F, entity.m_20189_(), 0.0F, 0.0F);
      AbilityHelper.setDeltaMovement(smokeCloud, (double)0.0F, (double)0.0F, (double)0.0F);
      entity.m_9236_().m_7967_(smokeCloud);

      for(LivingEntity target : WyHelper.getNearbyLiving(entity.m_20182_(), entity.m_9236_(), (double)10.0F, ModEntityPredicates.getEnemyFactions(entity))) {
         target.m_7292_(new MobEffectInstance(MobEffects.f_19610_, 200, 1));
      }

   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-11.0F, 5.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.ART_OF_WEATHER.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)WeatherCloudTempo.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
