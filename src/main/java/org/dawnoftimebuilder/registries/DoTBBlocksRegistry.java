package org.dawnoftimebuilder.registries;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import org.dawnoftimebuilder.DawnOfTimeBuilder;
import org.dawnoftimebuilder.DoTBConfigs;
import org.dawnoftimebuilder.blocks.IBlockCustomItem;
import org.dawnoftimebuilder.blocks.IBlockMeta;
import org.dawnoftimebuilder.blocks.compatibility.*;
import org.dawnoftimebuilder.blocks.french.*;
import org.dawnoftimebuilder.blocks.general.*;
import org.dawnoftimebuilder.blocks.japanese.*;
import org.dawnoftimebuilder.blocks.mayan.*;
import org.dawnoftimebuilder.blocks.roman.BlockOchreRoofTilesMerged;
import org.dawnoftimebuilder.blocks.roman.BlockOchreRoofTilesSlab;
import org.dawnoftimebuilder.blocks.roman.BlockSandstoneColumn;
import org.dawnoftimebuilder.enums.IEnumMetaVariants;
import org.dawnoftimebuilder.items.general.DoTBItemMetaBlock;
import org.dawnoftimebuilder.items.roman.ItemOchreRoofTilesSlab;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.registries.DoTBItemsRegistry.setResourceLocation;

public class DoTBBlocksRegistry {

	public static List<Block> blocks_list = new ArrayList<>();
	private static void addToList(Block... blocks){
		Collections.addAll(blocks_list, blocks);
	}

	/** 注册一对原版式台阶（half + double），并互相绑定引用。 */
	private static void addSlabPair(DoTBBlockSlab single, DoTBBlockSlab dbl){
		single.setSlabs(single, dbl);
		dbl.setSlabs(single, dbl);
		addToList(single, dbl);
	}

