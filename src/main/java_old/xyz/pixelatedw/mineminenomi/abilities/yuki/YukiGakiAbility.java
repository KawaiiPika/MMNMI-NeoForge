package xyz.pixelatedw.mineminenomi.abilities.yuki;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.WallAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.yuki.KamakuraParticleEffect;

public class YukiGakiAbility extends WallAbility {
   private static final BlockProtectionRule GRIEF_RULE;
   private static final KamakuraParticleEffect.Details DETAILS;
   private static final int COOLDOWN = 100;
   public static final RegistryObject<AbilityCore<YukiGakiAbility>> INSTANCE;

   public YukiGakiAbility(AbilityCore<YukiGakiAbility> core) {
      super(core);
      this.addUseEvent(this::onUseEvent);
      this.continuousComponent.addEndEvent(100, this::endContinuityEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.KAMAKURA.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), DETAILS);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 100.0F);
   }

   public int getThickness() {
      return 2;
   }

   public int getHeight() {
      return 4;
   }

   public int getLength() {
      return 3;
   }

   public BlockState getWallBlock() {
      return ((Block)ModBlocks.HARDENED_SNOW.get()).m_49966_();
   }

   public BlockProtectionRule getGriefingRule() {
      return GRIEF_RULE;
   }

   public boolean stopAfterUse() {
      return true;
   }

   static {
      GRIEF_RULE = (new BlockProtectionRule.Builder(new BlockProtectionRule[]{DefaultProtectionRules.AIR_FOLIAGE})).build();
      DETAILS = new KamakuraParticleEffect.Details(4);
      INSTANCE = ModRegistry.registerAbility("yuki_gaki", "Yuki Gaki", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates a wall made of hardened snow to protect the user.", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, YukiGakiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F)).build("mineminenomi");
      });
   }
}
