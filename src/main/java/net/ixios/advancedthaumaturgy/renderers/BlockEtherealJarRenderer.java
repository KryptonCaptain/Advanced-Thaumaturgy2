package net.ixios.advancedthaumaturgy.renderers;

import net.ixios.advancedthaumaturgy.AdvThaum;
import net.ixios.advancedthaumaturgy.blocks.BlockEtherealJar;
import net.ixios.advancedthaumaturgy.tileentities.TileEtherealJar;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import cpw.mods.fml.client.FMLClientHandler;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.client.renderers.models.ModelJar;
import thaumcraft.common.blocks.BlockJar;
import thaumcraft.common.blocks.ItemJarFilled;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.tiles.*;

public class BlockEtherealJarRenderer extends TileEntitySpecialRenderer
{

	private ModelJar model;
	private ResourceLocation texture = new ResourceLocation("advthaum", "textures/models/etherealjar.png");
	
    public BlockEtherealJarRenderer()
    {
        model = new ModelJar();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f)
    {
    	TileEtherealJar tile= (TileEtherealJar)te;
    	
        float wobble = 0f;
     
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        GL11.glTranslatef((float)x + 0.5F, (float)y + 0.01F + wobble, (float)z + 0.5F);
        GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(0f, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(0f, 1.0F, 0.0F, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        if (tile.amount > 0)
            renderLiquid(tile, x, y, z, f);
            
        if (tile.aspectFilter != null)
        {
            GL11.glPushMatrix();
            switch (tile.facing)
            {
            case 3: // '\003'
                GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
                break;

            case 5: // '\005'
                GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
                break;

            case 4: // '\004'
                GL11.glRotatef(270F, 0.0F, 1.0F, 0.0F);
                break;
            }
            
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, -0.4F, 0.315F);
            UtilsFX.renderQuadCenteredFromTexture("textures/models/label.png", 0.5F, 1.0F, 1.0F, 1.0F, -99, 771, 1.0F);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, -0.4F, 0.316F);
            GL11.glScaled(0.021000000000000001D, 0.021000000000000001D, 0.021000000000000001D);
            GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
            UtilsFX.drawTag(-8, -8, ((TileJarFillable)tile).aspectFilter);
            GL11.glPopMatrix();
            GL11.glPopMatrix();
        }
        
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(getTexture());
        
        //GL11.glColor4f(1F, 1F, 1F, 0.5F);
        //GL11.glEnable(GL11.GL_BLEND);
        getModel().renderAll();
        //GL11.glDisable(GL11.GL_BLEND);
        
        GL11.glEnable(2884);
        GL11.glPopMatrix();
    }

    public void renderLiquid(TileEtherealJar te, double x, double y, double z, float f)
    {
        
        GL11.glPushMatrix();
        GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
        
        RenderBlocks renderBlocks = new RenderBlocks();
        GL11.glDisable(2896);
        float level = ((float)te.amount / (float)te.maxAmount) * 0.625F;
        Tessellator t = Tessellator.instance;
        renderBlocks.setRenderBounds(0.25D, 0.0625D, 0.25D, 0.75D, 0.0625D + (double)level, 0.75D);
        t.startDrawingQuads();
        
        if (te.aspect != null)
            t.setColorOpaque_I(te.aspect.getColor());
        
        int bright = 200;
        
        if (te.getWorldObj()!= null)
            bright = Math.max(200, ConfigBlocks.blockJar.getMixedBrightnessForBlock(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord));
        
        t.setBrightness(bright);
        
        IIcon icon = ((BlockJar)ConfigBlocks.blockJar).iconLiquid;

        this.bindTexture(TextureMap.locationBlocksTexture);

        
        Block jar = AdvThaum.EtherealJar;
        
        renderBlocks.renderFaceYNeg(jar, -0.5D, 0.0D, -0.5D, icon);
        renderBlocks.renderFaceYPos(jar, -0.5D, 0.0D, -0.5D, icon);
        renderBlocks.renderFaceZNeg(jar, -0.5D, 0.0D, -0.5D, icon);
        renderBlocks.renderFaceZPos(jar, -0.5D, 0.0D, -0.5D, icon);
        renderBlocks.renderFaceXNeg(jar, -0.5D, 0.0D, -0.5D, icon);
        renderBlocks.renderFaceXPos(jar, -0.5D, 0.0D, -0.5D, icon);
        
        t.draw();
        
        GL11.glEnable(2896);
        GL11.glPopMatrix();
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
    }

	public ModelJar getModel()
    {
	    return model;
    }

	public ResourceLocation getTexture()
    {
	    return texture;
    }

	public void setTexture(ResourceLocation texture)
    {
	    this.texture = texture;
    }

}