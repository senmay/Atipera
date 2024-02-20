package com.atipera.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BranchesResponse {
    @JsonProperty("name")
    private String name;
    @JsonProperty("commit")
    private Commit commit;
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Commit {
        @JsonProperty("sha")
        private String sha;
    }
}
