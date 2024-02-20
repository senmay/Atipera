package com.atipera.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;
import lombok.Value;

import java.util.List;

@Value
@Builder
@Setter
public class RepositoryInfoDto {
    @NonNull
    String repositoryName;
    @NonNull
    String ownerLogin;
    List<BranchInfo> branches;
    @Value
    @Builder
    public static class BranchInfo {
        @NonNull
        String branchName;
        @NonNull
        String lastCommitSha;
    }
}
