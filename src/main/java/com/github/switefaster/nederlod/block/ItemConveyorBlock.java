package com.github.switefaster.nederlod.block;

import com.github.switefaster.nederlod.capability.NederlodCapabilities;
import com.github.switefaster.nederlod.tileentity.ItemConveyorTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;

public class ItemConveyorBlock extends ContainerBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    
    public ItemConveyorBlock() {
        super(Properties.create(Material.IRON).notSolid());
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull IBlockReader worldIn) {
        return new ItemConveyorTileEntity();
    }

    @SuppressWarnings({"NullableProblems", "deprecation"})
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity != null) {
                tileEntity.getCapability(NederlodCapabilities.SYNC_PROGRESSIVE).ifPresent(conveyed -> conveyed.put(Collections.singletonList(player.getHeldItem(handIn))));
            }
        }
        return ActionResultType.SUCCESS;
    }
}
