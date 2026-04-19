package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;

public class StyleSwitchGoal extends TickedGoal<Mob> {
   private static final int COOLDOWN = 40;
   private LivingEntity target;
   private FightingStyle currentStyle;
   private FightingStyle nextStyle;
   private final IStyleSwitchEvent event;
   private final IEntityStats props;

   public StyleSwitchGoal(Mob entity, IStyleSwitchEvent event) {
      super(entity);
      this.event = event;
      this.props = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
   }

   public boolean m_8036_() {
      if (this.props == null) {
         return false;
      } else if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else if (!this.hasTimePassedSinceLastEnd(40.0F)) {
         return false;
      } else {
         this.target = this.entity.m_5448_();
         this.currentStyle = (FightingStyle)this.props.getFightingStyle().orElse((Object)null);
         if (this.currentStyle == null) {
            return false;
         } else {
            this.nextStyle = this.event.nextChange(this.entity, this.target, this.currentStyle);
            return !this.nextStyle.equals(this.currentStyle);
         }
      }
   }

   public boolean m_8045_() {
      return false;
   }

   public void m_8056_() {
      super.m_8056_();
      this.props.setFightingStyle(this.nextStyle);
      String var10000 = this.entity.m_5446_().getString();
      WyDebug.debug(var10000 + " has changed their style from " + this.currentStyle.getLabel().getString() + " to " + this.nextStyle.getLabel().getString());
   }

   @FunctionalInterface
   public interface IStyleSwitchEvent {
      FightingStyle nextChange(Mob var1, LivingEntity var2, FightingStyle var3);
   }
}
