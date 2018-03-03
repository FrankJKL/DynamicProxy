package 动态代理;

import java.lang.reflect.Method;

public interface InvocationHandler {
	public void invoke(Object o,Method m) throws Exception;
}
