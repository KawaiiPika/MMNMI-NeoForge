package xyz.pixelatedw.mineminenomi.abilities.jiki;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
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
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.jiki.GenocideRaidEffectEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GenocideRaidAbility extends Ability {
   public static final int REQUIRED_IRON = 20;
   private static final int COOLDOWN = 200;
   public static final int EFFECT_TIMER = 100;
   private static final int RANGE = 50;
   public static final RegistryObject<AbilityCore<GenocideRaidAbility>> INSTANCE = ModRegistry.registerAbility("genocide_raid", "Genocide Raid", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Uses %s magnetic items and sends them towards the target, spiriling around it and dealing damage over time.", new Object[]{MentionHelper.mentionText(20)}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GenocideRaidAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.METAL).setSourceType(SourceType.BLUNT).build("mineminenomi");
   });
   private final RangeComponent rangeComponent = new RangeComponent(this);

   public GenocideRaidAbility(AbilityCore<GenocideRaidAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.rangeComponent});
      this.addCanUseCheck(JikiHelper.getMetalicItemsCheck(20));
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      JikiHelper.spawnAttractEffect(entity);
      List<ItemStack> inventory = ItemsHelper.getAllInventoryItems(entity);
      List<ItemStack> magneticItems = JikiHelper.getMagneticItemsNeeded(inventory, 20.0F);
      List<LivingEntity> targets = this.rangeComponent.getTargetsInLine(entity, 50.0F, 3.0F);
      if (!targets.isEmpty()) {
         for(LivingEntity target : targets) {
            IAbilityData abilityDataProps = (IAbilityData)AbilityCapability.get(target).orElse((Object)null);
            if (abilityDataProps != null) {
               RustSkinAbility rustSkinAbility = (RustSkinAbility)abilityDataProps.getPassiveAbility((AbilityCore)RustSkinAbility.INSTANCE.get());
               if (rustSkinAbility != null && !rustSkinAbility.isPaused()) {
                  break;
               }
            }

            target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.GENOCIDE_RAID.get(), 100, 0));
            JikiHelper.spawnAttractEffect(target);
            GenocideRaidEffectEntity effect = new GenocideRaidEffectEntity(entity.m_9236_());
            effect.m_7678_(target.m_20185_(), target.m_20186_() + (double)1.0F, target.m_20189_(), 0.0F, 0.0F);
            effect.setTarget(target);
            effect.setOwner(entity);
            effect.setItemsUsed(magneticItems);
            entity.m_9236_().m_7967_(effect);
         }
      } else {
         HitResult hit = WyHelper.rayTraceBlocks(entity, (double)25.0F);
         JikiHelper.dropComponentItems(entity.m_9236_(), hit.m_82450_(), magneticItems);
      }

      this.cooldownComponent.startCooldown(entity, 200.0F);
   }
}
