package xyz.pixelatedw.mineminenomi.abilities.zoumammoth;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
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
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class AncientTrunkShotAbility extends PunchAbility {
   private static final int COOLDOWN = 100;
   public static final RegistryObject<AbilityCore<AncientTrunkShotAbility>> INSTANCE = ModRegistry.registerAbility("ancient_trunk_shot", "Ancient Trunk Shot", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Hit using the massive trunk.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AncientTrunkShotAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.MAMMOTH_GUARD, ModMorphs.MAMMOTH_HEAVY)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });

   public AncientTrunkShotAbility(AbilityCore<AncientTrunkShotAbility> core) {
      super(core);
      this.addCanUseCheck(ZouMammothHelper::requiresEitherPoint);
      this.addContinueUseCheck(ZouMammothHelper::requiresEitherPoint);
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      Vec3 dirVec = entity.m_20182_().m_82546_(target.m_20182_()).m_82541_().m_82542_((double)5.0F, (double)1.0F, (double)5.0F);
      AbilityHelper.setDeltaMovement(target, target.m_20184_().m_82520_(-dirVec.f_82479_, 0.65, -dirVec.f_82481_));
      target.m_6853_(false);
      return true;
   }

   public int getUseLimit() {
      return 1;
   }

   public float getPunchDamage() {
      return 15.0F;
   }

   public float getPunchCooldown() {
      return 100.0F;
   }
}
