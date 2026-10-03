package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public abstract class DoTBBlockSlabPath extends DoTBBlockSlab {

	private static final PropertyBool FULL = PropertyBool.create("full");
	private static final AxisAlignedBB AABB_BOTTOM = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
	private static final AxisAlignedBB AABB_TOP = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);
	private static final AxisAlignedBB AABB_BOTTOM_PATH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.4375D, 1.0D);
	private static final AxisAlignedBB AABB_TOP_PATH = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 0.9375D, 1.0D);
	private static final AxisAlignedBB AABB_FULL_PATH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

	public DoTBBlockSlabPath(String name, Material materialIn, float hardness, SoundType sound) {
		super(name, materialIn, hardness, sound);

		this.setDefaultState(this.blockState.getBaseState()
				.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM)
				.withProperty(FULL, Boolean.FALSE)
				.withProperty(VARIANT, EnumSlabVariant.DEFAULT));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, HALF, FULL, VARIANT);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(FULL, this.isFull(worldIn, pos));
	}

	private boolean isFull(IBlockAccess worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.up()).getMaterial().isSolid();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		IBlockState actualState = this.getActualState(state, worldIn, pos);
		boolean full = actualState.getValue(FULL);
		if (this.isDouble()) return full ? FULL_BLOCK_AABB : AABB_FULL_PATH;
		switch (actualState.getValue(HALF)) {
			default:
			case BOTTOM:
				return full ? AABB_BOTTOM : AABB_BOTTOM_PATH;
			case TOP:
				return full ? AABB_TOP : AABB_TOP_PATH;
		}
	}

	/**
	 * A path slab is only a full cube when its double variant has a block above
	 * it.  BlockSlab assumes every double slab is opaque, which makes the
	 * 15-pixel path model hide neighbouring faces and produces visible seams.
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return this.isDouble() && state.getValue(FULL);
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return this.isDouble() && state.getValue(FULL);
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess worldIn, BlockPos pos, EnumFacing face) {
		IBlockState actualState = this.getActualState(state, worldIn, pos);
		if (this.isDouble()) return actualState.getValue(FULL) || face == EnumFacing.DOWN;

		BlockSlab.EnumBlockHalf half = actualState.getValue(HALF);
		return half == BlockSlab.EnumBlockHalf.TOP && actualState.getValue(FULL) && face == EnumFacing.UP
				|| half == BlockSlab.EnumBlockHalf.BOTTOM && face == EnumFacing.DOWN;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		IBlockState actualState = this.getActualState(state, worldIn, pos);
		if (this.isDouble()) {
			return actualState.getValue(FULL) || face == EnumFacing.DOWN
					? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
		}

		BlockSlab.EnumBlockHalf half = actualState.getValue(HALF);
		return half == BlockSlab.EnumBlockHalf.TOP && actualState.getValue(FULL) && face == EnumFacing.UP
				|| half == BlockSlab.EnumBlockHalf.BOTTOM && face == EnumFacing.DOWN
				? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}

	@Override
	public boolean isSideSolid(IBlockState state, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
		IBlockState actualState = this.getActualState(state, worldIn, pos);
		if (this.isDouble()) return actualState.getValue(FULL) || side == EnumFacing.DOWN;

		BlockSlab.EnumBlockHalf half = actualState.getValue(HALF);
		return half == BlockSlab.EnumBlockHalf.TOP && actualState.getValue(FULL) && side == EnumFacing.UP
				|| half == BlockSlab.EnumBlockHalf.BOTTOM && side == EnumFacing.DOWN;
	}
}
