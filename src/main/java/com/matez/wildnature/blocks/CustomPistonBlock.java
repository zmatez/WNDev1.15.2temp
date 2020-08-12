package com.matez.wildnature.blocks;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;

import com.matez.wildnature.Main;
import com.matez.wildnature.gui.tileEntities.CustomPistonTileEntity;
import com.matez.wildnature.other.CustomPistonBlockStructureHelper;
import com.matez.wildnature.sounds.SoundRegistry;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.PistonType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CustomPistonBlock extends DirectionalBlock {
    public static final BooleanProperty EXTENDED = BlockStateProperties.EXTENDED;
    protected static final VoxelShape PISTON_BASE_EAST_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 12.0D, 16.0D, 16.0D);
    protected static final VoxelShape PISTON_BASE_WEST_AABB = Block.makeCuboidShape(4.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape PISTON_BASE_SOUTH_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 12.0D);
    protected static final VoxelShape PISTON_BASE_NORTH_AABB = Block.makeCuboidShape(0.0D, 0.0D, 4.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape PISTON_BASE_UP_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);
    protected static final VoxelShape PISTON_BASE_DOWN_AABB = Block.makeCuboidShape(0.0D, 4.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private final boolean isSticky;
    private static Block piston,head,moving;
    private ResourceLocation loc,hea,movi;

    public CustomPistonBlock(boolean sticky, Block.Properties properties, ResourceLocation location, ResourceLocation head, ResourceLocation moving) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(EXTENDED, Boolean.valueOf(false)));
        this.isSticky = sticky;
        this.setRegistryName(location);
        loc=location;
        hea=head;
        movi=moving;

    }

    public boolean causesSuffocation(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return !state.get(EXTENDED);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(EXTENDED)) {
            switch((Direction)state.get(FACING)) {
                case DOWN:
                    return PISTON_BASE_DOWN_AABB;
                case UP:
                default:
                    return PISTON_BASE_UP_AABB;
                case NORTH:
                    return PISTON_BASE_NORTH_AABB;
                case SOUTH:
                    return PISTON_BASE_SOUTH_AABB;
                case WEST:
                    return PISTON_BASE_WEST_AABB;
                case EAST:
                    return PISTON_BASE_EAST_AABB;
            }
        } else {
            return VoxelShapes.fullCube();
        }
    }

    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        this.piston= Main.getBlockByID(loc+"");
        this.head= Main.getBlockByID(hea+"");
        this.moving= Main.getBlockByID(movi+"");
        if (!worldIn.isRemote) {
            this.checkForMove(worldIn, pos, state);
        }
        

    }

    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!worldIn.isRemote) {
            this.checkForMove(worldIn, pos, state);
        }

    }

    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (oldState.getBlock() != state.getBlock()) {
            if (!worldIn.isRemote && worldIn.getTileEntity(pos) == null) {
                this.checkForMove(worldIn, pos, state);
            }

        }
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getNearestLookingDirection().getOpposite()).with(EXTENDED, Boolean.valueOf(false));
    }

    private void checkForMove(World worldIn, BlockPos pos, BlockState state) {
        Direction direction = state.get(FACING);
        boolean flag = this.shouldBeExtended(worldIn, pos, direction);
        if (flag && !state.get(EXTENDED)) {
            if ((new CustomPistonBlockStructureHelper(worldIn, pos, direction, true)).canMove()) {
                worldIn.addBlockEvent(pos, this, 0, direction.getIndex());
            }
        } else if (!flag && state.get(EXTENDED)) {
            BlockPos blockpos = pos.offset(direction, 2);
            BlockState blockstate = worldIn.getBlockState(blockpos);
            int i = 1;
            if (blockstate.getBlock() == moving && blockstate.get(FACING) == direction) {
                TileEntity tileentity = worldIn.getTileEntity(blockpos);
                if (tileentity instanceof CustomPistonTileEntity) {
                    CustomPistonTileEntity pistontileentity = (CustomPistonTileEntity)tileentity;
                    if (pistontileentity.isExtending() && (pistontileentity.getProgress(0.0F) < 0.5F || worldIn.getGameTime() == pistontileentity.getLastTicked() || ((ServerWorld)worldIn).isInsideTick())) {
                        i = 2;
                    }
                }
            }

            worldIn.addBlockEvent(pos, this, i, direction.getIndex());
        }

    }

    private boolean shouldBeExtended(World worldIn, BlockPos pos, Direction facing) {
        for(Direction direction : Direction.values()) {
            if (direction != facing && worldIn.isSidePowered(pos.offset(direction), direction)) {
                return true;
            }
        }

        if (worldIn.isSidePowered(pos, Direction.DOWN)) {
            return true;
        } else {
            BlockPos blockpos = pos.up();

            for(Direction direction1 : Direction.values()) {
                if (direction1 != Direction.DOWN && worldIn.isSidePowered(blockpos.offset(direction1), direction1)) {
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * Called on server when World#addBlockEvent is called. If server returns true, then also called on the client. On
     * the Server, this may perform additional changes to the world, like pistons replacing the block with an extended
     * base. On the client, the update may involve replacing tile entities or effects such as sounds or particles
     * @deprecated call via {@link #(World,BlockPos,int,int)} whenever possible.
     * Implementing/overriding is fine.
     */
    public boolean eventReceived(BlockState state, World worldIn, BlockPos pos, int id, int param) {
        Direction direction = state.get(FACING);
        if (!worldIn.isRemote) {
            boolean flag = this.shouldBeExtended(worldIn, pos, direction);
            if (flag && (id == 1 || id == 2)) {
                worldIn.setBlockState(pos, state.with(EXTENDED, Boolean.valueOf(true)), 2);
                return false;
            }

            if (!flag && id == 0) {
                return false;
            }
        }

        if (id == 0) {
            if(net.minecraftforge.event.ForgeEventFactory.onPistonMovePre(worldIn, pos, direction, true)) return false;
            if (!this.doMove(worldIn, pos, direction, true)) {
                return false;
            }

            worldIn.setBlockState(pos, state.with(EXTENDED, Boolean.valueOf(true)), 67);
            worldIn.playSound((PlayerEntity)null, pos, SoundRegistry.PISTON_2s_OPEN, SoundCategory.BLOCKS, 0.5F, worldIn.rand.nextFloat() * 0.25F + 0.6F);
        } else if (id == 1 || id == 2) {
            if(net.minecraftforge.event.ForgeEventFactory.onPistonMovePre(worldIn, pos, direction, false)) return false;
            TileEntity tileentity1 = worldIn.getTileEntity(pos.offset(direction));
            if (tileentity1 instanceof CustomPistonTileEntity) {
                System.out.println("somet1");
                ((CustomPistonTileEntity)tileentity1).clearPistonTileEntity();
            }

            worldIn.setBlockState(pos, moving.getDefaultState().with(CustomMovingPistonBlock.FACING, direction).with(CustomMovingPistonBlock.TYPE, this.isSticky ? PistonType.STICKY : PistonType.DEFAULT), 3);
            worldIn.setTileEntity(pos, CustomMovingPistonBlock.createTilePiston(this.getDefaultState().with(FACING, Direction.byIndex(param & 7)), direction, false, true));
            if (this.isSticky) {
                BlockPos blockpos = pos.add(direction.getXOffset() * 2, direction.getYOffset() * 2, direction.getZOffset() * 2);
                BlockState blockstate = worldIn.getBlockState(blockpos);
                Block block = blockstate.getBlock();
                boolean flag1 = false;
                if (block == moving) {
                    TileEntity tileentity = worldIn.getTileEntity(blockpos);
                    if (tileentity instanceof CustomPistonTileEntity) {
                        CustomPistonTileEntity pistontileentity = (CustomPistonTileEntity)tileentity;
                        if (pistontileentity.getFacing() == direction && pistontileentity.isExtending()) {
                            System.out.println("somet2");
                            pistontileentity.clearPistonTileEntity();
                            flag1 = true;
                        }
                    }
                }

                if (!flag1) {
                    if (id != 1 || blockstate.isAir(worldIn, blockpos) || !canPush(blockstate, worldIn, blockpos, direction.getOpposite(), false, direction) || blockstate.getPushReaction() != PushReaction.NORMAL && block != piston) {
                        worldIn.removeBlock(pos.offset(direction), false);
                    } else {
                        this.doMove(worldIn, pos, direction, false);
                    }
                }
            } else {
                worldIn.removeBlock(pos.offset(direction), false);
            }

            worldIn.playSound((PlayerEntity)null, pos, SoundRegistry.PISTON_2s_CLOSE, SoundCategory.BLOCKS, 0.5F, worldIn.rand.nextFloat() * 0.15F + 0.6F);
        }

        net.minecraftforge.event.ForgeEventFactory.onPistonMovePost(worldIn, pos, direction, (id == 0));
        return true;
    }

    /**
     * Checks if the piston can push the given BlockState.
     */
    public static boolean canPush(BlockState blockStateIn, World worldIn, BlockPos pos, Direction facing, boolean destroyBlocks, Direction p_185646_5_) {
        Block block = blockStateIn.getBlock();
        if (block == Blocks.OBSIDIAN) {
            return false;
        } else if (!worldIn.getWorldBorder().contains(pos)) {
            return false;
        } else if (pos.getY() >= 0 && (facing != Direction.DOWN || pos.getY() != 0)) {
            if (pos.getY() <= worldIn.getHeight() - 1 && (facing != Direction.UP || pos.getY() != worldIn.getHeight() - 1)) {
                if (block != piston) {
                    if (blockStateIn.getBlockHardness(worldIn, pos) == -1.0F) {
                        return false;
                    }

                    switch(blockStateIn.getPushReaction()) {
                        case BLOCK:
                            return false;
                        case DESTROY:
                            return destroyBlocks;
                        case PUSH_ONLY:
                            return facing == p_185646_5_;
                    }
                } else if (blockStateIn.get(EXTENDED)) {
                    return false;
                }

                return !blockStateIn.hasTileEntity();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean doMove(World worldIn, BlockPos pos, Direction directionIn, boolean extending) {
        BlockPos blockpos = pos.offset(directionIn);
        if (!extending && worldIn.getBlockState(blockpos).getBlock() == head) {
            worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 20);
        }

        CustomPistonBlockStructureHelper CustomPistonBlockStructureHelper = new CustomPistonBlockStructureHelper(worldIn, pos, directionIn, extending);
        if (!CustomPistonBlockStructureHelper.canMove()) {
            return false;
        } else {
            List<BlockPos> list = CustomPistonBlockStructureHelper.getBlocksToMove();
            List<BlockState> list1 = Lists.newArrayList();

            for(int i = 0; i < list.size(); ++i) {
                BlockPos blockpos1 = list.get(i);
                list1.add(worldIn.getBlockState(blockpos1));
            }

            List<BlockPos> list2 = CustomPistonBlockStructureHelper.getBlocksToDestroy();
            int k = list.size() + list2.size();
            BlockState[] ablockstate = new BlockState[k];
            Direction direction = extending ? directionIn : directionIn.getOpposite();
            Set<BlockPos> set = Sets.newHashSet(list);

            for(int j = list2.size() - 1; j >= 0; --j) {
                BlockPos blockpos2 = list2.get(j);
                BlockState blockstate = worldIn.getBlockState(blockpos2);
                TileEntity tileentity = blockstate.hasTileEntity() ? worldIn.getTileEntity(blockpos2) : null;
                spawnDrops(blockstate, worldIn, blockpos2, tileentity);
                worldIn.setBlockState(blockpos2, Blocks.AIR.getDefaultState(), 18);
                --k;
                ablockstate[k] = blockstate;
            }

            for(int l = list.size() - 1; l >= 0; --l) {
                BlockPos blockpos3 = list.get(l);
                BlockState blockstate3 = worldIn.getBlockState(blockpos3);
                blockpos3 = blockpos3.offset(direction);
                set.remove(blockpos3);
                worldIn.setBlockState(blockpos3, moving.getDefaultState().with(FACING, directionIn), 68);
                worldIn.setTileEntity(blockpos3, CustomMovingPistonBlock.createTilePiston(list1.get(l), directionIn, extending, false));
                --k;
                ablockstate[k] = blockstate3;
            }

            if (extending) {
                PistonType pistontype = this.isSticky ? PistonType.STICKY : PistonType.DEFAULT;
                BlockState blockstate1 = head.getDefaultState().with(CustomPistonHeadBlock.FACING, directionIn).with(CustomPistonHeadBlock.TYPE, pistontype);
                BlockState blockstate4 = moving.getDefaultState().with(CustomMovingPistonBlock.FACING, directionIn).with(CustomMovingPistonBlock.TYPE, this.isSticky ? PistonType.STICKY : PistonType.DEFAULT);
                set.remove(blockpos);
                worldIn.setBlockState(blockpos, blockstate4, 68);
                worldIn.setTileEntity(blockpos, CustomMovingPistonBlock.createTilePiston(blockstate1, directionIn, true, true));
            }

            for(BlockPos blockpos4 : set) {
                worldIn.setBlockState(blockpos4, Blocks.AIR.getDefaultState(), 66);
            }

            for(int i1 = list2.size() - 1; i1 >= 0; --i1) {
                BlockState blockstate2 = ablockstate[k++];
                BlockPos blockpos5 = list2.get(i1);
                blockstate2.updateDiagonalNeighbors(worldIn, blockpos5, 2);
                worldIn.notifyNeighborsOfStateChange(blockpos5, blockstate2.getBlock());
            }

            for(int j1 = list.size() - 1; j1 >= 0; --j1) {
                worldIn.notifyNeighborsOfStateChange(list.get(j1), ablockstate[k++].getBlock());
            }

            if (extending) {
                worldIn.notifyNeighborsOfStateChange(blockpos, head);
            }

            return true;
        }
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     * @deprecated call via {@link #(Rotation)} whenever possible. Implementing/overriding is
     * fine.
     */
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    public BlockState rotate(BlockState state, net.minecraft.world.IWorld world, BlockPos pos, Rotation direction) {
        return state.get(EXTENDED) ? state : super.rotate(state, world, pos, direction);
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     * @deprecated call via {@link #(Mirror)} whenever possible. Implementing/overriding is fine.
     */
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, EXTENDED);
    }

    public boolean func_220074_n(BlockState state) {
        return state.get(EXTENDED);
    }

    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }
}