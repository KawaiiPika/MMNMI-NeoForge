import re
import os

with open('src/main/java/xyz/pixelatedw/mineminenomi/init/ModEntities.java', 'r') as f:
    mod_entities = f.read()

entities_in_mod = re.findall(r'public static final [^\n]*Supplier<[^>]*EntityType<([^>]+)>> ([A-Z0-9_]+) = ModRegistry\.ENTITY_TYPES\.register', mod_entities)

added_entities = [
    "SNIPER_PELLET", "GEKISHIN", "PAD_HO", "OVERHEAT", "MERO_MERO_MELLOW",
    "NOSE_FANCY_CANNON", "BRICK_BAT", "BARA_BARA_HO", "NEGATIVE_HOLLOW",
    "GOMU_GOMU_NO_BAZOOKA", "NORO_NORO_BEAM", "PISTOL_KISS", "WHITE_OUT",
    "THROWN_SPEAR", "YAKKODORI", "SANBYAKUROKUJU_POUND_HO", "SANJUROKU_POUND_HO_PROJECTILE",
    "TATSU_MAKI_PROJECTILE", "NANAJUNI_POUND_HO_PROJECTILE", "NANAHYAKUNIJU_POUND_HO_PROJECTILE",
    "HYAKUHACHI_POUND_HO_PROJECTILE", "SENHACHIJU_POUND_HO_PROJECTILE", "RELAX_HOUR_PROJECTILE",
    "VIVRE_CARD"
]

def check_item_supplier(class_name):
    # Try to find file
    file_path = None
    for root, dirs, files in os.walk('src/main/java/xyz/pixelatedw/mineminenomi/'):
        for file in files:
            if file == class_name.split('.')[-1] + '.java':
                file_path = os.path.join(root, file)
                break
        if file_path:
            break
    if not file_path:
        return False
    with open(file_path, 'r') as f:
        content = f.read()
        return "ItemSupplier" in content or "ThrowableItemProjectile" in content

for entity_class, entity_name in entities_in_mod:
    if entity_name in added_entities:
        has_item_supplier = check_item_supplier(entity_class)
        print(f"{entity_name} ({entity_class}): {has_item_supplier}")
