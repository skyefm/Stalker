package sci4me.mods.stalker.common;

import net.minecraft.src.*;

public class EntityStalker extends EntityMob
{
    public EntityStalker(World par1World)
    {
        super(par1World);
        this.texture = "/sci4me/mods/stalker/texture/tunnel.png";
        this.moveSpeed = 0.3F;
        this.attackStrength = 4;
        this.getNavigator().setBreakDoors(false);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
        this.tasks.addTask(2, new EntityAIMoveTwardsRestriction(this, this.moveSpeed));
        this.tasks.addTask(3, new EntityAIMoveThroughVillage(this, this.moveSpeed, false));
        this.tasks.addTask(4, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
    }

    public int getMaxHealth()
    {
        return 50;
    }

    public int getTotalArmorValue()
    {
        return 10;
    }

    protected boolean isAIEnabled()
    {
        return true;
    }

    public void onLivingUpdate()
    {
        if (this.worldObj.isDaytime() && !this.worldObj.isRemote)
        {
            float var1 = this.getBrightness(1.0F);

            if (var1 > 0.5F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) && this.rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F)
            {

            }
        }

        super.onLivingUpdate();
    }

    public void onDeath(DamageSource dmg)
    {
    	super.onDeath(dmg);
    	
    	if(worldObj.canBlockSeeTheSky((int)this.posX, (int)this.posY, (int)this.posZ))
    	{
    		worldObj.spawnEntityInWorld(new EntityLightningBolt(worldObj, this.posX, this.posY, this.posZ));
    	}
    	else
    	{
    		if(worldObj.getBlockId((int)this.posX, (int)this.posY, (int)this.posZ) == 0 && Block.fire.canPlaceBlockAt(worldObj, (int)this.posX, (int)this.posY, (int)this.posZ));            
    		{
    			worldObj.setBlockWithNotify((int)this.posX, (int)this.posY, (int)this.posZ, Block.fire.blockID);
    		}
    	}
    }

    public void onStruckByLightning(EntityLightningBolt bolt)
    {
    	this.heal(10);
    }
    
    protected void dealFireDamage(int par1)
    {
        this.heal(4);
    }
    
    protected void setOnFireFromLava()
    {
        this.heal(4);
    }
    
    protected String getLivingSound()
    {
        return "mob.zombie";
    }

    protected String getHurtSound()
    {
        return "mob.zombiehurt";
    }

    protected String getDeathSound()
    {
        return "mob.zombiedeath";
    }

    protected int getDropItemId()
    {
        return 0;
    }

    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
}
