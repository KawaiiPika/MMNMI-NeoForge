package xyz.pixelatedw.mineminenomi.data.entity.stats;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.INBTSerializable;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.api.factions.IFactionRank;

public interface IEntityStats extends INBTSerializable<CompoundTag> {
   boolean isInCombatMode();

   void setCombatMode(boolean var1);

   double getDoriki();

   boolean alterDoriki(double var1, StatChangeSource var3);

   void setDoriki(double var1);

   long getBelly();

   boolean alterBelly(long var1, StatChangeSource var3);

   void setBelly(long var1);

   long getExtol();

   boolean alterExtol(long var1, StatChangeSource var3);

   void setExtol(long var1);

   long getBounty();

   boolean alterBounty(long var1, StatChangeSource var3);

   void setBounty(long var1);

   int getCola();

   void alterCola(int var1);

   void setCola(int var1);

   void updateCola();

   int getMaxCola();

   int getUltraCola();

   void setUltraCola(int var1);

   void addUltraCola(int var1);

   int getInvulnerableTime();

   void alterInvulnerableTime(int var1);

   void setInvulnerableTime(int var1);

   double getLoyalty();

   boolean alterLoyalty(double var1, StatChangeSource var3);

   void setLoyalty(double var1);

   <E extends Enum<E> & IFactionRank> Optional<E> getRank();

   <E extends Enum<E> & IFactionRank> boolean hasRank(E var1);

   boolean isPirate();

   boolean isMarine();

   boolean isBountyHunter();

   boolean isRevolutionary();

   boolean isBandit();

   boolean isWorldGovernment();

   boolean isCivilian();

   boolean hasFaction();

   void setFaction(@Nullable Faction var1);

   Optional<Faction> getFaction();

   boolean isHuman();

   boolean isFishman();

   boolean isCyborg();

   boolean isMink();

   boolean hasRace();

   void setRace(@Nullable Race var1);

   Optional<Race> getRace();

   boolean isBunnyMink();

   boolean isDogMink();

   boolean isLionMink();

   boolean isSawsharkFishman();

   void setSubRace(@Nullable Race var1);

   Optional<Race> getSubRace();

   void setSkinTint(int var1);

   Optional<Integer> getSkinTint();

   boolean isSwordsman();

   boolean isSniper();

   boolean isDoctor();

   boolean isWeatherWizard();

   boolean isBlackLeg();

   boolean isBrawler();

   boolean hasFightingStyle();

   void setFightingStyle(@Nullable FightingStyle var1);

   Optional<FightingStyle> getFightingStyle();

   boolean hasShadow();

   void setShadow(boolean var1);

   boolean hasHeart();

   void setHeart(boolean var1);

   double getDamageMultiplier();

   void setDamageMultiplier(double var1);

   boolean hasStrawDoll();

   void setStrawDoll(boolean var1);

   boolean isRogue();

   void setRogue(boolean var1);

   float getLeftImpulse();

   void setLeftImpulse(float var1);

   float getForwardImpulse();

   void setForwardImpulse(float var1);

   boolean isJumping();

   void setJumping(boolean var1);

   int getJumpTicks();

   void setJumpTicks(int var1);

   void alterJumpTicks(int var1);

   void addScreenShader(ResourceLocation var1);

   boolean hasScreenShaderActive(ResourceLocation var1);

   void removeScreenShader(ResourceLocation var1);

   void updateScreenShader();

   boolean hadChiyuEffect();

   void setChiyuEffect(boolean var1);

   float getOriginalChiyupopoHealth();

   void setOriginalChiyupopoHealth(float var1);

   void setDyeLayerCheck(boolean var1);

   boolean isDyeLayerCheck();

   int getTrainingPoints(TrainingPointType var1);

   void alterTrainingPoints(TrainingPointType var1, int var2);

   int getFreedSlaves();

   void addFreedSlaves(int var1);

   void setFreedSlaves(int var1);

   void setFlyingSpeed(Vec3 var1);

   Vec3 getFlyingSpeed();
}
