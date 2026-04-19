package xyz.pixelatedw.mineminenomi.abilities.beta;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.RepeaterAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.beta.StickyProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BetaLauncherAbility extends RepeaterAbility {
   private static final int COOLDOWN = 180;
   private static final int TRIGGERS = 6;
   private static final int INTERVAL = 3;
   public static final RegistryObject<AbilityCore<BetaLauncherAbility>> INSTANCE = ModRegistry.registerAbility("beta_launcher", "Beta Launcher", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Shoots sticky Mucus which cause explosions on contact when combined with fire (also holding Flint & Steel).", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BetaLauncherAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(180.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.SLIME).build("mineminenomi");
   });

   public BetaLauncherAbility(AbilityCore<BetaLauncherAbility> core) {
      super(core);
   }

   public float getProjectileSpeed() {
      return 1.5F;
   }

   public int getMaxTriggers() {
      return 6;
   }

   public int getTriggerInterval() {
      return 3;
   }

   public float getRepeaterCooldown() {
      return 180.0F;
   }

   public StickyProjectile getProjectileFactory(LivingEntity entity) {
      StickyProjectile proj = new StickyProjectile(entity.m_9236_(), entity, this);
      if (entity.m_21205_().m_41720_() == Items.f_42409_) {
         proj.setDamage(8.0F);
         proj.setCauseExplosion();
      }

      return proj;
   }
}
