package com.ehsunbehravesh.capturehome.dao;

import com.ehsunbehravesh.capturehome.entity.Image;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;
import java.util.List;

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

    public Image last() {
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
        }
    }
}
