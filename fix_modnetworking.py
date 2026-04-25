import re
with open('src/main/java/xyz/pixelatedw/mineminenomi/networking/ModNetworking.java', 'r') as f:
    content = f.read()

content = content.replace('payload -> context ->', '(payload, context) ->')

with open('src/main/java/xyz/pixelatedw/mineminenomi/networking/ModNetworking.java', 'w') as f:
    f.write(content)
