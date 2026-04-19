package xyz.pixelatedw.mineminenomi.abilities.kage;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.DoppelmanEntity;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KagemushaAbility extends Ability {
   private static final float COOLDOWN = 100.0F;
   public static final RegistryObject<AbilityCore<KagemushaAbility>> INSTANCE = ModRegistry.registerAbility("kagemusha", "Kagemusha", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user to change its position with that of the Doppelman.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, KagemushaAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F)).build("mineminenomi");
   });

   public KagemushaAbility(AbilityCore<KagemushaAbility> core) {
      super(core);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      DoppelmanAbility doppelmanAbility = (DoppelmanAbility)AbilityCapability.get(entity).map((props) -> (DoppelmanAbility)props.getEquippedAbility((AbilityCore)DoppelmanAbility.INSTANCE.get())).orElse((Object)null);
      if (doppelmanAbility != null && doppelmanAbility.isContinuous() && doppelmanAbility.getDoppelman() != null && doppelmanAbility.getDoppelman().m_6084_()) {
         DoppelmanEntity doppelman = doppelmanAbility.getDoppelman();
         BlockPos temp = entity.m_20183_();
         entity.m_8127_();
         entity.m_20324_(doppelman.m_20185_(), doppelman.m_20186_(), doppelman.m_20189_());
         doppelman.m_8127_();
         doppelman.m_20324_((double)temp.m_123341_(), (double)temp.m_123342_(), (double)temp.m_123343_());
      }

      this.cooldownComponent.startCooldown(entity, 100.0F);
   }
}
