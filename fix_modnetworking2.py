import os
with open('src/main/java/xyz/pixelatedw/mineminenomi/networking/ModNetworking.java', 'r') as f:
    content = f.read()

content = content.replace('xyz.pixelatedw.mineminenomi.networking.packets.SOpenCreateCrewScreenPacket::handle', 'xyz.pixelatedw.mineminenomi.client.networking.ClientPacketHandlers::handleOpenCreateCrewScreen')
content = content.replace('xyz.pixelatedw.mineminenomi.networking.packets.SOpenCrewScreenPacket::handle', 'xyz.pixelatedw.mineminenomi.client.networking.ClientPacketHandlers::handleOpenCrewScreen')
content = content.replace('xyz.pixelatedw.mineminenomi.networking.packets.SOpenJollyRogerEditorScreenPacket::handle', 'xyz.pixelatedw.mineminenomi.client.networking.ClientPacketHandlers::handleOpenJollyRogerEditorScreen')
content = content.replace('xyz.pixelatedw.mineminenomi.networking.packets.SSyncStrikerCrewPacket::handle', 'xyz.pixelatedw.mineminenomi.client.networking.ClientPacketHandlers::handleSyncStrikerCrew')
content = content.replace('xyz.pixelatedw.mineminenomi.networking.packets.SSimpleMessageScreenEventPacket::handle', 'xyz.pixelatedw.mineminenomi.client.networking.ClientPacketHandlers::handleSimpleMessageScreenEvent')

with open('src/main/java/xyz/pixelatedw/mineminenomi/networking/ModNetworking.java', 'w') as f:
    f.write(content)

packets_dir = 'src/main/java/xyz/pixelatedw/mineminenomi/networking/packets'
for filename in os.listdir(packets_dir):
    if filename.startswith('S') and filename.endswith('Packet.java'):
        filepath = os.path.join(packets_dir, filename)
        with open(filepath, 'r') as f:
            lines = f.readlines()

        new_lines = []
        skip = False
        for line in lines:
            if 'public static void handle' in line:
                skip = True
            if skip and line.strip() == '}':
                skip = False
                continue
            if not skip:
                new_lines.append(line)

        # also need to make sure we don't accidentally remove class closing brace.
        # simpler approach: just remove import net.minecraft.client.Minecraft;

with open('src/main/java/xyz/pixelatedw/mineminenomi/networking/packets/SOpenCreateCrewScreenPacket.java', 'r') as f:
    c = f.read()
c = c.replace('import net.minecraft.client.Minecraft;', '')
# use regex to remove handle method
import re
c = re.sub(r'public static void handle.*?\}', '', c, flags=re.DOTALL)
with open('src/main/java/xyz/pixelatedw/mineminenomi/networking/packets/SOpenCreateCrewScreenPacket.java', 'w') as f:
    f.write(c)
