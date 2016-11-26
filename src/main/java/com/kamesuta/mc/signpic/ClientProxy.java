package com.kamesuta.mc.signpic;

import java.io.File;
import java.io.IOException;

import com.kamesuta.mc.signpic.command.CommandVersion;
import com.kamesuta.mc.signpic.command.RootCommand;
import com.kamesuta.mc.signpic.render.CustomTileEntitySignRenderer;
import com.kamesuta.mc.signpic.render.CustomItemSignRenderer;
import com.mojang.util.UUIDTypeAdapter;

import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	@Override
	public void preInit(final FMLPreInitializationEvent event) {
		super.preInit(event);

		// Setup stencil clip
		// StencilClip.init();

		// Setup image
		Client.renderer = new CustomTileEntitySignRenderer();

		Client.mcversion = MinecraftForge.MC_VERSION;
		Client.forgeversion = ForgeVersion.getVersion();

		// Setup location
		Client.location = new Locations(event, getDataDirectory());

		// Get Id
		final String id = Client.mc.getSession().getPlayerID();
		try {
			final Object o = UUIDTypeAdapter.fromString(id);
			if (o!=null) {
				Client.id = id;
				Client.name = Client.mc.getSession().getUsername();
			}
		} catch (final IllegalArgumentException e) {
		}

		// Setup
		Client.handler = new CoreHandler();
		Client.rootCommand = new RootCommand();

		Client.rootCommand.addChildCommand(new CommandVersion());

		ModelLoader.setCustomModelResourceLocation(Items.sign, 0, CustomItemSignRenderer.modelResourceLocation);
	}

	private File getDataDirectory() {
		final File file = Client.mc.mcDataDir;
		try {
			return file.getCanonicalFile();
		} catch (final IOException e) {
			Reference.logger.debug("Could not canonize path!", e);
		}
		return file;
	}

	@Override
	public void init(final FMLInitializationEvent event) {
		super.init(event);

		// Replace Sign Renderer
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySign.class, Client.renderer);

		// Event Register
		Client.handler.init();
		ClientCommandHandler.instance.registerCommand(Client.rootCommand);
	}

	@Override
	public void postInit(final FMLPostInitializationEvent event) {
		super.postInit(event);
	}
}