	public static void init(){
		DoTBBlock thatch_bamboo = new DoTBBlock("thatch_bamboo", Material.CLOTH, 0.3F, SoundType.CLOTH);
		DoTBBlock thatch_wheat = new DoTBBlock("thatch_wheat", Material.CLOTH, 0.3F, SoundType.CLOTH);
		DoTBBlock flat_roof_tiles = new DoTBBlock("flat_roof_tiles", Material.ROCK,1.5F, SoundType.STONE);
		DoTBBlock limestone_brick = new DoTBBlock("limestone_brick", Material.ROCK,2.5F, SoundType.STONE);
		DoTBBlock oak_waxed_planks = new DoTBBlock("oak_waxed_planks", Material.WOOD);
		DoTBBlock grey_roof_tiles = new DoTBBlock("grey_roof_tiles", Material.ROCK,1.5F, SoundType.STONE);
		BlockPlasteredStone plastered_stone = new BlockPlasteredStone();

		//Slabs (standard BlockSlab: each material registers a half + double pair)
		DoTBBlockSlabHalf thatch_wheat_slab = new DoTBBlockSlabHalf("thatch_wheat_slab", Material.CLOTH, 0.3F, SoundType.CLOTH).setBurnable();
		DoTBBlockSlabDouble thatch_wheat_slab_double = new DoTBBlockSlabDouble("thatch_wheat_slab_double", Material.CLOTH, 0.3F, SoundType.CLOTH).setBurnable();
		addSlabPair(thatch_wheat_slab, thatch_wheat_slab_double);

		DoTBBlockSlabHalf thatch_bamboo_slab = new DoTBBlockSlabHalf("thatch_bamboo_slab", Material.CLOTH, 0.3F, SoundType.CLOTH).setBurnable();
		DoTBBlockSlabDouble thatch_bamboo_slab_double = new DoTBBlockSlabDouble("thatch_bamboo_slab_double", Material.CLOTH, 0.3F, SoundType.CLOTH).setBurnable();
		addSlabPair(thatch_bamboo_slab, thatch_bamboo_slab_double);

		DoTBBlockSlabHalf flat_roof_tiles_slab = new DoTBBlockSlabHalf("flat_roof_tiles_slab", Material.ROCK, 2.0F, SoundType.STONE);
		DoTBBlockSlabDouble flat_roof_tiles_slab_double = new DoTBBlockSlabDouble("flat_roof_tiles_slab_double", Material.ROCK, 2.0F, SoundType.STONE);
		addSlabPair(flat_roof_tiles_slab, flat_roof_tiles_slab_double);

		DoTBBlockSlabHalf limestone_brick_slab = new DoTBBlockSlabHalf("limestone_brick_slab", Material.ROCK, 2.0F, SoundType.STONE);
		DoTBBlockSlabDouble limestone_brick_slab_double = new DoTBBlockSlabDouble("limestone_brick_slab_double", Material.ROCK, 2.0F, SoundType.STONE);
		addSlabPair(limestone_brick_slab, limestone_brick_slab_double);

		DoTBBlockSlabHalf oak_waxed_planks_slab = new DoTBBlockSlabHalf("oak_waxed_planks_slab", Material.WOOD, 1.5F, SoundType.WOOD);
		DoTBBlockSlabDouble oak_waxed_planks_slab_double = new DoTBBlockSlabDouble("oak_waxed_planks_slab_double", Material.WOOD, 1.5F, SoundType.WOOD);
		addSlabPair(oak_waxed_planks_slab, oak_waxed_planks_slab_double);

		DoTBBlockSlabHalf grey_roof_tiles_slab = new DoTBBlockSlabHalf("grey_roof_tiles_slab", Material.ROCK, 2.0F, SoundType.STONE);
		DoTBBlockSlabDouble grey_roof_tiles_slab_double = new DoTBBlockSlabDouble("grey_roof_tiles_slab_double", Material.ROCK, 2.0F, SoundType.STONE);
		addSlabPair(grey_roof_tiles_slab, grey_roof_tiles_slab_double);

		DoTBBlockSlabHalf spruce_foundation_slab = new DoTBBlockSlabHalf("spruce_foundation_slab", Material.WOOD, 1.0F, SoundType.WOOD);
		DoTBBlockSlabDouble spruce_foundation_slab_double = new DoTBBlockSlabDouble("spruce_foundation_slab_double", Material.WOOD, 1.0F, SoundType.WOOD);
		addSlabPair(spruce_foundation_slab, spruce_foundation_slab_double);

		DoTBBlockSlabHalf plastered_stone_slab = new DoTBBlockSlabHalf("plastered_stone_slab", Material.ROCK, 2.0F, SoundType.STONE);
		DoTBBlockSlabDouble plastered_stone_slab_double = new DoTBBlockSlabDouble("plastered_stone_slab_double", Material.ROCK, 2.0F, SoundType.STONE);
		addSlabPair(plastered_stone_slab, plastered_stone_slab_double);

		DoTBBlockSlabHalf red_plastered_stone_slab = new DoTBBlockSlabHalf("red_plastered_stone_slab", Material.ROCK, 2.0F, SoundType.STONE);
		DoTBBlockSlabDouble red_plastered_stone_slab_double = new DoTBBlockSlabDouble("red_plastered_stone_slab_double", Material.ROCK, 2.0F, SoundType.STONE);
		addSlabPair(red_plastered_stone_slab, red_plastered_stone_slab_double);

		DoTBBlockSlabPathHalf path_gravel_slab = new DoTBBlockSlabPathHalf("path_gravel_slab", Material.GRASS, 0.5F, SoundType.GROUND);
		DoTBBlockSlabPathDouble path_gravel_slab_double = new DoTBBlockSlabPathDouble("path_gravel_slab_double", Material.GRASS, 0.5F, SoundType.GROUND);
		addSlabPair(path_gravel_slab, path_gravel_slab_double);

		DoTBBlockSlabPathHalf path_stepping_stones_slab = new DoTBBlockSlabPathHalf("path_stepping_stones_slab", Material.GRASS, 0.5F, SoundType.GROUND);
		DoTBBlockSlabPathDouble path_stepping_stones_slab_double = new DoTBBlockSlabPathDouble("path_stepping_stones_slab_double", Material.GRASS, 0.5F, SoundType.GROUND);
		addSlabPair(path_stepping_stones_slab, path_stepping_stones_slab_double);

		DoTBBlockSlabPathHalf path_cobbled_slab = new DoTBBlockSlabPathHalf("path_cobbled_slab", Material.GRASS, 0.5F, SoundType.GROUND);
		DoTBBlockSlabPathDouble path_cobbled_slab_double = new DoTBBlockSlabPathDouble("path_cobbled_slab_double", Material.GRASS, 0.5F, SoundType.GROUND);
		addSlabPair(path_cobbled_slab, path_cobbled_slab_double);

		DoTBBlockSlabPathHalf path_ochre_tiles_slab = new DoTBBlockSlabPathHalf("path_ochre_tiles_slab", Material.GRASS, 0.5F, SoundType.GROUND);
		DoTBBlockSlabPathDouble path_ochre_tiles_slab_double = new DoTBBlockSlabPathDouble("path_ochre_tiles_slab_double", Material.GRASS, 0.5F, SoundType.GROUND);
		addSlabPair(path_ochre_tiles_slab, path_ochre_tiles_slab_double);

		DoTBBlockSlabPathHalf path_dirt_slab = new DoTBBlockSlabPathHalf("path_dirt_slab", Material.GRASS, 0.5F, SoundType.GROUND);
		DoTBBlockSlabPathDouble path_dirt_slab_double = new DoTBBlockSlabPathDouble("path_dirt_slab_double", Material.GRASS, 0.5F, SoundType.GROUND);
		addSlabPair(path_dirt_slab, path_dirt_slab_double);

		BlockOchreRoofTilesSlab ochre_roof_tiles_slab = new BlockOchreRoofTilesSlab("ochre_roof_tiles_slab", false);
		BlockOchreRoofTilesSlab ochre_roof_tiles_slab_double = new BlockOchreRoofTilesSlab("ochre_roof_tiles_slab_double", true);
		ochre_roof_tiles_slab.setSlabs(ochre_roof_tiles_slab, ochre_roof_tiles_slab_double);
		ochre_roof_tiles_slab_double.setSlabs(ochre_roof_tiles_slab, ochre_roof_tiles_slab_double);
		addToList(ochre_roof_tiles_slab, ochre_roof_tiles_slab_double);

		addToList(
				//Compatibility
				new BlockPath(),
				new BlockThatch(),

				//General
				new BlockIronChain(),
				new DoTBBlockPath("path_gravel"),
				new DoTBBlockPath("path_stepping_stones"),
				new DoTBBlockPath("path_cobbled"),
				new DoTBBlockPath("path_ochre_tiles"),
				new DoTBBlockPath("path_dirt"),
				new DoTBBlock("rammed_dirt", Material.GROUND, 0.5F, SoundType.GROUND),
				thatch_wheat.setBurnable(),
				new DoTBBlockStairs("thatch_wheat_stairs", thatch_wheat, 0.3F, SoundType.CLOTH).setBurnable(),
				new DoTBBlockEdge("thatch_wheat_edge", Material.CLOTH, 0.3F, SoundType.CLOTH).setBurnable(),
				thatch_bamboo.setBurnable(),
				new DoTBBlockStairs("thatch_bamboo_stairs", thatch_bamboo, 0.3F, SoundType.CLOTH).setBurnable(),
				new DoTBBlockEdge("thatch_bamboo_edge", Material.CLOTH, 0.3F, SoundType.CLOTH).setBurnable(),
				new BlockFireplace(),

				//French
				new DoTBBlock("cobbled_limestone", Material.ROCK, 1.0F, SoundType.STONE),
				flat_roof_tiles,
				new DoTBBlockStairs("flat_roof_tiles_stairs", flat_roof_tiles, 2.0F, SoundType.STONE),
				new DoTBBlockEdge("flat_roof_tiles_edge", Material.ROCK, 1.5F, SoundType.STONE),
				new DoTBBlock("framed_rammed_dirt", Material.WOOD, 1.0F, SoundType.GROUND).setBurnable(),
				new DoTBBlockPortcullis("iron_portcullis", Material.IRON),
				new DoTBBlockGlassBlock("lattice_glass"),
				new BlockLatticeGlassPane(),
				new BlockLatticeOakWindow(),
				limestone_brick,
				new DoTBBlockStairs("limestone_brick_stairs", limestone_brick, 2.0F, SoundType.STONE),
				new DoTBBlockEdge("limestone_brick_edge", Material.ROCK, 2.0F, SoundType.STONE),
				new DoTBBlockWall("limestone_brick_wall", Material.ROCK, 2.0F, SoundType.STONE),
				new BlockLimestoneChimney(),
				new BlockLimestoneFireplace(),
				new DoTBBlockEdge("oak_planks_edge", Material.WOOD, 1.5F, SoundType.WOOD),
				new BlockOakShutters(),
				new DoTBBlockBeam("oak_beam", Material.WOOD, 1.5F, SoundType.WOOD).setBurnable(),
				new DoTBBlockSupportBeam("oak_support_beam", Material.WOOD, 1.5F, SoundType.WOOD).setBurnable(),
				new DoTBBlockSupportSlab("oak_support_slab", Material.WOOD, 1.0F, SoundType.WOOD).setBurnable(),
				new BlockSmallOakShutters(),
				new DoTBBlock("oak_timber_frame", Material.WOOD, 2.0F, SoundType.WOOD).setBurnable(),
				new DoTBBlockRotatedPillar("oak_timber_frame_corner", Material.WOOD,2.0F,SoundType.WOOD).setBurnable(),
				new DoTBBlockRotatedPillar("oak_timber_frame_pillar", Material.WOOD,2.0F,SoundType.WOOD).setBurnable(),
				new DoTBBlockFence("oak_waxed_fence", Material.WOOD).setBurnable(),
				oak_waxed_planks.setBurnable(),
				new DoTBBlockStairs("oak_waxed_planks_stairs", oak_waxed_planks, 1.5F, SoundType.WOOD),
				new DoTBBlockEdge("oak_waxed_planks_edge", Material.WOOD, 1.5F, SoundType.WOOD),

				//Japanese
				new DoTBBlockDryer("bamboo_drying_tray", Material.WOOD, 1.0F, SoundType.WOOD),
				new BlockCamellia(),
				new BlockMulberry(),
				new BlockCastIronTeapot(),
				new BlockCastIronTeacup(),
				new BlockFloweryPaperWall(),
				new BlockPaperLamp(),
				grey_roof_tiles,
				new DoTBBlockStairs("grey_roof_tiles_stairs", grey_roof_tiles, 2.0F, SoundType.STONE),
				new DoTBBlockEdge("grey_roof_tiles_edge", Material.ROCK, 1.5F, SoundType.STONE),
				new DoTBBlockWall("grey_roof_tiles_wall", Material.ROCK, 2.0F, SoundType.STONE),
				new BlockIkebanaFlowerPot(),
				new BlockSpruceLeglessChair(),
				new BlockLittleFlag(),
				new BlockPaperDoor(),
				new BlockPaperLantern(),
				new BlockPaperWall("paper_wall"),
				new BlockPaperWall("paper_wall_flat"),
				new DoTBBlockPane("paper_wall_window", Material.CLOTH).setBurnable(),
				new BlockPaperFoldingScreen(),
				new BlockRedPaintedLog(),
				new BlockRice(),
				new BlockSmallTatamiMat(),
				new DoTBBlockRotatedPillar("spruce_log_covered", Material.WOOD,2.0F,SoundType.WOOD).setBurnable(),
				new DoTBBlockBeam("spruce_beam", Material.WOOD, 1.5F, SoundType.WOOD).setBurnable(),
				new DoTBBlock("spruce_foundation", Material.WOOD, 1.0F, SoundType.WOOD).setBurnable(),
				new DoTBBlockFence("spruce_log_fence", Material.WOOD, 2.0F, SoundType.WOOD).setBurnable(),
				new DoTBBlockWall("spruce_log_wall", Material.WOOD, 2.0F, SoundType.WOOD).setBurnable(),
				new DoTBBlockEdge("spruce_planks_edge", Material.WOOD, 1.5F, SoundType.WOOD),
				new BlockSpruceRailing(),
				new BlockSpruceRoofSupport("spruce_roof_support"),
				new BlockSpruceRoofSupportMerged(),
				new DoTBBlockSupportBeam("spruce_support_beam", Material.WOOD, 1.5F, SoundType.WOOD).setBurnable(),
				new DoTBBlockSupportSlab("spruce_support_slab", Material.WOOD, 1.0F, SoundType.WOOD).setBurnable(),
				new BlockSpruceLowTable(),
				new DoTBBlock("spruce_timber_frame", Material.WOOD, 2.0F, SoundType.WOOD).setBurnable(),
				new DoTBBlockRotatedPillar("spruce_timber_frame_pillar", Material.WOOD, 2.0F, SoundType.WOOD).setBurnable(),
				new BlockStoneLantern(),
				new BlockTatamiMat("tatami_mat"),
				new BlockFuton(),
				new BlockSmallTatamiFloor(),
				new BlockTatamiFloor(),
				new BlockIrori(),
				new BlockSakeBottle(),
				new BlockSakeCup(),
				new BlockStickBundle(),

				//mayan
				new BlockChiseledPlasteredStone(),
				new BlockCommelina(),
				new BlockFeatheredSerpentSculpture(),
				new DoTBBlockPlate("green_ornamented_plastered_stone_frieze"),
				new DoTBBlockPlate("green_plastered_stone_frieze"),
				new BlockGreenSculptedPlasteredStoneFrieze(),
				new DoTBBlockEdge("green_small_plastered_stone_frieze", Material.ROCK, 1.5F, SoundType.STONE),
				new BlockMaize(),
				plastered_stone,
				new BlockPlasteredStoneColumn(),
				new BlockPlasteredStoneCresset(),
				new DoTBBlockPlate("plastered_stone_frieze"),
				new DoTBBlockPlate("plastered_stone_plate"),
				new DoTBBlockStairs("plastered_stone_stairs", plastered_stone, 2.0F, SoundType.STONE),
				new DoTBBlockEdge("plastered_stone_edge", Material.ROCK, 1.5F, SoundType.STONE),
				new BlockPlasteredStoneWindow(),
				new DoTBBlockPlate("red_ornamented_plastered_stone_frieze"),
				new DoTBBlockPlate("red_plastered_stone_frieze"),
				new DoTBBlockPlate("red_plastered_stone_plate"),
				new DoTBBlockStairs("red_plastered_stone_stairs", plastered_stone, 1, 2.0F, SoundType.STONE),
				new DoTBBlockEdge("red_plastered_stone_edge", Material.ROCK, 1.5F, SoundType.STONE),
				new BlockRedSculptedPlasteredStoneFrieze(),
				new DoTBBlockEdge("red_small_plastered_stone_frieze", Material.ROCK, 1.5F, SoundType.STONE),
				new BlockSerpentSculptedColumn(),
				new BlockStoneFrieze(),

				//roman
				new DoTBBlock("ochre_roof_tiles", Material.ROCK, 2.0F, SoundType.STONE),
				new BlockOchreRoofTilesMerged(),
				new BlockSandstoneColumn()
		);
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();
		for(Block block : blocks_list){
			registry.register(block);
		}
	}

	public static void initItemBlocks() {
		for (Block block : blocks_list) {

			Item item;
			if(block instanceof IBlockCustomItem){
				item = ((IBlockCustomItem)block).getCustomItemBlock();
			}else if(block instanceof IBlockMeta){
				IBlockMeta blockMeta = (IBlockMeta) block;
				item = new DoTBItemMetaBlock(blockMeta).setRegistryName(block.getRegistryName()).setTranslationKey(block.getTranslationKey());
			}else{
				item = new ItemBlock(block).setRegistryName(block.getRegistryName()).setTranslationKey(block.getTranslationKey());
			}

			if(item != null) DoTBItemsRegistry.items_list.add(item.setCreativeTab(DOTB_TAB));
		}
	}
}
