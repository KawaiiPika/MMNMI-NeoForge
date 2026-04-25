#!/bin/bash
mkdir -p src/main/java/xyz/pixelatedw/mineminenomi/entities/ai/goals/abilities
cat << 'END_OF_FILE' > src/main/java/xyz/pixelatedw/mineminenomi/entities/ai/goals/abilities/ProjectileAbilityWrapperGoal.java
package xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities;
public class ProjectileAbilityWrapperGoal<T> extends xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal<net.minecraft.world.entity.Mob> {
    public Object chargeComponent;
    public ProjectileAbilityWrapperGoal(net.minecraft.world.entity.Mob entity, Object ability) { super(entity); }
    public Object getAbility() { return null; }
    public void tickWrapper() {}
    public boolean canUse() { return false; }
}
END_OF_FILE

mkdir -p src/main/java/xyz/pixelatedw/mineminenomi/abilities/cyborg
cat << 'END_OF_FILE' > src/main/java/xyz/pixelatedw/mineminenomi/abilities/cyborg/RadicalBeamAbility.java
package xyz.pixelatedw.mineminenomi.abilities.cyborg;
public class RadicalBeamAbility {
    public static final java.util.function.Supplier<Object> INSTANCE = () -> null;
    public java.util.Optional<Object> getComponent(Object key) { return java.util.Optional.empty(); }
}
END_OF_FILE

mkdir -p src/main/java/xyz/pixelatedw/mineminenomi/api/abilities/components
cat << 'END_OF_FILE' > src/main/java/xyz/pixelatedw/mineminenomi/api/abilities/components/AbilityComponentKey.java
package xyz.pixelatedw.mineminenomi.api.abilities.components;
public class AbilityComponentKey {}
END_OF_FILE

cat << 'END_OF_FILE' > src/main/java/xyz/pixelatedw/mineminenomi/api/abilities/components/AnimationComponent.java
package xyz.pixelatedw.mineminenomi.api.abilities.components;
public class AnimationComponent {
    public void start(net.minecraft.world.entity.LivingEntity entity, Object anim) {}
    public void stop(net.minecraft.world.entity.LivingEntity entity) {}
}
END_OF_FILE

cat << 'END_OF_FILE' > src/main/java/xyz/pixelatedw/mineminenomi/api/abilities/components/BonusManager.java
package xyz.pixelatedw.mineminenomi.api.abilities.components;
public class BonusManager {
    public static class BonusValue {
        public BonusValue(java.util.UUID id, String name, Object op, float val) {}
    }
}
END_OF_FILE

cat << 'END_OF_FILE' > src/main/java/xyz/pixelatedw/mineminenomi/api/abilities/components/BonusOperation.java
package xyz.pixelatedw.mineminenomi.api.abilities.components;
public enum BonusOperation { MUL }
END_OF_FILE

mkdir -p src/main/java/xyz/pixelatedw/mineminenomi/init
cat << 'END_OF_FILE' > src/main/java/xyz/pixelatedw/mineminenomi/init/ModAbilityComponents.java
package xyz.pixelatedw.mineminenomi.init;
public class ModAbilityComponents {
    public static final java.util.function.Supplier<Object> COLA_USAGE = () -> null;
    public static final java.util.function.Supplier<Object> ANIMATION = () -> null;
}
END_OF_FILE

cat << 'END_OF_FILE' > src/main/java/xyz/pixelatedw/mineminenomi/init/ModAnimations.java
package xyz.pixelatedw.mineminenomi.init;
public class ModAnimations {
    public static final Object HEAD_LASER_CHARGE = null;
}
END_OF_FILE
