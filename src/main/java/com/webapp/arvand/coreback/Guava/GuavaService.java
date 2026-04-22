package com.webapp.arvand.coreback.Guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class GuavaService {
    private final Cache<Long,GuavaCache> authCache =
            CacheBuilder.newBuilder()
                    .maximumSize(500)
                    .expireAfterWrite(
                            16,
                            TimeUnit.MINUTES
                    )
                    .build();

    public boolean putToCache(Long ui, GuavaCache cache) {
        try {
            authCache.put(ui, cache);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    public boolean removeFromCache(Long ui) {
        try {
            authCache.invalidate(ui);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    public GuavaCache checkExistAncCompare(Claims claims) {
        Long id = Long.valueOf(claims.getSubject());
        String jit = claims.getId();
        GuavaCache guavaCache = authCache.getIfPresent(id);
        if (guavaCache == null) {
            return null;
        }
        if(!guavaCache.getCurrentJti().equals(jit)) {
            return null;
        }
        return guavaCache;
    }
}
