package com.webapp.arvand.coreback.Services;

import com.webapp.arvand.coreback.Dtos.GithubUserDto;
import com.webapp.arvand.coreback.Entity.GithubUser;

import java.util.List;
import java.util.Map;

public interface GitlabInterface {
    public Map<String, Object> getUser(String token)throws Exception;
    public List<Map> getEmails(String token)throws Exception;
    public Map<String, String> authHeader(String token)throws Exception;

    public GithubUser login(String accessToken)throws Exception;
}
