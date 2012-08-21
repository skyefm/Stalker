package sci4me.mods.stalker.common;

import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.src.Block;
import net.minecraft.src.EntityCreeper;
import net.minecraft.src.EntityEnderman;
import net.minecraft.src.EntitySkeleton;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.EnumCreatureType;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;

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