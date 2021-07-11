package html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {

    public static void main(String[] args) throws IOException {

    }


    @Override
    public List<Post> list(String link) throws IOException {
        SqlRuDateTimeParser sqlRuDateTimeParser = new SqlRuDateTimeParser();
       List<Post> posts = new ArrayList<>();
       for (int j = 1; j <= 5; j++) {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + j).get();
        Elements row = doc.select(".postslisttopic");
        Elements alt = doc.getElementsByClass("altCol");
        int i = 1;
        for (Element td : row) {
            Element href = td.child(0);
            String parseLink = href.attr("href");
            posts.add(new Post(href.text(), detail(parseLink).getDescription(),
                    parseLink, sqlRuDateTimeParser.parse(alt.get(i).text())));
            i = i + 2;
        }
    }
        return posts;
    }

    @Override
    public Post detail(String link) throws IOException {
        SqlRuDateTimeParser sqlRuDateTimeParser = new SqlRuDateTimeParser();
        Document doc = Jsoup.connect("link").get();
        Elements row = doc.select(".msgBody");
        Elements row1 = doc.select(".msgFooter");
        return new Post(row.get(1).text(), sqlRuDateTimeParser.parse(row1.get(0).text().split("\\[")[0]));
    }
}