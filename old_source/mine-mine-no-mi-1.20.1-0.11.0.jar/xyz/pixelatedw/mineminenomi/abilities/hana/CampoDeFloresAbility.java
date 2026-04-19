package xyz.pixelatedw.mineminenomi.abilities.hana;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hana.CampoDeFloresEntity;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class CampoDeFloresAbility extends Ability {
   private static final int COOLDOWN = 200;
   public static final int HOLD_TIME = 100;
   public static final int RANGE = 10;
   public static final int DAMAGE = 10;
   public static final RegistryObject<AbilityCore<CampoDeFloresAbility>> INSTANCE = ModRegistry.registerAbility("campo_de_flores", "Campo de Flores", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Sprouts multiple hands in front of the user stunning and sending all enemies in a radius flying", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, CampoDeFloresAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, HanaHelper.REQUIRES_MIL_FLEUR).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ContinuousComponent.getTooltip(100.0F), RangeComponent.getTooltip(10.0F, RangeComponent.RangeType.AOE), DealDamageComponent.getTooltip(10.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private CampoDeFloresEntity campoDeFlores;

   public CampoDeFloresAbility(AbilityCore<CampoDeFloresAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.rangeComponent, this.dealDamageComponent});
      this.addCanUseCheck(this::requiresMilFleur);
      this.addContinueUseCheck(this::requiresMilFleur);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 100.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      HitResult mop = WyHelper.rayTraceBlocksAndEntities(entity);
      this.campoDeFlores = new CampoDeFloresEntity(entity.m_9236_(), entity, this);
      this.campoDeFlores.m_6034_(mop.m_82450_().f_82479_, mop.m_82450_().f_82480_, mop.m_82450_().f_82481_);
      entity.m_9236_().m_7967_(this.campoDeFlores);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      if (this.campoDeFlores != null) {
         this.campoDeFlores.m_146870_();
      }

      this.cooldownComponent.startCooldown(entity, 200.0F);
   }

   private Result requiresMilFleur(LivingEntity entity, IAbility ability) {
      boolean hasMilFleur = (Boolean)AbilityCapability.get(entity).map((props) -> (MilFleurAbility)props.getEquippedAbility((AbilityCore)MilFleurAbility.INSTANCE.get())).map((abl) -> abl.isContinuous()).orElse(false);
      return hasMilFleur ? Result.success() : Result.fail(Component.m_237110_(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE, new Object[]{((AbilityCore)MilFleurAbility.INSTANCE.get()).getLocalizedName().getString()}));
   }
}
