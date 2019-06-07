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

    // �û���
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

    // �洢�����û���ַ�б�
    private Vector<String> friendAddressList;
    // �����û�name
    private Vector<String> friendNameList;
    // Ҫ���͵ĵ�ַ
    private String toAddress = "";

    // ����˵�ַ
    String serverAddress;


    public static void main(String[] args) throws InvalidArgumentException, SipException, TooManyListenersException, ParseException {
        new TextClient();
    }


    public TextClient() throws InvalidArgumentException, SipException, TooManyListenersException, ParseException {
        // ������ʽ�Ŀͻ���
        super();
        // �ͻ���name
        clientName = "test3";
        // �ͻ���ip
        clientIp = "127.0.0.1";
        // �ͻ��˶˿�
        clientPort = 50010;

        sipLayer = new SipLayer(clientName, clientIp, clientPort);

        initWindow();

        this.usernameTextarea.setText(sipLayer.getUsername());

        sipLayer.setMessageProcessor(this);
        this.show();

        // ����˵�ַ
        serverAddress = "sip:serverAddress@127.0.0.1:5060";
        //����server��ע����Ϣ
        sipLayer.sendMessage(serverAddress, ":"+clientName+"@"+clientIp+":"+clientPort, "register");

        System.out.printf("sipL%s@%s:%d �û�ע��ɹ�",clientName,clientIp,clientPort);
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

        setTitle("�ͻ���");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                quitBtnActionPerformed(null);
                System.exit(0);
            }
        });

        // �û��б����
        friendListLabel.setText("�û��б�");
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
                    // ����˿հ״�
                    return;
                }
                toAddress = friendAddressList.get(index);
            }
        });

        // ������
        usernameLabel.setText("�û�����");
        getContentPane().add(usernameLabel);
        usernameLabel.setBounds(10, 10, 60, 30);

        getContentPane().add(usernameTextarea);
        usernameTextarea.setBounds(75, 10, 60, 30);

        // �����¼
        chatRecordTextArea.setAlignmentX(0.0F);
        chatRecordTextArea.setEditable(false);
        chatRecordTextArea.setLineWrap(true);
        chatRecordTextArea.setWrapStyleWord(true);
        receivedScrollPane.setViewportView(chatRecordTextArea);
        receivedScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        getContentPane().add(receivedScrollPane);
        receivedScrollPane.setBounds(5, 50, 365, 290);

        // ���������
        getContentPane().add(sendMessages);
        sendMessages.setBounds(5, 350, 365, 80);

        sendBtn.setText("����");
        sendBtn.addActionListener(this::sendBtnActionPerformed);

        getContentPane().add(sendBtn);
        sendBtn.setBounds(245, 440, 60, 30);

        quitBtn.setText("�ر�");
        quitBtn.addActionListener(this::quitBtnActionPerformed);

        getContentPane().add(quitBtn);
        quitBtn.setBounds(310, 440, 60, 30);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-400)/2, (screenSize.height-320)/2, 545, 520);
    }
    /*
    * ������Ͱ�ť
    * */
    private void sendBtnActionPerformed(ActionEvent evt) {
        // ��Ϣû�����ݵ�ʱ�򲻷���
        String messageText = this.sendMessages.getText().trim();
        if (messageText.length() == 0){
            return;
        }

        try
        {
            /*
            * �����˷���������Ϣ
            * ���ͷ���ַ
            * &
            * ���շ���ַ(Ϊ�ձ�ʾȺ��)
            * &
            * ��Ϣ����
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
    * ����رհ�ť
    * */
    private void quitBtnActionPerformed(ActionEvent evt){
//        �����˷����˳���Ϣ
    	 try
         {
             String message = "sip:"+ this.getClientName()+"@"+ this.getClientIp()+":"+ this.getClientPort();

             sipLayer.sendMessage(serverAddress, message, "quit");
         } catch (Throwable e)
         {
             e.printStackTrace();
             this.chatRecordTextArea.append("ERROR sending message: " + e.getMessage() + "\n");
         }
         //�ر����촰��
         System.exit(0);
    }

    /*
    * ����ӷ���˽��ܵ���Ϣ
    * */
    public void processMessage(User sender, String message)
    {

        /*
        * ��Ϣ��ʽ
        * 0/1 0��ʾȺ�� 1��ʾ˽��
        * &
        * ���ͷ�����
        * &
        * ���շ�����
        * */

        String[] data = message.split("&");
        String typeContent;
        if (data[0].equals("0")){
            // Ⱥ��
            typeContent = "[Ⱥ��]";
        }
        else {
            //˽��
            typeContent = "[˽��]";
        }
        // ���͸���Ϣ�Ŀͻ���
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
        * �����û��б�
        * message:
        * sip:test1@127.0.0.1:50008
        * sip:test1@127.0.0.1:50004
        * */
        updateFriendsJList(message);
    }
    public void processCancel(User user,String message){
        /*
        * ���û��˳���½
        * */
        updateFriendsJList(message);
    }

    /*
    * ���������û��б�
    * */
    private void updateFriendsJList(String message){
        /*
        * ��Ϣ����
        * test1&sip:test1@127.0.0.1:50008
        * test1&sip:test1@127.0.0.1:50008
        * */
        friendAddressList = new Vector<>();
        friendNameList = new Vector<>();
        friendNameList.add("ȫ��");
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
