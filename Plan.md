1. **Register `VivreCardEntity` in `ModEntities`:**
    * Use `sed` or rewrite `src/main/java/xyz/pixelatedw/mineminenomi/init/ModEntities.java` to add `VIVRE_CARD` entity registration.
    * Registration block:
      ```java
      public static final Supplier<EntityType<xyz.pixelatedw.mineminenomi.entities.VivreCardEntity>> VIVRE_CARD = ModRegistry.ENTITY_TYPES.register("vivre_card",
            () -> EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.VivreCardEntity>of(xyz.pixelatedw.mineminenomi.entities.VivreCardEntity::new, MobCategory.MISC)
                    .sized(0.4F, 0.4F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("vivre_card"));
      ```
    * Verify the file modifications.

2. **Port `VivreCardEntity` to 1.21.1:**
    * Create file `src/main/java/xyz/pixelatedw/mineminenomi/entities/VivreCardEntity.java` with base class `net.minecraft.world.entity.Entity` and implement `net.minecraft.world.entity.projectile.ItemSupplier`.
    * Port the movement logic towards the `ownerUUID` from the old `VivreCardEntity.java`. Use `tick()` for movement, `defineSynchedData()` (empty), `readAdditionalSaveData()` and `addAdditionalSaveData()` for `OwnerUUID`.
    * The movement logic limits max speed to 0.001 and moves using `move(MoverType.SELF, new Vec3(-posX, 0.0F, -posZ))`. It gives a VivreCardItem to nearby players.
    * The entity must implement `getItem()` returning `new ItemStack(ModItems.VIVRE_CARD.get())` to work with `ThrownItemRenderer`.
    * Verify the file content with `read_file`.

3. **Update `VivreCardItem.java` to spawn `VivreCardEntity`:**
    * Implement `use(Level level, Player player, InteractionHand hand)` in `src/main/java/xyz/pixelatedw/mineminenomi/items/VivreCardItem.java`.
    * In `use`, get `ownerUUID` from `SoulboundItemHelper.getOwnerUUID(level, stack)`.
    * If `ownerUUID` is not null, spawn `VivreCardEntity`, set its position slightly above the player, and copy the ownerUUID. Also, add the logic to apply rotation to the entity similar to the old 1.20.1 implementation:
      ```java
      VivreCardEntity vivreCard = new VivreCardEntity(level);
      vivreCard.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
      vivreCard.setOwner(ownerUUID);
      level.addFreshEntity(vivreCard);
      stack.shrink(1);
      ```
    * Remove the existing `onDroppedByPlayer` method with the TODO comment.
    * Verify the file modifications with `read_file`.

4. **Register Client Renderer:**
    * Use `replace_with_git_merge_diff` to add `event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.VIVRE_CARD.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));` to `src/main/java/xyz/pixelatedw/mineminenomi/client/events/ClientEvents.java`.
    * Verify the file modifications.

5. **Test and Verify Changes:**
    * Run `./gradlew build` or `./gradlew test` to ensure compilation succeeds and tests pass.

6. **Pre-commit checks:**
    * Complete pre-commit steps to ensure proper testing, verification, review, and reflection are done.
