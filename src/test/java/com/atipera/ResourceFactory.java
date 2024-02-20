package com.atipera;

import com.atipera.mappers.RepositoryInfoMapper;
import com.atipera.model.BranchesResponse;
import com.atipera.model.GithubResponse;
import com.atipera.model.RepositoryInfoDto;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class ResourceFactory {
    protected List<BranchesResponse> createBranchesResponse(Integer size) {
        List<BranchesResponse> branchesResponses = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            branchesResponses.add(BranchesResponse.builder()
                    .name(RandomStringUtils.random(9))
                    .commit(BranchesResponse.Commit.builder().sha(RandomStringUtils.random(10)).build())
                    .build());
        }
        return branchesResponses;
    }
    protected List<RepositoryInfoDto.BranchInfo> createBranchInfoList(Integer size) {
        List<RepositoryInfoDto.BranchInfo> branchInfos = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            branchInfos.add(RepositoryInfoDto.BranchInfo.builder()
                    .branchName(RandomStringUtils.random(9))
                    .lastCommitSha(RandomStringUtils.random(9))
                    .build());
        }
        return branchInfos;
    }
    protected GithubResponse createGithubResponse(Integer branchSize, boolean isFork) {
        return GithubResponse.builder()
                .name(RandomStringUtils.random(9))
                .owner(GithubResponse.Owner.builder().name(RandomStringUtils.random(9)).build())
                .fork(isFork)
                .branches(createBranchesResponse(branchSize))
                .build();
    }
}
