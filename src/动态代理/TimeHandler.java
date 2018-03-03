package 动态代理;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TimeHandler implements InvocationHandler {
	// 被代理对象在这里
	private Object target;

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}
	
	/**
	 * 这里面就是切面逻辑
	 */
	@Override
	public void invoke(Object o, Method m) throws Exception {
		// o是Proxy代理对象，m是正在调用的方法
		long start = System.currentTimeMillis();
		System.out.println("startTime:" + start);
		// 被代理对象作为真正的方法调用者
		m.invoke(target);
		long end = System.currentTimeMillis();
		System.out.println("endTime:" + end);
	}

}
