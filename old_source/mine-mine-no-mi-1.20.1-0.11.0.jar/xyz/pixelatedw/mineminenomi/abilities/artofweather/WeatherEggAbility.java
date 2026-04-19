package xyz.pixelatedw.mineminenomi.abilities.artofweather;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.FogTempo;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.MirageTempo;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.RainTempo;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.WeatherEggProjectile;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class WeatherEggAbility extends Ability {
   private static final float COOLDOWN = 320.0F;
   public static final RegistryObject<AbilityCore<WeatherEggAbility>> INSTANCE = ModRegistry.registerAbility("weather_egg", "Weather Egg", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Instantly creates a Weather Cloud as if combining a Cool Ball and a Heat Ball", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, WeatherEggAbility::new)).addDescriptionLine(desc).setSourceHakiNature(SourceHakiNature.IMBUING).setNodeFactories(WeatherEggAbility::createNode).build("mineminenomi");
   });

   public WeatherEggAbility(AbilityCore<WeatherEggAbility> core) {
      super(core);
      this.addCanUseCheck(AbilityUseConditions::requiresClimaTact);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      Vec3 lookVec = entity.m_20154_();
      WeatherEggProjectile proj = new WeatherEggProjectile(entity.m_9236_(), entity, this);
      proj.m_7678_(entity.m_20185_() + lookVec.m_7096_(), entity.m_20186_() + (double)entity.m_20192_() - (double)0.5F, entity.m_20189_() + lookVec.m_7094_(), entity.m_146908_(), entity.m_146909_());
      AbilityHelper.setDeltaMovement(proj, (double)0.0F, 0.3, (double)0.0F);
      entity.m_9236_().m_7967_(proj);
      this.cooldownComponent.startCooldown(entity, 320.0F);
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-11.0F, 7.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.ART_OF_WEATHER.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)RainTempo.INSTANCE.get()).getNode(entity), ((AbilityCore)FogTempo.INSTANCE.get()).getNode(entity), ((AbilityCore)MirageTempo.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
