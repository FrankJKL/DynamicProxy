package 动态代理;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class Proxy {
	
	public static Object newProxyInstance(Class interfce,InvocationHandler h) throws Exception{		
		String methodStr = "";	
		String rt = "\r\n";
		
		//代理类实现接口中的方法
		Method[] methods = interfce.getMethods();
		for (Method method : methods) {
			methodStr+="    @Override"+rt +
					   "    public "+method.getReturnType()+" "+method.getName()+"() throws Exception {" + rt +	
					   "		Method md = "+interfce.getName()+".class.getMethod(\""+method.getName()+"\");"+rt+
					   "		h.invoke(this,md);"+rt+
					   "    }" + rt;
					   
		}
		//拼接成类的源码
		String src = 
			"package 动态代理;" +  rt +
			"import java.lang.reflect.Method;" +  rt +
			"public class TankTimeProxy implements "+interfce.getName()+" {" + rt +
			"    public TankTimeProxy(InvocationHandler h) {" + rt +
			"        super();" + rt +
			"        this.h = h;" + rt +
			"    }" + rt +
			
			"    InvocationHandler h;" + rt +
				
				 methodStr+				
			"}";
		// fileName是最终生成的代理类的路径
		String fileName = System.getProperty("user.dir")+"/src/动态代理/TankTimeProxy.java";
		File file = new File(fileName);
		FileWriter fw = new FileWriter(file);
		fw.write(src);//将源代码写入到文件中，供后面的Javac编译
		fw.flush();
		fw.close();
		
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
		Iterable units = fileMgr.getJavaFileObjects(fileName);//要编译的java文件
		CompilationTask t = compiler.getTask(null, fileMgr, null, null, null, units);//生成一个编译任务
		t.call();//编译
		fileMgr.close();
		
		//加载类，并生成代理类的实例
		URL[] urls = new URL[]{new URL("file:/"+System.getProperty("user.dir")+"/src")};
		URLClassLoader ul = new URLClassLoader(urls);
		Class c = ul.loadClass("动态代理.TankTimeProxy");
		System.out.println(c);
		
		Constructor ctr = c.getConstructor(InvocationHandler.class);
		Object m = ctr.newInstance(h);//产生代理对象，并把InvocationHandler注入进去
		
		return m;
	}
}
