package sci4me.mods.stalker.common;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityList;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityVillager;
import net.minecraft.src.Facing;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemStalkerSpawner extends Item
{
	protected ItemStalkerSpawner(int par1) 
	{
		super(par1);
		this.setIconIndex(3);
		this.setTabToDisplayOn(CreativeTabs.tabMisc);
	}
	
	public boolean tryPlaceIntoWorld(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par3World.isRemote)
        {
            return true;
        }
        else
        {
            int var11 = par3World.getBlockId(par4, par5, par6);
            par4 += Facing.offsetsXForSide[par7];
            par5 += Facing.offsetsYForSide[par7];
            par6 += Facing.offsetsZForSide[par7];
            double var12 = 0.0D;

            if (par7 == 1 && var11 == Block.fence.blockID || var11 == Block.netherFence.blockID)
            {
                var12 = 0.5D;
            }

            EntityStalker stalker = new EntityStalker(par3World); 
            stalker.setLocationAndAngles(par4, par5, par6, par3World.rand.nextFloat() * 360.0F, 0.0F);
            par3World.spawnEntityInWorld(stalker);
            return true;
        }
    }
	
	public String getTextureFile()
	{
		return "/sci4me/mods/stalker/texture/stalker.png";
	}
}
