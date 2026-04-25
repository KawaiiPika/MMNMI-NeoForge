import re
with open('src/main/java/xyz/pixelatedw/mineminenomi/gametest/ExampleGameTest.java', 'r') as f:
    content = f.read()

# Instead of @GameTestHolder, which automatically prepends the namespace AND the class name, we can omit @GameTestHolder and use GameTestRegistry.register(). No, we must use annotation.
# What if we use `@PrefixGameTestTemplate(false)` on the class?
content = content.replace('public class ExampleGameTest', '@PrefixGameTestTemplate(false)\npublic class ExampleGameTest')
with open('src/main/java/xyz/pixelatedw/mineminenomi/gametest/ExampleGameTest.java', 'w') as f:
    f.write(content)
