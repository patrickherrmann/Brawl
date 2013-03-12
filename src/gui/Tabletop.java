/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import deepdish.GameObject;
import deepdish.Viewport;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author patrick
 */
public class Tabletop extends GameObject {

    private Image image;

    public Tabletop() {
        try {
            image = ImageIO.read(new File("tabletop.jpg"));
        } catch (IOException ex) {
            System.out.println("The tabletop image did not load properly.");
        }
        setZIndex(-1);
    }

    @Override
    public void paint(Viewport viewport) {
        viewport.getGraphics().drawImage(image, 0, 0, null);
    }

    @Override
    public void update() {
    }

}
