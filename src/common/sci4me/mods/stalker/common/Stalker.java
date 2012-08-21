package sci4me.mods.stalker.common;

import java.util.logging.Level;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.*;
import net.minecraft.src.*;
import net.minecraftforge.common.*;

@Mod(modid = "Stalker", name = "Stalker", version = "0.1")
@NetworkMod(channels = { "Stalker" }, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class Stalker 
{
	@SidedProxy(clientSide = "sci4me.mods.stalker.client.ClientProxy", serverSide = "sci4me.mods.stalker.common.CommomProxy")
	public static CommonProxy proxy;
	
	@Instance
	public static Stalker instance;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try 
		{
			cfg.load();
		} 
		catch (Exception e)
		{			
			FMLLog.log(Level.SEVERE, e, "Stalker's configuration failed to load.");
		} 
		finally 
		{
			cfg.save();
		}
	}
	
	@Init
	public void init(FMLInitializationEvent event)
	{
		proxy.registerRenderInformation();
	}	
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
}