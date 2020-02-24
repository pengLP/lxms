package lxms.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface RequestLimit {
	/**
	 * ������ʴ�����Ĭ��ֵMax_value
	 * @return
	 */
	int count() default Integer.MAX_VALUE;
	 /**
     * 
     * ʱ��Σ���λΪ�룬Ĭ��ֵһ����
     */
	int time() default 60;
}
