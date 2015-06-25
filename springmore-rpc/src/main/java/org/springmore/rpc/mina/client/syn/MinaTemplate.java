package org.springmore.rpc.mina.client.syn;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;

/**
 * MinaTemplate
 * @author 唐延波
 * @date 2015-6-24
 */
public class MinaTemplate {

	/**
	 * conncet factory
	 */
	private ConnectFutureFactory connectFactory;
	
	/**
	 * 同步传输对象
	 * @param t
	 * @return
	 * @author 唐延波
	 * @throws InterruptedException 
	 * @date 2015-6-24
	 */
	public <T> T sengObject(T message) throws InterruptedException{
		Result result = new Result();
		//获取tcp连接
		ConnectFuture connection = connectFactory.getConnection();
		IoSession session = connection.getSession();
		session.setAttribute(Result.SESSION_KEY, result);
		//发送信息
		session.write(message);
		//同步阻塞获取响应
		T returnMsg = result.synGet();
		//此处并不是真正关闭连接，而是将连接放回连接池
		connectFactory.close(connection);
		return returnMsg;
	}
	
	public void setConnectFactory(ConnectFutureFactory connectFactory) {
		this.connectFactory = connectFactory;
	}
	
}
