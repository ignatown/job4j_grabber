package html;

import utils.SqlRuDateTimeParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private final Connection cnn;

    public static void main(String[] args) throws IOException, SQLException {
        Properties in = new Properties();
        in.load(new FileInputStream("C:\\projects\\job4j_grabber\\src\\main\\resources\\app.properties"));
        PsqlStore psqlStore = new PsqlStore(in);
        SqlRuDateTimeParser sqlRuDateTimeParser = new SqlRuDateTimeParser();
        ConnectionRollback.create(psqlStore.cnn);
        SqlRuParse sqlRuParse = new SqlRuParse(sqlRuDateTimeParser);
        sqlRuParse.list("https://www.sql.ru/forum/job-offers").forEach(psqlStore::save);
        psqlStore.getAll().forEach(x -> System.out.println(x));
        System.out.println(psqlStore.findById(5));
    }

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
            this.cnn = DriverManager.getConnection(cfg.getProperty("jdbc.url"), cfg.getProperty("jdbc.userName"), cfg.getProperty("jdbc.userPassword"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Post post)  {
        try (PreparedStatement pr = this.cnn.prepareStatement("insert into post(name, text, link, created) values(?, ?, ?, ?)  on conflict do nothing", Statement.RETURN_GENERATED_KEYS)) {
            pr.setString(1, post.getTitle());
            pr.setString(2, post.getDescription());
            pr.setString(3, post.getLink());
            pr.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
            pr.execute();
            try (ResultSet set = pr.getGeneratedKeys()) {
                if (set.next()) {
                    post.setId(set.getInt(1));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new LinkedList<>();
        try (PreparedStatement pr = this.cnn.prepareStatement("select * from post")) {
            ResultSet resultSet = pr.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("name");
                    String description = resultSet.getString("text");
                    String link = resultSet.getString("link");
                    LocalDateTime created = resultSet.getTimestamp("created").toLocalDateTime();
                    posts.add(new Post(id, title, description, link, created));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post findById(int id) {
        Post post = null;
        try (PreparedStatement pr = cnn.prepareStatement("select * from post where id = ?")) {
            pr.setInt(1, id);
            ResultSet resultSet = pr.executeQuery();
                if (resultSet.next()) {
                    int idPost = resultSet.getInt("id");
                    String title = resultSet.getString("name");
                    String description = resultSet.getString("text");
                    String link = resultSet.getString("link");
                    LocalDateTime created = resultSet.getTimestamp("created").toLocalDateTime();
                    post = new Post(idPost, title, description, link, created);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return post;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }
}