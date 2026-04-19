# Ability Migration Status
*Last Updated: 2026-04-19*

---

## Category System Design

Abilities are organized into the following UI categories:

| ID | Category | Tab Icon Source | Description |
|----|----------|-----------------|-------------|
| 0 | **Devil Fruit** | Fruit item texture | Abilities tied to the player's equipped Devil Fruit |
| 1 | **Passives** | Top passive ability | Always-on effects, immunities, passive bonuses (Empty Hands, Knockdown, etc.) |
| 2 | **Haki** | Top Haki ability (Conqueror's > Armament > Observation) | Haoshoku, Busoshoku, Kenbunshoku |
| 3 | **Styles / Fighting Arts** | Top style ability | Rokushiki, Brawler, Black Leg, Santoryu, Ittoryu, Nitoryu, Sniper, Art of Weather, Doctor |
| 4 | **Race** | Top race ability | Fishman Karate, Cyborg, Mink/Electro, Hasshoken (Fishman), Skypiean Tempos |
| 5 | **Misc** | Top misc ability | Overflow; Marine Loyalty, Mande Mon Tactics, Carnival Tricks, Command |

> **Important**: Race abilities should NOT appear in the Self/Style category. They are unlocked by race, not by fruit or training.

---

## Category Assignment by Source Package

| Legacy Package | Category | Notes |
|----------------|----------|-------|
| `abilities/` root | Passives / Misc | EmptyHands, Knockdown, Command, Zoom, FlyAbility, etc. |
| `haki/` | Haki (Cat 2) | All 10 haki abilities |
| `rokushiki/` | Styles (Cat 3) | Geppo, Soru, Rankyaku, Shigan, Tekkai, Kamie, Kamisori, Rokuogan |
| `brawler/` | Styles (Cat 3) | GenkotsuMeteor, HakaiHo, JishinHo, KingPunch, etc. |
| `blackleg/` | Styles (Cat 3) | Diable Jambe, Anti-Manner Kick, Party Table Kick, etc. |
| `santoryu/` | Styles (Cat 3) | All 3-sword style abilities |
| `ittoryu/` | Styles (Cat 3) | Single-sword style |
| `nitoryu/` | Styles (Cat 3) | Dual-sword style |
| `sniper/` | Styles (Cat 3) | Usopp-style slingshot/sniper abilities |
| `doctor/` | Styles (Cat 3) | Chopper medicine/doping abilities |
| `artofweather/` | Styles (Cat 3) | Nami weather egg abilities |
| `fishmankarate/` | Race (Cat 4) | Fishman race abilities |
| `electro/` | Race (Cat 4) | Mink race abilities |
| `cyborg/` | Race (Cat 4) | Cyborg (Franky-style) race abilities |
| `hasshoken/` | Race (Cat 4) | Fishman race sub-style |
| `tempos/` | Race (Cat 4) | Skypiean Tone Dial abilities |
| `carnivaltricks/` | Misc (Cat 5) | Buggy-squad tricks |
| `mandemontactics/` | Misc (Cat 5) | Mande-style tactics |
| `marineloyalty/` | Misc (Cat 5) | Marine-specific abilities |
| All Devil Fruit packages | Devil Fruit (Cat 0) | Tied to specific fruit |

---

## Devil Fruit Status

### ✅ FULLY PORTED (All abilities present)

| Fruit | Package | Legacy Count | Ported Count | Missing |
|-------|---------|-------------|-------------|---------|
| Suna Suna no Mi | `suna` | 15 | 4 | SunaLogiaAbility, SunaFlyAbility, SunaTravelAbility, DesertGirasole, DesertGrandeEspada, SablesGuard, SablesPesado, GrandeSables, GroundDeath, GroundSecco |
| Yami Yami no Mi | `yami` | 7 | 4 | AbsorbedBlocks, BlackRoad, Liberation |

### 🔶 PARTIALLY PORTED (Core abilities present, some missing)

| Fruit | Package | Legacy Count | Ported Count | Still Missing |
|-------|---------|-------------|-------------|-------------------|
| Gomu Gomu no Mi | `gomu` | 14 | 14 | Logic Complete |
| Mera Mera no Mi | `mera` | 10 | 10 | Logic Complete |
| Magu Magu no Mi | `magu` | 8 | 5 (+helper) | BakuretsuKazan, MaguLogiaAbility |
| Hie Hie no Mi | `hie` | 11 | 11 | Logic Complete |
| Goro Goro no Mi | `goro` | 10 | 6 | GoroLogia, GoroTravel, Kari, VoltAmaruFlight |
| Pika Pika no Mi | `pika` | 8 | 6 | AmaNoMurakumo, PikaLogia, PikaTravel |
| Moku Moku no Mi | `moku` | 11 | 7 | MokuFly, MokuImmunity, MokuLogia, WhiteBlowRush, WhitePull, WhiteSnake |
| Ope Ope no Mi | `ope` | 7 | 6 | Takt |
| Gura Gura no Mi | `gura` | 6 | 4 | GuraLogia, remaining 1 |
| Bari Bari no Mi | `bari` | 6 | 2 | Remaining 4 (shield variants) |
| Nikyu Nikyu no Mi | `nikyu` | 7 | 2 | Remaining paw variants, FlightAbility |
| Kage Kage no Mi | `kage` | 10 | 4 | KageGiri, KageKakumei, Kagemusha — 6 remaining |
| Ito Ito no Mi | `ito` | 8 | 3 | Torikago, Parasite, SoraNoMichi — 5 remaining |
| Doku Doku no Mi | `doku` | 7 | 3 | VenomRoad, Hydra, PoisonImmunity — 4 remaining |
| Hana Hana no Mi | `hana` | 9 | 2 | CienFleur, Clutch — 7 remaining |
| Horo Horo no Mi | `horo` | 4 | 3 | TokuHollow — 1 remaining |
| Wara Wara no Mi | `wara` | 3 | 1 | LifeMinus, StrawMan — 2 remaining |
| Mero Mero no Mi | `mero` | 4 | 2 | MeroMeroMellow, PistolKiss — 2 remaining |
| Pero Pero no Mi | `pero` | 6 | 2 | CandyWave, CandyArmor — 4 remaining |
| Bara Bara no Mi | `bara` | 6 | 3 | BaraBaraCar, BaraSplit, KuchuKirimomiDaiCircus |
| Bomu Bomu no Mi | `bomu` | 5 | 2 | BombThrow, NoseFancyCannon |
| Sube Sube no Mi | `sube` | 2 | 2 | Fully Ported |
| Suke Suke no Mi | `suke` | 3 | 2 | ShishaNoTe, Skatting |
| Noro Noro no Mi | `noro` | 3 | 1 | NoroNoroBeam |
| Yami Yami no Mi | `yami` | 7 | 4 | AbsorbedBlocks, BlackRoad, Liberation |
| Suna Suna no Mi | `suna` | 15 | 4 | SunaLogia, SunaFly, SunaTravel, DesertGirasole, DesertGrandeEspada, SablesGuard, SablesPesado, GrandeSables, GroundSecco |

### ❌ NOT YET PORTED (0 abilities ported)

#### Paramecia
| Fruit | Package | Legacy Count | Key Abilities |
|-------|---------|-------------|---------------|
| Bari Bari no Mi | `bari` | 6 | BarrierWall, BarrierBarrierPistol |
| Ito Ito no Mi | `ito` | 8 | Torikago, Parasite, SoraNoMichi, KumoNoSugaki |
| Nikyu Nikyu no Mi | `nikyu` | 7 | NikuyuPush, PawPad variants |
| Kage Kage no Mi | `kage` | 10 | KageGiri, KageKakumei, Kagemusha |
| Bari Bari no Mi | `bari` | 6 | 0 | BarrierWall, BarrierBarrierPistol |
| Horo Horo no Mi | `horo` | 4 | NegativeHollow, TokuHollow, Hollows |
| Horu Horu no Mi | `horu` | 5 | SexSwitch, Hormones |
| Doru Doru no Mi | `doru` | 11 | CandleWall, DoruDoruArts, TokudaiCandle |
| Noro Noro no Mi | `noro` | 3 | NoroNoroBeam |
| Awa Awa no Mi | `awa` | 3 | Bubbles |
| Bane Bane no Mi | `bane` | 3 | Springs |
| Doa Doa no Mi | `doa` | 4 | Door-Door |
| Doku Doku no Mi | `doku` | 7 | Venom abilities |
| Mero Mero no Mi | `mero` | 4 | MeroMeroMellow |
| Sube Sube no Mi | `sube` | 2 | SubeSubeDeflect, SubeSubeSpur |
| Deka Deka no Mi | `deka` | 2 | DekaDeka, DekaTrampple |
| Kilo Kilo no Mi | `kilo` | 3 | KiloPresses |
| Ori Ori no Mi | `ori` | 3 | Bind, AwaseBaori, GreatCage |
| Jiki Jiki no Mi | `jiki` | 10 | Attract, Repel, PunkCross, DamnedPunk, GenocideRaid |
| Sabi Sabi no Mi | `sabi` | 3 | RustBreak, RustSkin, RustTouch |
| Wara Wara no Mi | `wara` | 3 | LifeMinus, StrawMan, WarabideSword |
| Beta Beta no Mi | `beta` | 5 | Sticky abilities |
| Kachi Kachi no Mi | `kachi` | 3 | HotBoilingSpecial, Vulcanization |
| Kame Kame no Mi | `kame` | 3 | Turtle transform points |
| Pero Pero no Mi | `pero` | 6 | Candy abilities |
| Goe Goe no Mi | `goe` | 2 | DragonsRoar, Todoroki |
| Hiso Hiso no Mi | `hiso` | 2 | Forewarn, Lookout |
| Mini Mini no Mi | `mini` | 2 | MiniMini |
| Nagi Nagi no Mi | `nagi` | 1 | Soundproofing |
| Kobu Kobu no Mi | `kobu` | 1 | Pump ability |
| Karu Karu no Mi | `karu` | 2 | Karma |
| Kuku Kuku no Mi | `kuku` | 3 | Cook abilities |
| Baku Baku no Mi | `baku` | 4 | BakuFactory, BakuMunch, BakuTsuiho |
| Kira Kira no Mi | `kira` | 3 | BrilliantPunk, DiamondBody |
| Gasu Gasu no Mi | `gasu` | 10 | Gas abilities |
| Mochi Mochi no Mi | — | — | Not in legacy source |

#### Logia
| Fruit | Package | Legacy Count | Key Abilities |
|-------|---------|-------------|---------------|
| Yuki Yuki no Mi | `yuki` | 9 | Fubuki, Kamakura, TabiraYuki, YukiGaki, YukiRabi, YukiLogia |
| Yomi Yomi no Mi | `yomi` | 4 | KasuriutaFubukiGiri, SoulParade, YomiNoReiki |

#### Zoan
| Fruit | Package | Legacy Count | Notes |
|-------|---------|-------------|-------|
| Zou Zou no Mi | `zou` | 8 | Elephant form |
| Zou Zou no Mi (Mammoth) | `zoumammoth` | 8 | Mammoth form |
| Neko Neko no Mi (Leopard) | `nekoleopard` | 6 | Leopard form |
| Tori Tori no Mi (Phoenix) | `toriphoenix` | 10 | Phoenix form, blue fire |
| Ryu Ryu no Mi (Allosaurus) | `ryuallosaurus` | 6 | Allosaurus form |
| Ryu Ryu no Mi (Brachiosaurus) | `ryubrachiosaurus` | 6 | Brachiosaurus form |
| Ryu Ryu no Mi (Pteranodon) | `ryupteranodon` | 8 | Pteranodon form |
| Sara Sara no Mi (Axolotl) | `saraaxolotl` | 7 | Axolotl form |
| Ushi Ushi no Mi (Giraffe) | `ushigiraffe` | 5 | Giraffe form |
| Ushi Ushi no Mi (Bison) | `ushibison` | 7 | Bison form |
| Sai Sai no Mi | `sai` | 6 | Rhino form |
| Hito Hito no Mi (Daibutsu) | `hitodaibutsu` | 2 | Buddha form |
| Mogu Mogu no Mi | `mogu` | 4 | Mole form |
| Netsu Netsu no Mi | `netsu` | 4 | Heat form |

---

## Non-Fruit Abilities Status

### ✅ Haki (Category 4)
*Package: `api.abilities.haki/`*

| Ability | Status | Notes |
|---------|--------|-------|
| Haoshoku Haki | ✅ Ported | Base toggle |
| Haoshoku Haki Infusion | ✅ Ported | |
| Busoshoku Haki | ✅ Ported | Base toggle |
| Busoshoku Haki Hardening | ✅ Ported | |
| Busoshoku Haki Full Body Hardening | ✅ Ported | |
| Busoshoku Haki Imbuing | ✅ Ported | |
| Busoshoku Haki Emission | ✅ Ported | |
| Busoshoku Haki Internal Destruction | ✅ Ported | |
| Kenbunshoku Haki | ✅ Ported | Base toggle |
| Kenbunshoku Haki Aura | ✅ Ported | |
| Kenbunshoku Haki FutureSight | ✅ Ported | |

**Status: 100% Ported**

### ✅ Rokushiki / Styles (Category 3)
*Package: `rokushiki/`*

| Ability | Status |
|---------|--------|
| Geppo | ✅ Ported |
| Soru | ✅ Ported |
| Rankyaku | ✅ Ported |
| Shigan | ✅ Ported |
| Tekkai | ✅ Ported |
| Kamie | ✅ Ported |
| Rokuogan | ✅ Ported |
| Kamisori | ✅ Ported |

**Status: 100% Ported (Logic Complete)**

### ❌ Fighting Styles Not Started (Category 3)
- **Brawler** (11 abilities): GenkotsuMeteor, HakaiHo, JishinHo, KingPunch, etc.
- **Black Leg** (8 abilities): DiableJambe, AntiMannerKickCourse, etc.
- **Santoryu** (16 abilities): OniGiri, TatsuMaki, ToraGari, etc.
- **Ittoryu** (8 abilities): ShiShishiSonson, HiryuKaen, Yakkodori, etc.
- **Nitoryu** (2 abilities)
- **Sniper** (9 abilities): HiNoToriBoshi, KaenBoshi, KemuriBoshi, etc.
- **Doctor** (6 abilities): FirstAid, VirusZone, etc.
- **Art of Weather** (7 abilities): WeatherBall, GustSword, etc.

### ❌ Race Abilities Not Started (Category 4)
- **Fishman Karate** (13 abilities): Ported 5 (Mizu Taiho, Uchimizu, Yarinami, Kachiage Haisoku, Karakusagawara Seiken) | 🔶 Partially Ported |
- **Cyborg** (10 abilities): StrongRight, RadicalBeam, CoupDeBoo, CoupDeVent, etc.
- **Mink / Electro** (9 abilities): Eleclaw, ElectricalLuna, MinkPassiveBonuses, Sulong, etc.
- **Hasshoken** (6 abilities): Buto, ButoKaiten, KiryuKirikugi, etc.
- **Skypiean Tempos** (10 abilities): CycloneTempo, ThunderboltTempo, RainTempo, etc.
- **Ryusoken** (2 abilities): RyuNoIbuki, RyuNoKagizume (Dragon Claw martial art)

### ❌ Misc Abilities Not Started (Category 5)
- **Marine Loyalty** (2): Muster, SmallMuster
- **Mande Mon Tactics** (3): DemonicDance, DemonicDash, DemonicSmash
- **Carnival Tricks** (3): IchirinZashi, KajiOyaji, KamikazeHyakkomaGekijo

### ✅ Passives (Category 1)

| Ability | Status | Notes |
|---------|--------|-------|
| EmptyHands | ✅ Ported | Logic Complete |
| Knockdown | ✅ Ported | Logic Complete |
| Command | ✅ Ported | Logic Complete |
| Zoom | ❌ Not ported | |
| FlyAbility | ❌ Not ported | |
| LogiaImmunity | ✅ Ported | Core logic in CommonEvents |
| LogiaBlockBypassing | ❌ Not ported | |
| NoFallDamage | ❌ Not ported | |
| GrabLock | ❌ Not ported | |

---

## Status Effects Status

| Effect | Status | Notes |
|--------|--------|-------|
| Paralysis | ✅ Ported | |
| Dizzy | ✅ Ported | |
| Fatigue | ✅ Ported | |
| Fragile | ✅ Ported | |
| Frostbite | ✅ Ported | Includes `FrostbiteLayer` visuals |
| Frozen | ✅ Ported | |
| Lovestruck | ✅ Ported | |
| Negative | ✅ Ported | |
| Unconscious | ✅ Ported | |
| Candle Lock | ✅ Ported | |
| Candy Stuck | ✅ Ported | |
| Antidote | ✅ Ported | |
| Dehydration | ✅ Ported | |
| Tension Hormone | ✅ Ported | |
| Chiyu Hormone | ✅ Ported | |
| Hunger | ✅ Ported | |
| Drunk | ✅ Ported | |
| Sticky | ✅ Ported | |
| Mummy Virus | ✅ Ported | |
| Ice Oni | ✅ Ported | |
| Bleeding | ✅ Ported | |

**Visual Layers Ported:**
- `BodyCoatingLayer` (Busoshoku Haki Hardening/Full Body)
- `FrostbiteLayer` (Ice coating)
- `HaoshokuOverlay` (Screen flash)

---

## Summary Stats

| Status | Count |
|--------|-------|
| ✅ Ability files ported | ~110 files |
| 🔶 Partially ported fruits | 24 |
| ❌ Fruits not started | ~15 |
| Total legacy ability files | ~500+ |
| Approximate % complete | ~22% |

---

## Priority Order (Recommended Next Steps)

1. **Haki Ability Classes** — system exists, just need the ability wrapper files
2. **Rokushiki** — most commonly used non-fruit abilities
3. **Empty Hands + Knockdown + Command** — core passives every player needs
4. **Logia Immunity** — critical for Logia fruits to function correctly
5. **Gears (Gomu)** + remaining Mera, Hie, Goro, Pika, Moku abilities
6. **Fishman Karate** — most popular race ability set
7. **Remaining Paramecia** (Bari, Ito, Nikyu, Kage, Gura in that order)
8. **Zoan Transformations** — requires model-swapping system first
