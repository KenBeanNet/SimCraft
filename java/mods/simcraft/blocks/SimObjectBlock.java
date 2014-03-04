package mods.simcraft.blocks;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.UP;

import mods.simcraft.tileentity.SimObjectTileEntity;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class SimObjectBlock extends BlockContainer {

	protected SimObjectBlock(Material p_i45386_1_) {
		super(p_i45386_1_);
	}
	
	@Override
    public void onBlockAdded(World world, int i, int j, int k)
    {
        super.onBlockAdded(world, i, j, k);
        world.markBlockForUpdate(i, j, k);
    }

    @Override
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack itemStack)
    {
        byte chestFacing = 0;
        int facing = MathHelper.floor_double((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
        if (facing == 0)
        {
            chestFacing = 2;
        }
        if (facing == 1)
        {
            chestFacing = 5;
        }
        if (facing == 2)
        {
            chestFacing = 3;
        }
        if (facing == 3)
        {
            chestFacing = 4;
        }
        TileEntity te = world.getTileEntity(i, j, k);
        if (te != null && te instanceof SimObjectTileEntity)
        {
        	SimObjectTileEntity teic = (SimObjectTileEntity) te;
            teic.setDirection(chestFacing);
            world.markBlockForUpdate(i, j, k);
        }
    }
	
	private static final ForgeDirection[] validRotationAxes = new ForgeDirection[] { UP, DOWN };
    @Override
    public ForgeDirection[] getValidRotations(World worldObj, int x, int y, int z)
    {
        return validRotationAxes;
    }

    @Override
    public boolean rotateBlock(World worldObj, int x, int y, int z, ForgeDirection axis)
    {
        if (worldObj.isRemote)
        {
            return false;
        }
        if (axis == UP || axis == DOWN)
        {
            TileEntity tileEntity = worldObj.getTileEntity(x, y, z);
            if (tileEntity instanceof SimObjectTileEntity) {
            	SimObjectTileEntity icte = (SimObjectTileEntity) tileEntity;
                icte.rotateAround(axis);
            }
            return true;
        }
        return false;
    }

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		// TODO Auto-generated method stub
		return null;
	}

}
