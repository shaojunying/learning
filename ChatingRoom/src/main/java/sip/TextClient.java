package sip;

import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.Arrays;
import java.util.TooManyListenersException;
import java.util.Vector;


public class TextClient	extends JFrame implements MessageProcessor
{
	private String clientName;
	private String clientIp;
	private int clientPort;

    private SipLayer sipLayer;

    // 用户名
    private JTextField usernameTextarea;
    private JLabel usernameLabel;
    private JLabel receivedLbl;
    private JLabel friendListLabel;
    private JTextArea chatRecordTextArea;
    private JList<String> friendsJList;
    private JScrollPane receivedScrollPane;
    private JScrollPane friendsScrollPane;
    private JButton sendBtn;
    private JButton quitBtn;
    private JLabel sendLbl;
    private JTextField sendMessages;

    // 存储在线用户地址列表
    private Vector<String> friendAddressList;
    // 在线用户name
    private Vector<String> friendNameList;
    // 要发送的地址
    private String toAddress = "";

    // 服务端地址
    String serverAddress;


    public static void main(String[] args) throws InvalidArgumentException, SipException, TooManyListenersException, ParseException {
        new TextClient();
    }


    public TextClient() throws InvalidArgumentException, SipException, TooManyListenersException, ParseException {
        // 创建正式的客户端
        super();
        // 客户端name
        clientName = "test3";
        // 客户端ip
        clientIp = "127.0.0.1";
        // 客户端端口
        clientPort = 50010;

        sipLayer = new SipLayer(clientName, clientIp, clientPort);

        initWindow();

        this.usernameTextarea.setText(sipLayer.getUsername());

        sipLayer.setMessageProcessor(this);
        this.show();

        // 服务端地址
        serverAddress = "sip:serverAddress@127.0.0.1:5060";
        //发给server端注册消息
        sipLayer.sendMessage(serverAddress, ":"+clientName+"@"+clientIp+":"+clientPort, "register");

        System.out.printf("sipL%s@%s:%d 用户注册成功",clientName,clientIp,clientPort);
    }


