package com.ehsunbehravesh.capturehome.servlets;

import com.ehsunbehravesh.capturehome.entity.Image;

/**
 *
 * @author Ehsun Behravesh
 */
public class MemoryDatabase {

    private static Image image;
    
    public static void insert(Image image) {
        MemoryDatabase.image = image;
    }
    
    public static Image last() {
        return image != null ? image : new Image(0, "");
    }
}
