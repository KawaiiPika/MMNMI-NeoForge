package xyz.pixelatedw.mineminenomi.abilities.nekoleopard;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class NekoNightVisionAbility extends PassiveAbility {
   public static final RegistryObject<AbilityCore<NekoNightVisionAbility>> INSTANCE = ModRegistry.registerAbility("neko_night_vision", "Night Vision", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Grants the user night vision.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, NekoNightVisionAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.LEOPARD_HEAVY, ModMorphs.LEOPARD_WALK)).setHidden().build("mineminenomi");
   });

   public NekoNightVisionAbility(AbilityCore<NekoNightVisionAbility> core) {
      super(core);
      this.addCanUseCheck(NekoLeopardHelper::requiresEitherPoint);
      this.addDuringPassiveEvent(this::duringPassiveEvent);
   }

   private void duringPassiveEvent(LivingEntity entity) {
      if (entity.m_9236_().m_46462_() && (!entity.m_21023_(MobEffects.f_19611_) || entity.m_21124_(MobEffects.f_19611_).m_19557_() <= 210)) {
         entity.m_7292_(new MobEffectInstance(MobEffects.f_19611_, 500, 0, false, false));
      }

   }
}
