package xyz.pixelatedw.mineminenomi.abilities.carnivaltricks;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.RepeaterAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.carnivaltricks.TopEntity;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KamikazeHyakkomaGekijoAbility extends RepeaterAbility {
   private static final int COOLDOWN = 100;
   private static final int TRIGGERS = 10;
   private static final int INTERVAL = 2;
   public static final RegistryObject<AbilityCore<KamikazeHyakkomaGekijoAbility>> INSTANCE = ModRegistry.registerAbility("kamikaze_hyakkoma_gekijo", "Kamikaze Hyakkoma Gekijo", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user throws several tops at their opponent, some of them exploding on impact.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, KamikazeHyakkomaGekijoAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F)).setSourceHakiNature(SourceHakiNature.IMBUING).build("mineminenomi");
   });

   public KamikazeHyakkomaGekijoAbility(AbilityCore<KamikazeHyakkomaGekijoAbility> core) {
      super(core);
   }

   public int getMaxTriggers() {
      return 10;
   }

   public int getTriggerInterval() {
      return 2;
   }

   public float getRepeaterCooldown() {
      return 100.0F;
   }

   public TopEntity getProjectileFactory(LivingEntity entity) {
      TopEntity proj = new TopEntity(entity.m_9236_(), entity, this);
      return proj;
   }
}
