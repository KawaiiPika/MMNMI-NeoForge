import re
with open('src/test/java/xyz/pixelatedw/mineminenomi/gametest/AbilityGameTest.java', 'r') as f:
    content = f.read()
# Let's use the actual default GameTest.EMPTY_STRUCTURE instead of hardcoding our own. Oh wait, GameTest framework does have an empty one?
content = re.sub(r'template = "[^"]+"', 'template = "net.minecraft.gametest.framework.GameTest.EMPTY_STRUCTURE"', content)
# It's an annotation, so the value must be a constant expression. We can use GameTest.EMPTY_STRUCTURE
content = content.replace('"net.minecraft.gametest.framework.GameTest.EMPTY_STRUCTURE"', 'GameTest.EMPTY_STRUCTURE')
with open('src/test/java/xyz/pixelatedw/mineminenomi/gametest/AbilityGameTest.java', 'w') as f:
    f.write(content)

with open('src/test/java/xyz/pixelatedw/mineminenomi/gametest/ExampleGameTest.java', 'r') as f:
    content = f.read()
content = re.sub(r'template = "[^"]+"', 'template = net.minecraft.gametest.framework.GameTest.EMPTY_STRUCTURE', content)
with open('src/test/java/xyz/pixelatedw/mineminenomi/gametest/ExampleGameTest.java', 'w') as f:
    f.write(content)
