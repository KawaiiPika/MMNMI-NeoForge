package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import java.awt.Color;
import java.text.DecimalFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ConsumptionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdateAbilityNBTPacket;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class FlamesOfRegenerationAbility extends PassiveAbility {
   private static final int MAX_ENERGY = 100;
   public static final RegistryObject<AbilityCore<FlamesOfRegenerationAbility>> INSTANCE = ModRegistry.registerAbility("flames_of_regeneration", "Flames of Regeneration", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Protects the user and heals them back up when damage is taken, has an initial reserve of §a%s Energy§r which increases with time and decreses with each heal.", new Object[]{100}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, FlamesOfRegenerationAbility::new)).addDescriptionLine(desc).build("mineminenomi");
   });
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::onDamageTaken);
   private final ConsumptionComponent consumptionComponent = (new ConsumptionComponent(this)).addConsumptionEvent(this::onConsumption);
   private static final int MAX_COOLDOWN = 100;
   private double energy = (double)100.0F;
   private int cooldown = 0;
   private int invulnerableTime = 0;
   private Interval recuperationInterval = new Interval(10);
   private Interval regenerationInterval = new Interval(40);

   public FlamesOfRegenerationAbility(AbilityCore<FlamesOfRegenerationAbility> ability) {
      super(ability);
      if (super.isClientSide()) {
         GaugeComponent gaugeComponent = new GaugeComponent(this, this::renderGauge);
         super.addComponents(gaugeComponent);
      }

      super.addComponents(this.damageTakenComponent, this.consumptionComponent);
      super.addDuringPassiveEvent(this::duringPassive);
   }

   private void duringPassive(LivingEntity entity) {
      if (!entity.m_9236_().f_46443_) {
         --this.invulnerableTime;
         if (this.regenerationInterval.canTick() && this.energy - (double)4.0F >= (double)0.0F && entity.m_21223_() < entity.m_21233_()) {
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.FLAMES_OF_REGEN.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
            entity.m_5634_(4.0F);
            this.addEnergy(entity, (double)-5.0F);
         }

         if (!WyHelper.isInCombat(entity)) {
            if (entity.m_5803_() && this.recuperationInterval.canTick()) {
               this.addEnergy(entity, (double)10.0F);
            }

            if (this.cooldown > 0) {
               --this.cooldown;
            } else {
               if (this.cooldown <= 0 && this.energy <= (double)0.0F) {
                  this.addEnergy(entity, (double)1.0F);
               }

               if (this.energy <= (double)0.0F) {
                  this.cooldown = 100;
               } else {
                  this.addEnergy(entity, 0.05);
               }
            }
         }
      }
   }

   public float onDamageTaken(LivingEntity entity, IAbility ability, DamageSource source, float damage) {
      if (super.isPaused()) {
         return damage;
      } else if (this.invulnerableTime > 0) {
         return 0.0F;
      } else {
         boolean hasShadow = (Boolean)EntityStatsCapability.get(entity).map((props) -> props.hasShadow()).orElse(false);
         IDamageSourceHandler handler = IDamageSourceHandler.getHandler(source);
         if (!handler.isBypassingLogia() && (!source.m_276093_(DamageTypes.f_268468_) || hasShadow)) {
            boolean isYamiUser = (Boolean)DevilFruitCapability.get(entity).map((props) -> props.hasYamiPower()).orElse(false);
            if (isYamiUser) {
               return damage;
            } else {
               boolean isImmune = true;
               if (source.m_7640_() != null) {
                  LivingEntity sourceOwner = null;
                  Entity projectileOwner = source.m_7640_();
                  if (projectileOwner instanceof LivingEntity) {
                     LivingEntity living = (LivingEntity)projectileOwner;
                     sourceOwner = living;
                  } else {
                     projectileOwner = source.m_7640_();
                     if (projectileOwner instanceof Projectile) {
                        Projectile proj = (Projectile)projectileOwner;
                        projectileOwner = proj.m_19749_();
                        if (projectileOwner != null && projectileOwner instanceof LivingEntity) {
                           sourceOwner = (LivingEntity)projectileOwner;
                           boolean hasImbuingActive = HakiHelper.hasImbuingActive((LivingEntity)projectileOwner);
                           isImmune &= !hasImbuingActive;
                        }
                     }
                  }

                  if (sourceOwner != null) {
                     boolean hasImbuingActive = HakiHelper.hasImbuingActive(sourceOwner);
                     boolean hasHardeningActive = HakiHelper.hasHardeningActive(sourceOwner);
                     if (handler.hasHakiNature(SourceHakiNature.IMBUING) && hasImbuingActive) {
                        isImmune = false;
                     } else if (handler.hasHakiNature(SourceHakiNature.HARDENING) && hasHardeningActive) {
                        isImmune = false;
                     } else if (handler.hasHakiNature(SourceHakiNature.SPECIAL) && (hasImbuingActive || hasHardeningActive)) {
                        isImmune = false;
                     }
                  }
               }

               if (isImmune && this.energy - (double)damage >= (double)0.0F) {
                  WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.FLAMES_OF_REGEN.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
                  this.addEnergy(entity, (double)(-damage));
                  this.invulnerableTime = 10;
                  return 0.0F;
               } else {
                  this.cooldown = 100;
                  return damage;
               }
            }
         } else {
            return damage;
         }
      }
   }

   private boolean onConsumption(LivingEntity entity, IAbility ability, int nutrition, float saturationModifier) {
      this.addEnergy(entity, (double)((float)nutrition * saturationModifier) * (double)2.0F);
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   private void renderGauge(Player player, GuiGraphics graphics, int posX, int posY, FlamesOfRegenerationAbility ability) {
      Minecraft mc = Minecraft.m_91087_();
      RendererHelper.drawIcon(((AbilityCore)PhoenixFlyPointAbility.INSTANCE.get()).getIcon(), graphics.m_280168_(), (float)posX, (float)(posY - 38), 0.0F, 32.0F, 32.0F);
      DecimalFormat energyFormat = new DecimalFormat("#0.0");
      String energy = energyFormat.format(ability.energy);
      RendererHelper.drawStringWithBorder(mc.f_91062_, graphics, energy, posX + 15 - mc.f_91062_.m_92895_(energy) / 2, posY - 25, Color.WHITE.getRGB());
   }

   public void addEnergy(LivingEntity entity, double energy) {
      this.energy = Mth.m_14008_(this.energy + energy, (double)0.0F, (double)100.0F);
      if (entity instanceof Player player) {
         ModNetwork.sendTo(new SUpdateAbilityNBTPacket(entity, this), player);
      }

   }

   public void saveAdditional(CompoundTag nbt) {
      nbt.m_128347_("energy", this.energy);
   }

   public void loadAdditional(CompoundTag nbt) {
      this.energy = nbt.m_128459_("energy");
   }
}
