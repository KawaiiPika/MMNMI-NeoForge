with open("src/main/java/xyz/pixelatedw/mineminenomi/init/ModBlocks.java", "r") as f:
    content = f.read()

lines = content.split('\n')
new_lines = []
for line in lines:
    if "registerSimpleBlockItem" in line and ("string_wall" in line or "barrier" in line or "darkness" in line or "ori_bars" in line or "mucus" in line or "sponge_cake" in line or "hardened_snow" in line or "kairoseki_bars" in line or "sky_block" in line or "wanted_poster" in line or "custom_spawner" in line or "oil_spill" in line or "design_barrel" in line or "structure_air" in line or "challenge_air" in line or "flag" in line or "mangrove_log" in line or "mangrove_wood" in line or "stripped_mangrove_log" in line or "stripped_mangrove_wood" in line or "mangrove_leaves" in line or "mangrove_planks" in line):
        continue
    new_lines.append(line)

content = '\n'.join(new_lines)
with open("src/main/java/xyz/pixelatedw/mineminenomi/init/ModBlocks.java", "w") as f:
    f.write(content)
