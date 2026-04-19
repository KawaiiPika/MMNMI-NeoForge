package xyz.pixelatedw.mineminenomi.entities.mobs.civilians;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.TrainerEntity;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class ArtOfWeatherTrainerEntity extends TrainerEntity {
   protected static final List<Supplier<? extends Item>> TACTS;

   public ArtOfWeatherTrainerEntity(EntityType<ArtOfWeatherTrainerEntity> type, Level world) {
      super(type, world, MobsHelper.WEATHER_TRAINER_TEXTURES);
   }

   public void m_8099_() {
      super.m_8099_();
      EntityStatsCapability.get(this).ifPresent((props) -> {
         props.setFaction((Faction)ModFactions.CIVILIAN.get());
         props.setRace((Race)ModRaces.HUMAN.get());
         props.setFightingStyle((FightingStyle)ModFightingStyles.ART_OF_WEATHER.get());
         props.setDoriki((double)100.0F);
         props.setBelly(10L + Math.round(WyHelper.randomWithRange(0, 10)));
      });
      if (this.f_19796_.m_188500_() < 0.4) {
         ItemStack randomTact = new ItemStack((ItemLike)((Supplier)TACTS.get(this.f_19796_.m_188503_(TACTS.size()))).get());
         this.m_8061_(EquipmentSlot.MAINHAND, randomTact);
      }

      this.m_8061_(EquipmentSlot.HEAD, ((Item)ModArmors.WIZARD_HAT.get()).m_7968_());
      this.f_21346_.m_25352_(2, new HurtByTargetGoal(this, new Class[0]));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)35.0F).m_22268_(Attributes.f_22279_, (double)0.22F).m_22268_(Attributes.f_22281_, (double)2.0F).m_22268_(Attributes.f_22276_, (double)50.0F).m_22268_(Attributes.f_22284_, (double)2.0F).m_22268_((Attribute)ModAttributes.TOUGHNESS.get(), (double)12.0F);
   }

   public List<QuestId<?>> getAvailableQuests(Player player) {
      return EMPTY;
   }

   static {
      TACTS = Arrays.asList(ModWeapons.CLIMA_TACT, ModWeapons.SORCERY_CLIMA_TACT, ModWeapons.PERFECT_CLIMA_TACT);
   }
}
