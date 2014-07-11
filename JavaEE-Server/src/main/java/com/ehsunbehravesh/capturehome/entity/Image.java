package com.ehsunbehravesh.capturehome.entity;

/**
 *
 * @author Ehsun Behravesh
 */
public class Image {
    public static final String KIND = "Image";
    private long timestamp;
    private String Content;

    public Image(long timestamp, String Content) {
        this.timestamp = timestamp;
        this.Content = Content;
    }

    public Image() {
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }
    
    
}
