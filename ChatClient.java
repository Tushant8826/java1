package networking;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChatClient extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private DataInputStream in;
	private DataOutputStream out;
	JTextArea textArea = new JTextArea();
	private Socket socket;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatClient frame = new ChatClient();
					frame.intialize();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public void intialize() throws UnknownHostException, IOException {
		 socket = new Socket("localhost",1039);
		 in =  new DataInputStream(socket.getInputStream());
		 out = new DataOutputStream(socket.getOutputStream());
	}
	public void recieveMessageServer() throws IOException {
	    String reciveMessage="";
		while(!reciveMessage.equalsIgnoreCase("exit")) {
			reciveMessage = in.readUTF();
			textField.setText(textArea.getText()+"\n server::"+reciveMessage);
		}
	}
	public void sendMessage() {
		String message=textField.getText();
		try {
			out.writeUTF(message);
			textArea.setText(textArea.getText()+"\n cleint::"+message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public ChatClient() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 783, 680);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 25, 715, 406);
		contentPane.add(scrollPane);
		
		textArea.setFont(new Font("Dialog", Font.BOLD, 16));
		textArea.setBorder(new LineBorder(Color.CYAN));
		scrollPane.setViewportView(textArea);
		
		textField = new JTextField();
		textField.setBorder(new LineBorder(Color.CYAN));
		textField.setFont(new Font("Dialog", Font.BOLD, 16));
		textField.setBounds(22, 473, 703, 59);
		contentPane.add(textField);
		textField.setColumns(10);		
		JButton sendButton = new JButton("SEND");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});
		sendButton.setFont(new Font("Dialog", Font.BOLD, 17));
		sendButton.setBounds(571, 574, 97, 25);
		contentPane.add(sendButton);
	}
}
