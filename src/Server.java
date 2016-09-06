import javax.swing.*;
import javax.xml.stream.Location;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Naidovich on 31.08.2016.
 */
public class Server extends JFrame implements Runnable {

    static Thread server;

    public static void main(String[] args) {

        server = new Thread(new Server("Server"));

    }

    public Server(String title) throws HeadlessException {
        super(title);

        setLayout(new FlowLayout());
        setLocationRelativeTo(null);
        setSize(300, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        JButton b2 = new JButton("Stop");
        JButton b3 = new JButton("Start");

        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.start();
                System.out.println("Сервер запущен");
            }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.stop();
                System.out.println("Сервер остановлен");
            }
        });



        add(b2);
        add(b3);

    }

    private static ServerSocket serverSocket;
    private static Socket connection;
    private static ObjectOutputStream outputStream;
    private static ObjectInputStream inputStream;

    @Override

    public void run() {

        try {
            serverSocket = new ServerSocket(5678, 10);
            while (true) {
                connection = serverSocket.accept();
                outputStream = new ObjectOutputStream(connection.getOutputStream());
                inputStream = new ObjectInputStream(connection.getInputStream());
                String s = (String) inputStream.readObject();

                outputStream.writeObject("Вы прислали: " + s);
                JOptionPane.showMessageDialog(null, s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}