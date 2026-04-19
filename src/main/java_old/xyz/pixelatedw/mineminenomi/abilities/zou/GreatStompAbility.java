package xyz.pixelatedw.mineminenomi.abilities.zou;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GreatStompAbility extends Ability {
   private static final int COOLDOWN = 240;
   private static final int RANGE = 10;
   private static final float DAMAGE = 15.0F;
   public static final RegistryObject<AbilityCore<GreatStompAbility>> INSTANCE = ModRegistry.registerAbility("great_stomp", "Great Stomp", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("By stomping the ground as a full-form elephant, the user creates a shockwave.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GreatStompAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.ZOU_GUARD)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F)).setSourceType(SourceType.INDIRECT).setSourceElement(SourceElement.SHOCKWAVE).setSourceHakiNature(SourceHakiNature.SPECIAL).build("mineminenomi");
   });
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);

   public GreatStompAbility(AbilityCore<GreatStompAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.rangeComponent, this.dealDamageComponent});
      this.addCanUseCheck(ZouHelper::requiresGuardPoint);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 10.0F)) {
         if (this.dealDamageComponent.hurtTarget(entity, target, 15.0F)) {
            target.m_6034_(target.m_20185_(), target.m_20186_() + (double)3.0F, target.m_20189_());
         }
      }

      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GREAT_STOMP.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      this.cooldownComponent.startCooldown(entity, 240.0F);
   }
}
