package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.advancements.CriteriaTriggers;
import xyz.pixelatedw.mineminenomi.data.triggers.CompleteChallengeTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.CompleteQuestTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.ConsumeDevilFruitTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.DFEncyclopediaCompletionTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.FreedSlavesTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.JoinCrewTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.LeaveCrewTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.MooteorologistTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.ObtainBellyTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.ObtainBountyTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.ObtainDorikiTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.ObtainLoyaltyTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.SelectFactionTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.SelectRaceTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.SelectStyleTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.SetCrewCaptainTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.SubtleTweaksTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.UnlockAbilityTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.UnlockChallengeTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.YomiReviveTrigger;

public class ModAdvancements {
   public static final ObtainBellyTrigger OBTAIN_BELLY = new ObtainBellyTrigger();
   public static final UnlockAbilityTrigger UNLOCK_ABILITY = new UnlockAbilityTrigger();
   public static final DFEncyclopediaCompletionTrigger ENCYCLOPEDIA_COMPLETION = new DFEncyclopediaCompletionTrigger();
   public static final ConsumeDevilFruitTrigger CONSUME_DEVIL_FRUIT = new ConsumeDevilFruitTrigger();
   public static final MooteorologistTrigger MOOTEOROLOGIST = new MooteorologistTrigger();
   public static final SubtleTweaksTrigger SUBTLE_TWEAKS = new SubtleTweaksTrigger();
   public static final YomiReviveTrigger YOMI_REVIVE = new YomiReviveTrigger();
   public static final CompleteChallengeTrigger COMPLETE_CHALLENGE = new CompleteChallengeTrigger();
   public static final UnlockChallengeTrigger UNLOCK_CHALLENGE = new UnlockChallengeTrigger();
   public static final ObtainDorikiTrigger OBTAIN_DORIKI = new ObtainDorikiTrigger();
   public static final ObtainBountyTrigger OBTAIN_BOUNTY = new ObtainBountyTrigger();
   public static final ObtainLoyaltyTrigger OBTAIN_LOYALTY = new ObtainLoyaltyTrigger();
   public static final SelectFactionTrigger SELECT_FACTION = new SelectFactionTrigger();
   public static final SelectStyleTrigger SELECT_STYLE = new SelectStyleTrigger();
   public static final SelectRaceTrigger SELECT_RACE = new SelectRaceTrigger();
   public static final SetCrewCaptainTrigger SET_CREW_CAPTAIN = new SetCrewCaptainTrigger();
   public static final LeaveCrewTrigger LEAVE_CREW = new LeaveCrewTrigger();
   public static final JoinCrewTrigger JOIN_CREW = new JoinCrewTrigger();
   public static final CompleteQuestTrigger COMPLETE_QUEST = new CompleteQuestTrigger();
   public static final FreedSlavesTrigger FREED_SLAVES = new FreedSlavesTrigger();

   public static void register() {
      CriteriaTriggers.m_10595_(OBTAIN_BELLY);
      CriteriaTriggers.m_10595_(UNLOCK_ABILITY);
      CriteriaTriggers.m_10595_(ENCYCLOPEDIA_COMPLETION);
      CriteriaTriggers.m_10595_(CONSUME_DEVIL_FRUIT);
      CriteriaTriggers.m_10595_(MOOTEOROLOGIST);
      CriteriaTriggers.m_10595_(SUBTLE_TWEAKS);
      CriteriaTriggers.m_10595_(YOMI_REVIVE);
      CriteriaTriggers.m_10595_(COMPLETE_CHALLENGE);
      CriteriaTriggers.m_10595_(UNLOCK_CHALLENGE);
      CriteriaTriggers.m_10595_(OBTAIN_DORIKI);
      CriteriaTriggers.m_10595_(OBTAIN_BOUNTY);
      CriteriaTriggers.m_10595_(OBTAIN_LOYALTY);
      CriteriaTriggers.m_10595_(SELECT_FACTION);
      CriteriaTriggers.m_10595_(SELECT_STYLE);
      CriteriaTriggers.m_10595_(SELECT_RACE);
      CriteriaTriggers.m_10595_(SET_CREW_CAPTAIN);
      CriteriaTriggers.m_10595_(LEAVE_CREW);
      CriteriaTriggers.m_10595_(JOIN_CREW);
      CriteriaTriggers.m_10595_(COMPLETE_QUEST);
      CriteriaTriggers.m_10595_(FREED_SLAVES);
   }
}
