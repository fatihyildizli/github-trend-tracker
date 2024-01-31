package com.fatihyildizli.githubtrendtracker.controller;

import com.fatihyildizli.githubtrendtracker.model.GithubModel;
import com.fatihyildizli.githubtrendtracker.service.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GithubController {

    @Autowired
    GithubService service;

    @CrossOrigin(origins = {"*"})
    @RequestMapping(path = "/githubTrends", method = RequestMethod.GET)
    @ResponseBody
    public List<GithubModel> getGithubTrends() throws Exception {

        return service.getGithubTrendInfo();

    }


    @CrossOrigin(origins = {"*"})
    @RequestMapping(path = "/languages", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getLanguages() {

        return service.getLanguages();

    }

    @CrossOrigin(origins = {"*"})
    @RequestMapping(path = "/githubTrends/all", method = RequestMethod.GET)
    @ResponseBody
    public List<GithubModel> getAllGithubTrends() throws Exception {

        return service.gitAllGithubTrendInfo();

    }


}
