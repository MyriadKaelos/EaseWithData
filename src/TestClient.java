import static java.awt.Color.black;

public class TestClient extends Ease2 {
    int ballX = width/2;
    int ballY = height/2;
    int vX = 8;
    int vY = 8;
    int opponent = 0;
    public TestClient(int width, int height, int refreshRate) throws InterruptedException {
        super(width, height, refreshRate, "UDPclient");
    }
    public static void main(String[] args) throws InterruptedException {
        TestClient s = new TestClient(400,400,100);
    }
    public void paint() {
        receive();
        ball();
        me();
        you();
        send();
    }
    public void send() {
        byteOut = new byte[]{(byte) (mouseX / 256), (byte) (mouseX % 256),
                (byte) (ballX / 256), (byte) (ballX % 256),
                (byte) ((height-ballY) / 256), (byte) ((height-ballY) % 256),
                (byte) (vX+8),(byte) (8-vY)};
    }
    public void receive() {
        opponent = (int) byteIn[0] * 256 + ((int) byteIn[1] >= 0 ? ((int) byteIn[1]) : ((int) byteIn[1] + 256));
        ballX = (int) byteIn[2] * 256 + ((int) byteIn[3] >= 0 ? ((int) byteIn[3]) : ((int) byteIn[3] + 256));
        ballY = (int) byteIn[4] * 256 + ((int) byteIn[5] >= 0 ? ((int) byteIn[5]) : ((int) byteIn[5] + 256));
        vX = (int) byteIn[6] - 8;
        vY = (int) byteIn[7] - 8;
    }
    public void ball() {
        if((ballX+(width/40) >= width && vX > 0) || (ballX <= 0 && vX < 0)) {
            vX *= -1;
        }
        if((ballY+(width/40) >= height) || ballY <= 0 ||
                (ballX+(width/40) > mouseX-(width/8) && ballX < mouseX+(width/8) && ballY < height/10 && ballY+(width/40) > height/20)) {
            vY *= -1;
        }
        System.out.println(ballX + " " + ballY);
        ballX += vX;
        ballY += vY;
        rect(ballX,ballY,width/40,width/40,black);
    }
    public void me() {
        rect(mouseX-(width/8),height/20,width/4,height/20,black);
    }
    public void you() {
        rect(opponent-(width/8),9*height/10,width/4,height/20,black);
    }
}
