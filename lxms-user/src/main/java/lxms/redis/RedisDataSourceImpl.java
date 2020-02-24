package lxms.redis;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
@Repository("redisDataSource")
public class RedisDataSourceImpl implements RedisDataSource{
	
	private static final Logger log = LoggerFactory.getLogger(RedisDataSourceImpl.class);
	@Resource
	private ShardedJedisPool shardedJedisPool; 
	public ShardedJedis getRedisClient() {
		try {
			ShardedJedis shardedJedis = shardedJedisPool.getResource();
			return shardedJedis;
		} catch (Exception e) {
			log.error("get RedisClient error"+e);
		}
		return null;
	}
	

	
}
