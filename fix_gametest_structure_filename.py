import shutil

# When no template is specified, GameTest uses `class_name.method_name` all lowercase.
# For AbilityGameTest.testProjectileSpawn, it looks for:
# mineminenomi:abilitygametest.testprojectilespawn

# Let's ensure the file is named exactly that.
shutil.copy('src/main/resources/data/mineminenomi/gametest/structures/empty.snbt', 'src/main/resources/data/mineminenomi/gametest/structures/abilitygametest.testprojectilespawn.snbt')

# Let's see what ExampleGameTest is looking for.
with open('src/test/java/xyz/pixelatedw/mineminenomi/gametest/ExampleGameTest.java', 'r') as f:
    print(f.read())
