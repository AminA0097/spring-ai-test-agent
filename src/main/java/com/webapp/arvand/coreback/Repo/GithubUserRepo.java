package com.webapp.arvand.coreback.Repo;

import com.webapp.arvand.coreback.Entity.GithubUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GithubUserRepo extends JpaRepository<GithubUser, Long> {
    Optional<GithubUser> findByGithubIdAndGithubUsername(String githubId, String githubUsername);
}
