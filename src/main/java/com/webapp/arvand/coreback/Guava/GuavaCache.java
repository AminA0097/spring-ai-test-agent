package com.webapp.arvand.coreback.Guava;

import com.webapp.arvand.coreback.Entity.GithubUser;
import com.webapp.arvand.coreback.Entity.RoleEntity;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public final class GuavaCache {

    private final Long userId;
    private final String username;
    private final Set<String> roles;
    private final String currentJti;

    public GuavaCache(GithubUser githubUser,
                      String currentJti) {

        this.userId = githubUser.getUserId();

        this.username =
                githubUser.getGithubName();

        Set<String> mappedRoles =
                new HashSet<>();

        for (RoleEntity role : githubUser.getRoleEntitySet()) {
            mappedRoles.add(
                    role.getRoleName()
            );
        }

        this.roles = Set.copyOf(mappedRoles);

        this.currentJti = currentJti;
    }
}