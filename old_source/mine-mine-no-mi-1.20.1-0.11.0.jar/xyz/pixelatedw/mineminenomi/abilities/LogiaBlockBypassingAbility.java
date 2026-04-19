package xyz.pixelatedw.mineminenomi.abilities;

import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;

public abstract class LogiaBlockBypassingAbility extends PassiveAbility {
   public static final Component[] DESCRIPTION = AbilityHelper.registerDescriptionText("mineminenomi", "logia_block_bypassing", ImmutablePair.of("Allows the user to move through specific blocks based on their element", (Object)null));
   private boolean isInBlock = false;

   public LogiaBlockBypassingAbility(AbilityCore<? extends LogiaBlockBypassingAbility> core) {
      super(core);
      this.addDuringPassiveEvent(this::duringPassive);
      this.pauseTickComponent.addPauseEvent(110, this::onPauseEvent);
   }

   private void duringPassive(LivingEntity entity) {
      if (entity instanceof Player player) {
         if (this.canGoThrough(entity.m_9236_().m_8055_(entity.m_20183_()))) {
            Abilities abilities = player.m_150110_();
            if (!abilities.f_35935_) {
               abilities.f_35935_ = true;
               player.m_6885_();
            }

            this.isInBlock = true;
            this.spawnParticles(entity);
            return;
         }
      }

      if (entity instanceof Player player) {
         if (this.isInBlock) {
            Abilities abilities = player.m_150110_();
            abilities.f_35935_ = false;
            player.m_6885_();
            this.isInBlock = false;
         }
      }

   }

   private void onPauseEvent(LivingEntity entity, IAbility ability) {
      if (entity instanceof Player player) {
         if (this.isInBlock) {
            Abilities abilities = player.m_150110_();
            abilities.f_35935_ = false;
            player.m_6885_();
            this.isInBlock = false;
         }
      }

   }

   public abstract void spawnParticles(LivingEntity var1);

   public abstract boolean canGoThrough(BlockState var1);

   public static boolean isAllowedToGoThrough(LivingEntity player, BlockState state) {
      if (player == null) {
         return false;
      } else {
         IAbilityData props = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
         if (props == null) {
            return false;
         } else {
            Stream var10000 = props.getPassiveAbilities().stream().filter((abl) -> abl instanceof LogiaBlockBypassingAbility);
            Objects.requireNonNull(LogiaBlockBypassingAbility.class);
            return var10000.map(LogiaBlockBypassingAbility.class::cast).anyMatch((abl) -> abl.canGoThrough(state) && !abl.isPaused());
         }
      }
   }
}
