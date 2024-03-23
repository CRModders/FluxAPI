package me.zombii.mod.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Queue;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.world.BlockPosition;
import finalforeach.cosmicreach.world.BlockSetter;
import finalforeach.cosmicreach.world.World;
import finalforeach.cosmicreach.world.blocks.Block;
import finalforeach.cosmicreach.world.blocks.BlockState;
import finalforeach.cosmicreach.world.entities.Player;
import io.github.crmodders.flux.FluxAPI;
import io.github.crmodders.flux.api.blocks.ModBlock;
import io.github.crmodders.flux.api.blocks.WorkingBlock;
import io.github.crmodders.flux.api.generators.data.BlockSupplier;
import io.github.crmodders.flux.menus.ConfigViewMenu;
import io.github.crmodders.flux.util.BlockPositionUtil;

public class TestBlock extends ModBlock implements WorkingBlock {

    public Block block;

    public TestBlock() {
        super();
    }
    public TestBlock(BlockSupplier supplier) {
        super(supplier);
    }

    @Override
    public void onInteract(World world, Player player, BlockState blockState, BlockPosition position) {
    }

    @Override
    public void onPlace(World world, Player player, BlockState blockState, BlockPosition position) {
    }

    @Override
    public void onBreak(World world, Player player, BlockState blockState, BlockPosition position) {
    }


}
