package com.atipera.it;

import com.atipera.ResourceFactory;
import com.atipera.controller.GithubController;
import com.atipera.exceptions.UserNotFoundException;
import com.atipera.model.RepositoryInfoDto;
import com.atipera.services.GithubService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = GithubController.class)
@AutoConfigureMockMvc(addFilters = false)
class GithubControllerTest extends ResourceFactory {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GithubService githubService;
    @Test
    void given_correct_user_when_getAllNonForkReposWithBranchInfo_then_return_repos() throws Exception {
        String user = "testUser";
        List<RepositoryInfoDto> expectedRepos = List.of(
                RepositoryInfoDto.builder().repositoryName("repo1").branches(createBranchInfoList(2)).build(),
                RepositoryInfoDto.builder().repositoryName("repo2").branches(createBranchInfoList(1)).build());

        given(githubService.getAllNonForkReposWithBranches(user)).willReturn(expectedRepos);

        mockMvc.perform(get("/github/repos")
                        .param("user", user)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$[0].repositoryName").value("repo1"))
                .andExpect(jsonPath("$[0].branches[0].branchName").value("branch1"))
                .andExpect(jsonPath("$[0].branches[0].lastCommitSha").value("sha0"))
                .andExpect(jsonPath("$[0].branches[1].branchName").value("branch2"))
                .andExpect(jsonPath("$[0].branches[1].lastCommitSha").value("sha1"))
                .andExpect(jsonPath("$[0].branches.length()").value(2))
                .andExpect(jsonPath("$[1].repositoryName").value("repo2"))
                .andExpect(jsonPath("$[1].branches[0].branchName").value("branch1"))
                .andExpect(jsonPath("$[1].branches.length()").value(1));
    }
    @Test
    void whenUserNotFound_thenThrowUserNotFoundException() throws Exception {
        String nonExistentUser = "nonExistentUser";
        Mockito.when(githubService.getAllNonForkReposWithBranches(nonExistentUser))
                .thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(get("/github/repos")
                        .param("user", nonExistentUser)
                        .contentType("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
                .andExpect(result -> assertEquals("User not found", result.getResolvedException().getMessage()));
    }
}
