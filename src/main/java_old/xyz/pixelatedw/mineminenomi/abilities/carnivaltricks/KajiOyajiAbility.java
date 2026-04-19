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
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.FreshFireProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KajiOyajiAbility extends RepeaterAbility {
   private static final float COOLDOWN = 160.0F;
   private static final int TRIGGERS = 12;
   private static final int INTERVAL = 1;
   public static final RegistryObject<AbilityCore<KajiOyajiAbility>> INSTANCE = ModRegistry.registerAbility("kaji_oyaji", "Kaji Oyaji", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Breathes fire.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, KajiOyajiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(160.0F)).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.FIRE).build("mineminenomi");
   });

   public KajiOyajiAbility(AbilityCore<KajiOyajiAbility> core) {
      super(core);
   }

   public int getMaxTriggers() {
      return 12;
   }

   public int getTriggerInterval() {
      return 1;
   }

   public float getRepeaterCooldown() {
      return 160.0F;
   }

   public FreshFireProjectile getProjectileFactory(LivingEntity entity) {
      FreshFireProjectile proj = new FreshFireProjectile(entity.m_9236_(), entity, this);
      proj.setMaxLife(2);
      return proj;
   }
}
