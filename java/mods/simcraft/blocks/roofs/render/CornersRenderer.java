package mods.simcraft.blocks.roofs.render;

import org.lwjgl.opengl.GL11;

import mods.simcraft.blocks.render.RendererBlock;
import mods.simcraft.common.Repository;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CornersRenderer implements ISimpleBlockRenderingHandler 
{

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) 
	{
		Tessellator  tessellator = Tessellator.instance;
	    if (modelId == Repository.CornersRenderID)
	    {
	    	renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		    tessellator.startDrawingQuads();
		    tessellator.setNormal(0.0F, -1.0F, 0.0F);
		    renderCornersBottomFace(renderer, metadata, 0.0D, 0.0D, 0.0D, RendererBlock.getBlockIcon(block));
		    tessellator.draw();
		    tessellator.startDrawingQuads();
		    tessellator.setNormal(0.0F, 1.0F, 0.0F);
		    renderCornersTopFace(renderer, metadata, 0.0D, 0.0D, 0.0D, RendererBlock.getBlockIcon(block));
		    tessellator.draw();
		    tessellator.startDrawingQuads();
		    tessellator.setNormal(0.0F, 0.0F, -1.0F);
		    renderCornersEastFace(renderer, metadata, 0.0D, 0.0D, 0.0D, RendererBlock.getBlockIcon(block));
		    tessellator.draw();
		    tessellator.startDrawingQuads();
		    tessellator.setNormal(0.0F, 0.0F, 1.0F);
		    renderCornersWestFace(renderer, metadata, 0.0D, 0.0D, 0.0D, RendererBlock.getBlockIcon(block));
		    tessellator.draw();
		    tessellator.startDrawingQuads();
		    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		    renderCornersNorthFace(renderer, metadata, 0.0D, 0.0D, 0.0D, RendererBlock.getBlockIcon(block));
		    tessellator.draw();
		    tessellator.startDrawingQuads();
		    tessellator.setNormal(1.0F, 0.0F, 0.0F);
		    renderCornersSouthFace(renderer, metadata, 0.0D, 0.0D, 0.0D, RendererBlock.getBlockIcon(block));
		    tessellator.draw();
		    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	    }
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		return modelId == Repository.CornersRenderID ? this.renderBlockCorners(block, x, y, z, renderer, world) : false;
	}
	
	public boolean renderBlockCorners(Block block, int x, int y, int z, RenderBlocks renderblocks, IBlockAccess iblockaccess)
    {
        int meta = iblockaccess.getBlockMetadata(x, y, z);
        int i1 = block.colorMultiplier(iblockaccess, x, y, z);
        float f = (float)(i1 >> 16 & 255) / 255.0F;
        float f1 = (float)(i1 >> 8 & 255) / 255.0F;
        float f2 = (float)(i1 & 255) / 255.0F;
        return this.renderCornersBlockWithColorMultiplier(block, x, y, z, f, f1, f2, meta, renderblocks, iblockaccess);
    }
	
	public boolean renderCornersBlockWithColorMultiplier(Block block, int x, int y, int z, float f, float f1, float f2, int meta, RenderBlocks renderblocks, IBlockAccess iblockaccess)
    {
        Tessellator tessellator = Tessellator.instance;
        boolean flag = false;
        float f3 = 0.5F;
        float f4 = 1.0F;
        float f5 = 0.8F;
        float f6 = 0.6F;
        float f7 = f4 * f;
        float f8 = f4 * f1;
        float f9 = f4 * f2;

        if (block == Blocks.grass)
        {
            f2 = 1.0F;
            f1 = 1.0F;
            f = 1.0F;
        }

        float f10 = f3 * f;
        float f11 = f5 * f;
        float f12 = f6 * f;
        float f13 = f3 * f1;
        float f14 = f5 * f1;
        float f15 = f6 * f1;
        float f16 = f3 * f2;
        float f17 = f5 * f2;
        float f18 = f6 * f2;
        float f19 = getAmbientOcclusionLightValue(iblockaccess, x, y, z);
        int slope;
        IIcon icon;
        float f25;

        if ((renderblocks.renderAllFaces || block.shouldSideBeRendered(iblockaccess, x, y - 1, z, 0)) && meta / 4 != 1 && meta / 4 != 3)
        {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(iblockaccess, x, y, z));
            slope = renderblocks.blockAccess.getBlockMetadata(x, y, z);
            f25 = getAmbientOcclusionLightValue(iblockaccess, x, y - 1, z);
            tessellator.setColorOpaque_F(f10 * f25, f13 * f25, f16 * f25);
            icon = renderblocks.getBlockIcon(block, iblockaccess, x, y, z, 0);
            this.renderCornersBottomFace(renderblocks, slope, (double)x, (double)y, (double)z, icon);
            flag = true;
        }

        if ((renderblocks.renderAllFaces || block.shouldSideBeRendered(iblockaccess, x, y + 1, z, 1)) && meta / 4 != 0 && meta / 4 != 2)
        {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(iblockaccess, x, y, z));
            slope = renderblocks.blockAccess.getBlockMetadata(x, y, z);
            f25 = getAmbientOcclusionLightValue(iblockaccess, x, y + 1, z);

            if (block.getBlockBoundsMaxY() != 1.0D && !block.getMaterial().isLiquid())
            {
                f25 = f19;
            }

            tessellator.setColorOpaque_F(f7 * f25, f8 * f25, f9 * f25);
            icon = renderblocks.getBlockIcon(block, iblockaccess, x, y, z, 0);
            this.renderCornersTopFace(renderblocks, slope, (double)x, (double)y, (double)z, icon);
            flag = true;
        }

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(iblockaccess, x, y, z - 1, 2) || meta % 2 == 0)
        {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(iblockaccess, x, y, z));
            slope = renderblocks.blockAccess.getBlockMetadata(x, y, z);
            f25 = getAmbientOcclusionLightValue(iblockaccess, x, y, z - 1);

            if (meta % 2 == 0)
            {
                f25 = f19;
            }

            tessellator.setColorOpaque_F(f11 * f25, f14 * f25, f17 * f25);
            icon = renderblocks.getBlockIcon(block, iblockaccess, x, y, z, 0);
            this.renderCornersEastFace(renderblocks, slope, (double)x, (double)y, (double)z, icon);
            flag = true;
        }

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(iblockaccess, x, y, z + 1, 3) || meta % 2 == 1)
        {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(iblockaccess, x, y, z));
            slope = renderblocks.blockAccess.getBlockMetadata(x, y, z);
            f25 = getAmbientOcclusionLightValue(iblockaccess, x, y, z + 1);

            if (meta % 2 == 1)
            {
                f25 = f19;
            }

            tessellator.setColorOpaque_F(f11 * f25, f14 * f25, f17 * f25);
            icon = renderblocks.getBlockIcon(block, iblockaccess, x, y, z, 0);
            this.renderCornersWestFace(renderblocks, slope, (double)x, (double)y, (double)z, icon);
            flag = true;
        }

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(iblockaccess, x - 1, y, z, 4) || meta == 0 || meta == 3 || meta == 4 || meta == 7 || meta == 11 || meta == 15)
        {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(iblockaccess, x, y, z));
            slope = renderblocks.blockAccess.getBlockMetadata(x, y, z);
            f25 = getAmbientOcclusionLightValue(iblockaccess, x - 1, y, z);

            if (meta == 0 || meta == 3 || meta == 4 || meta == 7 || meta == 11 || meta == 15)
            {
                f25 = f19;
            }

            tessellator.setColorOpaque_F(f12 * f25, f15 * f25, f18 * f25);
            icon = renderblocks.getBlockIcon(block, iblockaccess, x, y, z, 0);
            this.renderCornersNorthFace(renderblocks, slope, (double)x, (double)y, (double)z, icon);
            flag = true;
        }

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(iblockaccess, x + 1, y, z, 5) || meta == 1 || meta == 2 || meta == 5 || meta == 6 || meta == 10 || meta == 14)
        {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(iblockaccess, x, y, z));
            slope = renderblocks.blockAccess.getBlockMetadata(x, y, z);
            f25 = getAmbientOcclusionLightValue(iblockaccess, x + 1, y, z);

            if (meta == 1 || meta == 2 || meta == 5 || meta == 6 || meta == 10 || meta == 14)
            {
                f25 = f19;
            }

            tessellator.setColorOpaque_F(f12 * f25, f15 * f25, f18 * f25);
            icon = renderblocks.getBlockIcon(block, iblockaccess, x, y, z, 0);
            this.renderCornersSouthFace(renderblocks, slope, (double)x, (double)y, (double)z, icon);
            flag = true;
        }

        return flag;
    }

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return Repository.CornersRenderID;
	}
	
	public void renderCornersBottomFace(RenderBlocks renderBlocks, int slope, double x, double y, double z, IIcon icon)
    {
        Tessellator tessellator = Tessellator.instance;

        if (renderBlocks.hasOverrideBlockTexture())
        {
            icon = renderBlocks.overrideBlockTexture;
        }

        double d3 = (double)icon.getInterpolatedU(renderBlocks.renderMinX * 16.0D);
        double d4 = (double)icon.getInterpolatedU(renderBlocks.renderMaxX * 16.0D);
        double d5 = (double)icon.getInterpolatedV(16.0D - renderBlocks.renderMinZ * 16.0D);
        double d6 = (double)icon.getInterpolatedV(16.0D - renderBlocks.renderMaxZ * 16.0D);
        double d7 = x + renderBlocks.renderMinX;
        double d8 = x + renderBlocks.renderMaxX;
        double d9 = y + renderBlocks.renderMinY;
        double d10 = z + renderBlocks.renderMinZ;
        double d11 = z + renderBlocks.renderMaxZ;

        if (slope / 4 == 0)
        {
            tessellator.addVertexWithUV(d7, d9, d11, d3, d6);
            tessellator.addVertexWithUV(d7, d9, d10, d3, d5);
            tessellator.addVertexWithUV(d8, d9, d10, d4, d5);
            tessellator.addVertexWithUV(d8, d9, d11, d4, d6);
        }
        else if (slope == 8)
        {
            tessellator.addVertexWithUV(d7, d9, d11, d3, d6);
            tessellator.addVertexWithUV(d8, d9, d10, d4, d5);
            tessellator.addVertexWithUV(d8, d9, d10, d4, d5);
            tessellator.addVertexWithUV(d8, d9, d11, d4, d6);
        }
        else if (slope == 9)
        {
            tessellator.addVertexWithUV(d7, d9, d11, d3, d6);
            tessellator.addVertexWithUV(d7, d9, d10, d3, d5);
            tessellator.addVertexWithUV(d8, d9, d10, d4, d5);
            tessellator.addVertexWithUV(d7, d9, d11, d3, d6);
        }
        else if (slope == 10)
        {
            tessellator.addVertexWithUV(d7, d9, d11, d3, d6);
            tessellator.addVertexWithUV(d7, d9, d10, d3, d5);
            tessellator.addVertexWithUV(d8, d9, d11, d4, d6);
            tessellator.addVertexWithUV(d8, d9, d11, d4, d6);
        }
        else if (slope == 11)
        {
            tessellator.addVertexWithUV(d7, d9, d10, d3, d5);
            tessellator.addVertexWithUV(d7, d9, d10, d3, d5);
            tessellator.addVertexWithUV(d8, d9, d10, d4, d5);
            tessellator.addVertexWithUV(d8, d9, d11, d4, d6);
        }
    }

    public void renderCornersEastFace(RenderBlocks renderBlocks, int slope, double x, double y, double z, IIcon icon)
    {
        Tessellator tessellator = Tessellator.instance;

        if (renderBlocks.hasOverrideBlockTexture())
        {
            icon = renderBlocks.overrideBlockTexture;
        }

        double d3 = (double)icon.getInterpolatedU(renderBlocks.renderMinX * 16.0D);
        double d4 = (double)icon.getInterpolatedU(renderBlocks.renderMaxX * 16.0D);
        double d5 = (double)icon.getInterpolatedV(16.0D - renderBlocks.renderMinY * 16.0D);
        double d6 = (double)icon.getInterpolatedV(16.0D - renderBlocks.renderMaxY * 16.0D);
        double d8 = x + renderBlocks.renderMinX;
        double d9 = x + renderBlocks.renderMaxX;
        double d10 = y + renderBlocks.renderMinY;
        double d11 = y + renderBlocks.renderMaxY;
        double d12 = z + renderBlocks.renderMinZ;
        double d13 = z + renderBlocks.renderMaxZ;

        if (slope == 0)
        {
            tessellator.addVertexWithUV(d9, d11, d13, d3, d5);
            tessellator.addVertexWithUV(d9, d11, d13, d3, d5);
            tessellator.addVertexWithUV(d9, d10, d12, d3, d6);
            tessellator.addVertexWithUV(d8, d10, d12, d4, d6);
        }
        else if (slope != 1 && slope != 9)
        {
            if (slope == 2)
            {
                tessellator.addVertexWithUV(d8, d11, d13, d4, d5);
                tessellator.addVertexWithUV(d8, d11, d13, d4, d5);
                tessellator.addVertexWithUV(d9, d10, d12, d3, d6);
                tessellator.addVertexWithUV(d8, d10, d12, d4, d6);
            }
            else if (slope != 3 && slope != 11)
            {
                if (slope == 4)
                {
                    tessellator.addVertexWithUV(d8, d11, d12, d4, d5);
                    tessellator.addVertexWithUV(d9, d11, d12, d3, d5);
                    tessellator.addVertexWithUV(d9, d10, d13, d3, d6);
                    tessellator.addVertexWithUV(d9, d10, d13, d3, d6);
                }
                else if (slope != 5 && slope != 13)
                {
                    if (slope == 6)
                    {
                        tessellator.addVertexWithUV(d8, d11, d12, d4, d5);
                        tessellator.addVertexWithUV(d9, d11, d12, d3, d5);
                        tessellator.addVertexWithUV(d8, d10, d13, d4, d6);
                        tessellator.addVertexWithUV(d8, d10, d13, d4, d6);
                    }
                    else if (slope != 7 && slope != 15)
                    {
                        if (slope == 8)
                        {
                            tessellator.addVertexWithUV(d9, d11, d13, (d3 + d4) / 2.0D, d5);
                            tessellator.addVertexWithUV(d9, d11, d13, (d3 + d4) / 2.0D, d5);
                            tessellator.addVertexWithUV(d9, d10, d12, d3, d6);
                            tessellator.addVertexWithUV(d8, d10, d13, d4, d6);
                        }
                        else if (slope == 12)
                        {
                            tessellator.addVertexWithUV(d8, d11, d13, d4, d5);
                            tessellator.addVertexWithUV(d9, d11, d12, d3, d5);
                            tessellator.addVertexWithUV(d9, d10, d13, (d3 + d4) / 2.0D, d6);
                            tessellator.addVertexWithUV(d9, d10, d13, (d3 + d4) / 2.0D, d6);
                        }
                    }
                    else
                    {
                        tessellator.addVertexWithUV(d8, d11, d12, d4, d5);
                        tessellator.addVertexWithUV(d9, d11, d12, d3, d5);
                        tessellator.addVertexWithUV(d9, d10, d12, d3, d6);
                        tessellator.addVertexWithUV(d9, d10, d12, d3, d6);
                    }
                }
                else
                {
                    tessellator.addVertexWithUV(d8, d11, d12, d4, d5);
                    tessellator.addVertexWithUV(d9, d11, d12, d3, d5);
                    tessellator.addVertexWithUV(d8, d10, d12, d4, d6);
                    tessellator.addVertexWithUV(d8, d10, d12, d4, d6);
                }
            }
            else
            {
                tessellator.addVertexWithUV(d9, d11, d12, d3, d5);
                tessellator.addVertexWithUV(d9, d11, d12, d3, d5);
                tessellator.addVertexWithUV(d9, d10, d12, d3, d6);
                tessellator.addVertexWithUV(d8, d10, d12, d4, d6);
            }
        }
        else
        {
            tessellator.addVertexWithUV(d8, d11, d12, d4, d5);
            tessellator.addVertexWithUV(d8, d11, d12, d4, d5);
            tessellator.addVertexWithUV(d9, d10, d12, d3, d6);
            tessellator.addVertexWithUV(d8, d10, d12, d4, d6);
        }
    }

    public void renderCornersNorthFace(RenderBlocks renderBlocks, int slope, double x, double y, double z, IIcon icon)
    {
        Tessellator tessellator = Tessellator.instance;

        if (renderBlocks.hasOverrideBlockTexture())
        {
            icon = renderBlocks.overrideBlockTexture;
        }

        double d3 = (double)icon.getInterpolatedU(renderBlocks.renderMinZ * 16.0D);
        double d4 = (double)icon.getInterpolatedU(renderBlocks.renderMaxZ * 16.0D);
        double d5 = (double)icon.getInterpolatedV(16.0D - renderBlocks.renderMinY * 16.0D);
        double d6 = (double)icon.getInterpolatedV(16.0D - renderBlocks.renderMaxY * 16.0D);
        double d8 = x + renderBlocks.renderMinX;
        double d9 = y + renderBlocks.renderMinY;
        double d10 = y + renderBlocks.renderMaxY;
        double d11 = z + renderBlocks.renderMinZ;
        double d12 = z + renderBlocks.renderMaxZ;
        double d13 = x + renderBlocks.renderMaxX;

        if (slope == 0)
        {
            tessellator.addVertexWithUV(d13, d10, d12, d4, d5);
            tessellator.addVertexWithUV(d13, d10, d12, d4, d5);
            tessellator.addVertexWithUV(d8, d9, d11, d3, d6);
            tessellator.addVertexWithUV(d8, d9, d12, d4, d6);
        }
        else if (slope != 1 && slope != 9)
        {
            if (slope != 2 && slope != 10)
            {
                if (slope == 3)
                {
                    tessellator.addVertexWithUV(d13, d10, d11, d3, d5);
                    tessellator.addVertexWithUV(d13, d10, d11, d3, d5);
                    tessellator.addVertexWithUV(d8, d9, d11, d3, d6);
                    tessellator.addVertexWithUV(d8, d9, d12, d4, d6);
                }
                else if (slope == 4)
                {
                    tessellator.addVertexWithUV(d8, d10, d12, d4, d5);
                    tessellator.addVertexWithUV(d8, d10, d11, d3, d5);
                    tessellator.addVertexWithUV(d13, d9, d12, d4, d6);
                    tessellator.addVertexWithUV(d13, d9, d12, d4, d6);
                }
                else if (slope != 5 && slope != 13)
                {
                    if (slope != 6 && slope != 14)
                    {
                        if (slope == 7)
                        {
                            tessellator.addVertexWithUV(d8, d10, d12, d4, d5);
                            tessellator.addVertexWithUV(d8, d10, d11, d3, d5);
                            tessellator.addVertexWithUV(d13, d9, d11, d3, d6);
                            tessellator.addVertexWithUV(d13, d9, d11, d3, d6);
                        }
                        else if (slope == 11)
                        {
                            tessellator.addVertexWithUV(d13, d10, d11, (d3 + d4) / 2.0D, d5);
                            tessellator.addVertexWithUV(d13, d10, d11, (d3 + d4) / 2.0D, d5);
                            tessellator.addVertexWithUV(d8, d9, d11, d3, d6);
                            tessellator.addVertexWithUV(d13, d9, d12, d4, d6);
                        }
                        else if (slope == 15)
                        {
                            tessellator.addVertexWithUV(d13, d10, d12, d4, d5);
                            tessellator.addVertexWithUV(d8, d10, d11, d3, d5);
                            tessellator.addVertexWithUV(d13, d9, d11, (d3 + d4) / 2.0D, d6);
                            tessellator.addVertexWithUV(d13, d9, d11, (d3 + d4) / 2.0D, d6);
                        }
                    }
                    else
                    {
                        tessellator.addVertexWithUV(d8, d10, d12, d4, d5);
                        tessellator.addVertexWithUV(d8, d10, d11, d3, d5);
                        tessellator.addVertexWithUV(d8, d9, d12, d4, d6);
                        tessellator.addVertexWithUV(d8, d9, d12, d4, d6);
                    }
                }
                else
                {
                    tessellator.addVertexWithUV(d8, d10, d12, d4, d5);
                    tessellator.addVertexWithUV(d8, d10, d11, d3, d5);
                    tessellator.addVertexWithUV(d8, d9, d11, d3, d6);
                    tessellator.addVertexWithUV(d8, d9, d11, d3, d6);
                }
            }
            else
            {
                tessellator.addVertexWithUV(d8, d10, d12, d4, d5);
                tessellator.addVertexWithUV(d8, d10, d12, d4, d5);
                tessellator.addVertexWithUV(d8, d9, d11, d3, d6);
                tessellator.addVertexWithUV(d8, d9, d12, d4, d6);
            }
        }
        else
        {
            tessellator.addVertexWithUV(d8, d10, d11, d3, d5);
            tessellator.addVertexWithUV(d8, d10, d11, d3, d5);
            tessellator.addVertexWithUV(d8, d9, d11, d3, d6);
            tessellator.addVertexWithUV(d8, d9, d12, d4, d6);
        }
    }

    public void renderCornersSouthFace(RenderBlocks renderBlocks, int slope, double x, double y, double z, IIcon icon)
    {
        Tessellator tessellator = Tessellator.instance;

        if (renderBlocks.hasOverrideBlockTexture())
        {
            icon = renderBlocks.overrideBlockTexture;
        }

        double d3 = (double)icon.getInterpolatedU(renderBlocks.renderMinZ * 16.0D);
        double d4 = (double)icon.getInterpolatedU(renderBlocks.renderMaxZ * 16.0D);
        double d5 = (double)icon.getInterpolatedV(16.0D - renderBlocks.renderMinY * 16.0D);
        double d6 = (double)icon.getInterpolatedV(16.0D - renderBlocks.renderMaxY * 16.0D);
        double d8 = x + renderBlocks.renderMaxX;
        double d9 = y + renderBlocks.renderMinY;
        double d10 = y + renderBlocks.renderMaxY;
        double d11 = z + renderBlocks.renderMinZ;
        double d12 = z + renderBlocks.renderMaxZ;
        double d13 = x + renderBlocks.renderMinX;

        if (slope != 0 && slope != 8)
        {
            if (slope == 1)
            {
                tessellator.addVertexWithUV(d8, d9, d12, d3, d6);
                tessellator.addVertexWithUV(d8, d9, d11, d4, d6);
                tessellator.addVertexWithUV(d13, d10, d11, d4, d5);
                tessellator.addVertexWithUV(d13, d10, d11, d4, d5);
            }
            else if (slope == 2)
            {
                tessellator.addVertexWithUV(d8, d9, d12, d3, d6);
                tessellator.addVertexWithUV(d8, d9, d11, d4, d6);
                tessellator.addVertexWithUV(d13, d10, d12, d3, d5);
                tessellator.addVertexWithUV(d13, d10, d12, d3, d5);
            }
            else if (slope != 3 && slope != 11)
            {
                if (slope != 4 && slope != 12)
                {
                    if (slope == 5)
                    {
                        tessellator.addVertexWithUV(d13, d9, d11, d4, d6);
                        tessellator.addVertexWithUV(d13, d9, d11, d4, d6);
                        tessellator.addVertexWithUV(d8, d10, d11, d4, d5);
                        tessellator.addVertexWithUV(d8, d10, d12, d3, d5);
                    }
                    else if (slope == 6)
                    {
                        tessellator.addVertexWithUV(d13, d9, d12, d3, d6);
                        tessellator.addVertexWithUV(d13, d9, d12, d3, d6);
                        tessellator.addVertexWithUV(d8, d10, d11, d4, d5);
                        tessellator.addVertexWithUV(d8, d10, d12, d3, d5);
                    }
                    else if (slope != 7 && slope != 15)
                    {
                        if (slope == 10)
                        {
                            tessellator.addVertexWithUV(d8, d9, d12, d3, d6);
                            tessellator.addVertexWithUV(d13, d9, d11, d4, d6);
                            tessellator.addVertexWithUV(d13, d10, d12, (d3 + d4) / 2.0D, d5);
                            tessellator.addVertexWithUV(d13, d10, d12, (d3 + d4) / 2.0D, d5);
                        }
                        else if (slope == 14)
                        {
                            tessellator.addVertexWithUV(d13, d9, d12, (d3 + d4) / 2.0D, d6);
                            tessellator.addVertexWithUV(d13, d9, d12, (d3 + d4) / 2.0D, d6);
                            tessellator.addVertexWithUV(d13, d10, d11, d4, d5);
                            tessellator.addVertexWithUV(d8, d10, d12, d3, d5);
                        }
                    }
                    else
                    {
                        tessellator.addVertexWithUV(d8, d9, d11, d4, d6);
                        tessellator.addVertexWithUV(d8, d9, d11, d4, d6);
                        tessellator.addVertexWithUV(d8, d10, d11, d4, d5);
                        tessellator.addVertexWithUV(d8, d10, d12, d3, d5);
                    }
                }
                else
                {
                    tessellator.addVertexWithUV(d8, d9, d12, d3, d6);
                    tessellator.addVertexWithUV(d8, d9, d12, d3, d6);
                    tessellator.addVertexWithUV(d8, d10, d11, d4, d5);
                    tessellator.addVertexWithUV(d8, d10, d12, d3, d5);
                }
            }
            else
            {
                tessellator.addVertexWithUV(d8, d9, d12, d3, d6);
                tessellator.addVertexWithUV(d8, d9, d11, d4, d6);
                tessellator.addVertexWithUV(d8, d10, d11, d4, d5);
                tessellator.addVertexWithUV(d8, d10, d11, d4, d5);
            }
        }
        else
        {
            tessellator.addVertexWithUV(d8, d9, d12, d3, d6);
            tessellator.addVertexWithUV(d8, d9, d11, d4, d6);
            tessellator.addVertexWithUV(d8, d10, d12, d3, d5);
            tessellator.addVertexWithUV(d8, d10, d12, d3, d5);
        }
    }

    public void renderCornersTopFace(RenderBlocks renderBlocks, int slope, double x, double y, double z, IIcon icon)
    {
        Tessellator tessellator = Tessellator.instance;

        if (renderBlocks.hasOverrideBlockTexture())
        {
            icon = renderBlocks.overrideBlockTexture;
        }

        double d3 = (double)icon.getInterpolatedU(renderBlocks.renderMinX * 16.0D);
        double d4 = (double)icon.getInterpolatedU(renderBlocks.renderMaxX * 16.0D);
        double d5 = (double)icon.getInterpolatedV(16.0D - renderBlocks.renderMinZ * 16.0D);
        double d6 = (double)icon.getInterpolatedV(16.0D - renderBlocks.renderMaxZ * 16.0D);
        double d7 = x + renderBlocks.renderMinX;
        double d8 = x + renderBlocks.renderMaxX;
        double d9 = y + renderBlocks.renderMaxY;
        double d10 = z + renderBlocks.renderMinZ;
        double d11 = z + renderBlocks.renderMaxZ;

        if (slope / 4 == 1)
        {
            tessellator.addVertexWithUV(d8, d9, d11, d4, d6);
            tessellator.addVertexWithUV(d8, d9, d10, d4, d5);
            tessellator.addVertexWithUV(d7, d9, d10, d3, d5);
            tessellator.addVertexWithUV(d7, d9, d11, d3, d6);
        }
        else if (slope == 12)
        {
            tessellator.addVertexWithUV(d8, d9, d11, d4, d6);
            tessellator.addVertexWithUV(d8, d9, d10, d4, d5);
            tessellator.addVertexWithUV(d7, d9, d11, d3, d6);
            tessellator.addVertexWithUV(d7, d9, d11, d3, d6);
        }
        else if (slope == 13)
        {
            tessellator.addVertexWithUV(d8, d9, d10, d4, d5);
            tessellator.addVertexWithUV(d8, d9, d10, d4, d5);
            tessellator.addVertexWithUV(d7, d9, d10, d3, d5);
            tessellator.addVertexWithUV(d7, d9, d11, d3, d6);
        }
        else if (slope == 14)
        {
            tessellator.addVertexWithUV(d8, d9, d11, d4, d6);
            tessellator.addVertexWithUV(d7, d9, d10, d3, d5);
            tessellator.addVertexWithUV(d7, d9, d10, d3, d5);
            tessellator.addVertexWithUV(d7, d9, d11, d3, d6);
        }
        else if (slope == 15)
        {
            tessellator.addVertexWithUV(d8, d9, d11, d4, d6);
            tessellator.addVertexWithUV(d8, d9, d10, d4, d5);
            tessellator.addVertexWithUV(d7, d9, d10, d3, d5);
            tessellator.addVertexWithUV(d8, d9, d11, d4, d6);
        }
    }

    public void renderCornersWestFace(RenderBlocks renderBlocks, int slope, double x, double y, double z, IIcon icon)
    {
        Tessellator tessellator = Tessellator.instance;

        if (renderBlocks.hasOverrideBlockTexture())
        {
            icon = renderBlocks.overrideBlockTexture;
        }

        double d3 = (double)icon.getInterpolatedU(renderBlocks.renderMinX * 16.0D);
        double d4 = (double)icon.getInterpolatedU(renderBlocks.renderMaxX * 16.0D);
        double d5 = (double)icon.getInterpolatedV(16.0D - renderBlocks.renderMinY * 16.0D);
        double d6 = (double)icon.getInterpolatedV(16.0D - renderBlocks.renderMaxY * 16.0D);
        double d8 = x + renderBlocks.renderMinX;
        double d9 = x + renderBlocks.renderMaxX;
        double d10 = y + renderBlocks.renderMinY;
        double d11 = y + renderBlocks.renderMaxY;
        double d12 = z + renderBlocks.renderMaxZ;
        double d13 = z + renderBlocks.renderMinZ;

        if (slope != 0 && slope != 8)
        {
            if (slope == 1)
            {
                tessellator.addVertexWithUV(d8, d11, d13, d3, d5);
                tessellator.addVertexWithUV(d8, d10, d12, d3, d6);
                tessellator.addVertexWithUV(d9, d10, d12, d4, d6);
                tessellator.addVertexWithUV(d8, d11, d13, d3, d5);
            }
            else if (slope != 2 && slope != 10)
            {
                if (slope == 3)
                {
                    tessellator.addVertexWithUV(d9, d11, d13, d4, d5);
                    tessellator.addVertexWithUV(d8, d10, d12, d3, d6);
                    tessellator.addVertexWithUV(d9, d10, d12, d4, d6);
                    tessellator.addVertexWithUV(d9, d11, d13, d4, d5);
                }
                else if (slope != 4 && slope != 12)
                {
                    if (slope == 5)
                    {
                        tessellator.addVertexWithUV(d8, d11, d12, d3, d5);
                        tessellator.addVertexWithUV(d8, d10, d13, d3, d6);
                        tessellator.addVertexWithUV(d8, d10, d13, d3, d6);
                        tessellator.addVertexWithUV(d9, d11, d12, d4, d5);
                    }
                    else if (slope != 6 && slope != 14)
                    {
                        if (slope == 7)
                        {
                            tessellator.addVertexWithUV(d8, d11, d12, d3, d5);
                            tessellator.addVertexWithUV(d9, d10, d13, d4, d6);
                            tessellator.addVertexWithUV(d9, d10, d13, d4, d6);
                            tessellator.addVertexWithUV(d9, d11, d12, d4, d5);
                        }
                        else if (slope == 9)
                        {
                            tessellator.addVertexWithUV(d8, d11, d13, (d3 + d4) / 2.0D, d5);
                            tessellator.addVertexWithUV(d8, d10, d12, d3, d6);
                            tessellator.addVertexWithUV(d9, d10, d13, d4, d6);
                            tessellator.addVertexWithUV(d8, d11, d13, (d3 + d4) / 2.0D, d5);
                        }
                        else if (slope == 13)
                        {
                            tessellator.addVertexWithUV(d8, d11, d12, d3, d5);
                            tessellator.addVertexWithUV(d8, d10, d13, (d3 + d4) / 2.0D, d6);
                            tessellator.addVertexWithUV(d8, d10, d13, (d3 + d4) / 2.0D, d6);
                            tessellator.addVertexWithUV(d9, d11, d13, d4, d5);
                        }
                    }
                    else
                    {
                        tessellator.addVertexWithUV(d8, d11, d12, d3, d5);
                        tessellator.addVertexWithUV(d8, d10, d12, d3, d6);
                        tessellator.addVertexWithUV(d8, d10, d12, d3, d6);
                        tessellator.addVertexWithUV(d9, d11, d12, d4, d5);
                    }
                }
                else
                {
                    tessellator.addVertexWithUV(d8, d11, d12, d3, d5);
                    tessellator.addVertexWithUV(d9, d10, d12, d4, d6);
                    tessellator.addVertexWithUV(d9, d10, d12, d4, d6);
                    tessellator.addVertexWithUV(d9, d11, d12, d4, d5);
                }
            }
            else
            {
                tessellator.addVertexWithUV(d8, d11, d12, d3, d5);
                tessellator.addVertexWithUV(d8, d10, d12, d3, d6);
                tessellator.addVertexWithUV(d9, d10, d12, d4, d6);
                tessellator.addVertexWithUV(d8, d11, d12, d3, d5);
            }
        }
        else
        {
            tessellator.addVertexWithUV(d9, d11, d12, d4, d5);
            tessellator.addVertexWithUV(d8, d10, d12, d3, d6);
            tessellator.addVertexWithUV(d9, d10, d12, d4, d6);
            tessellator.addVertexWithUV(d9, d11, d12, d4, d5);
        }
    }

    @SideOnly(Side.CLIENT)
    /**
     * Returns the default ambient occlusion value based on block opacity
     */
    public float getAmbientOcclusionLightValue(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return par1IBlockAccess.getBlock(par2, par3, par4).isNormalCube() ? 0.2F : 1.0F;
    }
}
