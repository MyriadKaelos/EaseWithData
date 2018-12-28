
import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Ease extends JPanel implements KeyListener, MouseListener {
    private Graphics2D g2d;
    private boolean isServer;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    boolean start = false;
    boolean serverClient;
    //Data which is only used by Ease, not by the user^^^
    int res;
    int height;
    int width;
    int trueHeight;
    int trueWidth;
    int refreshRate;
    //Parameters of the Screen. May be used by user^^^
    Object dataIn;
    Object dataOut;
    //Parameters which will only be used in the case that Ease is instantiated as a server or a client^^^
    public Ease(int width, int height, int res, int refreshRate) throws InterruptedException {
        this.serverClient = false;
        JFrame frame = new JFrame("Game");
        frame.add(this);
        frame.addKeyListener(this);
        frame.addMouseListener(this);
        frame.setVisible(true);
        frame.setSize(width,height + 22);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setFocusable(true);
        frame.setLocationRelativeTo(null);
        //setting common defaults for a screen.^^^
        this.height = height/res;
        this.width = width/res;
        this.trueHeight = height;
        this.trueWidth = width;
        this.res = res;
        this.refreshRate = refreshRate;
        this.start = true;
        //Instantiating variables.
    }
    public Ease(int width, int height, int res, int refreshRate,boolean isServer) throws InterruptedException, IOException {
        this.serverClient = true;
        this.isServer = isServer;
        JFrame frame = new JFrame("Game");
        frame.add(this);
        frame.addKeyListener(this);
        frame.addMouseListener(this);
        frame.setVisible(true);
        frame.setSize(width,height + 22);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setFocusable(true);
        frame.setLocationRelativeTo(null);
        //setting common defaults for a screen.^^^
        this.height = height/res;
        this.width = width/res;
        this.trueHeight = height;
        this.trueWidth = width;
        this.res = res;
        this.refreshRate = refreshRate;
        //Instantiating variables.
        if(isServer) {
            ServerSocket listener = new ServerSocket(9090);
            System.err.println(listener.getInetAddress());
            //Sets up a listener and print the IP to be connected to.
            while (true) try {
                socket = listener.accept();
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                System.err.println("Connected");
                break;
            } catch (IOException e) {
                System.err.println(e);
            }
        } else {
            while (true) try {
                socket = new Socket(JOptionPane.showInputDialog("Make sure that your Server is up and running before you type in your answer, \n What is your server's IP address?"), 9090);
                //Asks the user to put in the IP address of the server which they are trying to connect to.
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                //Sets up the ports which will send data in and out.
                System.err.println("Connected");
                break;
            } catch (IOException e) {
                System.err.println(e);
            }
        }
        start = true;
        repaint();
        //Starts up Ease.
    }
    @Override
    public void paintComponent(Graphics g) {
        if(start && serverClient) {
            g2d = (Graphics2D)g;

            try {
                if(in.ready()) {
                    dataIn = in.readLine();
                    //sets Ease up to use any data sent in.
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            paint();
            out.println(dataOut);
            //Sends out data which ease has set to the dataOut tag.
            try {
                Thread.sleep(refreshRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        } else if(start) {
            g2d = (Graphics2D)g;
            paint();
            try {
                Thread.sleep(refreshRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }
    public void paint() {

    }//Intended to be overwritten by client.
    public void pixel(int x, int y, Color color) {
        g2d.setColor(color);
        g2d.fillRect(x*res,y*res,res,res);
    }//Makes painting pixels easier.
    public void rect(int x, int y, int w, int h, Color color) {
        g2d.setColor(color);
        g2d.fillRect(x*res, y*res, w*res, h*res);
    }//Makes painting rectangles easier.
    @Override
    public void keyPressed(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {

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
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }
}
