import os
import glob

# The issue might be that gametests from test source set are not picked up or not correctly annotated.
# Actually, the test folder might not be automatically scanned for gametests by NeoForge by default unless specified.
# Let's move gametests to src/main/java temporarily to see if it fixes it.

os.makedirs('src/main/java/xyz/pixelatedw/mineminenomi/gametest', exist_ok=True)
for file in glob.glob('src/test/java/xyz/pixelatedw/mineminenomi/gametest/*.java'):
    os.rename(file, file.replace('src/test/java', 'src/main/java'))
