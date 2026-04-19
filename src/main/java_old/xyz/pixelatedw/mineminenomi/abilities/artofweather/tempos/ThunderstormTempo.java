package xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos;

import java.util.Collections;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.TempoAbility;
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
import xyz.pixelatedw.mineminenomi.entities.clouds.WeatherCloudEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.ThunderstormProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class ThunderstormTempo extends TempoAbility {
   public static final RegistryObject<AbilityCore<ThunderstormTempo>> INSTANCE = ModRegistry.registerAbility("thunderstorm_tempo", "Thunderstorm Tempo", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Hits multiple enemies below the cloud with multiple lightning bolts", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, AbilityType.PASSIVE, ThunderstormTempo::new)).setIcon(ModResources.TEMPO_ICON).addDescriptionLine(TempoAbility.getTooltip(true, 5, WeatherBallKind.THUNDER)).addDescriptionLine(desc).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.LIGHTNING).setNodeFactories(ThunderstormTempo::createNode).build("mineminenomi");
   });

   public ThunderstormTempo(AbilityCore<ThunderstormTempo> core) {
      super(core);
   }

   public void useTempo(LivingEntity entity, WeatherCloudEntity cloud) {
      List<LivingEntity> targets = WyHelper.<LivingEntity>getNearbyLiving(entity.m_20182_(), entity.m_9236_(), (double)25.0F, ModEntityPredicates.getEnemyFactions(entity));
      Collections.shuffle(targets);

      for(LivingEntity target : targets) {
         if (this.isUnderWeatherCloud(cloud, target)) {
            float distance = (float)Math.sqrt(target.m_20182_().m_82520_((double)0.0F, (double)14.0F, (double)0.0F).m_82531_(target.m_20185_(), target.m_20186_(), target.m_20189_()));
            if (target.m_20186_() <= cloud.m_20186_() && entity.m_142582_(target)) {
               ThunderstormProjectile thunderbolt = new ThunderstormProjectile(entity.m_9236_(), entity, target.m_20185_(), target.m_20186_(), target.m_20189_(), cloud.m_146904_(), distance + 14.0F, this);
               entity.m_9236_().m_7967_(thunderbolt);
            }
         }
      }

   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-17.0F, 4.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.ART_OF_WEATHER.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)ThunderboltTempo.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
