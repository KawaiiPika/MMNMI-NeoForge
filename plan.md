1. **Extend `TestEntityBuilder`** to support more data attachments and configurations (e.g., equipped abilities, active abilities, morph data).
2. **Review Memory directives**:
    - Mockito mockStatic and lenience where applicable.
    - `ModDataAttachments` setup logic (e.g., returning level as null to prevent NullPointerException in sound/particle interactions: `when(player.level()).thenReturn(null)` or mock it appropriately with `level() == null` note in memory). Let me double check the memory logic for level: "In unit tests, to prevent NullPointerExceptions from uninitialized sound or particle registries (like ModSounds) when testing events that spawn them via entity.level(), mock the entity to return null for level() to safely bypass these interactions." - *Will update TestEntityBuilder.build() to use `when(player.level()).thenReturn(null);` instead of returning a mocked level.*
    - Add `lenient().when(...)` for default mock behaviors.
    - Allow creation of other entities using a generic or specific method if needed, or create another builder like `TestLivingEntityBuilder`. Let's just enhance `TestEntityBuilder`.
3. **Run tests** and pre-commit instructions before submitting.
