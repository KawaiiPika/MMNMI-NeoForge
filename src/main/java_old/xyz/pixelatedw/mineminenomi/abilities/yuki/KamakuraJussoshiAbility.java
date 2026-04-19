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
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.yuki.KamakuraParticleEffect;

public class KamakuraJussoshiAbility extends Ability {
   private static final int COOLDOWN = 240;
   private static final KamakuraParticleEffect.Details DETAILS = new KamakuraParticleEffect.Details(8);
   public static final RegistryObject<AbilityCore<KamakuraJussoshiAbility>> INSTANCE = ModRegistry.registerAbility("kamakura_jussoshi", "Kamakura Jussoshi", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Like 'Kamakura', but creates a multi-layered snow barrier.", (Object)null), ImmutablePair.of("If used while crouching it'll create the igloo around the user.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, KamakuraJussoshiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F)).build("mineminenomi");
   });

   public KamakuraJussoshiAbility(AbilityCore<KamakuraJussoshiAbility> core) {
      super(core);
      super.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      Vec3 vec3d = entity.m_6047_() ? entity.m_20182_() : WyHelper.rayTraceBlocksAndEntities(entity, (double)64.0F).m_82450_();
      BlockPos pos = BlockPos.m_274446_(vec3d);
      SimpleBlockPlacer placer = (new SimpleBlockPlacer()).setBlock(((Block)ModBlocks.HARDENED_SNOW.get()).m_49966_()).setSize(4).setHollow().setRule(KamakuraAbility.GRIEF_RULE);
      placer.generate(entity.m_9236_(), pos, BlockGenerators.SPHERE);
      placer = (new SimpleBlockPlacer()).setBlock(((Block)ModBlocks.HARDENED_SNOW.get()).m_49966_()).setSize(6).setHollow().setRule(KamakuraAbility.GRIEF_RULE);
      placer.generate(entity.m_9236_(), pos, BlockGenerators.SPHERE);
      placer = (new SimpleBlockPlacer()).setBlock(((Block)ModBlocks.HARDENED_SNOW.get()).m_49966_()).setSize(8).setHollow().setRule(KamakuraAbility.GRIEF_RULE);
      placer.generate(entity.m_9236_(), pos, BlockGenerators.SPHERE);
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.KAMAKURA.get(), entity, vec3d.f_82479_, vec3d.f_82480_, vec3d.f_82481_, DETAILS);
      super.cooldownComponent.startCooldown(entity, 240.0F);
   }
}
