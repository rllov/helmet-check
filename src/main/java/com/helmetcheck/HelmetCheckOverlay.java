package com.helmetcheck;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import java.awt.*;

public class HelmetCheckOverlay extends Overlay {
    private final Client client;
    private final HelmetCheckPlugin plugin;

    @Inject
    public HelmetCheckOverlay(Client client, HelmetCheckPlugin plugin) {
        this.client = client;
        this.plugin = plugin;
        setPosition(OverlayPosition.TOP_LEFT);
    }
    @Override
    public Dimension render(Graphics2D graphics){
        if(!plugin.isWearingHelmet()){
            graphics.setColor(Color.RED);
            graphics.drawString("NO HELMET!",10,20);
        }
        return null;
    }
}
