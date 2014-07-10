package com.ehsunbehravesh.capturehome.entity;

/**
 *
 * @author Ehsun Behravesh
 */
public class Image {
    public static final String KIND = "Image";
    private String timestamp;
    private String Content;

    public Image(String timestamp, String Content) {
        this.timestamp = timestamp;
        this.Content = Content;
    }

    public Image() {
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }
    
    
}
