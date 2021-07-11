package html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        for (int j = 1; j <= 5; j++) {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + j).get();
        Elements row = doc.select(".postslisttopic");
        Elements alt = doc.getElementsByClass("altCol");
        int i = 1;
        for (Element td : row) {
            Element href = td.child(0);
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            Element pot = alt.get(i);
            System.out.println(pot.text());
            i = i + 2;
        }
    }
    }
}