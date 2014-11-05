package com.vivalux.cyb.client.gui.lexica;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;

public class LexicaPage {
    /**
     * The info to be displayed for this specific page
     */
    private String displayInfo;
    private String[] entries;
    /**
     * The top-most point of the info display area
     */
    private int top = 60;
    /**
     * The left-most point of the info display area
     */
    private int left = 240;
    /**
     * The width of the info display area
     */
    private int width = 95;
    /**
     * The height of the info display area
     */
    private int height = 110;

    public LexicaPage(String entryID) {
        this.displayInfo = StatCollector.translateToLocal("lexica." + entryID + ".info");
        this.entries = StatCollector.translateToLocal("lexica." + entryID + ".entries").split(", ");
    }

    public String getDisplayInfo() {
        return this.displayInfo;
    }

    public String[] getEntries() {
        return this.entries;
    }

    //goes through the string provided by localization and replaces image tags and recipes then displays the info on the page
    public void renderDisplayInfo() {
        //Testing
        this.renderText(this.displayInfo);

        //split the string into segments delineated by image and recipe tags, placing each into an array

        //go through the array and process each segment
        /*
        while() {
            if () {
                //if the current segment is only text, render the text
                this.renderText();
            }
            else if () {
                //if the current segment is an image tag, render the image
                this.renderImage();
            }
            else if () {
                //if the current segment is a recipe tag, render the recipe
                this.renderRecipe();
            }
        }
        */
    }

    private void renderText(String text) {
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer renderer = mc.fontRenderer;
        //remember the unicode flag for later
        boolean unicode = renderer.getUnicodeFlag();
        renderer.setUnicodeFlag(true);

        ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        //int x = this.left;
        int x = res.getScaledWidth() / 2 - 11;
        //int y = this.top;
        int y = res.getScaledHeight() / 2 - this.height / 2 - 19;
        String[] words = text.split(" ");
        ArrayList<String> lines = new ArrayList<String>();
        String currentLine = "";
        //split the text into lines bounded by the width of the info display area
        for (int i = 0; i < words.length; i++) {
            String token = words[i];
            if (currentLine == "") {
                currentLine = token;
            }
            else if (renderer.getStringWidth(currentLine + " " + token) > this.width) {
                lines.add(currentLine);
                currentLine = token;
            }
            else {
                currentLine += " " + token;
            }

            //if we are at the end of the words array, add our last line to the lines ArrayList
            if (i == words.length - 1) {
                lines.add(currentLine);
            }
        }

        //render all of the parsed line to the screen
        for (String line : lines) {
            renderer.drawString(line, x, y, 0xFFFFFF);
            y += 10;
        }

        //set the unicode flag back to the value it had before we began rendering our text
        renderer.setUnicodeFlag(unicode);
    }

    private void renderImage() {

    }

    private void renderRecipe() {

    }
}
