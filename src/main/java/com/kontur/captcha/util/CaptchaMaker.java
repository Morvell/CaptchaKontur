package com.kontur.captcha.util;

import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CaptchaMaker {

    public static int rnd(int min, int max)
    {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public static int rnd(int max)
    {
        return (int) (Math.random() * ++max);
    }

    public Captcha make() throws IOException {
        int width = 150;
        int height = 50;

        char data[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q',
                       'r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9'};

        int rotate[] = {5,10,15,20,25,30,-5,-10,-15,-20,-25,-30};

        List<String> captchaAnswer = new ArrayList<>();

        for (int i=0; i< rnd(5,7); i++){
            captchaAnswer.add(String.valueOf(data[rnd(data.length-1)]));
        }


        BufferedImage bufferedImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bufferedImage.createGraphics();

        Font font = new Font("Georgia", Font.BOLD, 18);
        g2d.setFont(font);

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_SPEED);

        g2d.setRenderingHints(rh);

        GradientPaint gp = new GradientPaint(0, 0,
                Color.lightGray, 0, height/2, Color.white, true);

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(new Color(0, 0, 0));

        int x = 0;
        int y = 0;


        for (int i=0; i<captchaAnswer.size(); i++) {
            x += 10 + (Math.abs(rnd(100)) % 15);
            y = 20 + Math.abs(rnd(100)) % 20;

            AffineTransform affineTransform = new AffineTransform();
            affineTransform.rotate(Math.toRadians(rotate[rnd(rotate.length-1)]), 0, 0);
            Font rotatedFont = font.deriveFont(affineTransform);
            g2d.setFont(rotatedFont);
            g2d.drawString(captchaAnswer.get(i), x, y);


        }

        g2d.drawLine(rnd(25),rnd(25),rnd(50,150),rnd(50));
        g2d.drawLine(rnd(25),rnd(25),rnd(50,150),rnd(50));
        g2d.drawLine(rnd(50,150),rnd(25),rnd(50,150),rnd(25,50));
        g2d.drawLine(rnd(50),rnd(50),rnd(150),rnd(50));


        g2d.dispose();


        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(bufferedImage, "png", bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Captcha(imageString, captchaAnswer.toString());

    }
}
