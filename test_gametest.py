import re
with open('src/main/java/xyz/pixelatedw/mineminenomi/gametest/ExampleGameTest.java', 'r') as f:
    content = f.read()

content = content.replace('template = "minecraft:empty"', 'template = "mineminenomi:examplegametest.empty"')
with open('src/main/java/xyz/pixelatedw/mineminenomi/gametest/ExampleGameTest.java', 'w') as f:
    f.write(content)
