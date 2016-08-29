package com.guhanjie.util;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TPS每秒事务数，这里泛指统计计数<br>
 * 获取TPS对象并启动，在需要计数的地方调用increment()方法，后台线程会定时打印统计日志<br>
 *  
 * 日志内容如下：<br>
 * 09:15:43(时间) - main(标题)      	TC 10(总数)	TPS 10(当前秒计数)<br>
 * 09:15:43 - main      	TC 10	TPS 10<br>
 * 09:15:44 - main      	TC 21	TPS 11<br>
 * 09:15:45 - main      	TC 31	TPS 10<br>
 * 
 * 用法参见main函数<br> 
 * @author a
 */
public class TPSUtil {
	private static Logger logger = LoggerFactory.getLogger("system.tps");
	private static TPSUtil instance = null;
	
	private String name = "default";
	private AtomicLong requestCount = new AtomicLong(0);

	private long currentCount;
	private long lastCount;	

	private int printIntervalSecond = 1;
	private boolean running = true;			// TPS线程运行中
	private boolean ignoreTPS0 = false;		// 是否忽略tps为0的log
	private boolean useStdout = false;		// 是否使用System.out输出日志
	
	public void setName(String name) {
		this.name = name;
	}
	public void useStdout(boolean useStdout) {
		this.useStdout = useStdout;
	}
	
	//是否忽略TPS为0的log
	public void setIgnoreTPS0(boolean ignoreTPS0) {
		this.ignoreTPS0 = ignoreTPS0;
	}

	//可以创建新的TPS
	public  TPSUtil(String name, int printIntervalSecond){
		//最多每10分钟输出一次log，printIntervalSecond<0,不启动
		if(printIntervalSecond > 600){
			this.printIntervalSecond = 600;
		}else{
			this.printIntervalSecond = printIntervalSecond;
		}
		
		//建议name 10长度
		if(name != null){
			int len = name.length();
			while(len < 10){
				name = name + " ";
				len = name.length();
			}
			this.name = name;
		}
	}
	
	/**
	 * 获取全局的默认的TPS
	 * @param printIntervalSecond
	 * @return
	 */
	public static synchronized TPSUtil getInstance(int printIntervalSecond){
		if(instance == null){		
				instance = new TPSUtil("default",printIntervalSecond);
		}
		return instance;
	}
	
	// 开始打印log
	public synchronized void start() {
		Printer p= new Printer();
		p.setDaemon(true);
		p.start();
	}
	
	// 内部打印线程
	class Printer extends Thread{
		@Override
		public void run() {
			running = true;
			try {
				String log = null;
				if(useStdout){
					while(running){
						Thread.sleep(printIntervalSecond*1000);		
						log = buildLog();
						if(log != null){
							//System.out.println(DateUtil.formatDate(new Date(),"HH:mm:ss") +" - "+ log);
							System.out.println(new Date() +" - "+ log);
						}			
					}
				}else{
					while(running){
						Thread.sleep(printIntervalSecond*1000);					
						if(logger.isDebugEnabled()){
							log = buildLog();
							if(log != null){
								logger.debug(log);
							}
						}					
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//停止log
	public void close() {
		running = false;
	}
	
	//调用增1
	public long increment(){
		return requestCount.incrementAndGet();
	}
	
	//调用增t
	public long add(long t){
		return requestCount.addAndGet(t);
	}
	//当前计数
	public long curentCount(){
		return requestCount.get();
	}
	
	public String buildLog(){
		currentCount = requestCount.get();
		long tps = currentCount - lastCount;
		if(tps == 0 && ignoreTPS0){
			return null;
		}

		lastCount = currentCount;
		
		StringBuilder sb = new StringBuilder(name);
		
		if(printIntervalSecond == 1){
			//平均TPS
			sb.append("\tTPS ").append(tps);			
		}else{
			//平均TPS
			sb.append("\tTPS ").append(tps/printIntervalSecond);
			//当前时间段调用次数
			sb.append("\tC ").append(tps).append("/").append(printIntervalSecond).append("s");
		} 
		//总调用次数
		sb.append("\tTC ").append(currentCount);
		
		return sb.toString();
	}
	
	public static void main(String[] args ){
		//TPS tps = TPS.getInstance(1);		// 获取全局默认tps，多线程可以共用
		TPSUtil tps = new TPSUtil("main",2);		// 或者创建一个独用tps
		tps.setIgnoreTPS0(true);			// 设置tps=0时不要打印日志
		tps.useStdout(true);				// 设置使用system.out.println()输出,否则使用log4j
		tps.start();						// 开始计数

		System.out.println("start");		// 业务场景，可以再其他线程中使用
		for(int i=0;i<100;i++){
			tps.increment();				// 需要计数的地方调用tps.increment()
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("stop");
		tps.close();						// 停用
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

