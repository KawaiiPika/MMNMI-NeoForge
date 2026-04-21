# Mine Mine no Mi - Deep Dive Scan Report

## Overview
A comprehensive comparison between the legacy `old_source` and the current modern port (`src`) has been executed to determine the exact porting status.

## Quantitative Metrics
* **Total Legacy Files (`old_source`)**: 2621
* **Total Modern Files (`src`)**: 672
* **Files Ported (Exist in Both)**: 479
* **Unported Legacy Files**: 2142
* **New Files Added in Modern Port**: 193

**Overall Completion Rate**: ~18%

## Directory Status Breakdown

### Top Missing Directories (Unported)
The following directories exist in the legacy mod but have not been ported yet:
* `animations`: 49 files
* `abilities`: 48 files
* `api/abilities/components`: 38 files
* `datagen/loottables/rewards`: 36 files
* `entities/ai/goals`: 31 files
* `mixins`: 31 files
* `models/armors`: 29 files
* `models/morphs`: 27 files
* `renderers/layers`: 22 files
* `models/morphs/partials`: 22 files

### Top Partial Directories (In Progress)
The following directories are partially ported (showing Old vs. New file counts):
* `init` (Old: 63, New: 32)
* `effects` (Old: 61, New: 27)
* `api/abilities` (Old: 32, New: 4)
* `api/helpers` (Old: 26, New: 9)
* `commands` (Old: 23, New: 8)
* `api/entities` (Old: 22, New: 3)
* `api` (Old: 20, New: 2)
* `ui/screens` (Old: 16, New: 2)
* `quests/objectives` (Old: 16, New: 6)
* `api/effects` (Old: 16, New: 1)

## System Gaps Identified
Based on the missing directories, several major systems are entirely absent from the modern port:
1. **Animations & Rendering Layers**: The entire custom animation framework and corresponding render layers are missing.
2. **Morphs & Models**: Custom models for Zoan transformations and specific fruit effects (like armors) are unported.
3. **Advanced AI**: Custom goals for entities (`entities/ai/goals`) are missing, meaning ported mobs will likely lack their specialized behaviors.
4. **Mixins**: Many core logic injections from the legacy mod are unported, which could result in missing vanilla interactions or broken mechanics.
