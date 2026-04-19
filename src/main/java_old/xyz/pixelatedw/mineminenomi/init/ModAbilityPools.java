package xyz.pixelatedw.mineminenomi.init;

import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;

public class ModAbilityPools {
   public static final String IGNORE_COOLDOWN_FLAG = "ignoreCooldown";
   public static final String SHARE_STACKS_FLAG = "shareStacks";
   public static final String SWITCH_FLAG = "switchContinuous";
   public static final AbilityPool DODGE_ABILITY = (new AbilityPool()).addFlag("ignoreCooldown", true);
   public static final AbilityPool GRAB_ABILITY = (new AbilityPool()).addFlag("ignoreCooldown", true);
   public static final AbilityPool GEPPO_LIKE = (new AbilityPool()).addFlag("shareStacks", true);
   public static final AbilityPool GUARD_ABILITY = new AbilityPool();
   public static final AbilityPool WEATHER_BALLS = new AbilityPool();
   public static final AbilityPool BODY_BUSOSHOKU_HAKI = new AbilityPool();
   public static final AbilityPool IMBUING_BUSOSHOKU_HAKI = new AbilityPool();
   public static final AbilityPool ADVANCED_BUSOSHOKU_HAKI = new AbilityPool();
   public static final AbilityPool SIMPLE_HAOSHOKU_HAKI = new AbilityPool();
   public static final AbilityPool ADVANCED_HAOSHOKU_HAKI = new AbilityPool();
   public static final AbilityPool SNIPER_ABILITY = new AbilityPool();
   public static final AbilityPool BARA_ABILITY = (new AbilityPool()).addFlag("ignoreCooldown", true);
   public static final AbilityPool MORPHS = (new AbilityPool()).addFlag("switchContinuous", true).addFlag("ignoreCooldown", true);
   public static final AbilityPool KIRYU_AND_MUKIRYU = new AbilityPool();
}
