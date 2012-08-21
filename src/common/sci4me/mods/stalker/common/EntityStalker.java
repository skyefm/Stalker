package sci4me.mods.stalker.common;

import java.util.List;

import net.minecraft.src.*;

public class EntityStalker extends EntityMob
{
	private EntityPlayer ep;
	private int tpDelay;
	
	private boolean attacking;
	private boolean isBlinded;
	
    public EntityStalker(World par1World)
    {
        super(par1World);
        this.texture = "/sci4me/mods/stalker/texture/tunnel.png";
        this.moveSpeed = 0.325F;
        this.attackStrength = 6;
        this.isBlinded = false;
        this.attacking = false;
        this.isImmuneToFire = true;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIBreakDoor(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
        this.tasks.addTask(3, new EntityAIMoveTwardsRestriction(this, this.moveSpeed));
        this.tasks.addTask(4, new EntityAIMoveThroughVillage(this, this.moveSpeed, false));
        this.tasks.addTask(5, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
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
	    findPlayerToAttack();
	    moveSpeed = entityToAttack == null ? 0.325F : 0.425F;
	    	
	    if(ep != null && entityToAttack != null)
	    {
	    	attacking = true;
	    }
	    else
	    {
	    	attacking = false;
	    }
	    	
	    randomBreakBlock();
	    
	    if(rand.nextInt(100) == 0)
	    {
	        if(rand.nextInt(1000) < 100)
	        {
	        	setVisible(false);
	        }
	        else
	        {
	        	setVisible(true);
	        }
	    }
	    	
	    if(this.isWet() || this.isInWater())
	    {
	        this.attackEntityFrom(DamageSource.drown, 2);
	    }
	    	
	    if(this.ep != null)
	    {			
	        if(dist(ep, this) > 8)
	        {
	        	if(rand.nextBoolean() && visible())
	        	{
	        		teleportToEntity(this.ep);
	        	}
	        }
	        		
	        if(dist(ep, this) < 8)
	        {
	        	if(ep.canEntityBeSeen(this))
	        	{	
	        		if(!isBlinded)
	        		{
	        			ep.addPotionEffect(new PotionEffect(Potion.blindness.id, 100, 0));	
	        			isBlinded = true;
	        		}
	        	}
	        }
	        else
	        {
	        	if(isBlinded)
	        	{
	        		ep.removePotionEffect(Potion.blindness.id);
	        		isBlinded = false;
	        	}
	        }
	    }
	    else
	    {
	    	if(this.entityToAttack == null && ep != null)
	    	{
	    		int del = rand.nextInt(50 - 5 + 1) + 4;
	    			
	    		if(tpDelay++ == del)
	    		{
	    			teleportToEntity(ep);
	    		}
	    		this.entityToAttack = ep;	
	    	}
	    	else
	    	{
	    		if(this.entityToAttack == null && ep == null && !attacking)
	    		{
	    			if(rand.nextInt(10000) < 3)
	    			{
	    				teleportRandomly();
	    			}
	    		}
	    	}
    	}
        super.onLivingUpdate();
    }
    
    private int dist(EntityPlayer ep, EntityStalker st)
    {
    	return (int)Math.sqrt(Math.pow(ep.posX - st.posX, 2) + Math.pow(ep.posY - st.posY, 2) + Math.pow(ep.posZ - st.posZ, 2));
    }
    
    private void setVisible(boolean visible)
    {
    	if(visible)
    	{
    		this.texture = "/sci4me/mods/stalker/texture/tunnel.png";
    	}
    	else
    	{
    		this.texture = "/sci4me/mods/stalker/texture/a0.png";
    	}
    }
    
    private boolean visible()
    {
    	if(this.texture == "/sci4me/tunnel.png")
    	{
    		return true;
    	}
    	return false;
    }
    
    private void randomBreakBlock()
    {
    	int x, y, z;  
        x = (int)this.posX;
        y = (int)this.posY - 1;
        z = (int)this.posZ;
        int bbl = worldObj.getBlockId(x, y, z);
        if(bbl != Block.bedrock.blockID && bbl != Block.bed.blockID && bbl != Block.chest.blockID && bbl != Block.oreCoal.blockID && bbl != Block.oreDiamond.blockID && bbl != Block.oreGold.blockID && bbl != Block.oreIron.blockID && bbl != Block.oreLapis.blockID && bbl != Block.oreRedstone.blockID && bbl != Block.oreRedstoneGlowing.blockID && bbl != Block.portal.blockID && bbl != Block.endPortal.blockID && bbl != Block.endPortalFrame.blockID)
        {
        	if(rand.nextInt(1000) == 0)
        	{
        		worldObj.setBlockWithNotify(x, y, z, 0);
        	}
        }
    }    
    
    protected void fall(float par1) 
    {

    }
    
    public boolean canDespawn()
    {
    	if(attacking)
    	{
    		return false;
    	}
    	return true;
    }
    
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }
    
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
    }
    
