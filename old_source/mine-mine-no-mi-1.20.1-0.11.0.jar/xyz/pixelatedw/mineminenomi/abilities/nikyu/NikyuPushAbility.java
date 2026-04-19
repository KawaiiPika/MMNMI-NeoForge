package xyz.pixelatedw.mineminenomi.abilities.nikyu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class NikyuPushAbility extends Ability {
   private static final float MAX_TELEPORT_DISTANCE = 100.0F;
   private static final int COOLDOWN = 140;
   public static final RegistryObject<AbilityCore<NikyuPushAbility>> INSTANCE = ModRegistry.registerAbility("nikyu_push", "Nikyu Push", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user to push themselves in the direction they're looking at incredible speed", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, NikyuPushAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(140.0F)).build("mineminenomi");
   });
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::onDamageTaken);
   private boolean hasFallDamage = true;

   public NikyuPushAbility(AbilityCore<NikyuPushAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.damageTakenComponent});
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      BlockHitResult mop = WyHelper.rayTraceBlocks(entity, (double)100.0F);
      BlockPos blockpos;
      if (mop != null && mop.m_6662_() != Type.MISS) {
         blockpos = WyHelper.getClearPositionForPlayer(entity, mop.m_82425_());
      } else {
         blockpos = WyHelper.rayTraceBlockSafe(entity, 64.0F);
      }

      if (blockpos == null) {
         blockpos = WyHelper.rayTraceBlockSafe(entity, 64.0F);
      }

      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.PAD_HO_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
      entity.m_8127_();
      entity.m_20324_((double)blockpos.m_123341_(), (double)blockpos.m_123342_(), (double)blockpos.m_123343_());
      this.hasFallDamage = false;
      this.cooldownComponent.startCooldown(entity, 140.0F);
   }

   private float onDamageTaken(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      if (!this.hasFallDamage && damageSource.m_276093_(DamageTypes.f_268671_)) {
         this.hasFallDamage = true;
         return 0.0F;
      } else {
         return damage;
      }
   }
}
