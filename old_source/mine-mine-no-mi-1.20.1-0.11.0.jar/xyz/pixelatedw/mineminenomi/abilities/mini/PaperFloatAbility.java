package xyz.pixelatedw.mineminenomi.abilities.mini;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PaperFloatAbility extends PassiveAbility {
   public static final RegistryObject<AbilityCore<PaperFloatAbility>> INSTANCE = ModRegistry.registerAbility("paper_float", "Paper Float", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("While in the mini form and holding a piece of paper the user is able to float.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, PaperFloatAbility::new)).addDescriptionLine(desc).build("mineminenomi");
   });
   private final AnimationComponent animationComponent = new AnimationComponent(this);

   public PaperFloatAbility(AbilityCore<PaperFloatAbility> ability) {
      super(ability);
      this.addComponents(new AbilityComponent[]{this.animationComponent});
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addDuringPassiveEvent(this::duringPassiveEvent);
   }

   private void duringPassiveEvent(LivingEntity entity) {
      boolean isMiniActive = (Boolean)AbilityCapability.get(entity).map((props) -> (MiniMiniAbility)props.getEquippedAbility((AbilityCore)MiniMiniAbility.INSTANCE.get())).map((abl) -> abl.isContinuous()).orElse(false);
      boolean hasPaper = entity.m_21205_().m_41720_() == Items.f_42516_ || entity.m_21206_().m_41720_() == Items.f_42516_;
      boolean inAir = !entity.m_20096_() && entity.m_20184_().f_82480_ < (double)0.0F;
      if (isMiniActive && hasPaper && inAir) {
         entity.f_19789_ = 0.0F;
         this.animationComponent.start(entity, ModAnimations.RAISE_ARMS, 20);
         AbilityHelper.setDeltaMovement(entity, entity.m_20184_().f_82479_, entity.m_20184_().f_82480_ / (double)2.0F, entity.m_20184_().f_82481_);
      }

   }
}
