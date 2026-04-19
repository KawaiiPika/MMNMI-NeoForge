package xyz.pixelatedw.mineminenomi.abilities.hana;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class HanaHelper {
   public static final AbilityDescriptionLine.IDescriptionLine REQUIRES_MIL_FLEUR = (entity, ability) -> Component.m_237110_(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE, new Object[]{"§a" + ((AbilityCore)MilFleurAbility.INSTANCE.get()).getLocalizedName().getString() + "§r"});
   public static final UUID MIL_DAMAGE_BONUS = UUID.fromString("9fab4eda-6b81-4602-90f9-d2373304604f");

   public static void spawnBlossomEffect(LivingEntity source) {
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.BLOOM.get(), source, source.m_20185_(), source.m_20186_(), source.m_20189_());
      source.m_9236_().m_5594_((Player)null, source.m_20183_(), (SoundEvent)ModSounds.HANA_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
   }
}
