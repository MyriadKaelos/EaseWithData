import java.awt.event.KeyEvent;
import java.io.IOException;

public class Client extends Ease {
    public Client(int width, int height, int res, int refreshRate) throws InterruptedException, IOException {
        super(width, height, res, refreshRate, false);
    }
    public static void main(String[] args) throws InterruptedException, IOException {
        Client c = new Client(400,400,4,300);
    }
    public void paint() {

    }
    public void keyPressed(KeyEvent e) {
        dataOut = e.getKeyChar();
        System.out.println(dataIn);
    }
}
