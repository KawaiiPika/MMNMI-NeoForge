package xyz.pixelatedw.mineminenomi.abilities.zou;

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

public class IvoryStompAbility extends PunchAbility {
   private static final int COOLDOWN = 160;
   public static final RegistryObject<AbilityCore<IvoryStompAbility>> INSTANCE = ModRegistry.registerAbility("ivory_stomp", "Ivory Stomp", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("A strong punch from a hybrid elephant form, which launches the enemy.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, IvoryStompAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.ZOU_HEAVY)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(160.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });

   public IvoryStompAbility(AbilityCore<IvoryStompAbility> core) {
      super(core);
      this.addCanUseCheck(ZouHelper::requiresHeavyPoint);
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      Vec3 speed = entity.m_20154_().m_82541_().m_82542_((double)2.5F, (double)1.0F, (double)2.5F);
      AbilityHelper.setDeltaMovement(target, speed.f_82479_, entity.m_20184_().m_7098_() + 0.1, speed.f_82481_);
      return true;
   }

   public float getPunchDamage() {
      return 20.0F;
   }

   public int getUseLimit() {
      return 1;
   }

   public float getPunchCooldown() {
      return 160.0F;
   }
}
