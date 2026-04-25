1. **Create `StatBonusAbility` (Done)**
   - Extend `Ability` and add methods for `pushStaticAttribute` and `pushDynamicAttribute`.
   - Override `tick` to continuously test `getCheck()` and apply/remove `AttributeModifier`s.
   - Add new methods for early-phase damage pipeline handling (`onIncomingDamage`, `onIncomingDamageCheckInvulnerability`, `onAttackKnockdown`).

2. **Update `Ability` Base Class**
   - Add default `onIncomingDamage` and `onIncomingDamageCheckInvulnerability` methods so they can be accessed from `Ability`.

3. **Update `CommonEvents`**
   - Modify `onLivingIncomingDamage` to loop through the entity's active abilities (including passives now correctly granted via `PlayerStats.grantAbility`) and invoke their `onIncomingDamage` and `onIncomingDamageCheckInvulnerability` hooks.
   - Remove hardcoded perk checks (like sniper accuracy, brawler damage, magma coating, knockdown) and replace them with calls to the generic ability methods.
   - Refactor `onLivingDamagePost` slightly if needed to clean up any remaining damage/effect logic that should belong in the specific ability class.
   - Refactor `Knockdown` implementation to use the new `onAttackKnockdown` or existing `onAttack` mechanism effectively. Ensure it applies the unconscious effect correctly.

4. **Port `MaguPassives`**
   - Migrate logic from `MaguPassivesHandler` to `LavaFlowAbility` or create dedicated passives like `MaguPassivesAbility` extending `StatBonusAbility`. This will involve `lavaMovementBoost`, `canSeeThroughFire`, and `canSeeInsideLava`. *(Note: sight/render logic may need to remain in client-side events, but data can be read from active abilities).*

5. **Port `YomiPassives`**
   - Migrate logic from `YomiPassiveHandler` to `YomiPassiveAbility` extending `StatBonusAbility` or `Ability`. Handle `tick` for water running, and `triggerFirstDeath`.

6. **Port `KagePassives`**
   - Migrate `KagePassivesHandler.tryBurningInSun` to a `KagePassivesAbility` extending `Ability` that checks sun exposure and damages the entity in its `tick` method.

7. **Migrate Generic Perks to `StatBonusAbility` or `Ability`**
   - Update `KnockdownAbility`, `BrawlerDamagePerkAbility`, `EmptyHandsAbility` to utilize the standard methods (`onIncomingDamage` etc.) properly without global hardcoded checks.

8. **Unit Tests**
   - Create unit tests for `StatBonusAbility` attributes application/removal.
   - Create unit tests for `MaguPassives` and `YomiPassives` logic using Mockito to verify the correct modifiers and effects are applied.

9. **Pre-commit Steps**
   - Run `pre_commit_instructions` tool to make sure proper testing, verifications, reviews, and reflections are done before submit.

10. **Submit Change**
    - Commit and push to remote.
