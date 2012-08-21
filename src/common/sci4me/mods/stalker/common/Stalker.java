package sci4me.mods.stalker.common;

import java.util.logging.Level;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.registry.*;
import net.minecraft.src.*;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraftforge.common.*;

@Mod(modid = "Stalker", name = "Stalker", version = "0.1")
@NetworkMod(channels = { "Stalker" }, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class Stalker 
{
	@SidedProxy(clientSide = "sci4me.mods.stalker.client.ClientProxy", serverSide = "sci4me.mods.stalker.common.CommomProxy")
	public static CommonProxy proxy;
	
	@Instance
	public static Stalker instance;
	
	private int spawnerID;
	
	private Item spawner;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try 
		{
			cfg.load();
			spawnerID = cfg.getOrCreateProperty("Spawner", Configuration.CATEGORY_ITEM, "400").getInt();
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
		spawner = new ItemStalkerSpawner(spawnerID).setItemName("stalkerSpawner");
		LanguageRegistry.instance().addStringLocalization(spawner.getItemName() + ".name", "en_US", "Spawn Stalker");
		
		LanguageRegistry.instance().addStringLocalization("entity.Stalker.name", "en_US", "Stalker");
		EntityRegistry.registerModEntity(EntityStalker.class, "Stalker", 1, this, 250, 5, true);
		EntityRegistry.addSpawn(EntityStalker.class, 10, 10, 10, EnumCreatureType.monster);
		
		proxy.registerRenderInformation();
	}	
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
}