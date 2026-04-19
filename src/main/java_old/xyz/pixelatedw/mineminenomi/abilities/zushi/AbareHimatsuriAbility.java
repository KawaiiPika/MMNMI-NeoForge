package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class AbareHimatsuriAbility extends Ability {
   private static final int COOLDOWN = 300;
   private static final int HOLD_TIME = 1200;
   public static final RegistryObject<AbilityCore<AbareHimatsuriAbility>> INSTANCE = ModRegistry.registerAbility("abare_himatsuri", "Abare Himatsuri", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Makes the user fly using gravity on the ground below you.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbareHimatsuriAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F), ContinuousComponent.getTooltip(1200.0F)).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private boolean stateChanged = false;
   public BlockState[] blocks = null;

   public AbareHimatsuriAbility(AbilityCore<AbareHimatsuriAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresOnGround);
      this.addCanUseCheck((entity, ability) -> AbilityHelper.isInCreativeOrSpectator(entity) ? Result.fail(ModI18nAbilities.MESSAGE_SUVIVAL_ONLY) : Result.success());
      this.addContinueUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 1200.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity instanceof Player player) {
         player.m_150110_().f_35936_ = true;
         if (!entity.m_9236_().f_46443_) {
            ((ServerPlayer)entity).f_8906_.m_9829_(new ClientboundPlayerAbilitiesPacket(player.m_150110_()));
         }
      }

      this.blocks = new BlockState[18];
      BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();
      int i = 0;

      for(int x = -1; x <= 1; ++x) {
         for(int y = 0; y <= 1; ++y) {
            for(int z = -1; z <= 1; ++z) {
               mutpos.m_122190_(entity.m_20183_().m_7918_(x, y - 2, z));
               BlockState state = entity.m_9236_().m_8055_(mutpos);
               this.blocks[i] = state;
               ++i;
            }
         }
      }

   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity instanceof Player player) {
         player.f_19789_ = 0.0F;
         if (!player.m_150110_().f_35936_) {
            player.m_150110_().f_35936_ = true;
            this.stateChanged = true;
         }

         if (!entity.m_9236_().f_46443_) {
            ((ServerPlayer)player).f_8906_.m_9829_(new ClientboundPlayerAbilitiesPacket(player.m_150110_()));
         }

         boolean canFly = AbilityHelper.flyingAtMaxHeight(player, (double)48.0F);
         if (player.m_150110_().f_35935_) {
            AbilityHelper.vanillaFlightThreshold(player, canFly ? 256 : (int)player.m_20186_() - 1);
         }

         if (player.m_20142_()) {
            AbilityHelper.setDeltaMovement(player, player.m_20184_().m_82542_(0.69, (double)1.0F, 0.69));
            player.m_6858_(false);
            this.stateChanged = true;
         }

         if (this.stateChanged) {
            this.stateChanged = false;
         }
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity instanceof Player player) {
         player.m_150110_().f_35936_ = false;
         player.m_150110_().f_35935_ = false;
         if (!entity.m_9236_().f_46443_) {
            ((ServerPlayer)player).f_8906_.m_9829_(new ClientboundPlayerAbilitiesPacket(player.m_150110_()));
         }
      }

      this.blocks = null;
      this.cooldownComponent.startCooldown(entity, 300.0F);
   }
}
