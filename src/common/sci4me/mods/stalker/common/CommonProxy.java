package sci4me.mods.stalker.common;

import net.minecraft.src.*;
import cpw.mods.fml.common.network.*;

public class CommonProxy implements IGuiHandler
{
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		return null;
	}

	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		return null;
	}

	public void registerRenderInformation() 
	{
		
	}

	public World getClientWorld() 
	{
		return null;
	}
}
