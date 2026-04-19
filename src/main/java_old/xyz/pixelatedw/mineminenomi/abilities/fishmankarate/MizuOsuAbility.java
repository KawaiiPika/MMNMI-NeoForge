package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class MizuOsuAbility extends Ability {
   private static final int COOLDOWN = 160;
   private static final float RANGE = 2.0F;
   public static final RegistryObject<AbilityCore<MizuOsuAbility>> INSTANCE = ModRegistry.registerAbility("mizu_osu", "Mizu Osu", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Spits water with enough force to push away any enemy in front of the user.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, MizuOsuAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(160.0F), RangeComponent.getTooltip(2.0F, RangeComponent.RangeType.LINE)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final RangeComponent rangeComponent = new RangeComponent(this);

   public MizuOsuAbility(AbilityCore<MizuOsuAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.rangeComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      for(LivingEntity target : this.rangeComponent.getTargetsInLine(entity, 1.5F, 2.0F)) {
         Vec3 diff = target.m_20182_().m_82546_(entity.m_20182_()).m_82541_().m_82490_((double)4.0F);
         AbilityHelper.setDeltaMovement(target, diff.f_82479_, 0.2, diff.f_82481_);
      }

      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.MIZU_OSU.get(), entity, entity.m_20185_(), (double)1.75F + entity.m_20186_(), entity.m_20189_());
      this.cooldownComponent.startCooldown(entity, 160.0F);
   }
}
