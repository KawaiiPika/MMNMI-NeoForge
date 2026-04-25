# The user gave me the exact SNBT but without the I; prefix, and maybe that was correct.
# Wait, let's use the exact user provided snbt!
# Because the `[I;` might have thrown an exception during parsing in Minecraft, which is swallowed, and then it reports "Missing test structure" because it couldn't parse it!!
with open('src/main/resources/data/mineminenomi/gametest/structures/examplegametest.empty.snbt', 'w') as f:
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
