package com.ehsunbehravesh.capturehome.servlets;

import com.ehsunbehravesh.capturehome.entity.Image;
import com.ehsunbehravesh.capturehome.motion.MotionCurrent;
import java.awt.image.BufferedImage;

/**
 *
 * @author Ehsun Behravesh
 */
public class MemoryDatabase {

    //private static Image image;
    private static MotionCurrent current = new MotionCurrent(1, 10);
    
    /*
    
    public static void insert(Image image) {
        MemoryDatabase.image = image;
    }
    
    public static Image last() {
        return image != null ? image : new Image(0, "");
    }*/
    
    public static void insert(BufferedImage image) {
        current.enq(image);
    }
    
    public static BufferedImage last() {
        return current.deq();
    }
}
