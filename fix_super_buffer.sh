#!/bin/bash
sed -i 's/super.writeSpawnData(buffer);//g' src/main/java/xyz/pixelatedw/mineminenomi/entities/mobs/worldgov/CelestialDragonEntity.java
sed -i 's/super.readSpawnData(buffer);//g' src/main/java/xyz/pixelatedw/mineminenomi/entities/mobs/worldgov/CelestialDragonEntity.java
