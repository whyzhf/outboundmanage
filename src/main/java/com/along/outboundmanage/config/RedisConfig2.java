/*
package com.along.outboundmanage.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig extends CachingConfigurerSupport {

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

		RedisTemplate<String, Object> template = new RedisTemplate<>();

		RedisSerializer<String> redisSerializer = new StringRedisSerializer();
		//使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = getJackson2JsonRedisSerializer();

		template.setConnectionFactory(factory);
		//key序列化方式
		template.setKeySerializer(redisSerializer);
		//value序列化
		template.setValueSerializer(jackson2JsonRedisSerializer);
		//value hashmap序列化
		template.setHashValueSerializer(jackson2JsonRedisSerializer);

		return template;
	}

	private Jackson2JsonRedisSerializer getJackson2JsonRedisSerializer() {
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		// 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		// 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		return jackson2JsonRedisSerializer;
	}

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory factory) {
		RedisSerializer<String> redisSerializer = new StringRedisSerializer();
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = getJackson2JsonRedisSerializer();
		// 配置序列化（解决乱码的问题）,过期时间300秒
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofSeconds(300))
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
				.disableCachingNullValues();

		RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
				.cacheDefaults(config)
				.build();
		return cacheManager;
	}


	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		//  设置自动key的生成规则，配置spring boot的注解，进行方法级别的缓存
		// 使用：进行分割，可以很多显示出层级关系
		// 这里其实就是new了一个KeyGenerator对象，只是这是lambda表达式的写法，我感觉很好用，大家感兴趣可以去了解下
		return (target, method, params) -> {
			StringBuilder sb = new StringBuilder();
			sb.append(target.getClass().getName());
			sb.append(":");
			sb.append(method.getName());
			for (Object obj : params) {
				sb.append(":" + String.valueOf(obj));
			}
			String rsToUse = String.valueOf(sb);
			return rsToUse;
		};
	}
}
*/
