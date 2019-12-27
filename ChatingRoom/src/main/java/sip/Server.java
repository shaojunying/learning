package sip;

import javax.sip.*;
import java.text.ParseException;
import java.util.TooManyListenersException;
import java.util.Vector;


public class Server implements MessageProcessor {
	Vector<User> users = new Vector<>();

	private static String serverName;
	private static String serverIp;
	private static int serverPort;
	SipLayer sipLayer;


	public static void main(String[] args) throws PeerUnavailableException, TransportNotSupportedException, ObjectInUseException, InvalidArgumentException, TooManyListenersException{
			serverName = "serverAddress";
			serverPort = 5060;
		    serverIp = "127.0.0.1";
		    new Server(serverName,serverIp,serverPort);
		    System.out.println("Server ready!");
    }

    public Server(String servername,String ip,int port ) throws PeerUnavailableException,
            TransportNotSupportedException, InvalidArgumentException, ObjectInUseException, TooManyListenersException{
		sipLayer = new SipLayer(servername, ip, port);
    	sipLayer.setMessageProcessor(this);
    }

    /*
    * 想所有用户发送更新之后的在线用户列表
    * */
	private void notifyUser(){
		StringBuilder message = new StringBuilder();
        for(User user : users){
      	  message.append(user.getName()).append("&sip:").append(user.getName()).append("@").append(user.getAddress());
      	  message.append("\n");
        }
        for(User user : users){
      		String receiver ="sip:"+user.getName()+"@"+ user.getAddress();
      			  try {
      			  		sipLayer.sendMessage(receiver, message.toString(),"register");
					} catch (Throwable e) {
						e.printStackTrace();
					}

			}
	}


	/*
	* 处理从客户端接受的消息
	* */
	@Override
	public void processMessage(User sender, String message){

		/*
		* 消息内容
		* 发送端地址
		* &
		* 接收端地址
		* &
		* 消息内容
		* */

		String[] data = message.split("&");

		if (data[1].equals("")) {
			/*
			* 群发消息
			* */

			String[] str1 = data[0].split("@");
			String[] str2 = str1[0].split(":");
			// 发送方用户名
			final String fromName = str2[1];
			System.out.printf("收到一条[%s]发来的群发消息,消息内容是[%s]\n",fromName,data[2]);
			// data[1](接受端地址)为空 向所有用户发送
			users.forEach((user -> {
				String realReceiver = user.toAddress();
				try {
					sipLayer.sendMessage(realReceiver, "0&"+fromName + "&" + data[2],"message");
				} catch (ParseException | InvalidArgumentException | SipException e) {
					e.printStackTrace();
				}
			}));
		} else {
			/*
			* 私发消息
			* */
			String toAddress = "";

			String[] str1 = data[0].split("@");
			String[] str2 = str1[0].split(":");
			// 发送方用户名
			final String fromName = str2[1];
			String fromAddress = data[0];
			String toName = "";
			// 确定接收方的地址
			for (User user : users) {
				String tempAddress = "sip:" + user.getName() + "@" + user.getAddress();
				if (data[1].equals(tempAddress)) {
					toName = user.getName();
					toAddress = tempAddress;
				}
			}

			System.out.printf("收到从[%s]发送给[%s]的消息,内容是[%s]\n",fromName,toName,data[2]);

			String sendMessage = "1&"+fromName + "&" + data[2];
			// 首先一定要向自己发送该条消息 不管消息是发给他人还是发给自己
			try {
				sipLayer.sendMessage(toAddress,sendMessage ,"message");
			} catch (ParseException | InvalidArgumentException | SipException e) {
				e.printStackTrace();
			}
			// 不是想自己发送的还要向他人发送一条
			if (!data[1].equals(fromAddress)) {
				try {
					sipLayer.sendMessage(fromAddress, sendMessage,"message");
				} catch (ParseException | InvalidArgumentException | SipException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void processError(String errorMessage) {

	}

	@Override
	public void processInfo(String errorMessage) {

	}

	/*
	* 处理注册信息
	* */
	@Override
	public void processRegister(User user, String message) {
		String[] str1 = message.split("@");
		String[] str2 = str1[0].split(":");
		// 注册名
		String register_name = str2[1];
		// 注册地址
		String register_address = str1[1];
		User newUser = new User(register_name, register_address);
		users.add(newUser);

		// 通知其他用户当前在线用户列表
		this.notifyUser();
		System.out.printf("用户名为[%s]的用户注册成功\n",register_name);
	}

	/*
	* 处理退出信息
	* */
	@Override
	public void processCancel(User fromUser, String message) {
		String[] str1 = message.split("@");
		String[] str2 = str1[0].split(":");

		String register_address = str1[1];
		String register_name = "";

		for (int i = 0; i < users.size(); i++) {
			if (register_address.equals(users.elementAt(i).getAddress())) {
				register_name = users.elementAt(i).getName();
				users.removeElementAt(i);
				break;
			}
		}
		this.notifyUser();
		System.out.printf("用户名为[%s]的用户退出成功\n",register_name);
	}
}
