
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
    int res;
    int height;
    int width;
    int trueHeight;
    int trueWidth;
    Graphics2D g2d;
    int refreshRate;
    boolean isServer;
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    Object dataIn;
    Object dataOut;
    boolean start = false;
    public Ease(int width, int height, int res, int refreshRate) throws InterruptedException {
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
        this.height = height/res;
        this.width = width/res;
        this.trueHeight = height;
        this.trueWidth = width;
        this.res = res;
        this.refreshRate = refreshRate;
    }
    public Ease(int width, int height, int res, int refreshRate,boolean isServer) throws InterruptedException, IOException {
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
        this.height = height/res;
        this.width = width/res;
        this.trueHeight = height;
        this.trueWidth = width;
        this.res = res;
        this.refreshRate = refreshRate;
        if(isServer) {
            ServerSocket listener = new ServerSocket(9090);
            System.err.println(listener.getInetAddress());
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
                socket = new Socket("10.137.41.74", 9090);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                System.err.println("Connected");
                break;
            } catch (IOException e) {
                System.err.println(e);
            }
        }
        start = true;
        repaint();
    }
    @Override
    public void paintComponent(Graphics g) {
        if(start) {
            g2d = (Graphics2D)g;
            if(isServer) {
                try {
                    if(in.ready()) {
                        dataIn = in.readLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                paint();
                out.println(dataOut);
            } else {
                try {
                    if(in.ready()) {
                        dataIn = in.readLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                paint();
                out.println(dataOut);
            }
            try {
                Thread.sleep(refreshRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }
    public void paint() {

    }
    public void pixel(int x, int y, Color color) {
        g2d.setColor(color);
        g2d.fillRect(x*res,y*res,res,res);
    }
    public void rect(int x, int y, int w, int h, Color color) {
        g2d.setColor(color);
        g2d.fillRect(x*res, y*res, w*res, h*res);
    }
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
