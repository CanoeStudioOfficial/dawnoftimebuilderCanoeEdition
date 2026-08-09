package org.dawnoftimebuilder.items.general;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;

/**
 * 标准原版台阶物品：继承 {@link ItemSlab}，双击合成、放置朝向均由原版逻辑处理。
 */
public class DoTBItemSlab extends ItemSlab {

	public DoTBItemSlab(Block block, BlockSlab singleSlab, BlockSlab doubleSlab) {
		super(block, singleSlab, doubleSlab);

		this.setTranslationKey(singleSlab.getTranslationKey());
		this.setRegistryName(singleSlab.getRegistryName());
	}
}
