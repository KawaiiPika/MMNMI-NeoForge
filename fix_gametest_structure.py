# Ah... I changed `mineminenomi:examplegametest.empty` back to `mineminenomi:minecraft:empty`. No. I didn't change the ExampleGameTest... Wait, I deleted ExampleGameTest!
# Wait, NO. Look at the crash report:
# java.lang.IllegalStateException: Missing test structure: mineminenomi:examplegametest.empty
# It's STILL failing to find `mineminenomi:examplegametest.empty` despite the file being placed in `gametest/structures` and `gametest/structure` and `structures` AND `structure`.
# BUT look at this path!
# In 1.21, there's NO gametest directory for structures! Gametest structures are loaded from `data/<namespace>/structure/` instead of `gametest/structures/`
# AND the .snbt might need to be converted to .nbt?
import os
import shutil

os.makedirs('src/main/resources/data/mineminenomi/structure/', exist_ok=True)
shutil.copy('src/main/resources/data/mineminenomi/gametest/structures/examplegametest.empty.snbt', 'src/main/resources/data/mineminenomi/structure/examplegametest.empty.snbt')

# Let's also check if snbt even works, or if I should just write NBT using nbtlib... but nbtlib isn't available.
