package xyz.pixelatedw.mineminenomi.abilities.kilo;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KiloPunch5000Ability extends PunchAbility {
   private static final float COOLDOWN = 140.0F;
   public static final RegistryObject<AbilityCore<KiloPunch5000Ability>> INSTANCE = ModRegistry.registerAbility("5000_kilo_punch", "5000 Kilo Punch", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Delivers a 5000 kilo punch, the user is slowed down due to the extra kilos", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, KiloPunch5000Ability::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(140.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private static final AbilityAttributeModifier MOVEMENT_MODIFIER;

   public KiloPunch5000Ability(AbilityCore<KiloPunch5000Ability> core) {
      super(core);
      this.statsComponent.addAttributeModifier(Attributes.f_22279_, MOVEMENT_MODIFIER);
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      return true;
   }

   public int getUseLimit() {
      return 1;
   }

   public boolean isParallel() {
      return true;
   }

   public float getPunchDamage() {
      return 20.0F;
   }

   public float getPunchCooldown() {
      return 140.0F;
   }

   static {
      MOVEMENT_MODIFIER = new AbilityAttributeModifier(UUID.fromString("969f4880-faf9-41e9-bdae-26a57422254a"), INSTANCE, "Kilo Punch 5000 Movement Modifier", -0.01, Operation.ADDITION);
   }
}
