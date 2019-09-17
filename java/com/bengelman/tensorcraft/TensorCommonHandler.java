package com.bengelman.tensorcraft;


import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

@Mod.EventBusSubscriber
public class TensorCommonHandler {
	
	static int ID = 200;
	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
		/*
		 * Example:
		EntityEntry entry = EntityEntryBuilder.create()
			    .entity(EntityKnight.class)
			    .id(new ResourceLocation("adventureawaits:knight"), ID++)
			    .name("Knight")
			    .egg(0xAAAAAA, 0xFFFFFF)
			    .tracker(64, 1, false)
			    .build();
		event.getRegistry().register(entry);
		*/
	}
	
}