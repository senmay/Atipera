package com.atipera.services;

import com.atipera.mappers.RepositoryInfoMapper;
import com.atipera.model.BranchesResponse;
import com.atipera.model.GithubResponse;
import com.atipera.model.RepositoryInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class GithubService {
    private final HttpClientService httpClientService;
    private final RepositoryInfoMapper repositoryInfoMapper;

    public GithubService(HttpClientService httpClientService, RepositoryInfoMapper repositoryInfoMapper) {
        this.httpClientService = httpClientService;
        this.repositoryInfoMapper = repositoryInfoMapper;
    }

    public List<RepositoryInfoDto> getAllNonForkReposWithBranches(String user) {
        List<GithubResponse> repos = getAllNonForkReposFromUser(user);
        addAllBranchesToRepositories(user, repos);
        return repositoryInfoMapper.toDto(repos);
    }
    private List<GithubResponse> getAllNonForkReposFromUser(String user) {
        String url = "https://api.github.com/users/" + user + "/repos";
        log.warn("Fetching from: " + url);
        HttpRequest request = buildRequest(url);
        GithubResponse[] githubResponse = httpClientService.sendRequest(request, GithubResponse[].class);
        return Arrays.stream(githubResponse)
                .filter(repo -> !repo.isFork())
                .toList();
    }
    private List<BranchesResponse> getAllBranchesFromRepo(String user, String repo) {
        String url = "https://api.github.com/repos/" + user + "/" + repo + "/branches";
        log.warn("Fetching from: " + url);
        HttpRequest request = buildRequest(url);
        BranchesResponse[] branchesResponse = httpClientService.sendRequest(request, BranchesResponse[].class);
        return Arrays.asList(branchesResponse);
    }
    private void addAllBranchesToRepositories(String user, List<GithubResponse> repos) {
        repos.forEach(repo -> {
            List<BranchesResponse> branches = getAllBranchesFromRepo(user, repo.getName());
            branches.forEach(repo::addBranch);
        });
    }
    private HttpRequest buildRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .build();
    }

}
