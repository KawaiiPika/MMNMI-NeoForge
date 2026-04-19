package xyz.pixelatedw.mineminenomi.abilities.yami;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdateAbilityNBTPacket;

public class AbsorbedBlocksAbility extends PassiveAbility {
   public static final RegistryObject<AbilityCore<AbsorbedBlocksAbility>> INSTANCE = ModRegistry.registerAbility("absorbed_blocks", "Absorbed Blocks", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Displays the total amount of blocks the user has absorbed.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, AbsorbedBlocksAbility::new)).addDescriptionLine(desc).build("mineminenomi");
   });
   private List<BlockData> absorbedBlocks = new ArrayList();
   private int absorbedBlocksAmount = 0;
   private int previousAbsorbedBlocksAmount = -1;

   public AbsorbedBlocksAbility(AbilityCore<AbsorbedBlocksAbility> core) {
      super(core);
      if (super.isClientSide()) {
         GaugeComponent gaugeComponent = new GaugeComponent(this, this::renderGauge);
         super.addComponents(gaugeComponent);
      }

      super.addDuringPassiveEvent(this::onDuringPassive);
   }

   public void onDuringPassive(LivingEntity entity) {
      if (!entity.m_9236_().f_46443_) {
         this.absorbedBlocksAmount = this.absorbedBlocks.size();
         if (this.absorbedBlocksAmount != this.previousAbsorbedBlocksAmount) {
            this.previousAbsorbedBlocksAmount = this.absorbedBlocksAmount;
            ModNetwork.sendTo(new SUpdateAbilityNBTPacket(entity, this), (Player)entity);
         }
      }

   }

   @OnlyIn(Dist.CLIENT)
   private void renderGauge(Player player, GuiGraphics graphics, int posX, int posY, AbsorbedBlocksAbility ability) {
      Minecraft mc = Minecraft.m_91087_();
      RenderSystem.setShaderTexture(0, ModResources.WIDGETS);
      RendererHelper.drawIcon(((AbilityCore)BlackHoleAbility.INSTANCE.get()).getIcon(), graphics.m_280168_(), (float)posX, (float)(posY - 38), 0.0F, 32.0F, 32.0F);
      String strAbsorbedBlocksAmount = Integer.toString(ability.absorbedBlocksAmount);
      RendererHelper.drawStringWithBorder(mc.f_91062_, graphics, strAbsorbedBlocksAmount, posX + 15 - mc.f_91062_.m_92895_(strAbsorbedBlocksAmount) / 2, posY - 25, Color.WHITE.getRGB());
   }

   public List<BlockData> getAbsorbedBlocks() {
      return this.absorbedBlocks;
   }

   public List<BlockData> getCompressedBlocks() {
      return (List)this.absorbedBlocks.stream().filter((blockData) -> blockData.isCompressed()).collect(Collectors.toList());
   }

   public List<BlockData> getUncompressedBlocks() {
      return (List)this.absorbedBlocks.stream().filter((blockData) -> !blockData.isCompressed()).collect(Collectors.toList());
   }

   public void addAbsorbedBlock(BlockState blockState, BlockPos blockPos) {
      this.absorbedBlocks.add(new BlockData(blockState, blockPos));
   }

   public void removeAbsorbedBlock(BlockData blockData) {
      this.absorbedBlocks.remove(blockData);
   }

   public void saveAdditional(CompoundTag nbt) {
      nbt.m_128405_("absorbedBlocksAmount", this.absorbedBlocksAmount);
   }

   public void loadAdditional(CompoundTag nbt) {
      this.absorbedBlocksAmount = nbt.m_128451_("absorbedBlocksAmount");
   }

   public static class BlockData {
      private BlockState blockState;
      private BlockPos blockPos;
      private boolean isCompressed = false;

      public BlockData(BlockState blockState, BlockPos blockPos) {
         this.blockState = blockState;
         this.blockPos = blockPos;
      }

      public BlockState getBlockState() {
         return this.blockState;
      }

      public BlockPos getBlockPos() {
         return this.blockPos;
      }

      public boolean isCompressed() {
         return this.isCompressed;
      }

      public void setCompressed(boolean flag) {
         this.isCompressed = flag;
      }
   }
}
