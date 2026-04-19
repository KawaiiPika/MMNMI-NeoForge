package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class StealPunchAbility extends PunchAbility {
   private static final float COOLDOWN = 160.0F;
   public static final RegistryObject<AbilityCore<StealPunchAbility>> INSTANCE = ModRegistry.registerAbility("steal_punch", "Steal Punch", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Hits the enemy and throws their held item away from them.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, StealPunchAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(160.0F), ContinuousComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });

   public StealPunchAbility(AbilityCore<StealPunchAbility> core) {
      super(core);
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      ItemStack droppedStack = ItemStack.f_41583_;
      if (!target.m_21205_().m_41619_()) {
         droppedStack = target.m_21205_();
      } else if (!target.m_21206_().m_41619_()) {
         droppedStack = target.m_21206_();
      } else if (target.m_21207_() > 0.0F) {
         for(ItemStack armorStack : target.m_6168_()) {
            if (armorStack != null && !armorStack.m_41619_() && target.m_217043_().m_188500_() < 0.35) {
               droppedStack = armorStack;
               break;
            }
         }
      }

      if (droppedStack != null && !droppedStack.m_41619_()) {
         Vec3 lookVec = target.m_20154_();
         entity.m_6034_(target.m_20185_() + lookVec.f_82479_, target.m_20186_(), target.m_20189_() + lookVec.f_82481_);
         double d0 = target.m_20188_() - (double)0.3F;
         ItemEntity itementity = new ItemEntity(target.m_9236_(), target.m_20185_(), d0, target.m_20189_(), droppedStack.m_41777_());
         itementity.m_32010_(40);
         itementity.m_32052_(target.m_20148_());
         float f = target.m_217043_().m_188501_() * 1.5F;
         float f1 = target.m_217043_().m_188501_() * ((float)Math.PI * 2F);
         AbilityHelper.setDeltaMovement(itementity, (double)(-Mth.m_14031_(f1) * f), (double)0.75F, (double)(Mth.m_14089_(f1) * f));
         target.m_9236_().m_7967_(itementity);
         droppedStack.m_41774_(droppedStack.m_41613_());
      }

      return true;
   }

   public int getUseLimit() {
      return 1;
   }

   public boolean isParallel() {
      return true;
   }

   public float getPunchCooldown() {
      return 160.0F;
   }
}
