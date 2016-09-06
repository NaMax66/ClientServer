import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Naidovich on 31.08.2016.
 */
public class Client extends JFrame implements Runnable {

    private static Thread client, server;
    private static ArrayList<String> list = new ArrayList<>();
    private static Socket connection;
    private static ObjectOutputStream outputStream;
    private static ObjectInputStream inputStream;

    public static void main(String[] args) {
        client = new Thread(new Client("Client"));
    }

    public Client(String title) throws HeadlessException {
        super(title);

        setLayout(new FlowLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);



        JTextField t1 = new JTextField(10);
        JButton b1 = new JButton("Send");
        JButton b2 = new JButton("Stop");
        JButton b3 = new JButton("Start");
        setVisible(true);


        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.start();
                System.out.println("клиент запущен");
            }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.stop();
                System.out.println("клиент остановлен");
                for (String a : list)
                {
                    System.out.println(a);
                }
            }
        });



        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == b1)
                {
                    sendData(t1.getText());
                }
            }
        });
        add(b1);
        add(b2);
        add(b3);
        add(t1);
    }

    private static void sendData(Object obj) {

        try {
            outputStream.flush();
            outputStream.writeObject(obj);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        try
        {
            while (true)
            {
                connection = new Socket(InetAddress.getByName("127.0.0.1"), 5678);
                outputStream = new ObjectOutputStream(connection.getOutputStream());
                inputStream = new ObjectInputStream(connection.getInputStream());
                String massage = (String) inputStream.readObject();
                JOptionPane.showMessageDialog(null, massage);
                list.add(massage);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
