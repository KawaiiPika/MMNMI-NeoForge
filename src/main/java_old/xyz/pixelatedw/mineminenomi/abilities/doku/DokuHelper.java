package xyz.pixelatedw.mineminenomi.abilities.doku;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.particles.effects.doku.DokuParticleEffectDetails;

public class DokuHelper {
   public static final DokuParticleEffectDetails VENOM_DETAILS = (new DokuParticleEffectDetails()).setHasVenomDemon();

   public static Result requiresVenomDemon(LivingEntity entity, IAbility ability) {
      return AbilityUseConditions.requiresMorph(entity, ability, (MorphInfo)ModMorphs.VENOM_DEMON.get());
   }
}
