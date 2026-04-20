import re

with open('src/main/java/xyz/pixelatedw/mineminenomi/init/ModEntities.java', 'r') as f:
    mod_entities = f.read()

with open('src/main/java/xyz/pixelatedw/mineminenomi/client/events/ClientEvents.java', 'r') as f:
    client_events = f.read()

entities_in_mod = re.findall(r'public static final [^\n]*Supplier<[^>]*EntityType<([^>]+)>> ([A-Z0-9_]+) = ModRegistry\.ENTITY_TYPES\.register', mod_entities)

print("Entities in ModEntities.java:", len(entities_in_mod))

for entity_class, entity_name in entities_in_mod:
    print(f"{entity_name}: {entity_class}")
