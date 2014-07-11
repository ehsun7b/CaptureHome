package com.ehsunbehravesh.capturehome.dao;

import com.ehsunbehravesh.capturehome.entity.Image;
import com.ehsunbehravesh.capturehome.servlets.MemoryDatabase;
import java.awt.image.BufferedImage;

/**
 *
 * @author Ehsun Behravesh
 */
public class ImageDAO {

    public ImageDAO() {
        
    }

    public void insert(final BufferedImage image) {
        /*
        Entity result = new Entity(Image.KIND);

        result.setProperty("timestamp", image.getTimestamp());
        Text content = new Text(image.getContent());
        result.setProperty("content", content);

        Key key = ds.put(result);

        return result;*/
        MemoryDatabase.insert(image);        
    }

    public BufferedImage last() {
        /*
        Query q = new Query(Image.KIND);
        q.addSort("timestamp", Query.SortDirection.DESCENDING);
        PreparedQuery pq = ds.prepare(q);
        List<Entity> entities = pq.asList(FetchOptions.Builder.withDefaults());

        if (entities.size() <= 0) {
            return new Image(0, "");
        } else {
            Entity entity = entities.get(0);
            Text text = (Text) entity.getProperty("content");
            String content = text.getValue();
            Image image = new Image(Long.parseLong(entity.getProperty("timestamp").toString()), content);
            return image;
        }*/
        
        return MemoryDatabase.last();
    }
}
