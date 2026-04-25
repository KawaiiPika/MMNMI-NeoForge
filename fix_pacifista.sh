#!/bin/bash
sed -i 's/this.entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 10, 0));/this.mob.addEffect(new MobEffectInstance(ModEffects.MOVEMENT_BLOCKED.getDelegateOrThrow(), 10, 0));/g' src/main/java/xyz/pixelatedw/mineminenomi/entities/ai/goals/pacifista/PacifistaRadicalBeamGoal.java
sed -i 's/LivingEntity target = this.entity.m_5448_();/LivingEntity target = this.mob.getTarget();/g' src/main/java/xyz/pixelatedw/mineminenomi/entities/ai/goals/pacifista/PacifistaRadicalBeamGoal.java
sed -i 's/GoalHelper.lookAtEntity(this.entity, target);/GoalHelper.lookAtEntity(this.mob, target);/g' src/main/java/xyz/pixelatedw/mineminenomi/entities/ai/goals/pacifista/PacifistaRadicalBeamGoal.java
sed -i 's/entity.m_9236_().m_7605_(entity, (byte)100);/entity.level().broadcastEntityEvent(entity, (byte)100);/g' src/main/java/xyz/pixelatedw/mineminenomi/entities/ai/goals/pacifista/PacifistaRadicalBeamGoal.java
sed -i 's/entity.m_9236_().m_7605_(entity, (byte)101);/entity.level().broadcastEntityEvent(entity, (byte)101);/g' src/main/java/xyz/pixelatedw/mineminenomi/entities/ai/goals/pacifista/PacifistaRadicalBeamGoal.java
