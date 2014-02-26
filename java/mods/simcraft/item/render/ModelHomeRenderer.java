package mods.simcraft.item.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.simcraft.tileentity.render.ModelResourceFile;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

/**
* Created by kyahco on 1/26/14.
*/
@SideOnly(Side.CLIENT)
public class ModelHomeRenderer {

    private IModelCustom modelHomeHall;

    public ModelHomeRenderer()
    {
        modelHomeHall = AdvancedModelLoader.loadModel(ModelResourceFile.homeBlockObj);
    }

    public void render()
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(ModelResourceFile.homeBlock);
        modelHomeHall.renderAll();
    }
}
