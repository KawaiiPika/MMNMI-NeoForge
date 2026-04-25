import nbtlib

# The issue might be that it literally only looks for .nbt files by default, NOT .snbt files!
# If so, we can convert snbt to nbt maybe? No, we don't have nbtlib installed in the sandbox.
import sys

# In 1.21.1, the game tests require `.snbt` in `gametest/structures` because the framework natively parses snbt files for game tests (it was added recently).
# Wait, `mineminenomi:abilitygametest.empty` means it looks for `data/mineminenomi/gametest/structures/abilitygametest.empty.snbt`!
# Oh, `gametest/structures` or `gametest/structure`?
# In NeoForge 1.21.1, the gametest framework structure directory path is `data/<namespace>/gametest/structure/`!
