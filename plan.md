1. **MarineTraderEntity**: Add `MarineTraderEntity` to `xyz/pixelatedw/mineminenomi/entities/mobs/marines/MarineTraderEntity.java`.
2. **MorganEntity**: Add `MorganEntity` to `xyz/pixelatedw/mineminenomi/entities/mobs/marines/MorganEntity.java`.
3. **MorganMusterGoal**: Add `MorganMusterGoal` to `xyz/pixelatedw/mineminenomi/entities/ai/goals/morgan/MorganMusterGoal.java`.
4. **PacifistaEntity**: Add `PacifistaEntity` to `xyz/pixelatedw/mineminenomi/entities/mobs/PacifistaEntity.java`.
5. **PacifistaRadicalBeamGoal**: Add `PacifistaRadicalBeamGoal` to `xyz/pixelatedw/mineminenomi/entities/ai/goals/pacifista/PacifistaRadicalBeamGoal.java`.
6. **HandleCannonGoal**: Ensure `HandleCannonGoal` is present.
7. **MarineRank**: Update `MarineRank.java` inside `xyz/pixelatedw/mineminenomi/api/factions/MarineRank.java`.
8. **Pre-commit**: Complete pre commit steps to ensure changes are accurate.
1. **Extend `TestEntityBuilder`** to support more data attachments and configurations (e.g., equipped abilities, active abilities, morph data).
2. **Review Memory directives**:
    - Mockito mockStatic and lenience where applicable.
    - `ModDataAttachments` setup logic (e.g., returning level as null to prevent NullPointerException in sound/particle interactions: `when(player.level()).thenReturn(null)` or mock it appropriately with `level() == null` note in memory). Let me double check the memory logic for level: "In unit tests, to prevent NullPointerExceptions from uninitialized sound or particle registries (like ModSounds) when testing events that spawn them via entity.level(), mock the entity to return null for level() to safely bypass these interactions." - *Will update TestEntityBuilder.build() to use `when(player.level()).thenReturn(null);` instead of returning a mocked level.*
    - Add `lenient().when(...)` for default mock behaviors.
    - Allow creation of other entities using a generic or specific method if needed, or create another builder like `TestLivingEntityBuilder`. Let's just enhance `TestEntityBuilder`.
3. **Run tests** and pre-commit instructions before submitting.
