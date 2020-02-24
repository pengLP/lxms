package lxms.redis;

import redis.clients.jedis.ShardedJedis;

public interface RedisDataSource {
	public abstract ShardedJedis getRedisClient();
	

}
