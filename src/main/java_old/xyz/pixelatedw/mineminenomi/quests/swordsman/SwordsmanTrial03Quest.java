package xyz.pixelatedw.mineminenomi.quests.swordsman;

import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.SanbyakurokujuPoundHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.YakkodoriAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ittoryu.YakkodoriProjectile;
import xyz.pixelatedw.mineminenomi.init.ModQuests;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;

public class SwordsmanTrial03Quest extends Quest {
   public static final String ID = "trial_sanbyakurokuju_pound_ho";
   public static final QuestId<SwordsmanTrial03Quest> INSTANCE;
   private static final int O1_KILL_COUNT = 10;
   private static final int O2_KILL_COUNT = 30;
   private static final Predicate<ItemStack> ITEM_WITH_UNBREAKING;
   private static final KillEntityObjective.ICheckKill YAKKODORI_KILL_CHECK;
   private static final KillEntityObjective.ICheckKill SHI_SHISHI_SONSON_KILL_CHECK;
   private static final Component O1_TITLE;
   private static final Component O2_TITLE;
   private Objective objective01;
   private Objective objective02;

   public SwordsmanTrial03Quest(QuestId id) {
      super(id);
      this.objective01 = new KillEntityObjective(this, O1_TITLE, 10, YAKKODORI_KILL_CHECK);
      this.objective02 = (new KillEntityObjective(this, O2_TITLE, 30, SHI_SHISHI_SONSON_KILL_CHECK)).addRequirement(this.objective01);
      this.addObjectives(new Objective[]{this.objective01, this.objective02});
      this.addTurnInEvent(100, this::giveReward);
   }

   public void giveReward(Player player) {
      AbilityCapability.get(player).ifPresent((props) -> props.addUnlockedAbility((AbilityCore)SanbyakurokujuPoundHoAbility.INSTANCE.get(), AbilityUnlock.PROGRESSION));
   }

   static {
      INSTANCE = (new QuestId.Builder<SwordsmanTrial03Quest>(SwordsmanTrial03Quest::new)).addRequirements(ModQuests.SWORDSMAN_TRIAL_01).build();
      ITEM_WITH_UNBREAKING = (itemStack) -> ItemsHelper.isSword(itemStack) && EnchantmentHelper.m_44843_(Enchantments.f_44986_, itemStack) > 0;
      YAKKODORI_KILL_CHECK = (player, target, source) -> {
         if (source.m_7640_() instanceof YakkodoriProjectile) {
            return true;
         } else {
            return IDamageSourceHandler.getHandler(source).hasAbility((AbilityCore)YakkodoriAbility.INSTANCE.get());
         }
      };
      SHI_SHISHI_SONSON_KILL_CHECK = (player, target, source) -> {
         IAbilityData props = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
         if (props == null) {
            return false;
         } else {
            boolean isInSource = IDamageSourceHandler.getHandler(source).hasAbility((AbilityCore)ShiShishiSonsonAbility.INSTANCE.get());
            ShiShishiSonsonAbility ability = (ShiShishiSonsonAbility)props.getEquippedAbility((AbilityCore)ShiShishiSonsonAbility.INSTANCE.get());
            return ability != null && isInSource;
         }
      };
      O1_TITLE = ModRegistry.registerObjectiveTitle("trial_sanbyakurokuju_pound_ho", 0, "Kill %s enemies using Yakkodori", 10);
      O2_TITLE = ModRegistry.registerObjectiveTitle("trial_sanbyakurokuju_pound_ho", 1, "Kill %s enemies using Shi Shishi Sonson", 30);
   }
}
