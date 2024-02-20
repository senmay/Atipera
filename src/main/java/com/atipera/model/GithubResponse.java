package com.atipera.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubResponse {
    @JsonProperty("name")
    String name;
    @JsonProperty("owner")
    Owner owner;
    @JsonProperty("fork")
    @JsonIgnore
    boolean fork;
    List<BranchesResponse> branches;
    public void addBranch(BranchesResponse branch) {
        if (branches == null)
            branches = new ArrayList<>();
        branches.add(branch);
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Owner {
        @JsonProperty("login")
        String name;
    }
}
