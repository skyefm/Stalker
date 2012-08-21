package sci4me.mods.stalker.client;

import cpw.mods.fml.client.*;
import net.minecraft.src.*;
import sci4me.mods.stalker.common.CommonProxy;


public class ClientProxy extends CommonProxy
{
	public void registerRenderInformation() 
	{
		
	}

	public World getClientWorld() 
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
}