    private void initWindow() {
    	receivedLbl = new JLabel();
        friendListLabel =new JLabel();
        sendLbl = new JLabel();
        sendMessages = new JTextField();
        receivedScrollPane = new JScrollPane();
        friendsScrollPane = new JScrollPane();
        chatRecordTextArea = new JTextArea();
        friendsJList = new JList<>();
        usernameLabel = new JLabel();
        usernameTextarea = new JTextField();
        sendBtn = new JButton();
        quitBtn = new JButton();

        getContentPane().setLayout(null);

        setTitle("客户端");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                quitBtnActionPerformed(null);
                System.exit(0);
            }
        });

        // 用户列表标题
        friendListLabel.setText("用户列表");
        friendListLabel.setAlignmentY(0.0F);
        friendListLabel.setPreferredSize(new Dimension(25, 100));
        getContentPane().add(friendListLabel);
        friendListLabel.setBounds(375, 10, 100, 30);

        friendsJList.setBounds(380, 50, 140, 420);
        getContentPane().add(friendsJList);
        friendsJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()){
                int index = friendsJList.getSelectedIndex();
                if (!(index < friendAddressList.size())){
                    // 点击了空白处
                    return;
                }
                toAddress = friendAddressList.get(index);
            }
        });

        // 本机名
        usernameLabel.setText("用户名：");
        getContentPane().add(usernameLabel);
        usernameLabel.setBounds(10, 10, 60, 30);

        getContentPane().add(usernameTextarea);
        usernameTextarea.setBounds(75, 10, 60, 30);

        // 聊天记录
        chatRecordTextArea.setAlignmentX(0.0F);
        chatRecordTextArea.setEditable(false);
        chatRecordTextArea.setLineWrap(true);
        chatRecordTextArea.setWrapStyleWord(true);
        receivedScrollPane.setViewportView(chatRecordTextArea);
        receivedScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        getContentPane().add(receivedScrollPane);
        receivedScrollPane.setBounds(5, 50, 365, 290);

        // 聊天输入框
        getContentPane().add(sendMessages);
        sendMessages.setBounds(5, 350, 365, 80);

        sendBtn.setText("发送");
        sendBtn.addActionListener(this::sendBtnActionPerformed);

        getContentPane().add(sendBtn);
        sendBtn.setBounds(245, 440, 60, 30);

        quitBtn.setText("关闭");
        quitBtn.addActionListener(this::quitBtnActionPerformed);

        getContentPane().add(quitBtn);
        quitBtn.setBounds(310, 440, 60, 30);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-400)/2, (screenSize.height-320)/2, 545, 520);
    }
    /*
    * 点击发送按钮
    * */
    private void sendBtnActionPerformed(ActionEvent evt) {
        // 消息没有内容的时候不发送
        String messageText = this.sendMessages.getText().trim();
        if (messageText.length() == 0){
            return;
        }

        try
        {
            /*
            * 向服务端发送聊天消息
            * 发送方地址
            * &
            * 接收方地址(为空表示群发)
            * &
            * 消息内容
            * */
            System.out.println(toAddress);
        	String from = "sip:"+this.getClientName()+"@"+this.getClientIp()+":"+this.getClientPort();
            String message = from+"&"+toAddress+"&"+messageText;

            System.out.println(serverAddress);
            sipLayer.sendMessage(serverAddress, message, "message");
            this.sendMessages.setText("");
        } catch (Throwable e)
        {
            e.printStackTrace();
            this.chatRecordTextArea.append("ERROR sending message: " + e.getMessage() + "\n");
        }

    }
    /*
    * 点击关闭按钮
    * */
    private void quitBtnActionPerformed(ActionEvent evt){
//        向服务端发送退出消息
    	 try
         {
             String message = "sip:"+ this.getClientName()+"@"+ this.getClientIp()+":"+ this.getClientPort();

             sipLayer.sendMessage(serverAddress, message, "quit");
         } catch (Throwable e)
         {
             e.printStackTrace();
             this.chatRecordTextArea.append("ERROR sending message: " + e.getMessage() + "\n");
         }
         //关闭聊天窗口
         System.exit(0);
    }

    /*
    * 处理从服务端接受的消息
    * */
    public void processMessage(User sender, String message)
    {

        /*
        * 消息格式
        * 0/1 0表示群聊 1表示私聊
        * &
        * 发送方姓名
        * &
        * 接收方姓名
        * */

        String[] data = message.split("&");
        String typeContent;
        if (data[0].equals("0")){
            // 群聊
            typeContent = "[群聊]";
        }
        else {
            //私聊
            typeContent = "[私聊]";
        }
        // 发送该消息的客户端
        String fromName = data[1];
        message = data[2];

        this.chatRecordTextArea.append(typeContent+fromName+"\n"+message+"\n\n");
    }

    public void processError(String errorMessage)
    {

    }

    public void processInfo(String infoMessage)
    {

    }

    @Override
    public void processRegister(User user, String message) {
        /*
        * 更新用户列表
        * message:
        * sip:test1@127.0.0.1:50008
        * sip:test1@127.0.0.1:50004
        * */
        updateFriendsJList(message);
    }
    public void processCancel(User user,String message){
        /*
        * 有用户退出登陆
        * */
        updateFriendsJList(message);
    }

    /*
    * 更新在线用户列表
    * */
    private void updateFriendsJList(String message){
        /*
        * 消息内容
        * test1&sip:test1@127.0.0.1:50008
        * test1&sip:test1@127.0.0.1:50008
        * */
        friendAddressList = new Vector<>();
        friendNameList = new Vector<>();
        friendNameList.add("全部");
        friendAddressList.add("");
        for (String userInfo : message.split("\n")) {
            String[] str1 = userInfo.split("&");
            String name = str1[0];
            String address = str1[1];
            friendAddressList.add(address);
            friendNameList.add(name);
        }
        this.friendsJList.setListData(friendNameList);
    }

    private String getClientName(){
    	return clientName;
    }

    private String getClientIp(){
    	return clientIp;
    }

    private int getClientPort(){
    	return clientPort;
    }
}
