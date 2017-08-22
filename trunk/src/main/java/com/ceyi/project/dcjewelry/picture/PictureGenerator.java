package com.ceyi.project.dcjewelry.picture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pw.wecode.project.framework.image.QrcodeGenerator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by lingh on 2017/5/3.
 */
@Component
public class PictureGenerator {
    private static final Logger logger = LoggerFactory.getLogger(PictureGenerator.class);
    private QrcodeGenerator qrcodeGenerator = new QrcodeGenerator();

    public BufferedImage generate(File logoFile, File txtFile, File articleFile, final String url) {
        try {
            BufferedImage qrcodeImg = qrcodeGenerator.generate(url, 200, 200);
            BufferedImage logoImg = ImageIO.read(logoFile);
            BufferedImage txtImg = ImageIO.read(txtFile);
            BufferedImage articleImg = ImageIO.read(articleFile);
            int logoWidth = logoImg.getWidth();
            int w1;
            int h1;
            if (articleImg.getWidth() > logoWidth || articleImg.getHeight() > logoWidth) {
                if (articleImg.getWidth() > articleImg.getHeight()) {
                    w1 = logoWidth;
                    h1 = logoWidth * articleImg.getHeight() / articleImg.getWidth();
                } else {
                    w1 = logoWidth * articleImg.getWidth() / articleImg.getHeight();
                    h1 = logoWidth;
                }
            } else {
                w1 = articleImg.getWidth();
                h1 = articleImg.getHeight();
            }
            int w = logoWidth + 10;
            int h = logoImg.getHeight() + logoWidth + qrcodeImg.getHeight() + 20;
            BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D gd = bufferedImage.createGraphics();
            gd.setBackground(Color.WHITE);
            gd.clearRect(0, 0, w, h);
            int x = w / 2 - w1 / 2;
            int y = logoWidth / 2 - h1 / 2 + logoImg.getHeight() + 10;
            gd.drawImage(logoImg, 5, 5, logoImg.getWidth(), logoImg.getHeight(), null);
            Image image = articleImg.getScaledInstance(w1, h1, Image.SCALE_SMOOTH);
            gd.drawImage(image, x, y, w1, h1, null);
            gd.drawImage(txtImg, 30, h - 60, txtImg.getWidth(), txtImg.getHeight(), null);
            gd.drawImage(qrcodeImg, w - qrcodeImg.getWidth() - 5, h - qrcodeImg.getHeight() - 5, qrcodeImg.getWidth(), qrcodeImg.getHeight(), null);
            gd.dispose();
            logger.debug("二维码生成成功 logoFile:{} txtFile:{} articleFile:{} url:{}", logoFile.getPath(), txtFile.getPath(), articleFile.getPath(), url);
            return bufferedImage;
        } catch (Exception e) {
            logger.error("二维码生成失败 logoFile:{} txtFile:{} articleFile:{} url:{} e:{}", logoFile.getPath(), txtFile.getPath(), articleFile.getPath(), url, e);
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        File logoFile = new File("D:/tmp/qrcode/qrcode_logo.png");
        File txtFile = new File("D:/tmp/qrcode/qrcode_txt.png");
        PictureGenerator pictureGenerator = new PictureGenerator();
        BufferedImage bufferedImage = pictureGenerator.generate(logoFile, txtFile, new File("D:/tmp/qrcode/1.png"), "http://www.baidu.com");
        ImageIO.write(bufferedImage, "png", new File("d:/tmp/qrcode/1_.png"));
        bufferedImage = pictureGenerator.generate(logoFile, txtFile, new File("D:/tmp/qrcode/2.png"), "http://www.baidu.com");
        ImageIO.write(bufferedImage, "png", new File("d:/tmp/qrcode/2_.png"));
        bufferedImage = pictureGenerator.generate(logoFile, txtFile, new File("D:/tmp/qrcode/3.png"), "http://www.baidu.com");
        ImageIO.write(bufferedImage, "png", new File("d:/tmp/qrcode/3_.png"));
        bufferedImage = pictureGenerator.generate(logoFile, txtFile, new File("D:/tmp/qrcode/4.jpg"), "http://www.baidu.com");
        ImageIO.write(bufferedImage, "png", new File("d:/tmp/qrcode/4_.png"));
        bufferedImage = pictureGenerator.generate(logoFile, txtFile, new File("D:/tmp/qrcode/5.jpg"), "http://www.baidu.com");
        ImageIO.write(bufferedImage, "png", new File("d:/tmp/qrcode/5_.png"));
        bufferedImage = pictureGenerator.generate(logoFile, txtFile, new File("D:/tmp/qrcode/6.png"), "http://www.baidu.com");
        ImageIO.write(bufferedImage, "png", new File("d:/tmp/qrcode/6_.png"));
    }
}
