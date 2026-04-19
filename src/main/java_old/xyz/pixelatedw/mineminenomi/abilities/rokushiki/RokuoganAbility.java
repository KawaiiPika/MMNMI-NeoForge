package xyz.pixelatedw.mineminenomi.abilities.rokushiki;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.rokushiki.RokuoganProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class RokuoganAbility extends PunchAbility {
   private static final float COOLDOWN = 700.0F;
   private static final int DORIKI = 9000;
   private static final int MARTIAL_ARTS_POINTS = 50;
   public static final RegistryObject<AbilityCore<RokuoganAbility>> INSTANCE = ModRegistry.registerAbility("rokuogan", "Rokuogan", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user places both their fists right in front of the target to focus their physical strength to launch a devastating shockwave forward", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, RokuoganAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(700.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceElement(SourceElement.SHOCKWAVE).setSourceType(SourceType.FIST).setNodeFactories(RokuoganAbility::createNode).build("mineminenomi");
   });
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);

   public RokuoganAbility(AbilityCore<RokuoganAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.projectileComponent, this.explosionComponent});
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      RokuoganProjectile proj = (RokuoganProjectile)this.projectileComponent.getNewProjectile(entity);
      entity.m_9236_().m_7967_(proj);
      proj.m_37251_(entity, entity.m_146909_(), entity.m_146908_(), 0.0F, 2.0F, 1.0F);
      Vec3 lookVec = entity.m_20154_().m_82542_((double)2.0F, (double)2.0F, (double)2.0F);
      proj.m_6034_(target.m_20185_() + lookVec.f_82479_, target.m_20186_() + lookVec.f_82480_, target.m_20189_() + lookVec.f_82481_);
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.ROKUOGAN.get(), SoundSource.PLAYERS, 2.5F, 0.2F + entity.m_217043_().m_188501_());
      return true;
   }

   public int getUseLimit() {
      return 1;
   }

   public float getPunchDamage() {
      return 60.0F;
   }

   public float getPunchCooldown() {
      return 700.0F;
   }

   private RokuoganProjectile createProjectile(LivingEntity entity) {
      RokuoganProjectile proj = new RokuoganProjectile(entity.m_9236_(), entity, this);
      return proj;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode rokuogan = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(7.0F, 0.0F));
      rokuogan.addPrerequisites(((AbilityCore)GeppoAbility.INSTANCE.get()).getNode(entity), ((AbilityCore)TekkaiAbility.INSTANCE.get()).getNode(entity), ((AbilityCore)RankyakuAbility.INSTANCE.get()).getNode(entity), ((AbilityCore)SoruAbility.INSTANCE.get()).getNode(entity), ((AbilityCore)ShiganAbility.INSTANCE.get()).getNode(entity), ((AbilityCore)KamieAbility.INSTANCE.get()).getNode(entity));
      NodeUnlockCondition unlockCondition = RequiredRaceUnlockCondition.requires((Race)ModRaces.HUMAN.get()).and(DorikiUnlockCondition.atLeast((double)9000.0F));
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      rokuogan.setUnlockRule(unlockCondition, unlockAction);
      return rokuogan;
   }
}
