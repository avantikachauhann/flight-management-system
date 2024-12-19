/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Saksham
 */
import javax.imageio.ImageIO;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {

    public static void writeOTP(String OTP){
        BufferedImage image = null;
        File imag = new File("C:\\Users\\Saksham\\IdeaProjects\\NTCC\\src\\Images\\Mail\\ConfirmEmail.png");
        try {
            image = ImageIO.read(imag);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Graphics g = image.getGraphics();
        Font f = new Font("Arial", Font.BOLD, 60);
        g.setFont(f);
        g.setColor(Color.black);
        g.drawString(OTP, 55, 470);
        g.dispose();

        try {
            ImageIO.write(image, "png",
                    new File("C:\\Users\\Saksham\\IdeaProjects\\NTCC\\src\\Images\\Mail\\ConfirmedEmail.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        writeOTP(Integer.toString(Database.getOTP()));
    }
}

