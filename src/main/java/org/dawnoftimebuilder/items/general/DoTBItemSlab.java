package org.dawnoftimebuilder.items.general;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 标准原版台阶物品：继承 {@link ItemSlab}。
 * 注意：DoTBBlockSlab 的 getVariantProperty() 返回 null（材质无变体），
 * 而原版 ItemSlab.onItemUse/canPlaceBlockOnSide 会无条件调用它导致崩溃，
 * 因此这里重写这两个方法，去掉 variant 依赖并保留原版合并/放置行为。
 */
public class DoTBItemSlab extends ItemSlab {

	protected final BlockSlab singleSlabField;
	protected final BlockSlab doubleSlabField;

	public DoTBItemSlab(Block block, BlockSlab singleSlab, BlockSlab doubleSlab) {
		super(block, singleSlab, doubleSlab);

		this.singleSlabField = singleSlab;
		this.doubleSlabField = doubleSlab;
		this.setTranslationKey(singleSlab.getTranslationKey());
		this.setRegistryName(singleSlab.getRegistryName());
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack itemstack = player.getHeldItem(hand);

		if (!itemstack.isEmpty() && player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
			IBlockState state = worldIn.getBlockState(pos);

			if (state.getBlock() == this.singleSlabField) {
				BlockSlab.EnumBlockHalf half = state.getValue(BlockSlab.HALF);

				if ((facing == EnumFacing.UP && half == BlockSlab.EnumBlockHalf.BOTTOM) || (facing == EnumFacing.DOWN && half == BlockSlab.EnumBlockHalf.TOP)) {
					this.mergeToDouble(worldIn, pos, itemstack, player);
					return EnumActionResult.SUCCESS;
				}
			}

			if (this.tryMerge(worldIn, pos.offset(facing), itemstack, player)) {
				return EnumActionResult.SUCCESS;
			}

			return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
		}

		return EnumActionResult.FAIL;
	}

	private boolean mergeToDouble(World worldIn, BlockPos pos, ItemStack stack, EntityPlayer player) {
		IBlockState doubleState = this.doubleSlabField.getDefaultState();
		AxisAlignedBB aabb = doubleState.getCollisionBoundingBox(worldIn, pos);

		if (aabb != Block.NULL_AABB && worldIn.checkNoEntityCollision(aabb.offset(pos)) && worldIn.setBlockState(pos, doubleState, 11)) {
			SoundType soundtype = this.doubleSlabField.getSoundType(doubleState, worldIn, pos, player);
			worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
			stack.shrink(1);

			if (player instanceof EntityPlayerMP) {
				CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos, stack);
			}

			return true;
		}

		return false;
	}

	private boolean tryMerge(World worldIn, BlockPos pos, ItemStack stack, EntityPlayer player) {
		IBlockState state = worldIn.getBlockState(pos);
		return state.getBlock() == this.singleSlabField && this.mergeToDouble(worldIn, pos, stack, player);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
		BlockPos blockpos = pos;
		IBlockState state = worldIn.getBlockState(pos);

		if (state.getBlock() == this.singleSlabField) {
			boolean isTop = state.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP;

			if ((side == EnumFacing.UP && !isTop) || (side == EnumFacing.DOWN && isTop)) {
				return true;
			}
		}

		pos = pos.offset(side);
		IBlockState offsetState = worldIn.getBlockState(pos);
		return offsetState.getBlock() == this.singleSlabField ? true : super.canPlaceBlockOnSide(worldIn, blockpos, side, player, stack);
	}
}
