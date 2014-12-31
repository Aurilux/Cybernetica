package com.vivalux.cyb.client.gui.lexica;

import com.vivalux.cyb.Cybernetica;
import com.vivalux.cyb.util.CYBResources;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuiLexica extends GuiScreen {
    private final ResourceLocation texture = CYBResources.TEXTURE_GUI_LEXICA;

    private LexicaPage currentPage;

    private int guiWidth = 176, guiHeight = 166;
    private int left = 0, top = 0;

    private double radius = 50, diameter = radius * 2;

    @Override
    public void initGui() {
        refreshDisplay();

        /*
        //TODO finish this once I have a better understanding of openGL
        String[] topics = new String[] {
            "implants", "modules", "blocks", "items"
        };

        for (String topic : topics) {
            buttonList.add(new Tag(idCount++, left, top, 50, 20, topic));
        }

        //must be after all other buttons and tags have been added
        initTags();
        */
    }

    public void refreshDisplay() {
        currentPage = new LexicaPage(Cybernetica.proxy.currentLexicaTopic);

        left = width / 2 - guiWidth / 2;
        top = height / 2 - guiHeight / 2;

        int idCount = 0;
        int offset = 11;
        int buttonHeight = 20;
        int verticalSpacing = 2;
        String[] entries = currentPage.getEntries();
        buttonList.clear();
        for (int i = 0; i < entries.length && entries.length > 1; i++) {
            int x = left + offset + 0;
            int y = top + offset + i * (buttonHeight + verticalSpacing);
            buttonList.add(new GuiButton(idCount, x, y, 50, buttonHeight, entries[i]));
            idCount++;
        }
        if (!Cybernetica.proxy.currentLexicaTopic.equals("home")) {
            buttonList.add(new GuiButton(idCount, left + offset, top + offset + 5 * (buttonHeight + verticalSpacing), 50, buttonHeight, "Home"));
        }
    }

    public void initTags() {
        List<Tag> tagList = getTags();
        int max = tagList.size();
        for (int i = 0; i < max; i++) {
            double phi = Math.acos((2 * (i + 1)) / (double)max - 1);
            double theta = Math.sqrt(max * Math.PI) * phi;
            Tag currentTag = tagList.get(i);
            currentTag.xPosition = left + (int)(radius * Math.cos(theta) * Math.sin(phi));
            currentTag.yPosition = top + (int)(radius * Math.sin(theta) * Math.sin(phi));
            currentTag.zPosition = (int)(radius * Math.cos(phi));
            currentTag.setScale(Math.abs(currentTag.zPosition / radius));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        //sortTags();
        //draw the background
        GL11.glColor4f(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(texture);
        drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight);

        //TODO comment this out once I get tags working? I'll can make my own method to render the tags
        super.drawScreen(mouseX, mouseY, partialTicks);

        currentPage.renderDisplayInfo();
    }

    /**
     * Sorts the tag list based on the tag's z-index
     * Lower z-indeces will be ordered first (0 index)
     */
    private void sortTags() {
        //get a subset of the array list that only includes tag buttons
        List tags = getTags();
        buttonList.removeAll(tags);
        Collections.sort(tags);
        buttonList.addAll(tags);
    }

    /**
     * Gets only the tags from 'buttonList'. Since we are only adding 3 other non-tag buttons
     * we begin the search from the 3rd item (index 3 because its inclusive)
     * @return a subset of 'buttonList' containing only tags
     */
    private List getTags() {
        List tags = new ArrayList();
        for (Object button : buttonList) {
            if (button instanceof Tag) {
                tags.add(button);
            }
        }
        return tags;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        Cybernetica.proxy.currentLexicaTopic = button.displayString.toLowerCase();
        //currentPage = new LexicaPage(button.displayString.toLowerCase());
        refreshDisplay();
    }

    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int which) {
        super.mouseMovedOrUp(mouseX, mouseY, which);
        //TODO this is where I calculate the tag rotation and scale
    }
}