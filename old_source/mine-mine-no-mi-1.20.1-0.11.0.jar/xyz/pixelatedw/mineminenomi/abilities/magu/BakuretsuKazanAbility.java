package xyz.pixelatedw.mineminenomi.abilities.magu;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class BakuretsuKazanAbility extends Ability {
   private static final float MAX_CHARGE_TIME = 100.0F;
   private static final float MIN_COOLDOWN = 100.0F;
   private static final float MAX_COOLDOWN = 600.0F;
   public static final RegistryObject<AbilityCore<BakuretsuKazanAbility>> INSTANCE = ModRegistry.registerAbility("bakuretsu_kazan", "Bakuretsu Kazan", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("By spreading magma to the surroundings, the user turns everything into lava", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BakuretsuKazanAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F, 600.0F)).setSourceElement(SourceElement.MAGMA).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this, (comp) -> (double)comp.getChargePercentage() >= 0.3)).addEndEvent(this::onChargeEnd);

   public BakuretsuKazanAbility(AbilityCore<BakuretsuKazanAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent});
      this.addUseEvent(this::onUse);
   }

   private void onUse(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 100.0F);
   }

   private void onChargeEnd(LivingEntity entity, IAbility ability) {
      Level level = entity.m_9236_();
      if (level != null && !level.f_46443_) {
         int range = (int)(this.chargeComponent.getChargePercentage() * 16.0F);
         MaguHelper.generateLavaPool(level, entity.m_20183_(), range);
         level.m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.BAKURETSU_KAZAN.get(), SoundSource.PLAYERS, 10.0F, 1.0F);
         float cooldown = 100.0F + this.chargeComponent.getChargeTime() * 5.0F;
         this.cooldownComponent.startCooldown(entity, cooldown);
      }
   }
}
