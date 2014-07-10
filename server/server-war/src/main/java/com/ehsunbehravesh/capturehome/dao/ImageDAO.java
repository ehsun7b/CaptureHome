package com.ehsunbehravesh.capturehome.dao;

import com.ehsunbehravesh.capturehome.entity.Image;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

/**
 *
 * @author Ehsun Behravesh
 */
public class ImageDAO {

    private final DatastoreService ds;

    public ImageDAO(DatastoreService ds) {
        this.ds = ds;
    }
    
    public Entity insert(final Image image) {
        Entity result = new Entity(Image.KIND);
        
        result.setProperty("timestamp", image.getTimestamp());
        Text content = new Text(image.getContent());        
        result.setProperty("content", content);
        
        Key key = ds.put(result);
        
        return result;
    }
}
