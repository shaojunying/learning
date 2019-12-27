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
    * �������û����͸���֮��������û��б�
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
	* ����ӿͻ��˽��ܵ���Ϣ
	* */
	@Override
	public void processMessage(User sender, String message){

		/*
		* ��Ϣ����
		* ���Ͷ˵�ַ
		* &
		* ���ն˵�ַ
		* &
		* ��Ϣ����
		* */

		String[] data = message.split("&");

		if (data[1].equals("")) {
			/*
			* Ⱥ����Ϣ
			* */

			String[] str1 = data[0].split("@");
			String[] str2 = str1[0].split(":");
			// ���ͷ��û���
			final String fromName = str2[1];
			System.out.printf("�յ�һ��[%s]������Ⱥ����Ϣ,��Ϣ������[%s]\n",fromName,data[2]);
			// data[1](���ܶ˵�ַ)Ϊ�� �������û�����
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
			* ˽����Ϣ
			* */
			String toAddress = "";

			String[] str1 = data[0].split("@");
			String[] str2 = str1[0].split(":");
			// ���ͷ��û���
			final String fromName = str2[1];
			String fromAddress = data[0];
			String toName = "";
			// ȷ�����շ��ĵ�ַ
			for (User user : users) {
				String tempAddress = "sip:" + user.getName() + "@" + user.getAddress();
				if (data[1].equals(tempAddress)) {
					toName = user.getName();
					toAddress = tempAddress;
				}
			}

			System.out.printf("�յ���[%s]���͸�[%s]����Ϣ,������[%s]\n",fromName,toName,data[2]);

			String sendMessage = "1&"+fromName + "&" + data[2];
			// ����һ��Ҫ���Լ����͸�����Ϣ ������Ϣ�Ƿ������˻��Ƿ����Լ�
			try {
				sipLayer.sendMessage(toAddress,sendMessage ,"message");
			} catch (ParseException | InvalidArgumentException | SipException e) {
				e.printStackTrace();
			}
			// �������Լ����͵Ļ�Ҫ�����˷���һ��
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
	* ����ע����Ϣ
	* */
	@Override
	public void processRegister(User user, String message) {
		String[] str1 = message.split("@");
		String[] str2 = str1[0].split(":");
		// ע����
		String register_name = str2[1];
		// ע���ַ
		String register_address = str1[1];
		User newUser = new User(register_name, register_address);
		users.add(newUser);

		// ֪ͨ�����û���ǰ�����û��б�
		this.notifyUser();
		System.out.printf("�û���Ϊ[%s]���û�ע��ɹ�\n",register_name);
	}

	/*
	* �����˳���Ϣ
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
		System.out.printf("�û���Ϊ[%s]���û��˳��ɹ�\n",register_name);
	}
}
