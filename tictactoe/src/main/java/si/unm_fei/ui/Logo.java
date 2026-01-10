package si.unm_fei.ui;

import si.unm_fei.core.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Logo {
    private BufferedImage logo;

    public Logo(Assets assets) {
        logo = assets.getImage("/unm_logoFull.png");
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(logo, 20, 20, logo.getWidth(), logo.getHeight(), null);
    }
}
