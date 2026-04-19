package xyz.pixelatedw.mineminenomi.abilities.wara;

import java.util.UUID;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.SoulboundItemHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.items.StrawDollItem;

public class LifeMinusAbility extends PassiveAbility {
   public static final RegistryObject<AbilityCore<LifeMinusAbility>> INSTANCE = ModRegistry.registerAbility("life_minus", "Life Minus", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Transfers the damage taken to a Straw Doll in your inventory.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, LifeMinusAbility::new)).addDescriptionLine(desc).setSourceType(SourceType.FRIENDLY, SourceType.INTERNAL).build("mineminenomi");
   });
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::onDamageTaken);

   public LifeMinusAbility(AbilityCore<LifeMinusAbility> ability) {
      super(ability);
      this.addComponents(new AbilityComponent[]{this.dealDamageComponent, this.damageTakenComponent});
   }

   private float onDamageTaken(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      if (super.canUse(entity).isFail()) {
         return damage;
      } else if (!(entity instanceof Player)) {
         return damage;
      } else {
         Player attacked = (Player)entity;

         for(int i = 0; i < attacked.m_150109_().f_35974_.size(); ++i) {
            ItemStack stack = attacked.m_150109_().m_8020_(i);
            if (stack.m_41720_() == ModItems.STRAW_DOLL.get()) {
               Pair<UUID, LivingEntity> strawDollOwner = SoulboundItemHelper.getOwner(attacked.m_9236_(), stack);
               StrawDollItem.alterStrawDollHealth(stack, -damage);
               if (attacked == strawDollOwner.getValue()) {
                  return damage;
               }

               if (strawDollOwner.getValue() != null) {
                  boolean attack = this.dealDamageComponent.hurtTarget(entity, (LivingEntity)strawDollOwner.getValue(), damage);
                  if (attack && ((LivingEntity)strawDollOwner.getValue()).m_21223_() <= 0.0F || StrawDollItem.getStrawDollHealth(stack) <= 0.0F) {
                     this.spawnParticles((ServerLevel)attacked.m_9236_(), attacked.m_20185_(), attacked.m_20186_(), attacked.m_20189_());
                     this.spawnParticles((ServerLevel)((LivingEntity)strawDollOwner.getValue()).m_9236_(), ((LivingEntity)strawDollOwner.getValue()).m_20185_(), ((LivingEntity)strawDollOwner.getValue()).m_20186_(), ((LivingEntity)strawDollOwner.getValue()).m_20189_());
                     EntityStatsCapability.get((LivingEntity)strawDollOwner.getValue()).ifPresent((props) -> props.setStrawDoll(true));
                     attacked.m_150109_().m_36057_(stack);
                  }

                  return 0.0F;
               }
            }
         }

         return damage;
      }
   }

   private void spawnParticles(ServerLevel world, double posX, double posY, double posZ) {
      for(int i = 0; i < 5; ++i) {
         double offsetX = WyHelper.randomDouble() / (double)2.0F;
         double offsetY = WyHelper.randomDouble() / (double)2.0F;
         double offsetZ = WyHelper.randomDouble() / (double)2.0F;
         world.m_8767_(ParticleTypes.f_123799_, posX + offsetX, posY + offsetY, posZ + offsetZ, 25, (double)0.0F, (double)0.0F, (double)0.0F, 0.1);
      }

   }
}
