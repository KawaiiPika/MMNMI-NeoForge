package xyz.pixelatedw.mineminenomi.abilities.yuki;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.yuki.KamakuraParticleEffect;

public class KamakuraAbility extends Ability {
   private static final int COOLDOWN = 160;
   public static final BlockProtectionRule GRIEF_RULE;
   private static final KamakuraParticleEffect.Details DETAILS;
   public static final RegistryObject<AbilityCore<KamakuraAbility>> INSTANCE;

   public KamakuraAbility(AbilityCore<KamakuraAbility> core) {
      super(core);
      super.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      Vec3 vec3d = entity.m_6047_() ? entity.m_20182_() : WyHelper.rayTraceBlocksAndEntities(entity, (double)64.0F).m_82450_();
      SimpleBlockPlacer placer = (new SimpleBlockPlacer()).setBlock(((Block)ModBlocks.HARDENED_SNOW.get()).m_49966_()).setSize(4).setHollow().setRule(GRIEF_RULE);
      placer.generate(entity.m_9236_(), BlockPos.m_274446_(vec3d), BlockGenerators.SPHERE);
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.KAMAKURA.get(), entity, vec3d.f_82479_, vec3d.f_82480_, vec3d.f_82481_, DETAILS);
      super.cooldownComponent.startCooldown(entity, 160.0F);
   }

   static {
      GRIEF_RULE = (new BlockProtectionRule.Builder(new BlockProtectionRule[]{DefaultProtectionRules.AIR_FOLIAGE_LIQUID})).addApprovedTags(ModTags.Blocks.BLOCK_PROT_SNOW).build();
      DETAILS = new KamakuraParticleEffect.Details(4);
      INSTANCE = ModRegistry.registerAbility("kamakura", "Kamakura", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates an igloo-like snow barrier where the user's pointing.", (Object)null), ImmutablePair.of("If used while crouching it'll create the igloo around the user.", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, KamakuraAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(160.0F)).build("mineminenomi");
      });
   }
}
