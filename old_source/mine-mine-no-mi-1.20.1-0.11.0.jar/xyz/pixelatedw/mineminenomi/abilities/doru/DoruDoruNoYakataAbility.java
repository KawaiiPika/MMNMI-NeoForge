package xyz.pixelatedw.mineminenomi.abilities.doru;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.WaxCloneEntity;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class DoruDoruNoYakataAbility extends Ability {
   private static final float COOLDOWN = 800.0F;
   public static final RegistryObject<AbilityCore<DoruDoruNoYakataAbility>> INSTANCE = ModRegistry.registerAbility("doru_doru_no_yakata", "Doru Doru no Yakata", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user creates a few wax copies of themselves (Use the Color Palette for color on the clones)", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, DoruDoruNoYakataAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(800.0F)).build("mineminenomi");
   });

   public DoruDoruNoYakataAbility(AbilityCore<DoruDoruNoYakataAbility> core) {
      super(core);
      this.addUseEvent(this::oneUseEvent);
   }

   private void oneUseEvent(LivingEntity entity, IAbility ability) {
      for(int i = 0; i < 7; ++i) {
         int offsetX = (int)WyHelper.randomWithRange(-2, 2);
         int offsetZ = (int)WyHelper.randomWithRange(-2, 2);
         WaxCloneEntity clone = new WaxCloneEntity(entity.m_9236_(), entity);
         clone.m_7678_(entity.m_20185_() + (double)offsetX, entity.m_20186_(), entity.m_20189_() + (double)offsetZ, 180.0F, 0.0F);
         clone.m_6703_(entity);
         if (DoruHelper.hasColorPalette(entity)) {
            clone.setUseOwnerTexture();
            clone.copyOwnerEquipment();
         }

         entity.m_9236_().m_7967_(clone);
      }

      this.cooldownComponent.startCooldown(entity, 800.0F);
   }
}
