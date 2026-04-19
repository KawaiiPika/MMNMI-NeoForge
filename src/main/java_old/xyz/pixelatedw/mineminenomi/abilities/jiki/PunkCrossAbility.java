package xyz.pixelatedw.mineminenomi.abilities.jiki;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.abilities.sabi.RustSkinAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PunkCrossAbility extends Ability {
   private static final int REQUIRED_IRON = 50;
   private static final int COOLDOWN = 200;
   private static final int EFFECT_TIMER = 80;
   private static final int RANGE = 80;
   public static final RegistryObject<AbilityCore<PunkCrossAbility>> INSTANCE = ModRegistry.registerAbility("punk_cross", "Punk Cross", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Uses %s magnetic items and stuns the target in a cross-shaped structure.", new Object[]{MentionHelper.mentionText(50)}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, PunkCrossAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ContinuousComponent.getTooltip(80.0F)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.METAL).setSourceType(SourceType.BLUNT).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private LivingEntity target;
   private boolean hasTarget;
   private Vec3 lastKnownTargetPos;
   private List<ItemStack> magneticItems = new ArrayList();

   public PunkCrossAbility(AbilityCore<PunkCrossAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.rangeComponent});
      this.addCanUseCheck(JikiHelper.getMetalicItemsCheck(50));
      this.addUseEvent(this::useEvent);
      this.addTickEvent(this::tickEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 80.0F);
   }

   private void tickEvent(LivingEntity entity, IAbility ability) {
      if (this.hasTarget && (this.target == null || !this.target.m_6084_() || !this.target.m_21023_((MobEffect)ModEffects.PUNK_CROSS.get()))) {
         JikiHelper.dropComponentItems(entity.m_9236_(), this.lastKnownTargetPos, this.magneticItems);
         this.target = null;
         this.hasTarget = false;
      }

   }

   private void startContinuityEvent(LivingEntity player, IAbility ability) {
      this.target = null;
      this.hasTarget = false;
      List<ItemStack> inventory = ItemsHelper.getAllInventoryItems(player);
      this.magneticItems = JikiHelper.getMagneticItemsNeeded(inventory, 50.0F);
      List<LivingEntity> targets = this.rangeComponent.getTargetsInLine(player, 80.0F, 3.0F);
      if (!targets.isEmpty()) {
         for(LivingEntity target : targets) {
            IAbilityData abilityDataProps = (IAbilityData)AbilityCapability.get(target).orElse((Object)null);
            if (abilityDataProps != null) {
               RustSkinAbility rustSkinAbility = (RustSkinAbility)abilityDataProps.getPassiveAbility((AbilityCore)RustSkinAbility.INSTANCE.get());
               if (rustSkinAbility != null && !rustSkinAbility.isPaused()) {
                  break;
               }
            }

            target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.PUNK_CROSS.get(), 80, 1));
            JikiHelper.spawnAttractEffect(target);
            this.hasTarget = true;
            this.target = target;
            this.lastKnownTargetPos = target.m_20182_();
         }
      } else {
         HitResult hit = WyHelper.rayTraceBlocks(player, (double)40.0F);
         JikiHelper.dropComponentItems(player.m_9236_(), hit.m_82450_(), this.magneticItems);
         this.continuousComponent.stopContinuity(player);
      }

   }

   private void duringContinuityEvent(LivingEntity player, IAbility ability) {
      if (this.target != null) {
         this.lastKnownTargetPos = this.target.m_20182_();
      }

   }

   private void endContinuityEvent(LivingEntity player, IAbility ability) {
      if (this.target != null) {
         this.target.m_21195_((MobEffect)ModEffects.PUNK_CROSS.get());
      }

      if (this.target != null) {
         JikiHelper.dropComponentItems(player.m_9236_(), this.lastKnownTargetPos, this.magneticItems);
      }

      this.hasTarget = false;
      this.target = null;
      this.cooldownComponent.startCooldown(player, 200.0F);
   }
}
