package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.RepeaterAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.toriphoenix.PhoenixGoenProjectile;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PhoenixGoenAbility extends RepeaterAbility {
   private static final int COOLDOWN = 160;
   private static final int TRIGGERS = 5;
   private static final int INTERVAL = 4;
   public static final RegistryObject<AbilityCore<PhoenixGoenAbility>> INSTANCE = ModRegistry.registerAbility("phoenix_goen", "Phoenix Goen", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Launches high speed blue flames while midair.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, PhoenixGoenAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.PHOENIX_ASSAULT, ModMorphs.PHOENIX_FLY)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(160.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).build("mineminenomi");
   });

   public PhoenixGoenAbility(AbilityCore<PhoenixGoenAbility> core) {
      super(core);
      this.addCanUseCheck(ToriPhoenixHelper::requiresEitherPoint);
      this.addContinueUseCheck(ToriPhoenixHelper::requiresEitherPoint);
      this.addCanUseCheck(AbilityUseConditions::requiresInAir);
   }

   public int getMaxTriggers() {
      return 5;
   }

   public int getTriggerInterval() {
      return 4;
   }

   public float getRepeaterCooldown() {
      return 160.0F;
   }

   public float getProjectileSpeed() {
      return 2.5F;
   }

   public float getProjectileSpread() {
      return 5.0F;
   }

   public PhoenixGoenProjectile getProjectileFactory(LivingEntity entity) {
      PhoenixGoenProjectile proj = new PhoenixGoenProjectile(entity.m_9236_(), entity, this, entity.m_20154_());
      return proj;
   }
}
