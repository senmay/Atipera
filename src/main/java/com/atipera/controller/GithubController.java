package com.atipera.controller;

import com.atipera.model.RepositoryInfoDto;
import com.atipera.services.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("github")
public class GithubController {
    private final GithubService githubService;
    @GetMapping("/repos")
    @ResponseStatus(HttpStatus.OK)
    public List<RepositoryInfoDto> getAllNonForkReposWithBranchInfo (@RequestParam String user) {
        return githubService.getAllNonForkReposWithBranches(user);
    }
}
