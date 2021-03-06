package networking;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

public class ChatServer extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatServer frame = new ChatServer();
					frame.setVisible(true);
					frame.intialize();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	private  DataInputStream in;
	private DataOutputStream out;
	private ServerSocket server;
	JTextArea textArea = new JTextArea();
	public void intialize() throws IOException {
		server = new ServerSocket(1039);
		textArea.setText("Waiting for client..");
		Socket socket=server.accept();
		in= new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
	}
	public void recieveMessageClient() throws IOException {
	    String reciveMessage="";
		while(!reciveMessage.equalsIgnoreCase("exit")) {
			reciveMessage = in.readUTF();
			textField.setText(textArea.getText()+"\n client::"+reciveMessage);
		}
	}
	public void sendMessage() {
		String message=textField.getText();
		try {
			out.writeUTF(message);
			textArea.setText(textField.getText()+"\n srever::"+message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public ChatServer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 823, 696);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 25, 743, 443);
		contentPane.add(scrollPane);
		
		
		textArea.setBorder(new LineBorder(Color.BLUE));
		textArea.setFont(new Font("Dialog", Font.BOLD, 16));
		scrollPane.setViewportView(textArea);
		
		textField = new JTextField();
		textField.setBorder(new LineBorder(Color.BLUE));
		textField.setFont(new Font("Dialog", Font.BOLD, 16));
		textField.setBounds(27, 500, 743, 57);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("SEND");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});
		btnNewButton.setFont(new Font("Dialog", Font.BOLD, 16));
		btnNewButton.setBounds(637, 595, 97, 25);
		contentPane.add(btnNewButton);
	}
}
