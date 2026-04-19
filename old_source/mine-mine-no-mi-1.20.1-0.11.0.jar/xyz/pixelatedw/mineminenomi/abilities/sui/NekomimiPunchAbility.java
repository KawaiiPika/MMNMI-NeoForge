package xyz.pixelatedw.mineminenomi.abilities.sui;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.DashAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class NekomimiPunchAbility extends DashAbility {
   private static final int COOLDOWN = 120;
   private static final float RANGE = 1.6F;
   private static final int DAMAGE = 20;
   public static final RegistryObject<AbilityCore<NekomimiPunchAbility>> INSTANCE = ModRegistry.registerAbility("nekomimi_punch", "Nekomimi Punch", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Propels the swimming user forward and deals huge damage to all entities they hit.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, NekomimiPunchAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(120.0F), RangeComponent.getTooltip(1.6F, RangeComponent.RangeType.AOE), DealDamageComponent.getTooltip(20.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });

   public NekomimiPunchAbility(AbilityCore<NekomimiPunchAbility> core) {
      super(core);
      this.addCanUseCheck(SuiHelper::requiresFreeSwimming);
   }

   public void onTargetHit(LivingEntity entity, LivingEntity target, float damage, DamageSource source) {
   }

   public boolean isParallel() {
      return true;
   }

   public float getDashCooldown() {
      return 120.0F;
   }

   public float getDamage() {
      return 20.0F;
   }

   public float getRange() {
      return 1.6F;
   }

   public float getSpeed() {
      return 3.0F;
   }

   public int getHoldTime() {
      return 20;
   }
}
