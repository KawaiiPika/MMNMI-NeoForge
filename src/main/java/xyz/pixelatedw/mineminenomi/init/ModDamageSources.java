package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class ModDamageSources {
    private static ModDamageSources instance;

    public static ModDamageSources getInstance() {
        if (instance == null) instance = new ModDamageSources();
        return instance;
    }

    public DamageSource frozen(LivingEntity entity) {
        return entity.damageSources().magic();
    }

    public DamageSource poison(LivingEntity entity) {
        return entity.damageSources().magic();
    }

    public DamageSource bleed(LivingEntity entity) {
        return entity.damageSources().magic();
    }

    public DamageSource heartAttack(LivingEntity entity) {
        return entity.damageSources().magic();
    }
}
