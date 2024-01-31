package com.fatihyildizli.githubtrendtracker.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GithubModel {

    private String repository_name;
    private Long repository_total_star_count;
    private Long repository_daily_star_count;
    private Long repository_total_fork_count;
    private String repository_description;
    private String repository_language;
    private String repository_author;
    private String date;

}