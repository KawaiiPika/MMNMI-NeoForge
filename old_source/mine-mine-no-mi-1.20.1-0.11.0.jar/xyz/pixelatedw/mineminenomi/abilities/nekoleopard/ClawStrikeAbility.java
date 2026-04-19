package xyz.pixelatedw.mineminenomi.abilities.nekoleopard;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class ClawStrikeAbility extends PunchAbility {
   private static final float COOLDOWN = 100.0F;
   public static final RegistryObject<AbilityCore<ClawStrikeAbility>> INSTANCE = ModRegistry.registerAbility("claw_strike", "Claw Strike", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Hits using the user's sharp claws.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, ClawStrikeAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.LEOPARD_HEAVY, ModMorphs.LEOPARD_WALK)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });

   public ClawStrikeAbility(AbilityCore<ClawStrikeAbility> core) {
      super(core);
      this.addCanUseCheck(NekoLeopardHelper::requiresEitherPoint);
      this.addContinueUseCheck(NekoLeopardHelper::requiresEitherPoint);
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      entity.m_9236_().m_5594_((Player)null, target.m_20183_(), (SoundEvent)ModSounds.SHIGAN_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
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
      return 100.0F;
   }
}
