package org.dawnoftimebuilder.items.roman;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.blocks.DoTBBlocks;
import org.dawnoftimebuilder.items.general.DoTBItemSlab;

import static net.minecraft.block.Block.FULL_BLOCK_AABB;

public class ItemOchreRoofTilesSlab extends DoTBItemSlab {

	public ItemOchreRoofTilesSlab(Block block, BlockSlab singleSlab, BlockSlab doubleSlab) {
		super(block, singleSlab, doubleSlab);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack itemstack = player.getHeldItem(hand);
		if (!itemstack.isEmpty() && player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
			IBlockState state = worldIn.getBlockState(pos);
			if (isSandstoneSlab(state) && canMergeOnFace(state, facing)) {
				mergeToMerged(worldIn, pos, itemstack, player);
				return EnumActionResult.SUCCESS;
			}
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
		IBlockState state = worldIn.getBlockState(pos);
		return isSandstoneSlab(state) && canMergeOnFace(state, side)
				|| super.canPlaceBlockOnSide(worldIn, pos, side, player, stack);
	}

	private boolean isSandstoneSlab(IBlockState state) {
		return state.getBlock() == Blocks.STONE_SLAB
				&& !((BlockSlab) state.getBlock()).isDouble()
				&& state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.SAND;
	}

	private boolean canMergeOnFace(IBlockState state, EnumFacing facing) {
		BlockSlab.EnumBlockHalf half = state.getValue(BlockSlab.HALF);
		return facing == EnumFacing.UP && half == BlockSlab.EnumBlockHalf.BOTTOM
				|| facing == EnumFacing.DOWN && half == BlockSlab.EnumBlockHalf.TOP;
	}

	private void mergeToMerged(World worldIn, BlockPos pos, ItemStack stack, EntityPlayer player) {
		IBlockState madeState = DoTBBlocks.ochre_roof_tiles_merged.getDefaultState();
		if (worldIn.checkNoEntityCollision(FULL_BLOCK_AABB.offset(pos)) && worldIn.setBlockState(pos, madeState, 11)) {
			SoundType soundtype = DoTBBlocks.ochre_roof_tiles_merged.getSoundType(madeState, worldIn, pos, player);
			worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
			if (!player.isCreative()) stack.shrink(1);

			if (player instanceof EntityPlayerMP) {
				CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos, stack);
			}
		}
	}
}
