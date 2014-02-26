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
	public void renderTileEntityAt(TileEntity tileentity, double x, double y,
			double z, float f) {
		GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        if (tileentity instanceof HomeTileEntity)
        {
        	HomeTileEntity teHome = (HomeTileEntity)tileentity;
        	switch (teHome.getLevel())
        	{
        		case 1:
        		{
                    Minecraft.getMinecraft().renderEngine.bindTexture(ModelResourceFile.homeBlock);
                    myModelLevel1.renderAll();
        			break;
        		}
        		case 2:
        		{
        			Minecraft.getMinecraft().renderEngine.bindTexture(ModelResourceFile.homeBlock);
                    myModelLevel2.renderAll();
        			break;
        		}
        		case 3:
        		{
        			Minecraft.getMinecraft().renderEngine.bindTexture(ModelResourceFile.homeBlock);
                    myModelLevel3.renderAll();
        			break;
        		}
        		case 4:
        		{
        			Minecraft.getMinecraft().renderEngine.bindTexture(ModelResourceFile.homeBlock);
                    myModelLevel4.renderAll();
        			break;
        		}
        	}
        }
        GL11.glPopMatrix();
		
	}

}