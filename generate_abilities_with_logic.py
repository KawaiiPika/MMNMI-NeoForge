import os

def write_kilo_press_1():
    content = """package xyz.pixelatedw.mineminenomi.abilities.kilo;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class KiloPress1Ability extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_kilo_no_mi");
    private static final ResourceLocation JUMP_MOD_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_press_1_jump");

    public KiloPress1Ability() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            var jumpAttr = entity.getAttribute(ModAttributes.JUMP_HEIGHT);
            if (jumpAttr != null) {
                jumpAttr.addOrUpdateTransientModifier(new AttributeModifier(JUMP_MOD_ID, 4.8D, AttributeModifier.Operation.ADD_VALUE));
            }
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
             if (duration > 10 && entity.onGround()) {
                 this.stop(entity);
             } else {
                 boolean hasUmbrella = entity.getMainHandItem().getItem() == ModWeapons.UMBRELLA.get() || entity.getOffhandItem().getItem() == ModWeapons.UMBRELLA.get();
                 boolean inAir = !entity.onGround() && entity.getDeltaMovement().y < 0.0D;
                 if (hasUmbrella && inAir) {
                    AbilityHelper.setDeltaMovement(entity, entity.getDeltaMovement().x, entity.getDeltaMovement().y / 2.0D, entity.getDeltaMovement().z);
                 }
             }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            var jumpAttr = entity.getAttribute(ModAttributes.JUMP_HEIGHT);
            if (jumpAttr != null) {
                jumpAttr.removeModifier(JUMP_MOD_ID);
            }
        }
        this.startCooldown(entity, 20);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.1_kilo_press");
    }
}
"""
    with open('src/main/java/xyz/pixelatedw/mineminenomi/abilities/kilo/KiloPress1Ability.java', 'w') as f:
        f.write(content)

def write_kilo_press_10000():
    content = """package xyz.pixelatedw.mineminenomi.abilities.kilo;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import net.minecraft.util.Mth;
import java.util.List;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class KiloPress10000Ability extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_kilo_no_mi");
    private static final ResourceLocation JUMP_MOD_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_press_10000_jump");
    private static final ResourceLocation KNOCKBACK_MOD_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_press_10000_knockback");
    private static final ResourceLocation SPEED_MOD_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_press_10000_speed");

    private double initialPosY = 0.0D;

    public KiloPress10000Ability() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            var jumpAttr = entity.getAttribute(ModAttributes.JUMP_HEIGHT);
            if (jumpAttr != null) jumpAttr.addOrUpdateTransientModifier(new AttributeModifier(JUMP_MOD_ID, -10.0D, AttributeModifier.Operation.ADD_VALUE));

            var kbAttr = entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            if (kbAttr != null) kbAttr.addOrUpdateTransientModifier(new AttributeModifier(KNOCKBACK_MOD_ID, 1.0D, AttributeModifier.Operation.ADD_VALUE));

            var speedAttr = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttr != null) speedAttr.addOrUpdateTransientModifier(new AttributeModifier(SPEED_MOD_ID, -0.02D, AttributeModifier.Operation.ADD_VALUE));

            AbilityHelper.setDeltaMovement(entity, entity.getDeltaMovement().x, -5.0D, entity.getDeltaMovement().z);
            if (!entity.onGround()) {
                this.initialPosY = entity.getY();
            } else {
                this.initialPosY = 0.0D;
            }
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
             if (entity.onGround()) {
                 if (this.initialPosY > 0.0D && entity.getY() < this.initialPosY) {
                     float damage = (float)Mth.clamp(this.initialPosY - entity.getY(), 1.0D, 80.0D);
                     if (damage > 0.0F) {
                         List<LivingEntity> nearTargets = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(1.0D), e -> e != entity);
                         for(LivingEntity target : nearTargets) {
                             target.hurt(entity.damageSources().mobAttack(entity), damage);
                         }

                         List<LivingEntity> farTargets = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(5.0D), e -> e != entity);
                         farTargets.removeAll(nearTargets);
                         for(LivingEntity target : farTargets) {
                             target.hurt(entity.damageSources().mobAttack(entity), damage);
                         }
                         this.initialPosY = 0.0D;
                     }

                     if (entity.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                         serverLevel.sendParticles(new xyz.pixelatedw.mineminenomi.particles.SimpleParticleData(xyz.pixelatedw.mineminenomi.init.ModParticleTypes.GREAT_STOMP.get()), true, entity.getX(), entity.getY(), entity.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                     }
                 }
                 this.stop(entity);
             }
             if (duration > 1200) {
                 this.stop(entity);
             }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            var jumpAttr = entity.getAttribute(ModAttributes.JUMP_HEIGHT);
            if (jumpAttr != null) jumpAttr.removeModifier(JUMP_MOD_ID);

            var kbAttr = entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            if (kbAttr != null) kbAttr.removeModifier(KNOCKBACK_MOD_ID);

            var speedAttr = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttr != null) speedAttr.removeModifier(SPEED_MOD_ID);
        }
        this.startCooldown(entity, 20);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.10000_kilo_press");
    }
}
"""
    with open('src/main/java/xyz/pixelatedw/mineminenomi/abilities/kilo/KiloPress10000Ability.java', 'w') as f:
        f.write(content)

