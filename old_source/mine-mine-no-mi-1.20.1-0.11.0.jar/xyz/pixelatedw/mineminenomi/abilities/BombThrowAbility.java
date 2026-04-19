package xyz.pixelatedw.mineminenomi.abilities;

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
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.BombEntity;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BombThrowAbility extends Ability {
   private static final float COOLDOWN = 240.0F;
   public static final RegistryObject<AbilityCore<BombThrowAbility>> INSTANCE = ModRegistry.registerAbility("bomb_throw", "Bomb Throw", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Throws a bomb.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, BombThrowAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F)).setSourceElement(SourceElement.EXPLOSION).build("mineminenomi");
   });

   public BombThrowAbility(AbilityCore<BombThrowAbility> core) {
      super(core);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      BombEntity bomb = new BombEntity(entity.m_9236_(), entity);
      bomb.setExplodeOnImpact();
      Vec3 look = entity.m_20154_().m_82542_((double)2.0F, (double)2.0F, (double)2.0F);
      bomb.m_7678_(entity.m_20182_().m_7096_() + look.m_7096_(), entity.m_20188_(), entity.m_20182_().m_7094_() + look.m_7094_(), (float)entity.m_217043_().m_188503_(360), (float)entity.m_217043_().m_188503_(360));
      AbilityHelper.setDeltaMovement(bomb, look);
      entity.m_9236_().m_7967_(bomb);
      this.cooldownComponent.startCooldown(entity, 240.0F);
   }
}
