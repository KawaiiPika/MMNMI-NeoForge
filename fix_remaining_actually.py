import os

files_to_restore = [
    "src/main/java/xyz/pixelatedw/mineminenomi/abilities/doa/AirDoorAbility.java",
    "src/main/java/xyz/pixelatedw/mineminenomi/abilities/doa/DoorDoorAbility.java",
    "src/main/java/xyz/pixelatedw/mineminenomi/abilities/doa/KaitenDoorAbility.java",
    "src/main/java/xyz/pixelatedw/mineminenomi/abilities/ori/BindAbility.java",
    "src/main/java/xyz/pixelatedw/mineminenomi/abilities/ori/AwaseBaoriAbility.java",
    "src/main/java/xyz/pixelatedw/mineminenomi/abilities/ori/GreatCageAbility.java",
    "src/main/java/xyz/pixelatedw/mineminenomi/abilities/beta/BetaBetaChainAbility.java",
    "src/main/java/xyz/pixelatedw/mineminenomi/abilities/beta/BetaCoatingAbility.java",
    "src/main/java/xyz/pixelatedw/mineminenomi/abilities/beta/BetaImmunityAbility.java",
    "src/main/java/xyz/pixelatedw/mineminenomi/abilities/beta/BetaLauncherAbility.java",
    "src/main/java/xyz/pixelatedw/mineminenomi/abilities/beta/HanamizuShinkenAbility.java",
    "src/main/java/xyz/pixelatedw/mineminenomi/abilities/kuku/GastronomorphAbility.java",
    "src/main/java/xyz/pixelatedw/mineminenomi/abilities/kuku/GourmetSnipeAbility.java",
    "src/main/java/xyz/pixelatedw/mineminenomi/abilities/kuku/GourmetamorphosisAbility.java",
    "src/main/java/xyz/pixelatedw/mineminenomi/abilities/baku/BakuMunchAbility.java",
    "src/main/java/xyz/pixelatedw/mineminenomi/abilities/baku/BakuFactoryAbility.java",
    "src/main/java/xyz/pixelatedw/mineminenomi/abilities/baku/BakuTsuihoAbility.java",
    "src/main/java/xyz/pixelatedw/mineminenomi/abilities/baku/BeroCannonAbility.java",
    "src/main/java/xyz/pixelatedw/mineminenomi/abilities/kira/BrilliantPunkAbility.java",
    "src/main/java/xyz/pixelatedw/mineminenomi/abilities/kira/CabochonKnuckleAbility.java",
    "src/main/java/xyz/pixelatedw/mineminenomi/abilities/kira/DiamondBodyAbility.java"
]

for filepath in files_to_restore:
    if os.path.exists(filepath):
        with open(filepath, "w") as f_out:
            pass # Just clear them first to write the new logic
