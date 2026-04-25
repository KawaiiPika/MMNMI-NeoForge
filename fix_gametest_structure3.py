import glob
import re

for file in glob.glob('src/test/java/xyz/pixelatedw/mineminenomi/gametest/*.java'):
    with open(file, 'r') as f:
        content = f.read()
    content = re.sub(r'template = "[^"]+"', 'template = "mineminenomi:gametest.empty"', content)
    with open(file, 'w') as f:
        f.write(content)
