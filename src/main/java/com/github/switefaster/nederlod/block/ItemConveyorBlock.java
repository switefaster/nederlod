package com.github.switefaster.nederlod.block;

import com.github.switefaster.nederlod.tileentity.ItemConveyorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RailShape;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemConveyorBlock extends ContainerBlock {
    public static final EnumProperty<RailShape> SHAPE = BlockStateProperties.RAIL_SHAPE_STRAIGHT;
    
    public ItemConveyorBlock() {
        super(Properties.create(Material.IRON).notSolid());
        this.setDefaultState(this.getStateContainer().getBaseState().with(SHAPE, RailShape.NORTH_SOUTH));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull IBlockReader worldIn) {
        return new ItemConveyorTileEntity();
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public ActionResultType onBlockActivated(@Nonnull BlockState state, World worldIn, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand handIn, @Nonnull BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity != null) {
            }
        }
        return ActionResultType.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public BlockRenderType getRenderType(@Nonnull BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SHAPE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@Nonnull BlockItemUseContext context) {
        return this.getDefaultState().with(SHAPE, directionToShape(context.getPlacementHorizontalFacing(), context.getWorld(), context.getPos()));
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isValidPosition(@Nonnull BlockState state, @Nonnull IWorldReader worldIn, @Nonnull BlockPos pos) {
        return worldIn.getBlockState(pos.down()).isSolid();
    }

    @Nonnull
    private RailShape directionToShape(@Nonnull Direction direction, @Nonnull World world, @Nonnull BlockPos pos) {
        BlockPos forwardPos = pos.offset(direction);
        BlockPos backwardPos = pos.offset(direction.getOpposite());
        boolean forwardBlock = world.getBlockState(forwardPos).getBlock() instanceof ItemConveyorBlock;
        boolean backwardBlock = world.getBlockState(backwardPos).getBlock() instanceof ItemConveyorBlock;
        boolean backwardUp = world.getBlockState(backwardPos.up()).getBlock() instanceof ItemConveyorBlock;
        boolean forwardDown = world.getBlockState(forwardPos.down()).getBlock() instanceof ItemConveyorBlock;
        if (forwardBlock && backwardBlock) {
            return (direction == Direction.NORTH || direction == Direction.SOUTH) ? RailShape.NORTH_SOUTH : RailShape.EAST_WEST;
        } else {
            if (backwardUp || forwardDown) {
                direction = direction.getOpposite();
            }
            switch (direction) {
                case EAST:
                    return RailShape.ASCENDING_EAST;
                case WEST:
                    return RailShape.ASCENDING_WEST;
                case NORTH:
                    return RailShape.ASCENDING_NORTH;
                case SOUTH:
                    return RailShape.ASCENDING_SOUTH;
                default:
                    throw new IllegalArgumentException("Unexpected direction: " + direction.getName());
            }
        }
    }
}
