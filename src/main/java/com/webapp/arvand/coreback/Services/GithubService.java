package com.webapp.arvand.coreback.Services;

import com.webapp.arvand.coreback.Dtos.GithubUserDto;
import com.webapp.arvand.coreback.Entity.GithubUser;
import com.webapp.arvand.coreback.Entity.RoleEntity;
import com.webapp.arvand.coreback.Repo.GithubUserRepo;
import com.webapp.arvand.coreback.Repo.RoleRepo;
import com.webapp.arvand.coreback.Utils.CryptoUtil;
import com.webapp.arvand.coreback.Utils.WebClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GithubService implements GitlabInterface{
    @Value("${github.base.api.url}")
    private String githubBaseUrl;
    @Autowired
    private GithubUserRepo githubUserRepo;
    @Autowired
    private RoleRepo roleRepo;
    private WebClientUtil webClientUtil;

    public GithubService(WebClientUtil webClientUtil) {
        this.webClientUtil = webClientUtil;
    }

    public Map<String, Object> getUser(String token) throws Exception {

        return webClientUtil.get(
                githubBaseUrl +"/user",
                authHeader(token),
                Map.class
        );
    }

    public List<Map> getEmails(String token) throws Exception {

        return webClientUtil.get(
                githubBaseUrl + "/user/emails",
                authHeader(token),
                List.class
        );
    }
    public Map<String, String> authHeader(String token) throws Exception{
        return Map.of(
                "Authorization", "Bearer " + token
        );
    }

    @Override
    public GithubUser login(String accessToken) throws Exception {

        Set<RoleEntity> roles = new HashSet<>();
        RoleEntity roleEntity = roleRepo.findById(163l).orElse(null);
        if (roleEntity == null) {
            return null;
        }
        roles.add(roleEntity);
        Map<String, Object> res = getUser(accessToken);
        List<Map> emails = getEmails(accessToken);

        accessToken = CryptoUtil.encryptSecret(accessToken);
        String githubId = res.get("id").toString();
        String username =  res.get("login").toString();
        String name =  res.get("name").toString();
        String emailObj = res.get("email").toString();
        String reposUrl =  res.get("repos_url").toString();
        String email = (emailObj != null && !emailObj.toString().isEmpty())
                ? emailObj.toString()
                : emails.get(0).get("email").toString();

        GithubUser user = githubUserRepo.findByGithubIdAndGithubUsername(githubId,username)
                .orElse(null);
        boolean isCreate = user == null ? true : false;
        Long newId = isCreate ? null : user.getUserId();
        GithubUser githubUser = new GithubUser();
        githubUser.setUserId(newId);
        githubUser.setGithubId(githubId);
        githubUser.setGithubToken(accessToken);
        githubUser.setGithubUsername(username);
        githubUser.setGithubName(name);
        githubUser.setGithubRepos(reposUrl);
        githubUser.setGithubEmail(email);
        githubUser.setRoleEntitySet(roles);

        try {
            githubUserRepo.save(githubUser);
            return githubUser;
        }
        catch (Exception e){
            return null;
        }
    }
}
