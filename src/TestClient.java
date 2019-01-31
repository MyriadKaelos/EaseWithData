import static java.awt.Color.black;

public class TestClient extends Ease2 {
    int ballX = width/2;
    int ballY = height/2;
    int vX = 4;
    int vY = 4;
    int opponent = 0;
    int timePassed = 0;
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
        timePassed++;
    }
    public byte[] toByteConvert(int[] arr) {
        byte[] b = new byte[15];
        for(int i = 0; i < arr.length; i ++) {
            b[(i*3)] = (byte) (Math.abs(arr[i])%128);
            b[(i*3)+1] = (byte) (Math.abs(arr[i])/128);
            b[(i*3)+2] = (byte) (arr[i] < 0 ? 0 : 2);
        }
        return b;
    }
    public int[] toIntConvert(byte[] b) {
        int[] arr = new int[5];
        for(int i = 0; i < arr.length; i ++) {
            int ones = (int) b[i*3];
            int tens = ((int) b[(i*3)+1]) * 128;
            int sign = ((int) b[(i*3)+2]) - 1;
            arr[i] = (ones + tens) * sign;
        }
        return arr;
    }
    public void send() {
        byteOut = toByteConvert(new int[] {mouseX,ballX,height-ballY,vX,vY * -1});
    }
    public void receive() {
        int[] arr = toIntConvert(byteIn);
        opponent = arr[0];
        if (vX == 0 && timePassed < 10) {
            vX = 4;
            vY = 4;
            ballX = width/2;
            ballY = height/2;
        } else if(timePassed > 10) {
            ballX = arr[1];
            ballY = arr[2];
            vY = arr[4];
            vX = arr[3];
        }
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
        rect(opponent-(width/8),9*height/10,width/4,height/20,black);
    }
}
