1. **Create AnimationStateData**: Create a record to hold `activeAnimation` and `startTimeTicks` similar to `PlayerStats`.
2. **Register Data Attachments**: Add `ANIMATION_STATE` and `MORPH_DATA` to `ModDataAttachments`.
3. **Port Zoan Morph Rendering**: Ensure `MorphRenderingHandler` utilizes the new `MORPH_DATA` attachment. Or since morph models depend on active abilities in `PlayerStats`, update `ZoanAbility` to set the correct morph model resource location.
4. **Implement Keyframe Animations**: Create `PlayerAnimationWrapper` extending `HierarchicalModel` to wrap vanilla `HumanoidModel` parts. Create `ModAnimations` to hold Vanilla `AnimationDefinition`s. Create `AnimationHandler` to subscribe to `RenderLivingEvent.Pre` and apply keyframe animations via `KeyframeAnimations.animate()`.
5. **Convert Legacy Animations**: Convert at least one legacy animation (e.g., `ScreamAnimation`) to data-driven keyframes in `ModAnimations` as a proof-of-concept for the task.
