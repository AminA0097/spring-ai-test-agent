package com.webapp.arvand.coreback.Entity;

import jakarta.persistence.*;
import lombok.Data;


import java.util.Set;

@Entity
@Table(name = "TBL_GITHUB_USER")
@Data
public class GithubUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(name = "FLD_GITHUB_ID", unique = true)
    private String githubId;

    @Column(name = "FLD_GITHUB_UNAME", unique = true)
    private String githubUsername;

    @Column(name = "FLD_GITHUB_TOKEN")
    private String githubToken;

    @Column(name = "FLD_GITHUB_EMAIL")
    private String githubEmail;

    @Column(name = "FLD_GITHUB_NAME")
    private String githubName;

    @Column(name = "FLD_GITHUB_REPOS")
    private String githubRepos;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "TBL_USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private Set<RoleEntity> roleEntitySet;
}
