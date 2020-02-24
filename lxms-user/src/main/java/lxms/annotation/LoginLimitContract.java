package lxms.annotation;

import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lxms.content.StatusCodes;
import lxms.entity.User;
import lxms.tool.Json;
import lxms.utils.SessionUtil;

@Component
@Aspect
public class LoginLimitContract {
	private Log LOG = LogFactory.getLog(this.getClass());

	@Around("within(@org.springframework.stereotype.Controller *) && @annotation(login)")
	public Json requestLimit(final ProceedingJoinPoint joinPoint, LoginLimit login) {
		Json json = new Json();
		try {

			Object[] args = joinPoint.getArgs();
			HttpSession session = null;

			for (int i = 0; i < args.length; i++) {
				if (args[i] instanceof HttpSession) {
					session = (HttpSession) args[i];
					break;
				}

			}
			if (session == null) {
				System.out.println("ЕНет");
				return json;
			}
			User user = SessionUtil.getCurrentUser(session);
			if (user == null) {
				json.setCode(StatusCodes.S111);
				return json;
			} else {
				for (int i = 0; i < args.length; i++) {
					if (args[i] instanceof User) {
						args[i] = user;
						break;
					}
				}
			}
			json = (Json) joinPoint.proceed(args);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} catch (Throwable e) {
			LOG.error(e.getMessage());
		}
		return json;
	}
}
