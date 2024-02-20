package com.atipera.mappers;

import com.atipera.model.GithubResponse;
import com.atipera.model.RepositoryInfoDto;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class RepositoryInfoMapper {
    public List<RepositoryInfoDto> toDto(List<GithubResponse> repositoryInfoResponse) {
        return repositoryInfoResponse.stream()
                .map(this::toDto)
                .toList();
    }
    public RepositoryInfoDto toDto(GithubResponse repositoryInfoResponse) {
        return RepositoryInfoDto.builder()
                .repositoryName(repositoryInfoResponse.getName())
                .ownerLogin(repositoryInfoResponse.getOwner().getName())
                .branches(repositoryInfoResponse.getBranches().stream()
                        .map(branch -> RepositoryInfoDto.BranchInfo.builder()
                                .branchName(branch.getName())
                                .lastCommitSha(branch.getCommit().getSha())
                                .build())
                        .toList())
                .build();
    }
}
