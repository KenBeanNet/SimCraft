package mods.simcraft.tileentity.render;

import org.lwjgl.opengl.GL11;

import mods.simcraft.tileentity.HomeTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelHome extends TileEntitySpecialRenderer
{
	private IModelCustom myModelLevel1;
	private IModelCustom myModelLevel2;
	private IModelCustom myModelLevel3;
	private IModelCustom myModelLevel4;
	
	public ModelHome()
	{
		myModelLevel1 = AdvancedModelLoader.loadModel(ModelResourceFile.homeBlockObj);
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) 
	{
		this.bindTexture(ModelResourceFile.homeBlock);

		if (tileentity instanceof HomeTileEntity)
        {
         	HomeTileEntity teHome = (HomeTileEntity)tileentity;

        	int k = 0;
            if (teHome.getDirection() == 2) {
                k = 180;
            }
            else if (teHome.getDirection() == 3) {
                k = 0;
            }
            else if (teHome.getDirection() == 4) {
                k = 90;
            }
            else if (teHome.getDirection() == 5) {
                k = -90;
            }
            GL11.glPushMatrix();
            GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
            GL11.glScalef(1.0F, -1F, -1F);
            GL11.glRotatef(k, 0.0F, 1.0F, 0.0F);
        }
		myModelLevel1.renderAll();
		GL11.glPopMatrix();
	}
}