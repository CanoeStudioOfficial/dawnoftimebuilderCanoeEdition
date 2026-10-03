package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.blocks.compatibility.BlockPath;

public class DoTBBlockPath extends DoTBBlock {
	private static final PropertyBool FULL = PropertyBool.create("full");

	private static final AxisAlignedBB PATH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

	public DoTBBlockPath(String name) {
		super(name, Material.GRASS, 0.5F, SoundType.GROUND);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FULL, Boolean.FALSE));
		this.setLightOpacity(255);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(FULL, this.isFull(worldIn, pos));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	private boolean isFull(IBlockAccess worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.up()).getMaterial().isSolid();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return this.getActualState(state, source, pos).getValue(FULL) ? FULL_BLOCK_AABB : PATH_AABB;
	}

	/**
	 * Get the MapColor for this Block and the given BlockState
	 */
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return MapColor.GRAY;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state){
		return state.getValue(FULL);
	}

	@Override
	public boolean isFullCube(IBlockState state){
		return state.getValue(FULL);
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess worldIn, BlockPos pos, EnumFacing face) {
		return this.getActualState(state, worldIn, pos).getValue(FULL);
	}

	@SuppressWarnings("deprecation")
	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		switch (side) {
			case UP:
				// The full variant reaches the block boundary and must let a solid
				// block above cull its top face.  A normal path is one pixel short,
				// so its top face remains visible just like vanilla grass path.
				return !this.getActualState(blockState, blockAccess, pos).getValue(FULL)
						|| super.shouldSideBeRendered(blockState, blockAccess, pos, side);
			case NORTH:
			case SOUTH:
			case WEST:
			case EAST:
				BlockPos neighbourPos = pos.offset(side);
				IBlockState neighbourState = blockAccess.getBlockState(neighbourPos);
				Block neighbour = neighbourState.getBlock();
				IBlockState actualNeighbour = neighbourState.getActualState(blockAccess, neighbourPos);
				return !actualNeighbour.doesSideBlockRendering(blockAccess, neighbourPos, side.getOpposite())
						&& neighbour != Blocks.FARMLAND
						&& neighbour != Blocks.GRASS_PATH
						&& !(neighbour instanceof BlockPath)
						&& !(neighbour instanceof DoTBBlockPath);
			default:
				return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
		}
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return this.getActualState(state, worldIn, pos).getValue(FULL)
				? BlockFaceShape.SOLID
				: face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}

	@Override
	public boolean isSideSolid(IBlockState state, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
		// A normal path is only a solid support surface from below.  Treating
		// its vertical sides as solid makes adjacent blocks attach to the
		// 15-pixel path wall and also changes face/lighting decisions.  The
		// dynamic full state is a real cube, so it is solid on every side.
		return this.getActualState(state, worldIn, pos).getValue(FULL) || side == EnumFacing.DOWN;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FULL);
	}
}
