package html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.DateTimeParser;
import utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {

    private final DateTimeParser dateTimeParser;

    public SqlRuParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    public SqlRuParse(SqlRuDateTimeParser sqlRuDateTimeParser) {
        this.dateTimeParser = sqlRuDateTimeParser;
    }

    public static void main(String[] args) {
    }

    @Override
    public List<Post> list(String link)  {
       List<Post> posts = new ArrayList<>();
      try { for (int j = 1; j <= 5; j++) {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + j).get();
        Elements row = doc.select(".postslisttopic");
        Elements alt = doc.getElementsByClass("altCol");
        int i = 1;
        for (Element td : row) {
            Element href = td.child(0);
            String parseLink = href.attr("href");
            posts.add(new Post(href.text(), detail(parseLink).getDescription(),
                    parseLink, dateTimeParser.parse(alt.get(i).text())));
            i = i + 2;
        }
      }
      } catch (IOException e) {
          e.printStackTrace();
      }
        return posts;
    }

    @Override
    public Post detail(String link) throws IOException {
        Document doc = Jsoup.connect(link).get();
        Elements row = doc.select(".msgBody");
        Elements row1 = doc.select(".msgFooter");
        return new Post(row.get(1).text(), dateTimeParser.parse(row1.get(0).text().split("\\[")[0]));
    }
}