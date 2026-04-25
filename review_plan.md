## Plan for Porting the Crew System

The goal is to port the rest of the Crew system from the legacy 1.20.1 Forge codebase to the modern 1.21.1 NeoForge port.

### 1. Events
- Update `xyz.pixelatedw.mineminenomi.api.events.entity.CrewEvent` to include the missing inner classes: `Join`, `Leave`, and `Kick`. These should implement `ICancellableEvent` just like `Create`.
- Port `xyz.pixelatedw.mineminenomi.api.events.JollyRogerEvent` to modern NeoForge format (`Event` base class, etc).

### 2. Networking Packets
- **Server to Client**
  - Port `SOpenCrewScreenPacket` to `xyz.pixelatedw.mineminenomi.networking.packets`. Use a `CustomPacketPayload` and `StreamCodec` (needs to serialize/deserialize `Crew` from/to `CompoundTag` using `ByteBufCodecs.COMPOUND_TAG` and `crew.write()`/`Crew.from(nbt)`).
  - Port `SOpenJollyRogerEditorScreenPacket` (needs `isEditing`, `Crew`, and a list of `JollyRogerElement`s). Register it.
  - Port `SSimpleMessageScreenEventPacket` (renaming it/re-purposing the logic to support the modernized `SimpleMessageScreenDTO`).
  - Port `SSyncStrikerCrewPacket`.

- **Client to Server**
  - Port `CKickFromCrewPacket` (takes a UUID).
  - Port `CLeaveCrewPacket` (no args, unit packet).
  - Port `CUpdateJollyRogerPacket` (takes a `JollyRoger`).
  - Port `COpenCrewScreenPacket` (no args, requests server to open the crew screen).
  - Port `COpenJollyRogerEditorScreenPacket` (no args).

- **Registration**
  - Register all the above new packets in `ModNetworking`.

### 3. Screen Event API
- Port `SimpleMessageScreenEvent` logic (already partly existing as `SimpleMessageScreenDTO`) and ensure it integrates well with `CreateCrewScreen` which uses it for error messages.
- Add `IEventReceiverScreen` interface if needed by screens to handle these events.

### 4. UI Elements
- Port `CrewDetailsScreen` (displays crew details, members, and buttons for Leave / Edit Jolly Roger).
- Port `CrewMembersScrollPanel` (list of members inside `CrewDetailsScreen` with kick buttons for captains).
- Port `JollyRogerEditorScreen` and related classes:
  - `JollyRogerElementsScrollPanel`
  - `JollyRogerElementColorComponent`
  - `TexturedRectUI` (port modern OpenGL logic). Note that `TexturedRectUI` already partially exists in `xyz.pixelatedw.mineminenomi.client.ui.TexturedRectUI`.
- Port any remaining widget additions if needed (e.g. `SimpleButton` or `PlankButton`).

### 5. Utilities & Data
- Update `FactionsWorldData` to ensure all methods referenced by the packets (like `removeCrewMember`, `updateCrewJollyRoger`, etc.) are fully ported. Ensure `FactionHelper` is ported or the relevant logic is added where needed.
- Ensure `ModJollyRogers` (Jolly Roger elements registry) is ported or properly referenced.

### 6. Pre-Commit Verification
- Run the required pre-commit checks using `pre_commit_instructions` tool to verify the changes compile and adhere to standards.
