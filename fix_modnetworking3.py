import os
import re

packets_dir = 'src/main/java/xyz/pixelatedw/mineminenomi/networking/packets'
for filename in ['SOpenCrewScreenPacket.java', 'SOpenJollyRogerEditorScreenPacket.java', 'SSyncStrikerCrewPacket.java', 'SSimpleMessageScreenEventPacket.java']:
    filepath = os.path.join(packets_dir, filename)
    with open(filepath, 'r') as f:
        c = f.read()

    c = c.replace('import net.minecraft.client.Minecraft;', '')
    c = re.sub(r'public static void handle.*?\}\n    \}', '', c, flags=re.DOTALL)
    c = re.sub(r'public static void handle.*?\}\n\}', '}', c, flags=re.DOTALL)

    with open(filepath, 'w') as f:
        f.write(c)
