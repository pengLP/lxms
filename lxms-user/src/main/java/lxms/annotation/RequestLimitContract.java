package lxms.annotation;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import lxms.exception.RequestLimitException;
import lxms.redis.RedisClientTemplate;
import lxms.tool.Json;

@Component
@Aspect
public class RequestLimitContract {
	private Log LOG = LogFactory.getLog(this.getClass());

	@Resource
	private RedisClientTemplate redisClient;

	@Around("within(@org.springframework.stereotype.Controller *) && @annotation(limit)")
	public Json requestLimit(final ProceedingJoinPoint joinPoint, RequestLimit limit) {
		Json json = new Json();
		try {
			Object[] args = joinPoint.getArgs();
			HttpServletRequest request = null;
			for (int i = 0; i < args.length; i++) {
				if (args[i] instanceof HttpServletRequest) {
					request = (HttpServletRequest) args[i];
				}
			}
			if (request == null) {
				return json;
			}
			String ip = request.getRemoteAddr();
			String url = request.getRequestURI().toString();
			String key = "req_limit_".concat(url).concat(ip);
			long count = redisClient.incrBy(key, 1);
			if (count == 1) {
				redisClient.expire(key, limit.time());
			}
			if (count > limit.count()) {
				LOG.info("用户IP【" + ip + "】访问地址【" + url + "】超过了限定次数【" + limit.count() + "】");
				return json;
			}
			json = (Json) joinPoint.proceed();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

}
