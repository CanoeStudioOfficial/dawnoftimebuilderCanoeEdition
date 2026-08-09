package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class DoTBBlockSlabPathDouble extends DoTBBlockSlabPath {

	public DoTBBlockSlabPathDouble(String name, Material materialIn, float hardness, SoundType sound) {
		super(name, materialIn, hardness, sound);
	}

	@Override
	public boolean isDouble() {
		return true;
	}
}
