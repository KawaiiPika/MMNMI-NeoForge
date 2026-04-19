package xyz.pixelatedw.mineminenomi.abilities.yami;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yami.LiberationProjectile;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class LiberationAbility extends Ability {
   private static final Component LIBERATION_RELEASE_NAME;
   private static final ResourceLocation LIBERATION_ABSORB_ICON;
   private static final ResourceLocation LIBERATION_RELEASE_ICON;
   private static final float COOLDOWN = 80.0F;
   public static final RegistryObject<AbilityCore<LiberationAbility>> INSTANCE;
   private final AltModeComponent<Mode> altModeComponent;
   private static final int BATCH_SIZE = 256;

   public LiberationAbility(AbilityCore<LiberationAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<Mode>(this, Mode.class, LiberationAbility.Mode.ABSORB)).addChangeModeEvent(this::onAltModeChange);
      this.addComponents(new AbilityComponent[]{this.altModeComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         AbsorbedBlocksAbility absorbAbility = (AbsorbedBlocksAbility)AbilityCapability.get(entity).map((props) -> (AbsorbedBlocksAbility)props.getPassiveAbility((AbilityCore)AbsorbedBlocksAbility.INSTANCE.get())).orElse((Object)null);
         if (absorbAbility != null) {
            if (this.altModeComponent.getCurrentMode() == LiberationAbility.Mode.ABSORB) {
               for(BlockPos blockPos : WyHelper.getNearbyBlocks(entity.m_20183_(), entity.m_9236_(), 40, 40, 40, (blockState) -> blockState.m_60734_() == ModBlocks.DARKNESS.get())) {
                  for(AbsorbedBlocksAbility.BlockData blockData : absorbAbility.getAbsorbedBlocks()) {
                     if (blockData.getBlockPos().equals(blockPos)) {
                        blockData.setCompressed(true);
                     }
                  }

                  entity.m_9236_().m_46597_(blockPos, Blocks.f_50016_.m_49966_());
               }
            } else if (this.altModeComponent.getCurrentMode() == LiberationAbility.Mode.RELEASE) {
               int batchSize = Math.min(256, absorbAbility.getCompressedBlocks().size());

               for(int i = 0; i < batchSize; ++i) {
                  AbsorbedBlocksAbility.BlockData blockData = (AbsorbedBlocksAbility.BlockData)absorbAbility.getCompressedBlocks().get(super.random.nextInt(absorbAbility.getCompressedBlocks().size()));
                  LiberationProjectile proj = new LiberationProjectile(entity.m_9236_(), entity, blockData.getBlockState(), this);
                  Vec3 lookVec = entity.m_20154_().m_82541_();
                  HitResult mop = WyHelper.rayTraceBlocksAndEntities(entity);
                  double x = mop.m_82450_().f_82479_;
                  double y = mop.m_82450_().f_82480_;
                  double z = mop.m_82450_().f_82481_;
                  if (lookVec.f_82480_ > 0.7) {
                     proj.m_37251_(entity, entity.m_146909_(), entity.m_146908_(), 0.0F, 3.0F, 6.0F);
                     proj.setMaxLife(300);
                     proj.setGravity(0.05F);
                  } else {
                     proj.m_7678_(x + WyHelper.randomWithRange(-3, 3), y + (double)14.0F + WyHelper.randomWithRange(0, 4), z + WyHelper.randomWithRange(-3, 3), 0.0F, 0.0F);
                     AbilityHelper.setDeltaMovement(proj, (double)0.0F, -0.7 - entity.m_9236_().f_46441_.m_188500_(), (double)0.0F);
                  }

                  entity.m_9236_().m_7967_(proj);
                  absorbAbility.removeAbsorbedBlock(blockData);
               }
            }

            this.cooldownComponent.startCooldown(entity, 80.0F);
         }
      }
   }

   private boolean onAltModeChange(LivingEntity entity, IAbility ability, Mode mode) {
      if (mode == LiberationAbility.Mode.ABSORB) {
         this.setDisplayIcon(LIBERATION_ABSORB_ICON);
      } else if (mode == LiberationAbility.Mode.RELEASE) {
         this.setDisplayIcon(LIBERATION_RELEASE_ICON);
      }

      return true;
   }

   static {
      LIBERATION_RELEASE_NAME = Component.m_237113_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "liberation_release", "Liberation: Release"));
      LIBERATION_ABSORB_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/liberation.png");
      LIBERATION_RELEASE_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/liberation_release.png");
      INSTANCE = ModRegistry.registerAbility("liberation", "Liberation", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user sucks up any Darkness they've placed.", (Object)null), ImmutablePair.of("The user releases all the Darkness stored at the location they're looking at.", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, LiberationAbility::new)).addAdvancedDescriptionLine(CooldownComponent.getTooltip(80.0F)).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, (e, a) -> Component.m_237113_(name).m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GREEN)), (e, a) -> desc[0]).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, (e, a) -> LIBERATION_RELEASE_NAME.m_6881_().m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GREEN)), (e, a) -> desc[1]).build("mineminenomi");
      });
   }

   public static enum Mode {
      ABSORB,
      RELEASE;

      // $FF: synthetic method
      private static Mode[] $values() {
         return new Mode[]{ABSORB, RELEASE};
      }
   }
}
