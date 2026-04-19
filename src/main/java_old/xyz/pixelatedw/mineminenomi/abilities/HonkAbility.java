package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class HonkAbility extends Ability {
   private static final float COOLDOWN = 10.0F;
   public static final RegistryObject<AbilityCore<HonkAbility>> INSTANCE = ModRegistry.registerAbility("honk", "Honk", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("§8§ohonk honk§r", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, HonkAbility::new)).addDescriptionLine(desc).build("mineminenomi");
   });

   public HonkAbility(AbilityCore<HonkAbility> core) {
      super(core);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.HONK.get(), SoundSource.PLAYERS, 1.0F, 0.9F + this.random.nextFloat() / 4.0F);
      this.cooldownComponent.startCooldown(entity, 10.0F);
   }
}
