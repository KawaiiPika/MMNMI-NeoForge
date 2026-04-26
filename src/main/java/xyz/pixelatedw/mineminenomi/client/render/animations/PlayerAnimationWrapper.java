package xyz.pixelatedw.mineminenomi.client.render.animations;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.Map;

public class PlayerAnimationWrapper extends HierarchicalModel<LivingEntity> {
    private final ModelPart root;

    public PlayerAnimationWrapper(HumanoidModel<?> humanoidModel) {
        this.root = new ModelPart(List.of(), Map.of(
        ModelPart trueRoot = new ModelPart(List.of(), Map.of(
                "head", humanoidModel.head,
                "hat", humanoidModel.hat,
                "body", humanoidModel.body,
                "right_arm", humanoidModel.rightArm,
                "left_arm", humanoidModel.leftArm,
                "right_leg", humanoidModel.rightLeg,
                "left_leg", humanoidModel.leftLeg
        ));
        this.root = new ModelPart(List.of(), Map.of("root", trueRoot));
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // Handled via mixin instead
    }
}
