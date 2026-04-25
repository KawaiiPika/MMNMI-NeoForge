import glob
for file in glob.glob('src/test/java/xyz/pixelatedw/mineminenomi/gametest/*.java'):
    with open(file, 'r') as f:
        content = f.read()
    content = content.replace('ModMain.MOD_ID', 'ModMain.PROJECT_ID')
    with open(file, 'w') as f:
        f.write(content)
