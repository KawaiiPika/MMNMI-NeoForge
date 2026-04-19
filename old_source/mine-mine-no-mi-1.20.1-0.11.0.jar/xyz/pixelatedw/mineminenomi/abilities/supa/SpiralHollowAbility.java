package xyz.pixelatedw.mineminenomi.abilities.supa;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.supa.SpiralHollowProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SpiralHollowAbility extends Ability {
   private static final float COOLDOWN = 180.0F;
   public static final RegistryObject<AbilityCore<SpiralHollowAbility>> INSTANCE = ModRegistry.registerAbility("spiral_hollow", "Spiral Hollow", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates circular blades along the user's forearms slicing enemies in a close line", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SpiralHollowAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(180.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);

   public SpiralHollowAbility(AbilityCore<SpiralHollowAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.dealDamageComponent, this.projectileComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      Vec3 dirVec = Vec3.f_82478_;
      Direction dir = Direction.m_122364_((double)entity.m_146908_());
      dirVec = dirVec.m_82520_((double)Math.abs(dir.m_122436_().m_123341_()), (double)Math.abs(dir.m_122436_().m_123342_()), (double)Math.abs(dir.m_122436_().m_123343_())).m_82542_(1.2, (double)1.0F, 1.2);
      double yPos = entity.m_20186_() + (double)entity.m_20192_() - (double)0.5F;
      SpiralHollowProjectile leftProj = (SpiralHollowProjectile)this.projectileComponent.getNewProjectile(entity);
      leftProj.m_7678_(entity.m_20185_() + dirVec.f_82481_, yPos, entity.m_20189_() + dirVec.f_82479_, 0.0F, 0.0F);
      entity.m_9236_().m_7967_(leftProj);
      leftProj.m_37251_(entity, entity.m_146909_(), entity.m_146908_(), 0.0F, 2.0F, 1.0F);
      SpiralHollowProjectile rightProj = (SpiralHollowProjectile)this.projectileComponent.getNewProjectile(entity);
      rightProj.m_7678_(entity.m_20185_() - dirVec.f_82481_, yPos, entity.m_20189_() - dirVec.f_82479_, 0.0F, 0.0F);
      entity.m_9236_().m_7967_(rightProj);
      rightProj.m_37251_(entity, entity.m_146909_(), entity.m_146908_(), 0.0F, 2.0F, 1.0F);
      entity.m_21011_(entity.m_7655_(), true);
      this.cooldownComponent.startCooldown(entity, 180.0F);
   }

   private SpiralHollowProjectile createProjectile(LivingEntity entity) {
      return new SpiralHollowProjectile(entity.m_9236_(), entity, this);
   }
}
