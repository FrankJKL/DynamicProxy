package 动态代理;
import java.lang.reflect.Method;
public class TankTimeProxy implements 动态代理.Moveable {
    public TankTimeProxy(InvocationHandler h) {
        super();
        this.h = h;
    }
    InvocationHandler h;
    @Override
    public void move() throws Exception {
		Method md = 动态代理.Moveable.class.getMethod("move");
		h.invoke(this,md);
    }
}