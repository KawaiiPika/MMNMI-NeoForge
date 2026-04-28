#!/bin/bash
git restore src/main/java/xyz/pixelatedw/mineminenomi/init/ModEntities.java

# Remove all those added entities that were crashing it since the brackets were wrong previously
# Wait, actually ModEntities.java was crashing on "unnamed class should not have package declaration" because it had a bad closing brace in the middle of the file. Let's fix ModEntities.java by appending properly inside the `ModEntities` class.

# Restore it entirely
git checkout src/main/java/xyz/pixelatedw/mineminenomi/init/ModEntities.java
