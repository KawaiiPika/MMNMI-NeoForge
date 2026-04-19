package xyz.pixelatedw.mineminenomi.api.level.structure.processor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import xyz.pixelatedw.mineminenomi.init.ModStructures;

public class RuleWithCopyStructureProcessor extends StructureProcessor {
   public static final Codec<RuleWithCopyStructureProcessor> CODEC;
   private final ImmutableList<ProcessorRule> rules;

   public RuleWithCopyStructureProcessor(List<? extends ProcessorRule> rules) {
      this.rules = ImmutableList.copyOf(rules);
   }

   @Nullable
   public StructureTemplate.StructureBlockInfo process(LevelReader world, BlockPos pos1, BlockPos pos2, StructureTemplate.StructureBlockInfo info1, StructureTemplate.StructureBlockInfo info2, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
      RandomSource rng = RandomSource.m_216335_(Mth.m_14057_(info2.f_74675_()));
      BlockState blockstate = world.m_8055_(info2.f_74675_());
      UnmodifiableIterator var10 = this.rules.iterator();

      while(var10.hasNext()) {
         ProcessorRule rule = (ProcessorRule)var10.next();
         if (rule.m_230309_(info2.f_74676_(), blockstate, info1.f_74675_(), info2.f_74675_(), pos2, rng)) {
            BlockState newState = rule.m_74237_();
            UnmodifiableIterator var13 = info1.f_74676_().m_61148_().entrySet().iterator();

            while(var13.hasNext()) {
               Map.Entry<Property<?>, Comparable<?>> entry = (Map.Entry)var13.next();
               if (newState.m_61138_((Property)entry.getKey())) {
                  newState = copyProperty(info1.f_74676_(), newState, (Property)entry.getKey());
               }
            }

            CompoundTag nbt = rule.m_276991_(rng, info2.f_74677_());
            return new StructureTemplate.StructureBlockInfo(info2.f_74675_(), newState, nbt);
         }
      }

      return info2;
   }

   private static <T extends Comparable<T>> BlockState copyProperty(BlockState from, BlockState to, Property<T> property) {
      return (BlockState)to.m_61124_(property, from.m_61143_(property));
   }

   protected StructureProcessorType<?> m_6953_() {
      return (StructureProcessorType)ModStructures.RULE_WITH_COPY_PROCESSOR.get();
   }

   static {
      CODEC = ProcessorRule.f_74215_.listOf().fieldOf("rules").xmap(RuleWithCopyStructureProcessor::new, (inst) -> inst.rules).codec();
   }
}
