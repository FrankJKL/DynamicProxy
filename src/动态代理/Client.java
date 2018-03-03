package 动态代理;

/**
 * 相对于静态代理的好处
 * 可以对实现了任何接口的任何对象 进行代理
 * @author Frank
 *
 */
public class Client {

	public static void main(String[] args) throws Exception {
		//被代理对象必须实现和 动态生成的代理对象一样的接口
		Tank t = new Tank();
		
		//TimeHandler装有切面逻辑
		TimeHandler h = new TimeHandler();
		h.setTarget(t);
		
		//返回的是动态生成的实现了指定接口的代理对象
		Moveable timeProxy = (Moveable)Proxy.newProxyInstance(Moveable.class,h);//产生实现了任意接口的代理
		
		timeProxy.move();
	}

}
