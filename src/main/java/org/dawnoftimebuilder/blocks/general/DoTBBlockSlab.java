package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSlab;
import net.minecraft.util.NonNullList;
import net.minecraft.util.IStringSerializable;
import org.dawnoftimebuilder.blocks.IBlockCustomItem;

import java.util.Random;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

/**
 * Standard {@link BlockSlab} implementation. Each material has one single
 * slab block and one double slab block; vanilla handles placement and item
 * behaviour.
 *
 * Vanilla {@link ItemSlab} requires a non-null variant property even when a
 * slab has no material variants. The single-value property below supplies the
 * required value and is ignored by the client state mapper, so it never
 * changes the blockstate/model keys.
 */
public abstract class DoTBBlockSlab extends BlockSlab implements IBlockCustomItem {

	public static final PropertyEnum<EnumSlabVariant> VARIANT = PropertyEnum.create("variant", EnumSlabVariant.class);

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

		this.setDefaultState(this.blockState.getBaseState()
				.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM)
				.withProperty(VARIANT, EnumSlabVariant.DEFAULT));
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
		return VARIANT;
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) {
		return EnumSlabVariant.DEFAULT;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, HALF, VARIANT);
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

		ItemSlab item = new ItemSlab(this, this, this.doubleSlab);
		item.setTranslationKey(this.getTranslationKey());
		item.setRegistryName(this.getRegistryName());
		return item;
	}

	@SuppressWarnings("unchecked")
	public <T extends DoTBBlockSlab> T setBurnable() {
		Blocks.FIRE.setFireInfo(this, 5, 20);
		return (T) this;
	}

	public enum EnumSlabVariant implements IStringSerializable {
		DEFAULT("default");

		private final String name;

		EnumSlabVariant(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return this.name;
		}
	}
}
