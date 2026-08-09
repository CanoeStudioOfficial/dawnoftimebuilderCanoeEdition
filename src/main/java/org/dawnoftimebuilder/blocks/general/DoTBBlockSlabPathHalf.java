package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class DoTBBlockSlabPathHalf extends DoTBBlockSlabPath {

	public DoTBBlockSlabPathHalf(String name, Material materialIn, float hardness, SoundType sound) {
		super(name, materialIn, hardness, sound);
	}

	@Override
	public boolean isDouble() {
		return false;
	}
}