   private boolean teleportToEntity(Entity par1Entity)
    {
        Vec3 vec3d = Vec3.createVectorHelper(posX - par1Entity.posX, ((boundingBox.minY + (double)(height / 2.0F)) - par1Entity.posY) + (double)par1Entity.getEyeHeight(), posZ - par1Entity.posZ);
        vec3d = vec3d.normalize();
        double d = 16D;
        double d1 = (posX + (rand.nextDouble() - 0.5D) * 8D) - vec3d.xCoord * d;
        double d2 = (posY + (double)(rand.nextInt(16) - 8)) - vec3d.yCoord * d;
        double d3 = (posZ + (rand.nextDouble() - 0.5D) * 8D) - vec3d.zCoord * d;
        setVisible(true);
        return teleportTo(d1, d2, d3);
    }
    
    private boolean teleportRandomly()
    {
        double var1 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
        double var3 = this.posY + (double)(this.rand.nextInt(64) - 32);
        double var5 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
        return this.teleportTo(var1, var3, var5);
    }
    
    private boolean teleportTo(double par1, double par3, double par5)
    {
        double d = posX;
        double d1 = posY;
        double d2 = posZ;
        posX = par1;
        posY = par3;
        posZ = par5;
        boolean flag = false;
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(posY);
        int k = MathHelper.floor_double(posZ);

        if (worldObj.blockExists(i, j, k))
        {
            boolean flag1;

            for (flag1 = false; !flag1 && j > 0;)
            {
                int i1 = worldObj.getBlockId(i, j - 1, k);

                if (i1 == 0 || !Block.blocksList[i1].blockMaterial.blocksMovement())
                {
                    posY--;
                    j--;
                }
                else
                {
                    flag1 = true;
                }
            }

            if (flag1)
            {
                setPosition(posX, posY, posZ);

                if (worldObj.getCollidingBoundingBoxes(this, boundingBox).size() == 0 && !worldObj.isAnyLiquid(boundingBox))
                {
                    flag = true;
                }
            }
        }

        if (!flag)
        {
            setPosition(d, d1, d2);
            return false;
        }

        int l = 128;

        for (int j1 = 0; j1 < l; j1++)
        {
            double d3 = (double)j1 / ((double)l - 1.0D);
            float f = (rand.nextFloat() - 0.5F) * 0.2F;
            float f1 = (rand.nextFloat() - 0.5F) * 0.2F;
            float f2 = (rand.nextFloat() - 0.5F) * 0.2F;
            double d4 = d + (posX - d) * d3 + (rand.nextDouble() - 0.5D) * (double)width * 2D;
            double d5 = d1 + (posY - d1) * d3 + rand.nextDouble() * (double)height;
            double d6 = d2 + (posZ - d2) * d3 + (rand.nextDouble() - 0.5D) * (double)width * 2D;
            worldObj.spawnParticle("portal", d4, d5, d6, f, f1, f2);
        }

        worldObj.playSoundEffect(d, d1, d2, "mob.endermen.portal", 1.0F, 1.0F);
        worldObj.playSoundAtEntity(this, "mob.endermen.portal", 1.0F, 1.0F);
        return true;
    }
    
    protected void dealFireDamage(int par1)
    {
        this.heal(4);
    }
    
    protected void setOnFireFromLava()
    {
        this.heal(4);
    }
    
    public boolean getCanSpawnHere()
    {
    	if(this.isValidLightLevel() && super.getCanSpawnHere())
    	{
    		if(rand.nextInt(1000) < 3)
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
    public void onDeath(DamageSource dmg)
    {
    	super.onDeath(dmg);
    	
    	List var4 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(32.0D, 32.0D, 32.0D));
    	
    	for (int var5 = 0; var5 < var4.size(); ++var5)
        {
            Entity var6 = (Entity)var4.get(var5);

            if (var6 instanceof EntityStalker)
            {
            	if(rand.nextInt(100) < 100)
            	{
            		((EntityStalker) var6).heal(4);
            	}
            }
        }
    	
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
    
    protected Entity findPlayerToAttack()
    {
    	EntityPlayer var1 = this.worldObj.getClosestVulnerablePlayerToEntity(this, 512.0D);
    	this.entityToAttack = var1;
    	ep = var1;
        return var1;
    }

    public void onStruckByLightning(EntityLightningBolt bolt)
    {
    	this.heal(10);
    }
    
    protected String getLivingSound()
    {
        return "mob.endermen.idle";
    }

    protected String getHurtSound()
    {
        return "mob.endermen.hit";
    }

    protected String getDeathSound()
    {
        return "mob.endermen.death";
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