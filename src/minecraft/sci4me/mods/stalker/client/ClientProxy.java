package sci4me.mods.stalker.client;

import sci4me.mods.stalker.common.CommonProxy;

import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.src.*;
import sci4me.mods.stalker.common.*;

public class ClientProxy extends CommonProxy
{
	public void registerRenderInformation() 
	{
		MinecraftForgeClient.preloadTexture("/sci4me/mods/stalker/texture/stalker.png");
		RenderingRegistry.registerEntityRenderingHandler(EntityStalker.class, new RenderBiped(new ModelBiped(), 0.5F));   
	}

	public World getClientWorld() 
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
}