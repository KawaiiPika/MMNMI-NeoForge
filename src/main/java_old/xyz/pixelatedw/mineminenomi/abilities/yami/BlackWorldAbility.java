package xyz.pixelatedw.mineminenomi.abilities.yami;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class BlackWorldAbility extends Ability {
   private static final int COOLDOWN = 200;
   public static final RegistryObject<AbilityCore<BlackWorldAbility>> INSTANCE = ModRegistry.registerAbility("black_world", "Black World", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user spreads their power to the surroundings, blinding enemies and creating pillars of Darkness.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BlackWorldAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F)).build("mineminenomi");
   });

   public BlackWorldAbility(AbilityCore<BlackWorldAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[0]);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      Level level = entity.m_9236_();
      if (level != null && !level.f_46443_) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.BLACK_WORLD.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
         BlockState defaultDarknessState = ((Block)ModBlocks.DARKNESS.get()).m_49966_();

         for(int i = 0; i < 12; ++i) {
            Vec3 vec = Vec3.m_82498_(0.0F, (float)entity.m_217043_().m_188503_(360)).m_82541_().m_82490_((double)(5 + entity.m_217043_().m_188503_(10)));

            for(int j = -5; j < 5; ++j) {
               NuWorld.setBlockState((Entity)entity, entity.m_20183_().m_7918_((int)vec.f_82479_, j, (int)vec.f_82481_), defaultDarknessState, 3, DefaultProtectionRules.AIR);
               NuWorld.setBlockState((Entity)entity, entity.m_20183_().m_7918_((int)vec.f_82479_ + 1, j, (int)vec.f_82481_), defaultDarknessState, 3, DefaultProtectionRules.AIR);
               NuWorld.setBlockState((Entity)entity, entity.m_20183_().m_7918_((int)vec.f_82479_, j, (int)vec.f_82481_ + 1), defaultDarknessState, 3, DefaultProtectionRules.AIR);
               NuWorld.setBlockState((Entity)entity, entity.m_20183_().m_7918_((int)vec.f_82479_ + 1, j, (int)vec.f_82481_ + 1), defaultDarknessState, 3, DefaultProtectionRules.AIR);
            }
         }

         for(LivingEntity target : WyHelper.getNearbyLiving(entity.m_20182_(), level, (double)20.0F, ModEntityPredicates.getEnemyFactions(entity))) {
            target.m_7292_(new MobEffectInstance(MobEffects.f_19610_, 200, 1, false, false));
            target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 200, 1, false, false));
         }

         this.cooldownComponent.startCooldown(entity, 200.0F);
      }
   }
}
