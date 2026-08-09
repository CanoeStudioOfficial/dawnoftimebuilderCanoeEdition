package org.dawnoftimebuilder.items.general;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;

public class DoTBItemSlab extends ItemSlab {

	public DoTBItemSlab(Block block, BlockSlab singleSlab, BlockSlab doubleSlab) {
		super(block, singleSlab, doubleSlab);
		this.setTranslationKey(singleSlab.getTranslationKey());
		this.setRegistryName(singleSlab.getRegistryName());
	}
}
