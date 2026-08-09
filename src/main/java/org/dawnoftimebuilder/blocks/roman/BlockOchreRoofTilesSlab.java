package org.dawnoftimebuilder.blocks.roman;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dawnoftimebuilder.blocks.DoTBBlocks;
import org.dawnoftimebuilder.blocks.general.DoTBBlockSlab;
import org.dawnoftimebuilder.items.roman.ItemOchreRoofTilesSlab;

import static net.minecraft.block.Block.FULL_BLOCK_AABB;

public class BlockOchreRoofTilesSlab extends DoTBBlockSlab {

	private final boolean isDouble;

	public BlockOchreRoofTilesSlab(String name, boolean isDouble) {
		super(name, Material.ROCK, 1.5F, SoundType.STONE);
		this.isDouble = isDouble;
	}

	@Override
	public boolean isDouble() {
		return this.isDouble;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack itemstack = playerIn.getHeldItem(hand);
		BlockSlab.EnumBlockHalf half = state.getValue(HALF);
		boolean correctFace = facing == EnumFacing.UP && half == BlockSlab.EnumBlockHalf.BOTTOM
				|| facing == EnumFacing.DOWN && half == BlockSlab.EnumBlockHalf.TOP;

		if (!this.isDouble() && correctFace && !itemstack.isEmpty()
				&& playerIn.canPlayerEdit(pos.offset(facing), facing, itemstack)
				&& itemstack.getItem() == Item.getItemFromBlock(Blocks.STONE_SLAB)
				&& itemstack.getMetadata() == 1) {
			IBlockState madeState = DoTBBlocks.ochre_roof_tiles_merged.getDefaultState();
			if (worldIn.checkNoEntityCollision(FULL_BLOCK_AABB.offset(pos)) && worldIn.setBlockState(pos, madeState, 11)) {
				SoundType soundtype = DoTBBlocks.ochre_roof_tiles_merged.getSoundType(madeState, worldIn, pos, playerIn);
				worldIn.playSound(playerIn, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				if (!playerIn.isCreative()) itemstack.shrink(1);
				if (playerIn instanceof EntityPlayerMP) {
					CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) playerIn, pos, itemstack);
				}
			}
			return true;
		}

		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public Item getCustomItemBlock() {
		if (this.isDouble()) return null;
		return new ItemOchreRoofTilesSlab(this, this, this.getDoubleSlab());
	}
}
