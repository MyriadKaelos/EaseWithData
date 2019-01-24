import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Ease2 extends JPanel implements KeyListener, MouseListener {
    private boolean start = false;
    public int mouseX;
    public int mouseY;
    public int height;
    public int width;
    public int refreshRate;
    private Frame frame;
    private String easeType;
    private Graphics2D g2d;
    private DatagramSocket udpSocket;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private InetAddress IPAddress;
    private byte[] sendData;
    private byte[] receiveData;
    byte[] byteIn = new byte[256];
    byte[] byteOut = new byte[256];
    String StringIn = "";
    String StringOut = "";
    private Timer t;

    public Ease2(int width, int height, int refreshRate, String easeType) {
        this.easeType = easeType;
        JFrame frame = new JFrame("Game");
        frame.add(this);
        frame.addKeyListener(this);
        frame.addMouseListener(this);
        frame.setVisible(true);
        frame.setSize(width, height + 22);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setFocusable(true);
        frame.setLocationRelativeTo(null);
        this.frame = frame;
        this.width = width;
        this.height = height;
        //setting common defaults for a screen.^^^
        this.refreshRate = refreshRate;
    }
    public void paintComponent(Graphics g) {
        g2d = (Graphics2D)g;
        if(!start) {
            System.out.println("starting " + easeType);
            if(easeType == "base") {
                start = true;
            } else if(easeType == "UDPserver") {
                UDPserver();
                System.out.println("server up.");
            } else if(easeType == "TCPserver") {
                TCPserver();
                System.out.println("server up.");
            } else if(easeType == "UDPclient") {
                UDPclient();
                System.out.println("client connected.");
            } else if(easeType == "TCPclient") {
                TCPclient();
                System.out.println("client connected.");
            }
            new Timer(refreshRate, taskPerformer).start();
        }
        mouseX = (int) MouseInfo.getPointerInfo().getLocation().getX() - frame.getX();
        mouseY = (int) MouseInfo.getPointerInfo().getLocation().getY() - frame.getY();
        if(easeType == "base") {
            paint();
        } else if(easeType == "UDPserver") {
            UDPserverS();
        } else if(easeType == "TCPserver") {
            TCPserverS();
        } else if(easeType == "UDPclient") {
            UDPclientS();
        } else if(easeType == "TCPclient") {
            TCPclientS();
        }
    }

    private void UDPserver() {
        try {
            udpSocket = new DatagramSocket(9090);
            System.err.println("Your hostname is: " + InetAddress.getLocalHost().getHostName());
            start = true;
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }
    private void TCPserver() {
        try {
            ServerSocket listener = new ServerSocket(9090);
            System.err.println("Your address is: " + listener.getInetAddress().getLocalHost());
            while (true) try {
                socket = listener.accept();
                //tries to accept the client until the client connects.
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                start = true;
                break;
            } catch (IOException e) {
                System.err.println(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void UDPclient() {
        try {
            udpSocket = new DatagramSocket();
            IPAddress = InetAddress.getByName(JOptionPane.showInputDialog("Type in the hostname which has been displayed on your server's console."));
            start = true;
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
    }
    private void TCPclient() {
        while (true) try {
            socket = new Socket(JOptionPane.showInputDialog("Type in the IP address which has been displayed on your server's console.\nOnly items after the '/'"), 9090);
            //Asks the user to put in the IP address of the server which they are trying to connect to.
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            //Sets up the ports which will send data in and out.
            start = true;
            break;
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    private void UDPserverS() {
        try {
            receiveData = new byte[256];
            sendData = new byte[256];
            DatagramPacket receivePacket;
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            udpSocket.receive(receivePacket);
            byteIn = receivePacket.getData();
            paint();
            sendData = byteOut;
            udpSocket.send(
                    new DatagramPacket(sendData,sendData.length,receivePacket.getAddress(),receivePacket.getPort())
            );
        } catch(IOException e) {
            System.err.println(e);
        }
    }
    private void TCPserverS() {
        try {
            if(in.ready()) {
                StringIn = in.readLine();
                //sets Ease up to use any data sent in.
                paint();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println(StringOut);
    }
    private void UDPclientS() {
        sendData = new byte[256];
        receiveData = new byte[256];
        DatagramPacket sendPacket = new DatagramPacket(
                byteOut,
                byteOut.length,
                IPAddress, 9090);
        try {
            udpSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            udpSocket.receive(receivePacket);
            byteIn = receivePacket.getData();
            paint();
        } catch(IOException e) {
            System.err.println(e);
        }
    }
    private void TCPclientS() {
        try {
            if(in.ready()) {
                StringIn = in.readLine();
                //sets Ease up to use any data sent in.
                paint();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println(StringOut);
    }

    ActionListener taskPerformer = evt -> {
        repaint();
    };
    public void paint() {}

    public void rect(int x, int y, int w, int h, Color c) {
        g2d.setColor(c);
        g2d.fillRect(x,y,w,h);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
