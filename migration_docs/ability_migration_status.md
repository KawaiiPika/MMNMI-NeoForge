# Ability Migration Status
*Last Updated: 2026-04-19 (Updated with Gap Analysis)*

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

---

## Devil Fruit Status

### ✅ FULLY PORTED (All abilities present)

| Fruit | Package | Legacy Count | Ported Count | Missing |
|-------|---------|-------------|-------------|---------|
| Gomu Gomu no Mi | `gomu` | 14 | 14 | Logic Complete |
| Mera Mera no Mi | `mera` | 10 | 10 | Logic Complete |
| Hie Hie no Mi | `hie` | 11 | 11 | Logic Complete |
| Sube Sube no Mi | `sube` | 2 | 2 | Fully Ported |

### 🔶 PARTIALLY PORTED (Core abilities present, some missing)

| Fruit | Package | Legacy Count | Ported Count | Still Missing |
|-------|---------|-------------|-------------|-------------------|
| Magu Magu no Mi | `magu` | 8 | 5 | BakuretsuKazan, MaguLogiaAbility |
| Goro Goro no Mi | `goro` | 10 | 6 | GoroLogia, GoroTravel, Kari, VoltAmaruFlight |
| Pika Pika no Mi | `pika` | 8 | 6 | AmaNoMurakumo, PikaLogia, PikaTravel |
| Moku Moku no Mi | `moku` | 11 | 7 | MokuFly, MokuImmunity, MokuLogia, WhiteBlowRush, WhitePull, WhiteSnake |
| Ope Ope no Mi | `ope` | 7 | 4 | Takt, Mess, Scan |
| Gura Gura no Mi | `gura` | 6 | 7 (incl. helper) | GuraLogia |
| Bari Bari no Mi | `bari` | 6 | 6 | Logic Complete |
| Nikyu Nikyu no Mi | `nikyu` | 7 | 6 | FlightAbility |
| Kage Kage no Mi | `kage` | 10 | 8 | KageGiri, KageKakumei |
| Ito Ito no Mi | `ito` | 8 | 7 | Parasite |
| Doku Doku no Mi | `doku` | 7 | 3 | VenomRoad, Hydra, PoisonImmunity — 4 remaining |
| Hana Hana no Mi | `hana` | 9 | 2 | CienFleur, Clutch — 7 remaining |
| Horo Horo no Mi | `horo` | 4 | 2 | TokuHollow, NegativeHollow |
| Wara Wara no Mi | `wara` | 3 | 1 | LifeMinus, StrawMan — 2 remaining |
| Mero Mero no Mi | `mero` | 4 | 2 | MeroMeroMellow, PistolKiss — 2 remaining |
| Pero Pero no Mi | `pero` | 6 | 2 | CandyWave, CandyArmor — 4 remaining |
| Bara Bara no Mi | `bara` | 6 | 2 | BaraBaraCar, BaraSplit, KuchuKirimomiDaiCircus, BaraFestival |
| Bomu Bomu no Mi | `bomu` | 5 | 2 | BombThrow, NoseFancyCannon, KickBomb |
| Suke Suke no Mi | `suke` | 3 | 2 | ShishaNoTe |
| Noro Noro no Mi | `noro` | 3 | 1 | NoroNoroBeam, NoroNoroSilverFox |
| Yami Yami no Mi | `yami` | 7 | 4 | AbsorbedBlocks, BlackRoad, Liberation |
| Suna Suna no Mi | `suna` | 15 | 4 | SunaLogia, SunaFly, SunaTravel, DesertGirasole, etc. |

### ❌ NOT YET PORTED (0 abilities ported)

- **Paramecia**: Horu Horu, Doru Doru, Awa Awa, Bane Bane, Doa Doa, Deka Deka, Kilo Kilo, Ori Ori, Jiki Jiki, Sabi Sabi, Beta Beta, Kachi Kachi, Mini Mini, Nagi Nagi, Kobu Kobu, Karu Karu, Kuku Kuku, Baku Baku, Kira Kira, Zushi Zushi.
- **Logia**: Yuki Yuki, Yomi Yomi, Gasu Gasu.
- **Zoan**: Core logic for several Ryu Ryu no Mi models (Allosaurus, Brachiosaurus, Pteranodon), Zou Zou no Mi models, Tori Tori no Mi (Phoenix), and Neko Neko no Mi (Leopard) have been started but remain unverified due to build environment issues.

---

## Non-Fruit Abilities Status

### ✅ Haki (Category 2)
- **Status: 100% Ported (Core Logic)**
- All 10 legacy abilities are represented in the new `PlayerStats` and `Haki` classes.

### ✅ Rokushiki (Category 3)
- **Status: 100% Ported (Logic Complete)**
- Geppo, Soru, Rankyaku, Shigan, Tekkai, Kamie, Rokuogan, Kamisori.

### 🔶 Styles / Race (Category 3 & 4)
- **Brawler**: Partially Ported (8/11)
- **Black Leg**: Fully Ported (8/8)
- **Santoryu**: Partially Ported (4/16)
- **Ittoryu**: Partially Ported (6/8)
- **Nitoryu**: Fully Ported (2/2)
- **Sniper**: Fully Ported (10/10)
- **Doctor**: Fully Ported (6/6)
- **Art of Weather**: Fully Ported (8/8)
- **Fishman Karate**: Partially Ported (4/13)
- **Cyborg**: Not Started (0/10)
- **Mink / Electro**: Not Started (0/9)
- **Hasshoken**: Not Started (0/6)
- **Ryusoken**: Not Started (0/2)

---

## Summary Stats

| Status | Count |
|--------|-------|
| ✅ Ability files ported | ~228 files |
| 🔶 Partially ported fruits | 21 |
| ❌ Fruits not started | ~25 |
| Approximate % complete | ~45% |

---

## Faithful Port Check (Gap Analysis)
- **Bullets**: Special ammunition for guns is missing.
- **Integrations**: Curios integration is listed in `old_source` but not yet implemented in `src`.
- **Animations**: The legacy mod has a dedicated `animations` package with 45+ classes. The current port needs to implement these to maintain visual fidelity.
- **Morphs**: Zoan morphs and other transformations are missing.


## Migration Batches (Action Plan)

To systematically port the remaining abilities, they have been grouped into logical batches:

### Devil Fruits
- **Batch 1: Logias & Simple Paramecias**: Yuki, Yomi, Gasu, Sube, Bari.
- **Batch 2: Gravity & Weight Paramecias**: Zushi, Kilo, Ton, Fuwa (if applicable).
- **Batch 3: Creation & Manipulation Paramecias**: Doru, Awa, Doa, Ori, Beta, Kuku, Baku, Kira.
- **Batch 4: Body & Misc Paramecias**: Horu, Bane, Deka, Jiki, Sabi, Kachi, Mini, Nagi, Kobu, Karu.
- **Batch 5: Remaining Zoans**: Ryu Ryu models, Zou Zou, Tori Tori (Phoenix), Neko Neko (Leopard).
- **Batch 6: Partially Ported Cleanup**: Finish remaining core abilities for all partially ported fruits.

### Fighting Styles & Races
- **Batch 7: Swordsman**: Santoryu, Ittoryu, Nitoryu.
- **Batch 8: Martial Arts & Tech**: Brawler, Fishman Karate, Cyborg, Mink/Electro, Hasshoken, Ryusoken.
- **Batch 9: Misc**: Sniper, Doctor, Art of Weather.
