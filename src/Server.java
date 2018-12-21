import java.awt.event.KeyEvent;
import java.io.IOException;

public class Server extends Ease {

    public Server(int width, int height, int res, int refreshRate) throws InterruptedException, IOException {
        super(width, height, res, refreshRate, true);
    }
    public static void main(String[] args) throws InterruptedException, IOException {
        Server s = new Server(400,400,4,300);
    }
    public void paint() {

    }

    public void keyPressed(KeyEvent e) {
        dataOut = e.getKeyChar();
        System.out.println(dataIn);
    }
}
