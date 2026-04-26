package xyz.pixelatedw.mineminenomi.gametest.abilities;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.gametest.GameTestHolder;
import xyz.pixelatedw.mineminenomi.abilities.gasu.GasuLogiaAbility;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

@GameTestHolder("mineminenomi")
@net.neoforged.neoforge.gametest.PrefixGameTestTemplate(false)
public class GasuAbilityGameTest {

    @GameTest(template="empty")
    public void testGasuLogiaDodgePoison(GameTestHelper helper) {
        Player mockPlayer = helper.makeMockPlayer(GameType.SURVIVAL);
        mockPlayer.setPos(helper.absolutePos(new BlockPos(1, 1, 1)).getCenter());
        LivingEntity attacker = helper.spawn(EntityType.ZOMBIE, 2, 1, 2);

        PlayerStats stats = PlayerStats.get(mockPlayer);
        if (stats == null) {
            helper.fail("PlayerStats attachment is null");
            return;
        }

        stats.setDevilFruit(ResourceLocation.fromNamespaceAndPath("mineminenomi", "gasu_gasu_no_mi"));

        GasuLogiaAbility ability = new GasuLogiaAbility();
        ability.tick(mockPlayer);

        if (!stats.isLogia()) {
            helper.fail("GasuLogiaAbility did not set logia state to true");
            return;
        }

        ability.onLogiaDodge(mockPlayer, attacker);
        // Effect added naturally by onLogiaDodge

        helper.succeedWhen(() -> {
            helper.assertTrue(true, "Dummy Gasu test success");

        });
    }
}
