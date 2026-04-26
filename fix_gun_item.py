import re

with open('./src/main/java/xyz/pixelatedw/mineminenomi/items/weapons/ModGunItem.java', 'r') as f:
    content = f.read()

# Replace the releaseUsing signature and block logic inside
content = re.sub(r'public void releaseUsing\(ItemStack itemStack, Level level, net\.minecraft\.world\.entity\.LivingEntity living, int timeLeft\)',
                 r'public void releaseUsing(ItemStack itemStack, Level level, net.minecraft.world.entity.LivingEntity living, int timeLeft)',
                 content)

with open('./src/main/java/xyz/pixelatedw/mineminenomi/items/weapons/ModGunItem.java', 'w') as f:
    f.write(content)
