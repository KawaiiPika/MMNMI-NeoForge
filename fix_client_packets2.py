import os

packets_dir = 'src/main/java/xyz/pixelatedw/mineminenomi/networking/packets'

for filename in os.listdir(packets_dir):
    if filename.startswith('S') and filename.endswith('Packet.java'):
        filepath = os.path.join(packets_dir, filename)
        with open(filepath, 'r') as f:
            content = f.read()

        content = content.replace('net.minecraft.client.xyz.pixelatedw.mineminenomi.client.ClientNetworking.getMinecraft()', 'xyz.pixelatedw.mineminenomi.client.ClientNetworking.getMinecraft()')

        with open(filepath, 'w') as f:
            f.write(content)
