import re

with open('./src/main/java/xyz/pixelatedw/mineminenomi/items/weapons/ModGunItem.java', 'r') as f:
    content = f.read()

content = content.replace('if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {\\n                            this.shootProjectile(player, proj, 0, this.bulletSpeed, this.inaccuracy, 0.0F, null);\\n                        }\\n                        level.addFreshEntity(proj);',
                          'if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {\n                            this.shootProjectile(player, proj, 0, this.bulletSpeed, this.inaccuracy, 0.0F, null);\n                            level.addFreshEntity(proj);\n                        }')

with open('./src/main/java/xyz/pixelatedw/mineminenomi/items/weapons/ModGunItem.java', 'w') as f:
    f.write(content)
