package com.bengelman.tensorcraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import org.apache.logging.log4j.Logger;

import com.bengelman.tensorcraft.block.BlockNeuralNetwork;
import com.bengelman.tensorcraft.tileentity.TileEntityNeuralNetwork;

@Mod.EventBusSubscriber(modid = TensorCraft.MODID)
@Mod(modid = TensorCraft.MODID, name = TensorCraft.NAME, version = TensorCraft.VERSION)
public class TensorCraft
{
    public static final String MODID = "tensorcraft";
    public static final String NAME = "TensorCraft";
    public static final String VERSION = "1.0";

    public static Logger logger;

    public static final Block NEURAL_NETWORK = new BlockNeuralNetwork(Material.IRON);
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        
        NEURAL_NETWORK.setUnlocalizedName("neural_network");
		NEURAL_NETWORK.setRegistryName("neural_network");
		NEURAL_NETWORK.setHardness(1.4f);
		NEURAL_NETWORK.setCreativeTab(CreativeTabs.REDSTONE);
		
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	GameRegistry.registerTileEntity(TileEntityNeuralNetwork.class, "tensorcraft:neural_network");

        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
    @SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(new ItemBlock(TensorCraft.NEURAL_NETWORK).setRegistryName(TensorCraft.NEURAL_NETWORK.getRegistryName()));
	}
	
	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TensorCraft.NEURAL_NETWORK), 0, new ModelResourceLocation(TensorCraft.NEURAL_NETWORK.getRegistryName(), "inventory"));
	}
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
	  event.getRegistry().registerAll(TensorCraft.NEURAL_NETWORK);
	}
}
