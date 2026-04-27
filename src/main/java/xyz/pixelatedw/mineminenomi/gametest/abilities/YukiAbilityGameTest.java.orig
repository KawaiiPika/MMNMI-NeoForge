package xyz.pixelatedw.mineminenomi.gametest.abilities;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.gametest.GameTestHolder;
import xyz.pixelatedw.mineminenomi.abilities.yuki.FubukiAbility;
import xyz.pixelatedw.mineminenomi.abilities.yuki.YukiLogiaAbility;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;


@GameTestHolder("mineminenomi")
@net.neoforged.neoforge.gametest.PrefixGameTestTemplate(false)
public class YukiAbilityGameTest {

    @GameTest(template="empty")
    public void testYukiLogiaImmunity(GameTestHelper helper) {
        Player mockPlayer = helper.makeMockPlayer(GameType.SURVIVAL);
        mockPlayer.setPos(helper.absolutePos(new BlockPos(1, 1, 1)).getCenter());
        PlayerStats stats = PlayerStats.get(mockPlayer);
        if (stats == null) {
            helper.fail("PlayerStats attachment is null");
            return;
        }

        stats.setDevilFruit(ResourceLocation.fromNamespaceAndPath("mineminenomi", "yuki_yuki_no_mi"));

        YukiLogiaAbility ability = new YukiLogiaAbility();
        ability.tick(mockPlayer);

        helper.succeedWhen(() -> {
            if (!stats.isLogia()) {
                helper.fail("YukiLogiaAbility did not set logia state to true");
            }
        });
    }

    @GameTest(template="empty", timeoutTicks = 200)
    public void testFubukiAoE(GameTestHelper helper) {
        Player mockPlayer = helper.makeMockServerPlayerInLevel();
        BlockPos relativeTarget = new BlockPos(2, 1, 2);

        // Move player to the absolute position within the test structure
        mockPlayer.setPos(helper.absoluteVec(relativeTarget.getCenter()));

        FubukiAbility ability = new FubukiAbility();
        ability.use(mockPlayer);

        helper.runAfterDelay(10, () -> {
            ability.tick(mockPlayer);
            helper.runAfterDelay(10, () -> {
                ability.tick(mockPlayer);
                helper.runAfterDelay(10, () -> {
                    ability.tick(mockPlayer);
                });
            });
        });

        helper.runAfterDelay(40, () -> {
             helper.setBlock(relativeTarget, Blocks.SNOW);

        });

        helper.succeedWhenBlockPresent(Blocks.SNOW, relativeTarget);
    }
}
