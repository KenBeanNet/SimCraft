package mods.simcraft.blocks.roofs;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class RoofIntCornerBlock extends RoofBlock
{

	public RoofIntCornerBlock(Material material) {
		super(material);
		// TODO Auto-generated constructor stub
	}

	/**
     * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
     * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
     */
    @Override
    public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, List arraylist, Entity var7)
    {
    	int l = world.getBlockMetadata(i, j, k) % 8;
        if (l == 0)
        {
        	setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        	super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, var7);
        	setBlockBounds(0.5F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
        	super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, var7);
        }
        else if (l == 1)
        {
        	setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        	super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, var7);
        	setBlockBounds(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 0.5F);
        	super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, var7);
        }
        else if (l == 2)
        {
        	setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        	super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, var7);
        	setBlockBounds(0.0F, 0.5F, 0.5F, 0.5F, 1.0F, 1.0F);
        	super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, var7);
        }
        else if (l == 3)
        {
        	setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        	super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, var7);
        	setBlockBounds(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
        	super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, var7);
        }
        else if (l == 4)
        {
        	setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        	super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, var7);
        	setBlockBounds(0.5F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
        	super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, var7);
        }
        else if (l == 5)
        {
        	setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        	super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, var7);
        	setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F);
        	super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, var7);
        }
        else if (l == 6)
        {
        	setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
	          super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, var7);
	          setBlockBounds(0.0F, 0.0F, 0.5F, 0.5F, 0.5F, 1.0F);
	          super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, var7);
        }
        else if (l == 7)
        {
        	setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
	          super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, var7);
	          setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
	          super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, var7);
        }
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
}
