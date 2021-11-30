package com.vector.manager.core.shiro.cache;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import com.vector.manager.core.common.Constant;

import java.util.Objects;

public class CacheManagerBuilder{

	private final ObjectProvider<org.springframework.cache.CacheManager> springCachePvd;
	private RedisConnectionFactory redisConnectionFactory;
	
	public CacheManagerBuilder(ObjectProvider<org.springframework.cache.CacheManager> springCachePvd, RedisConnectionFactory redisConnectionFactory) {
		this.springCachePvd = springCachePvd;
		this.redisConnectionFactory = redisConnectionFactory;
	}
	
	public CacheManager build() {
		CacheManager cacheManager;
		org.springframework.cache.CacheManager springCacheManager = springCachePvd.getIfAvailable();
		if(Objects.isNull(springCacheManager)) {
			cacheManager = new MapCacheManager();
		}else {
			cacheManager = this.decision(springCacheManager);
		}
		return cacheManager;
	}
	
	private CacheManager decision(org.springframework.cache.CacheManager springCacheManager) {
		// EhCache
		if (springCacheManager instanceof EhCacheCacheManager) {
			EhCacheManager ehCacheManager = new EhCacheManager();
			ehCacheManager.setCacheManager(((EhCacheCacheManager) springCacheManager).getCacheManager());
			return ehCacheManager;
		}
		// RedisCache
		if (springCacheManager instanceof org.springframework.data.redis.cache.RedisCacheManager) {
			GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer();
			RedisTemplate rt = new RedisTemplate();
			rt.setConnectionFactory(redisConnectionFactory);
			rt.setKeySerializer(jsonSerializer);
			rt.setHashKeySerializer(jsonSerializer);
			rt.setBeanClassLoader(this.getClass().getClassLoader());
			rt.afterPropertiesSet();
			LMRedisCacheManager redisCacheManager = new LMRedisCacheManager();
			redisCacheManager.setRedisTemplate(rt);
			redisCacheManager.setRedisTimeout(Constant.ACTIVE_SESSION_CACHE, Constant.SESSION_TIMEOUT);
			return redisCacheManager;
		}
		// SpringCache
		return new SpringCacheManager(springCacheManager);
	}

}