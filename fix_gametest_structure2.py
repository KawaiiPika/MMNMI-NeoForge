import os
import shutil

# If it looks for `examplegametest.empty`, we have `src/main/resources/data/mineminenomi/gametest/structures/examplegametest.empty.snbt`.
# Wait, maybe it should be `examplegametest.empty.nbt`?
# In NeoForge 1.21.1, the game tests might require SNBT to be explicitly converted into NBT or it only reads `.snbt` in dev environment. BUT we are in a dev environment.
# Let's ensure the DataVersion is exactly what Minecraft expects, or entirely omit it!
# Wait! In `examplegametest.testempty.snbt` I put `DataVersion: 3953`.
# I will write a simple SNBT without DataVersion.
os.makedirs('src/main/resources/data/mineminenomi/gametest/structures/', exist_ok=True)
with open('src/main/resources/data/mineminenomi/gametest/structures/examplegametest.empty.snbt', 'w') as f:
    f.write('''{
    size: [1, 1, 1],
    data: [
        {pos: [0, 0, 0], state: "minecraft:air"}
    ],
    entities: [],
    palette: [
        "minecraft:air"
    ]
}''')
