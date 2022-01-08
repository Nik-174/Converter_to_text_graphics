package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

public class TextGraphicsConverterNew implements TextGraphicsConverter {

    private TextColorSchema Schema = new TextColorSchemaNew(new char[]{'#', '$', '@', '%', '*', '+', '-', '\''});
    private double aspectRatio = 0;
    private int Height = 0;
    private int Width = 0;


    @Override
    public String convert(String url) throws IOException, BadImageSizeException {

        BufferedImage img = ImageIO.read(new URL(url));

        int newWidth = img.getWidth();
        int newHeight = img.getHeight();
        double ratio = newWidth / newHeight;
        if ((Height != 0 || Width != 0) && (newWidth > Height || newWidth > Width)) {
            newHeight = Height;
            newWidth = Width;
        }
        if (aspectRatio != 0 && ratio > aspectRatio) {
            throw new BadImageSizeException(ratio, aspectRatio);
        }
        char[][] picture = new char[newHeight][newWidth * 2];

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        WritableRaster bwRaster = bwImg.getRaster();


        for (int h = 0; h < newHeight; h++) {
            for (int w = 0, z = 0; w < newWidth; w++, z++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = Schema.convert(color);
                picture[h][z] = c;
                z++;
                picture[h][z] = c;
            }
        }
        StringBuilder finalPicture = new StringBuilder();
        for (char[] row : picture) {
            finalPicture.append(row).append("\n");

        }

        return finalPicture.toString();
    }

    @Override
    public void setWidth(int width) {
        this.Width = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.Height = height;
    }

    @Override
    public void setMaxRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.Schema = schema;
    }
}
