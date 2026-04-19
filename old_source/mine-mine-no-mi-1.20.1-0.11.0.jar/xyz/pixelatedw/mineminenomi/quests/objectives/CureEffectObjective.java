package xyz.pixelatedw.mineminenomi.quests.objectives;

import java.util.Arrays;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.ICureEffectObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class CureEffectObjective extends Objective implements ICureEffectObjective {
   private MobEffect[] effects;

   public CureEffectObjective(Quest parent, Component titleId, int count, MobEffect effect) {
      this(parent, titleId, count, new MobEffect[]{effect});
   }

   public CureEffectObjective(Quest parent, Component titleId, int count, MobEffect[] effects) {
      super(parent, titleId);
      this.setMaxProgress((float)count);
      this.effects = effects;
   }

   public boolean checkEffect(Player player, MobEffectInstance effectInstance) {
      return Arrays.stream(this.effects).anyMatch((effect) -> effectInstance.m_19544_() == effect);
   }
}
