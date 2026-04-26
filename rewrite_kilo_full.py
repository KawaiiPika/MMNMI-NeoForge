import os

# We will completely rewrite the Java source for Kilo using the modern approach but correctly mirroring attributes
# In 1.21.1, you can't use AbilityAttributeModifier. You need to use Vanilla AttributeModifiers, likely applied via NeoForge's methods or in startUsing/stopUsing.
# Looking at other abilities, how are modifiers applied?
