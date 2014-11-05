package com.vivalux.cyb.client.gui.lexica;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

public class Tag extends GuiButton implements Comparable {
    /** The default width value for all tags */
    private int defaultWidth = 50;
    /** The default height value for all tags */
    private int defaultHeight = 20;

    /** The 3D z-coordinate for this tag. Used to determine render order */
    public int zPosition;
    /** The scale of this tag. Gets used during rendering */
    private double scale;
    /** The image that will be displayed */
    //private Icon/Image image; //TODO how will I render this?

    public Tag(int id, int x, int y, int width, int height, String string) {
        super(id, x, y, width, height, string);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        //TODO modify this to render how I want
        if (this.visible) {
            //REMEMBER I need to take into account scaled resolution to make this work correctly
            FontRenderer fontrenderer = mc.fontRenderer;
            ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
            mc.getTextureManager().bindTexture(buttonTextures);
            int scaledWidth = this.getScaledWidth();
            int scaledHeight = this.getScaledHeight();
            int scaledX = this.adjustToFactor(this.xPosition);
            int scaledY = this.adjustToFactor(this.yPosition);
            System.out.println(res.getScaledHeight_double() + ", " + res.getScaledWidth_double() + ", " + res.getScaleFactor());
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = mouseX >= scaledX && mouseY >= scaledY
                && mouseX < scaledX + scaledWidth && mouseY < scaledY + scaledHeight;
            int hoverOffset = 46 + (this.getHoverState(this.field_146123_n) * 20);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            this.drawTexturedRect(scaledX,                   scaledY, 0,                     hoverOffset, scaledWidth / 2, scaledHeight);
            this.drawTexturedRect(scaledX + scaledWidth / 2, scaledY, 200 - scaledWidth / 2, hoverOffset, scaledWidth / 2, scaledHeight);
            this.mouseDragged(mc, mouseX, mouseY);
            int textColor = 14737632;

            if (packedFGColour != 0) {
                textColor = packedFGColour;
            }
            else if (!this.enabled) {
                textColor = 10526880;
            }
            else if (this.field_146123_n) {
                textColor = 16777120;
            }
            GL11.glDisable(GL11.GL_BLEND);

            GL11.glPushMatrix();
            GL11.glClear(256);
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0D, scaledWidth, scaledHeight, 0.0D, 1000.0D, 3000.0D);
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glTranslated(scaledX + scaledWidth / 2, scaledY + (scaledHeight) / 2, 0);
            GL11.glScaled(this.scale, this.scale, 1D);
            this.drawCenteredString(fontrenderer, this.displayString, scaledX + scaledWidth / 2, scaledY + (scaledHeight - 8) / 2, textColor);
            GL11.glTranslated(-(scaledX + scaledWidth / 2), -(scaledY + (scaledHeight) / 2), 0);
            //GL11.glScaled(2D, 2D, 1D); //1/this.scale, 1/this.scale, 1D);
            GL11.glPopMatrix();
        }
    }
    
    public void drawTexturedRect(int x, int y, int u, int v, int w, int h) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        //bottom-left x-y and u-v coords
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + h), (double)this.zLevel, (double)((float)(u + 0) * f), (double)((float)(v + this.defaultHeight) * f1));
        //bottom-right x-y and u-v coords
        tessellator.addVertexWithUV((double)(x + w), (double)(y + h), (double)this.zLevel, (double)((float)(u + w) * f), (double)((float)(v + this.defaultHeight) * f1));
        //top-left x-y and u-v coords
        tessellator.addVertexWithUV((double)(x + w), (double)(y + 0), (double)this.zLevel, (double)((float)(u + w) * f), (double)((float)(v + 0) * f1));
        //top-right x-y and u-v coords
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)this.zLevel, (double)((float)(u + 0) * f), (double)((float)(v + 0) * f1));
        tessellator.draw();
    }

    public int adjustToFactor(int fixed) {
        return (int) (fixed * (1 / scale));
    }

    @Override
    public int compareTo(Object otherTag) {
        return this.zPosition - ((Tag)otherTag).zPosition;
    }

    public void setScale(double s) {
        scale = s;
    }

    public int getScaledWidth() {
        return (int)Math.round(this.defaultWidth * this.scale);
    }

    public int getScaledHeight() {
        return (int)Math.round(this.defaultHeight * this.scale);
    }
}