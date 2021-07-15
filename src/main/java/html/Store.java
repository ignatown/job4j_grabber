package html;

import java.sql.SQLException;
import java.util.List;

public interface Store {
    void save(Post post);

    List<Post> getAll();

    Post findById(int id) throws SQLException;
}