def write_kilo_punch_5000():
    content = """package xyz.pixelatedw.mineminenomi.abilities.kilo;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import xyz.pixelatedw.mineminenomi.api.abilities.basic.PunchAbility;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.resources.ResourceLocation;

public class KiloPunch5000Ability extends PunchAbility {
    private static final ResourceLocation SPEED_MOD_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_punch_5000_speed");

    public KiloPunch5000Ability() {
        super();
    }

    @Override
    public void startUsing(LivingEntity entity) {
        super.startUsing(entity);
        if (!entity.level().isClientSide) {
            var speedAttr = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttr != null) speedAttr.addOrUpdateTransientModifier(new AttributeModifier(SPEED_MOD_ID, -0.01D, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    @Override
    public void stopUsing(LivingEntity entity) {
        super.stopUsing(entity);
        if (!entity.level().isClientSide) {
            var speedAttr = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttr != null) speedAttr.removeModifier(SPEED_MOD_ID);
        }
    }

    @Override
    public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
        target.hurt(source, 20.0F);
        return true;
    }

    @Override
    public int getUseLimit() {
        return 1;
    }

    @Override
    public float getPunchCooldown() {
        return 140.0F;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.5000_kilo_punch");
    }
}
"""
    with open('src/main/java/xyz/pixelatedw/mineminenomi/abilities/kilo/KiloPunch5000Ability.java', 'w') as f:
        f.write(content)

def write_zushi_abare_himatsuri():
    content = """package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

public class AbareHimatsuriAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi");

    public AbareHimatsuriAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (entity instanceof Player player) {
            player.getAbilities().mayfly = true;
            player.onUpdateAbilities();
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (entity instanceof Player player) {
            player.fallDistance = 0.0F;
            if (!player.getAbilities().mayfly) {
                player.getAbilities().mayfly = true;
                player.onUpdateAbilities();
            }

            if (player.isSprinting()) {
                AbilityHelper.setDeltaMovement(player, player.getDeltaMovement().x * 0.69D, player.getDeltaMovement().y, player.getDeltaMovement().z * 0.69D);
                player.setSprinting(false);
            }
        }

        if (duration > 1200) {
            this.stop(entity);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (entity instanceof Player player) {
            if (!player.isCreative() && !player.isSpectator()) {
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
                player.onUpdateAbilities();
            }
        }
        this.startCooldown(entity, 300);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.abare_himatsuri");
    }
}
"""
    with open('src/main/java/xyz/pixelatedw/mineminenomi/abilities/zushi/AbareHimatsuriAbility.java', 'w') as f:
        f.write(content)

def write_zushi_gravi_pull():
    content = """package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

public class GraviPullAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi");

    public GraviPullAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (entity.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            serverLevel.sendParticles(new xyz.pixelatedw.mineminenomi.particles.SimpleParticleData(xyz.pixelatedw.mineminenomi.init.ModParticleTypes.GRAVI_PULL_1.get()), true, entity.getX(), entity.getY(), entity.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration >= 60) {
            if (entity.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                serverLevel.sendParticles(new xyz.pixelatedw.mineminenomi.particles.SimpleParticleData(xyz.pixelatedw.mineminenomi.init.ModParticleTypes.GRAVI_PULL_2.get()), true, entity.getX(), entity.getY(), entity.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }

            for(LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(16.0D), e -> e != entity)) {
                double offsetX = entity.getX() - target.getX();
                double offsetZ = entity.getZ() - target.getZ();
                AbilityHelper.setDeltaMovement(target, offsetX / 2.0D, (entity.getY() - target.getY()) / 4.0D, offsetZ / 2.0D);
            }

            this.stop(entity);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, 340);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.gravi_pull");
    }
}
"""
    with open('src/main/java/xyz/pixelatedw/mineminenomi/abilities/zushi/GraviPullAbility.java', 'w') as f:
        f.write(content)

def write_zushi_gravi_zone():
    content = """package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import java.util.List;
import net.minecraft.world.phys.Vec3;

public class GraviZoneAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi");

    public GraviZoneAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Mode logic would usually go here, default to GUARD mode behavior
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
             boolean spawnGravityRings = duration % 10 == 0;
             int range = 8;
             List<LivingEntity> list = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(8.0D), e -> e != entity);
             list.forEach((target) -> {
                 target.setPos(target.xo, target.yo, target.zo);
                 target.addEffect(new net.minecraft.world.effect.MobEffectInstance(xyz.pixelatedw.mineminenomi.init.ModEffects.MOVEMENT_BLOCKED.get(), 5, 0, false, false));
             });

             if (spawnGravityRings) {
                 gravityRing(entity, range, 0, true);
                 gravityRing(entity, range - 2, 4, true);
                 gravityRing(entity, range - 4, 8, true);
             }

             if (duration > 100) {
                 this.stop(entity);
             }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, 200);
    }

    public static void gravityRing(net.minecraft.world.entity.LivingEntity entity, int range, int yOffset, boolean visibleOnlyFromOwner) {
       if (entity.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
           serverLevel.sendParticles(new xyz.pixelatedw.mineminenomi.particles.SimpleParticleData(xyz.pixelatedw.mineminenomi.init.ModParticleTypes.GRAVI_ZONE.get()), true, entity.getX(), entity.getY(), entity.getZ(), 1, (double)range, (double)yOffset, 0.0D, 0.0D);
       }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.gravi_zone_guard");
    }
}
"""
    with open('src/main/java/xyz/pixelatedw/mineminenomi/abilities/zushi/GraviZoneAbility.java', 'w') as f:
        f.write(content)

