package com.ehsunbehravesh.capturehome.motion;

import com.ehsunbehravesh.capturehome.entity.Image;
import java.awt.image.BufferedImage;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Ehsun Behravesh
 */
public class MotionCurrent {

    private final long id;
    private final int size;
    
    private final ConcurrentLinkedQueue<BufferedImage> current;    

    public MotionCurrent(long id, int size) {
        this.id = id;
        this.size = size;
        current = new ConcurrentLinkedQueue<BufferedImage>();
    }
    
    public void enq(BufferedImage img) {
        if (current.size() >= size) {
            current.poll();
        }        
        
        current.add(img);
    }
    
    public BufferedImage deq() {
        if (current.size() <= 0) {
            return null;
        }
        
        BufferedImage image = current.poll();
        
        if (current.size() <= 0) {
            current.add(image);
        }
        
        return image;
    }
}
