with open('src/main/java/xyz/pixelatedw/mineminenomi/client/events/ClientEvents.java', 'r') as f:
    lines = f.readlines()

new_lines = []
for line in lines:
    if '>>>>>>>' in line or '=======' in line or '<<<<<<<' in line:
        continue
    new_lines.append(line)

with open('src/main/java/xyz/pixelatedw/mineminenomi/client/events/ClientEvents.java', 'w') as f:
    f.writelines(new_lines)
