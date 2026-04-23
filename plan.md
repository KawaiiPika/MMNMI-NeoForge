1. **Implement `IEntityWithComplexSpawn` on ported entities**
   - Update `NuProjectileEntity`, `OPEntity`, `SphereEntity`, and `ThrownSpearEntity` in `src/main/java`.
   - Make these classes implement `net.neoforged.neoforge.entity.IEntityWithComplexSpawn`.
   - Add `writeSpawnData` and `readSpawnData` to these classes by bringing in the logic from the old files in `src/main/java_old`.
   - Since the buffer type is now `RegistryFriendlyByteBuf`, adapt the method signatures and buffer operations to use it.
   - For `NuProjectileEntity`, the original code syncs properties like owner, maxLife, and collision box. `OPEntity` syncs its current texture. `SphereEntity` syncs texture arrays, color, details, and animation data. `ThrownSpearEntity` syncs its `itemStack` and owner ID. We will port this logic into the current Java files.

2. **Fix compilation errors and pre commit instructions**
   - Run compilation using `./gradlew compileJava` to catch errors.
   - Run tests if applicable.
   - Use `pre_commit_instructions` before submitting.

3. **Submit the changes**
   - Create a commit and submit.