def write_zushi_jigoku_tabi():
    content = """package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;

public class JigokuTabiAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi");
    private int force = 4;

    public JigokuTabiAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        this.force = 4;
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
             List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(24.0D), e -> e != entity);

             for(LivingEntity target : targets) {
                 AbilityHelper.setDeltaMovement(target, 0.0D, target.getDeltaMovement().y - 4.0D, 0.0D);
                 if (duration % 20 == 0) {
                     target.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 25, 5, false, false));
                     target.hurt(entity.damageSources().mobAttack(entity), (float)(this.force * 2));
                     GraviZoneAbility.gravityRing(target, 3, 2, false);

                     BlockPos targetPos = target.blockPosition();
                     for(int dx = -this.force; dx <= this.force; dx++) {
                         for(int dy = -2; dy <= 0; dy++) {
                             for(int dz = -this.force; dz <= this.force; dz++) {
                                 if (dx*dx + dy*dy + dz*dz <= this.force*this.force) {
                                     BlockPos p = targetPos.offset(dx, dy, dz);
                                     if (!entity.level().getBlockState(p).is(net.minecraft.tags.BlockTags.LEAVES) &&
                                         !entity.level().getBlockState(p).isAir()) {
                                         entity.level().setBlockAndUpdate(p, Blocks.AIR.defaultBlockState());
                                     }
                                 }
                             }
                         }
                     }
                 }
             }

             if (duration % 60 == 0) {
                 ++this.force;
             }

             if (duration > 120) {
                 this.stop(entity);
             }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, 340);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.jigoku_tabi");
    }
}
"""
    with open('src/main/java/xyz/pixelatedw/mineminenomi/abilities/zushi/JigokuTabiAbility.java', 'w') as f:
        f.write(content)

def write_zushi_moko():
    content = """package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.zushi.MokoProjectile;

public class MokoAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi");

    public MokoAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Handled in tick
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration % 10 == 0 && duration <= 60) {
            MokoProjectile proj = new MokoProjectile(entity.level(), entity, this);
            proj.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 3.0F, 2.0F);
            entity.level().addFreshEntity(proj);
        }
        if (duration > 60) {
            this.stop(entity);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, 280);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.moko");
    }
}
"""
    with open('src/main/java/xyz/pixelatedw/mineminenomi/abilities/zushi/MokoAbility.java', 'w') as f:
        f.write(content)

def write_zushi_sagari_no_ryusei():
    content = """package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.zushi.SagariNoRyuseiProjectile;
import net.minecraft.world.phys.HitResult;

public class SagariNoRyuseiAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi");

    public SagariNoRyuseiAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            for(int i = 2; i < 16; i += 2) {
                GraviZoneAbility.gravityRing(entity, 4, i, false);
            }

            boolean has2nd = entity.getRandom().nextInt(3) == 0;
            spawnMeteor(entity, 0);
            if (has2nd) {
                spawnMeteor(entity, 1);
            }

            this.startCooldown(entity, 900);
        }
    }

    private void spawnMeteor(LivingEntity entity, int count) {
        HitResult mop = WyHelper.rayTraceEntities(entity, 64.0D);
        if (mop == null || mop.getLocation() == null) {
            // Check block ray trace
            mop = WyHelper.rayTraceBlockSafe(entity, 64.0F);
        }
        if (mop == null || mop.getLocation() == null) return;

        double x = mop.getLocation().x;
        double y = mop.getLocation().y;
        double z = mop.getLocation().z;
        float size = count == 0 ? (float)entity.getRandom().nextInt(7) + 24.0F : (float)entity.getRandom().nextInt(3) + 8.0F;

        SagariNoRyuseiProjectile proj = new SagariNoRyuseiProjectile(entity.level(), entity, this);
        proj.setSize(size);
        proj.setPos(x, y + 90.0D, z);
        proj.setXRot(0.0F);
        proj.setYRot(0.0F);
        proj.setDeltaMovement(0.0D, -1.85D, 0.0D);
        entity.level().addFreshEntity(proj);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.sagari_no_ryusei");
    }
}
"""
    with open('src/main/java/xyz/pixelatedw/mineminenomi/abilities/zushi/SagariNoRyuseiAbility.java', 'w') as f:
        f.write(content)

write_kilo_press_1()
write_kilo_press_10000()
write_kilo_punch_5000()

write_zushi_abare_himatsuri()
write_zushi_gravi_pull()
write_zushi_gravi_zone()
write_zushi_jigoku_tabi()
write_zushi_moko()
write_zushi_sagari_no_ryusei()
