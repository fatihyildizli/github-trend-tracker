package com.fatihyildizli.githubtrendtracker.service;

import com.fatihyildizli.githubtrendtracker.model.GithubModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class GithubService {


    @Value("${github_trend_url}")
    private String rootUrl;

    @Value("${github_trend_element_class_name}")
    private String github_trend_element_class_name;

    @Value("${github_trend_element_repository_link_name_class_name}")
    private String github_trend_element_repository_link_name_class_name;

    @Value("${github_trend_element_repository_total_metrics_class_name}")
    private String github_trend_element_repository_total_metrics_class_name;

    @Value("${github_trend_element_repository_daily_star_class_name}")
    private String github_trend_element_repository_daily_star_class_name;

    @Value("${github_trend_element_repository_attribute_name}")
    private String github_trend_element_repository_attribute_name;

    @Value("${github_trend_menu_language_class_name}")
    private String github_trend_menu_language_class_name;


    public List<GithubModel> getGithubTrendInfo(String language) {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://github.com" + language).get();
            Elements trends = doc.getElementsByClass(github_trend_element_class_name);
            List<GithubModel> githubModelList = new ArrayList<>();
            for (Element trend : trends) {
                if (trend.getElementsByClass(github_trend_element_repository_link_name_class_name).attr("href").getBytes().length > 0L &&
                        trend.getElementsByClass(github_trend_element_repository_total_metrics_class_name).size() > 1L &&
                        !trend.getElementsByClass(github_trend_element_repository_daily_star_class_name).isEmpty() &&
                        !trend.getElementsByClass(github_trend_element_repository_link_name_class_name).attr("href").isEmpty()
                ) {
                    String repository_name = trend.getElementsByClass(github_trend_element_repository_link_name_class_name).attr("href").split("/")[2];
                    String repository_author = trend.getElementsByClass(github_trend_element_repository_link_name_class_name).attr("href").split("/")[1];
                    String repository_description = trend.getElementsByTag("p").text();
                    Long repository_total_star_count = Long.valueOf(trend.getElementsByClass(github_trend_element_repository_total_metrics_class_name).get(0).text().replaceAll(",", ""));
                    Long repository_daily_star_count = Long.valueOf(trend.getElementsByClass(github_trend_element_repository_daily_star_class_name).text().replace(" stars today", "").replaceAll(",", "").replaceAll(" star today", ""));
                    Long repository_total_fork_count = Long.valueOf(trend.getElementsByClass(github_trend_element_repository_total_metrics_class_name).get(1).text().replaceAll(",", ""));
                    String repository_language = trend.getElementsByAttribute(github_trend_element_repository_attribute_name).text();

                    GithubModel githubModel = new GithubModel();
                    githubModel.setRepository_name(repository_name);
                    githubModel.setRepository_author(repository_author);
                    githubModel.setRepository_description(repository_description);
                    githubModel.setRepository_total_star_count(repository_total_star_count);
                    githubModel.setRepository_daily_star_count(repository_daily_star_count);
                    githubModel.setRepository_total_fork_count(repository_total_fork_count);
                    githubModel.setRepository_language(repository_language);
                    githubModel.setDate(DateTimeFormatter.BASIC_ISO_DATE.format(LocalDate.now()));
                    githubModelList.add(githubModel);
                }
            }

            return githubModelList;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<GithubModel> getGithubTrendInfo() {
        Document doc = null;
        try {
            doc = Jsoup.connect(rootUrl).get();
            Elements trends = doc.getElementsByClass(github_trend_element_class_name);
            List<GithubModel> githubModelList = new ArrayList<>();
            for (Element trend : trends) {
                String repository_name = trend.getElementsByClass(github_trend_element_repository_link_name_class_name).attr("href").split("/")[2];
                String repository_author = trend.getElementsByClass(github_trend_element_repository_link_name_class_name).attr("href").split("/")[1];
                String repository_description = trend.getElementsByTag("p").text();
                Long repository_total_star_count = Long.valueOf(trend.getElementsByClass(github_trend_element_repository_total_metrics_class_name).get(0).text().replaceAll(",", ""));
                Long repository_daily_star_count = Long.valueOf(trend.getElementsByClass(github_trend_element_repository_daily_star_class_name).text().replace(" stars today", "").replaceAll(",", "").replaceAll(" star today", ""));
                Long repository_total_fork_count = Long.valueOf(trend.getElementsByClass(github_trend_element_repository_total_metrics_class_name).get(1).text().replaceAll(",", ""));
                String repository_language = trend.getElementsByAttribute(github_trend_element_repository_attribute_name).text();


                GithubModel githubModel = new GithubModel();
                githubModel.setRepository_name(repository_name);
                githubModel.setRepository_author(repository_author);
                githubModel.setRepository_description(repository_description);
                githubModel.setRepository_total_star_count(repository_total_star_count);
                githubModel.setRepository_daily_star_count(repository_daily_star_count);
                githubModel.setRepository_total_fork_count(repository_total_fork_count);
                githubModel.setRepository_language(repository_language);
                githubModel.setDate(DateTimeFormatter.BASIC_ISO_DATE.format(LocalDate.now()));
                githubModelList.add(githubModel);

            }

            return githubModelList;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getLanguages() {
        List<String> response = new ArrayList<>();
        Document doc = null;
        try {
            doc = Jsoup.connect(rootUrl).get();
            for (Element element : doc.getElementsByClass(github_trend_menu_language_class_name).get(1).getElementsByAttribute("href")) {
                response.add(element.attr("href").replace("?since=daily", ""));

            }
            response.removeIf(i -> i.equals("/trending/robots.txt"));
            return response;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<GithubModel> gitAllGithubTrendInfo() throws IOException {
        List<GithubModel> response = new ArrayList<>();
        List<String> languageList = getLanguages();
        int progressCount = 1;
        for (String language : languageList) {
            System.out.println("Language Steps:" + language + "-" + progressCount + "/" + languageList.size());
            response.addAll(getGithubTrendInfo(language));
            progressCount = progressCount + 1;
        }
        return response;
    }


}
