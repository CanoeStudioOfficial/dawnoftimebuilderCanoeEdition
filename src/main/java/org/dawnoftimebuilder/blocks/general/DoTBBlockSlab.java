package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.dawnoftimebuilder.blocks.IBlockCustomItem;
import org.dawnoftimebuilder.items.general.DoTBItemSlab;

import java.util.Random;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

/**
 * 标准原版式台阶（BlockSlab）。每个材质注册 half/double 两个方块：
 * - half：放置/合成/创造栏可见，物品由 {@link DoTBItemSlab}（原版 ItemSlab）处理双击合成；
 * - double：仅用于世界中的完整台阶，掉落时还原为 2 个 half 物品。
 */
public abstract class DoTBBlockSlab extends BlockSlab implements IBlockCustomItem {

	private BlockSlab singleSlab;
	private BlockSlab doubleSlab;

	public DoTBBlockSlab(String name, Material materialIn, float hardness, SoundType sound) {
		super(materialIn);

		this.setRegistryName(MOD_ID, name);
		this.setTranslationKey(MOD_ID + "." + name);
		this.setCreativeTab(DOTB_TAB);
		this.setHardness(hardness);
		this.setSoundType(sound);
		this.useNeighborBrightness = true;

		this.setDefaultState(this.blockState.getBaseState().withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM));
	}

	/** 设置与之配对的 half/double 方块（half 指向自身与 double；double 指向 half 与自身）。 */
	public void setSlabs(BlockSlab singleSlab, BlockSlab doubleSlab) {
		this.singleSlab = singleSlab;
		this.doubleSlab = doubleSlab;
	}

	public BlockSlab getDoubleSlab() {
		return this.doubleSlab;
	}

	@Override
	public IProperty<?> getVariantProperty() {
		return null;
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) {
		return null;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, HALF);
	}

	@Override
	public String getTranslationKey(int meta) {
		return super.getTranslationKey();
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		if (this.isDouble()) return 0;
		return state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP ? 8 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		if (this.isDouble()) return this.getDefaultState();
		return this.getDefaultState().withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
	}

	@Override
	public int damageDropped(IBlockState state) {
		return 0;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if (this.isDouble()) return Item.getItemFromBlock(this.singleSlab);
		return super.getItemDropped(state, rand, fortune);
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		if (!this.isDouble()) items.add(new ItemStack(this, 1, 0));
	}

	@Override
	public Item getCustomItemBlock() {
		if (this.isDouble()) return null;
		return new DoTBItemSlab(this, this, this.doubleSlab);
	}

	@SuppressWarnings("unchecked")
	public <T extends DoTBBlockSlab> T setBurnable() {
		Blocks.FIRE.setFireInfo(this, 5, 20);
		return (T) this;
	}
}
