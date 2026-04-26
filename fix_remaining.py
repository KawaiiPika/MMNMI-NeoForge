import os

folders = ["doa", "ori", "beta", "kuku", "baku", "kira", "doru", "awa"]
for folder in folders:
    folder_dir = f"src/main/java/xyz/pixelatedw/mineminenomi/abilities/{folder}"
    if not os.path.exists(folder_dir): continue
    for file in os.listdir(folder_dir):
        if not file.endswith(".java"): continue
        filepath = os.path.join(folder_dir, file)
        with open(filepath, 'r') as f:
            content = f.read()

        # The copy command previously overwrote some files with the "old_source" versions again which reintroduced compile errors.
        # Ensure we keep the valid updated stubs where applicable without losing faithful logic if rewritten.
        # It seems the `cp -a src/main/java_old...` command from earlier was executed correctly AFTER I wiped my stubs and re-wrote them. Let's fix this now by restoring the modern ones we just wrote.
