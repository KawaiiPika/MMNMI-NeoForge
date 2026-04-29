import re
with open('src/main/java/xyz/pixelatedw/mineminenomi/client/events/ClientEvents.java', 'r') as f:
    data = f.read()

# Fix syntax errors resulting from the regex replacements
data = data.replace('        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.HIGAN.get(),', '        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.HIGAN.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));')
data = re.sub(r'event\.registerEntityRenderer\(xyz\.pixelatedw\.mineminenomi\.init\.ModEntities\.HIGAN\.get\(\),\s*ctx -\> new net\.minecraft\.client\.renderer\.entity\.ThrownItemRenderer\<\>\(ctx, 1\.0F, true\)\);\s*ctx -\> new net\.minecraft\.client\.renderer\.entity\.ThrownItemRenderer\<\>\(ctx, 1\.0F, true\)\);', 'event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.HIGAN.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));', data)

with open('src/main/java/xyz/pixelatedw/mineminenomi/client/events/ClientEvents.java', 'w') as f:
    f.write(data)
