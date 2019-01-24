import static java.awt.Color.black;

public class Example extends Ease2 {
    int ballX = width/2;
    int ballY = height/2;
    int vX = 8;
    int vY = 8;
    int opponent = 0;
    public Example(int width, int height, int refreshRate) throws InterruptedException {
        super(width, height, refreshRate, "base");
    }
    public static void main(String[] args) throws InterruptedException {
        Example e = new Example(400,400,100);
    }
    public void paint() {
        receive();
        ball();
        me();
        you();
        send();
    }
    public void send() {

    }
    public void receive() {

    }
    public void ball() {
        if((ballX+(width/40) >= width && vX > 0) || (ballX <= 0 && vX < 0)) {
            vX *= -1;
        }
        if((ballY+(width/40) >= height) || ballY <= 0 ||
                (ballX+(width/40) > mouseX-(width/8) && ballX < mouseX+(width/8) && ballY < height/10 && ballY+(width/40) > height/20)) {
            vY *= -1;
        }
        ballX += vX;
        ballY += vY;
        rect(ballX,ballY,width/40,width/40,black);
    }
    public void me() {
        rect(mouseX-(width/8),height/20,width/4,height/20,black);
    }
    public void you() {
        rect(opponent-(width/8),height/20,width/4,height/20,black);
    }
}
