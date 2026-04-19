package xyz.pixelatedw.mineminenomi.entities.mobs.civilians;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModRaces;

public class SkypieanCivilianEntity extends OPEntity {
   public SkypieanCivilianEntity(EntityType<? extends SkypieanCivilianEntity> type, Level world) {
      super(type, world, MobsHelper.SKYPEAN_TEXTURES);
   }

   public void m_8099_() {
      EntityStatsCapability.get(this).ifPresent((props) -> {
         props.setFaction((Faction)ModFactions.CIVILIAN.get());
         props.setRace((Race)ModRaces.SKYPIEAN.get());
      });
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new OpenDoorGoal(this, false));
      this.f_21345_.m_25352_(3, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.f_21345_.m_25352_(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(5, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(0, new HurtByTargetGoal(this, new Class[0]));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)10.0F).m_22268_(Attributes.f_22279_, 0.23).m_22268_(Attributes.f_22281_, (double)1.0F).m_22268_(Attributes.f_22276_, (double)20.0F).m_22268_(Attributes.f_22284_, (double)0.0F);
   }
}
