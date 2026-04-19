package xyz.pixelatedw.mineminenomi.abilities.doku;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doku.VenomRoadProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class VenomRoadAbility extends Ability {
   private static final Component VENOM_NAME;
   private static final ResourceLocation NORMAL_ICON;
   private static final ResourceLocation VENOM_ICON;
   public static final int COOLDOWN = 80;
   public static final RegistryObject<AbilityCore<VenomRoadAbility>> INSTANCE;
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final AltModeComponent<Mode> altModeComponent;
   private final AnimationComponent animationComponent;
   private boolean isMovingOwner;
   private int firstTick;
   private int ticks;
   private List<Pair<Vec3, VenomRoadProjectile>> projectiles;
   private VenomRoadProjectile projectileUsed;

   public VenomRoadAbility(AbilityCore<VenomRoadAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<Mode>(this, Mode.class, VenomRoadAbility.Mode.NORMAL, true)).addChangeModeEvent(this::onAltModeChange);
      this.animationComponent = new AnimationComponent(this);
      this.projectiles = new ArrayList();
      this.addComponents(new AbilityComponent[]{this.projectileComponent, this.altModeComponent, this.animationComponent});
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::useEvent);
      this.addTickEvent(this::tickEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      if (!this.isMovingOwner) {
         boolean isNearStartPoint = false;

         for(Pair<Vec3, VenomRoadProjectile> pair : this.projectiles) {
            if (pair.getValue() != null && ((VenomRoadProjectile)pair.getValue()).m_6084_() && entity.m_20182_().m_82509_((Position)pair.getKey(), (double)10.0F)) {
               isNearStartPoint = true;
               this.projectileUsed = (VenomRoadProjectile)pair.getValue();
               break;
            }
         }

         if (isNearStartPoint) {
            entity.m_20219_(this.projectileUsed.getStartPos().m_82520_((double)0.0F, (double)1.0F, (double)0.0F));
            this.firstTick = this.ticks;
            this.isMovingOwner = true;
            this.animationComponent.start(entity, ModAnimations.SHOOT_SELF_FORWARD, 30);
         } else {
            this.projectiles.removeIf((pairx) -> pairx.getValue() == null || !((VenomRoadProjectile)pairx.getValue()).m_6084_());
            if (this.projectiles.size() > 2) {
               Pair<Vec3, VenomRoadProjectile> firstPair = (Pair)this.projectiles.stream().findFirst().orElse((Object)null);
               if (firstPair == null) {
                  return;
               }

               ((VenomRoadProjectile)firstPair.getValue()).m_146870_();
               this.projectiles.remove(firstPair);
            }

            this.isMovingOwner = false;
            VenomRoadProjectile projectile = (VenomRoadProjectile)this.projectileComponent.getNewProjectile(entity);
            this.projectileComponent.shoot(projectile, entity, entity.m_146909_(), entity.m_146908_(), 3.0F, 1.0F);
            Pair<Vec3, VenomRoadProjectile> pair = ImmutablePair.of(entity.m_20182_(), projectile);
            this.projectiles.add(pair);
            this.cooldownComponent.startCooldown(entity, 80.0F);
         }

      }
   }

   private VenomRoadProjectile createProjectile(LivingEntity entity) {
      boolean isDemonForm = ((MorphInfo)ModMorphs.VENOM_DEMON.get()).isActive(entity);
      VenomRoadProjectile projectile = new VenomRoadProjectile(entity.m_9236_(), entity, this, isDemonForm);
      return projectile;
   }

   public void tickEvent(LivingEntity entity, IAbility ability) {
      ++this.ticks;
      if (!entity.m_9236_().f_46443_ && this.isMovingOwner) {
         Vec3 dest = new Vec3(this.projectileUsed.getPreciseCurrentX(), this.projectileUsed.getPreciseCurrentY(), this.projectileUsed.getPreciseCurrentZ());
         boolean hasArrived = entity.m_20182_().m_82509_(dest, (double)10.0F);
         boolean pushedForTooLong = this.ticks - this.firstTick > 40;
         if (hasArrived || pushedForTooLong) {
            this.isMovingOwner = false;
            this.cooldownComponent.startCooldown(entity, 80.0F);
            entity.f_19789_ = 0.0F;
            return;
         }

         if (AbilityUseConditions.canUseMomentumAbilities(entity).isSuccess()) {
            Vec3 vec = dest.m_82520_((double)0.0F, (double)1.0F, (double)0.0F).m_82546_(this.projectileUsed.getStartPos()).m_82541_().m_82490_((double)3.0F);
            AbilityHelper.setDeltaMovement(entity, vec);
            entity.f_19789_ = 0.0F;
         }
      }

   }

   public void setNormalMode(LivingEntity entity) {
      this.altModeComponent.setMode(entity, VenomRoadAbility.Mode.NORMAL);
   }

   public void setVenomMode(LivingEntity entity) {
      this.altModeComponent.setMode(entity, VenomRoadAbility.Mode.VENOM);
   }

   private boolean onAltModeChange(LivingEntity entity, IAbility ability, Mode mode) {
      if (mode == VenomRoadAbility.Mode.VENOM) {
         super.setDisplayName(VENOM_NAME);
         super.setDisplayIcon(VENOM_ICON);
      } else if (mode == VenomRoadAbility.Mode.NORMAL) {
         super.setDisplayName(((AbilityCore)INSTANCE.get()).getLocalizedName());
         super.setDisplayIcon(NORMAL_ICON);
      }

      return true;
   }

   static {
      VENOM_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "venom_road_venom", "Demon Road"));
      NORMAL_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/venom_road.png");
      VENOM_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/venom_road_venom.png");
      INSTANCE = ModRegistry.registerAbility("venom_road", "Venom Road", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Fires a Hydra at the target location that stays there for a few seconds during which time the user can use them to move along their path.", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, VenomRoadAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(80.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.POISON).build("mineminenomi");
      });
   }

   private static enum Mode {
      NORMAL,
      VENOM;

      // $FF: synthetic method
      private static Mode[] $values() {
         return new Mode[]{NORMAL, VENOM};
      }
   }
}
