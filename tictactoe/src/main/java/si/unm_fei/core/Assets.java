package si.unm_fei.core;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public final class Assets {

    private final Map<String, BufferedImage> images = new HashMap<>();

    public Assets() {
        load("/x_white.png");
        load("/o_white.png");
        load("/x_black.png");
        load("/o_black.png");

        load("/unm_logoFull.png");
    }

    private void load(String path) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalStateException("Missing resource on classpath: " + path);
            }
            images.put(path, ImageIO.read(is));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load resource: " + path, e);
        }
    }

    public BufferedImage getImage(String path) {
        return images.get(path);
    }
}
