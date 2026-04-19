package xyz.pixelatedw.mineminenomi.abilities.chiyu;

import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.items.DandelionItem;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KenpopoAbility extends PunchAbility {
   private static final float COOLDOWN = 1000.0F;
   public static final RegistryObject<AbilityCore<KenpopoAbility>> INSTANCE = ModRegistry.registerAbility("kenpopo", "Kenpopo", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Takes an ally's life force, transforming it into a Dandelion. Dandelions can be eaten for healing", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, KenpopoAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(1000.0F)).setSourceType(SourceType.FIST).build("mineminenomi");
   });

   public KenpopoAbility(AbilityCore<KenpopoAbility> core) {
      super(core);
      this.clearUseChecks();
   }

   public int getUseLimit() {
      return 1;
   }

   public float getPunchCooldown() {
      return 1000.0F;
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      Predicate<Entity> isAllyPredicate = ModEntityPredicates.getFriendlyFactions(entity);
      if (target.m_21124_(MobEffects.f_19613_) == null && isAllyPredicate.test(target)) {
         float healAmount = target.m_21223_() * 0.25F;
         ItemStack dandelionStack = new ItemStack((ItemLike)ModItems.DANDELION.get());
         DandelionItem.setHealAmount(dandelionStack, healAmount);
         if (entity instanceof Player) {
            Player player = (Player)entity;
            player.m_36356_(dandelionStack);
         }

         target.m_21153_(target.m_21223_() - healAmount);
         target.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 1800, 0));
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.CHIYUPOPO.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
         return false;
      } else {
         return false;
      }
   }
}
