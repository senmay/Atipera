package com.atipera.mappers;

import com.atipera.ResourceFactory;
import com.atipera.model.GithubResponse;
import com.atipera.model.RepositoryInfoDto;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class RepositoryInfoMapperTest extends ResourceFactory {
    RepositoryInfoMapper repositoryInfoMapper = new RepositoryInfoMapper();
    @Test
    public void given_githubResponse_return_repositoryInfoDto() {
        // given
        GithubResponse githubResponse = createGithubResponse(2, false);
        // when
        RepositoryInfoDto repositoryInfoDto = repositoryInfoMapper.toDto(githubResponse);
        // then
        assertEquals(githubResponse.getName(), repositoryInfoDto.getRepositoryName());
        assertEquals(githubResponse.getOwner().getName(), repositoryInfoDto.getOwnerLogin());
        assertEquals(githubResponse.getBranches().size(), repositoryInfoDto.getBranches().size());
        assertEquals(githubResponse.getBranches().get(0).getName(), repositoryInfoDto.getBranches().get(0).getBranchName());
        assertEquals(githubResponse.getBranches().get(0).getCommit().getSha(), repositoryInfoDto.getBranches().get(0).getLastCommitSha());
        assertEquals(githubResponse.getBranches().get(1).getName(), repositoryInfoDto.getBranches().get(1).getBranchName());
    }
}
