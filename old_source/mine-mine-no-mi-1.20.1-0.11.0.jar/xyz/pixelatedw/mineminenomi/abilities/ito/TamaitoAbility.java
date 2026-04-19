package xyz.pixelatedw.mineminenomi.abilities.ito;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ito.TamaitoProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class TamaitoAbility extends Ability {
   private static final float COOLDOWN = 30.0F;
   public static final RegistryObject<AbilityCore<TamaitoAbility>> INSTANCE = ModRegistry.registerAbility("tamaito", "Tamaito", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user shoots a small bundle of strings, acting like a bullet", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, TamaitoAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(30.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).build("mineminenomi");
   });
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final AnimationComponent animationComponent = new AnimationComponent(this);

   public TamaitoAbility(AbilityCore<TamaitoAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.projectileComponent, this.animationComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity, 4.0F, 1.2F);
      this.animationComponent.start(entity, ModAnimations.AIM_SNIPER, 7);
      if (!entity.m_9236_().f_46443_) {
         entity.m_21011_(InteractionHand.MAIN_HAND, true);
      }

      this.cooldownComponent.startCooldown(entity, 30.0F);
   }

   private TamaitoProjectile createProjectile(LivingEntity entity) {
      TamaitoProjectile proj = new TamaitoProjectile(entity.m_9236_(), entity, this);
      return proj;
   }
}
