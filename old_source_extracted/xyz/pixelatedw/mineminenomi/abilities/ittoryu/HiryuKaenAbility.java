package xyz.pixelatedw.mineminenomi.abilities.ittoryu;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.DropHitAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class HiryuKaenAbility extends DropHitAbility {
   private static final int COOLDOWN = 240;
   private static final float RANGE = 4.5F;
   private static final float DAMAGE = 20.0F;
   private static final float HOLD_TIME = 15.0F;
   private static final int DORIKI = 5000;
   private static final int WEAPON_MASTERY_POINTS = 20;
   public static final RegistryObject<AbilityCore<HiryuKaenAbility>> INSTANCE = ModRegistry.registerAbility("hiryu_kaen", "Hiryu: Kaen", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user jumps high up into the air and slashes their opponent. After slashing them, the user's opponent then bursts into flames from where they were slashed.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, HiryuKaenAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F), DealDamageComponent.getTooltip(20.0F), RangeComponent.getTooltip((e) -> (float)e.m_21133_((Attribute)ForgeMod.ENTITY_REACH.get()), RangeComponent.RangeType.AOE)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.SLASH).setSourceElement(SourceElement.FIRE).setNodeFactories(HiryuKaenAbility::createNode).build("mineminenomi");
   });
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);

   public HiryuKaenAbility(AbilityCore<HiryuKaenAbility> core) {
      super(core);
      this.setCancelable(false);
      this.addComponents(new AbilityComponent[]{this.dealDamageComponent, this.rangeComponent});
      this.continuousComponent.addStartEvent(this::onContinuityStart);
      this.continuousComponent.addTickEvent(this::onContinuityTick);
      this.continuousComponent.addEndEvent(this::onContinuityEnd);
      this.addCanUseCheck(AbilityUseConditions::requiresSword);
      this.addCanUseCheck(AbilityUseConditions::requiresOneFreeHand);
   }

   public void onLanding(LivingEntity entity) {
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      this.hitTrackerComponent.clearHits();
      Vec3 speed = entity.m_20154_().m_82542_((double)4.0F, (double)1.0F, (double)4.0F);
      AbilityHelper.setDeltaMovement(entity, speed.f_82479_, (double)2.25F, speed.f_82481_);
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      if (!(this.continuousComponent.getContinueTime() > 15.0F)) {
         List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, (float)entity.m_21133_((Attribute)ForgeMod.ENTITY_REACH.get()));
         targets.remove(entity);

         for(LivingEntity target : targets) {
            if (this.hitTrackerComponent.canHit(target) && entity.m_142582_(target) && this.dealDamageComponent.hurtTarget(entity, target, 20.0F)) {
               target.m_20254_(4);
               Level level = entity.m_9236_();
               if (!level.f_46443_) {
                  ((ServerLevel)entity.m_9236_()).m_7726_().m_8394_(entity, new ClientboundAnimatePacket(entity, 0));
                  WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.HIRYU_KAEN.get(), target, target.m_20185_(), target.m_20186_(), target.m_20189_());
               }
            }
         }

      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 240.0F);
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode hiryuKaen = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-5.0F, -5.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.SWORDSMAN.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      hiryuKaen.setUnlockRule(unlockCondition, unlockAction);
      return hiryuKaen;
   }
}
