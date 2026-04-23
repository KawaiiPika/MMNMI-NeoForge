#!/bin/bash
# Find class files in src/main/java corresponding to these classes and see if we should add IEntityWithComplexSpawn
for f in "NuProjectileEntity" "OPEntity" "SphereEntity" "ThrownSpearEntity" "OPBossEntity"; do
    find src/main/java -name "$f.java"
done
