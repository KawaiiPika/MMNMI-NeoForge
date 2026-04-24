import re

with open("src/main/java/xyz/pixelatedw/mineminenomi/blocks/DialBlock.java", "r") as f:
    content = f.read()

# Replace SRG names with mapping logic, assuming we know a few.
content = re.sub(r'Block\.m_49796_', 'Block.box', content)
content = re.sub(r'Properties\.m_284310_\(\)', 'Properties.of()', content)
content = re.sub(r'\.m_60978_', '.strength', content)
content = re.sub(r'\.m_61124_', '.setValue', content)
content = re.sub(r'\.m_49966_\(\)', '.defaultBlockState()', content)
content = re.sub(r'\.m_49959_', '.registerDefaultState', content)
content = re.sub(r'\.f_49792_', '.stateDefinition', content)
content = re.sub(r'\.m_61090_\(\)', '.any()', content)
content = re.sub(r'm_5940_', 'getShape', content)
content = re.sub(r'm_7420_', 'canSurvive', content)
content = re.sub(r'm_6104_', 'skipRendering', content)
content = re.sub(r'm_7753_', 'getLightBlock', content)
content = re.sub(r'm_7926_', 'createBlockStateDefinition', content)
content = re.sub(r'\.m_61104_', '.add', content)
content = re.sub(r'm_7417_', 'updateShape', content)
content = re.sub(r'm_61143_', 'getValue', content)
content = re.sub(r'm_186469_', 'scheduleTick', content)
content = re.sub(r'\.f_76193_', '.WATER', content) # Fluids.WATER
content = re.sub(r'm_6718_', 'getTickDelay', content)
content = re.sub(r'm_5888_', 'getFluidState', content)
content = re.sub(r'm_76068_', 'getSource', content)
content = re.sub(r'm_5573_', 'getStateForPlacement', content)
content = re.sub(r'm_43725_', 'getLevel', content)
content = re.sub(r'm_6425_', 'getFluidState', content)
content = re.sub(r'm_8083_', 'getClickedPos', content)
content = re.sub(r'm_8125_', 'getHorizontalDirection', content)
content = re.sub(r'\.f_13131_', '.WATER', content) # FluidTags.WATER
content = re.sub(r'm_205070_', 'is', content)
content = re.sub(r'm_76186_', 'getAmount', content)
content = re.sub(r'f_61362_', 'WATERLOGGED', content)
content = re.sub(r'f_54117_', 'FACING', content)

codec_code = """
   public static final com.mojang.serialization.MapCodec<DialBlock> CODEC = simpleCodec(DialBlock::new);

   @Override
   public com.mojang.serialization.MapCodec<DialBlock> codec() {
      return CODEC;
   }
"""
content = re.sub(r'public DialBlock\(\) \{', codec_code + '\n   public DialBlock() {', content)

content = re.sub(r'import net.minecraft.world.level.block.state.BlockBehaviour;', 'import net.minecraft.world.level.block.state.BlockBehaviour;\nimport net.minecraft.world.level.block.state.BlockState;', content)

with open("src/main/java/xyz/pixelatedw/mineminenomi/blocks/DialBlock.java", "w") as f:
    f.write(content)
