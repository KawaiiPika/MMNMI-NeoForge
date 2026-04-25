import os
import shutil

for base in ['src/main/resources/data/mineminenomi/gametest/structures/', 'src/test/resources/data/mineminenomi/gametest/structures/', 'src/main/resources/data/mineminenomi/structure/', 'src/test/resources/data/mineminenomi/structure/']:
    os.makedirs(base, exist_ok=True)
    with open(os.path.join(base, 'abilitygametest.empty.snbt'), 'w') as f:
        f.write('''{
    DataVersion: 3953,
    size: [1, 1, 1],
    data: [
        {pos: [0, 0, 0], state: "minecraft:air"}
    ],
    entities: [],
    palette: [
        "minecraft:air"
    ]
}''')
    with open(os.path.join(base, 'examplegametest.empty.snbt'), 'w') as f:
        f.write('''{
    DataVersion: 3953,
    size: [1, 1, 1],
    data: [
        {pos: [0, 0, 0], state: "minecraft:air"}
    ],
    entities: [],
    palette: [
        "minecraft:air"
    ]
}''')
