        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.*;
        import javax.sound.midi.*;
public class DrawCircle {
    private JFrame mainFrame;
    private DrawPanel panelR = new DrawPanel();
    private int moveCircle=0;
    private boolean circleStormFlag=false;
    private boolean animatedCircleFlag=false;
    private boolean soundCirclesFlag=false;
    private boolean pulseCircleFlag=false;
    private boolean colorCircleFlag=false;
    private int howLongWait=5;
    private Sequencer musicSequencer;

    public static void main(String[] args) {
        new DrawCircle().makeWindow();
    }

    private void makeWindow() {
        mainFrame = new JFrame("Draw Circle");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        JPanel backgroundPanel = new JPanel(layout);
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        Box buttonsArea = new Box(BoxLayout.Y_AXIS);

        JButton CircleStorm = new JButton("Circle Storm");
        CircleStorm.addActionListener(new CircleStormListener());
        buttonsArea.add(CircleStorm);

        JButton animatedCircle = new JButton("Animated Circle");
        animatedCircle.addActionListener(new AnimatedCircle());
        buttonsArea.add(animatedCircle);

        JButton pulseCircle = new JButton("Pulse Circle");
        pulseCircle.addActionListener(new PulseCircle());
        buttonsArea.add(pulseCircle);

        JButton colorCircle = new JButton("Draw Circle");
        colorCircle.addActionListener(new ColorCircle());
        buttonsArea.add(colorCircle);

        JButton soundCircle = new JButton("Sound Circle");
        soundCircle.addActionListener(new SoundCircle());
        buttonsArea.add(soundCircle);

        JButton slowerCircle = new JButton("Slower");
        slowerCircle.addActionListener(new SlowerCircle());
        buttonsArea.add(slowerCircle);

        JButton fasterCircle = new JButton("Faster");
        fasterCircle.addActionListener(new FasterCircle());
        buttonsArea.add(fasterCircle);

        panelR = new DrawPanel();
        backgroundPanel.add(BorderLayout.EAST, buttonsArea);
        backgroundPanel.add(BorderLayout.CENTER, panelR);
        mainFrame.getContentPane().add(backgroundPanel);
        mainFrame.setSize(1600, 800);
        mainFrame.setVisible(true);
        moveCircleForever();
    }

    private void makeAllFalse(){
        circleStormFlag=false;
        animatedCircleFlag=false;
        soundCirclesFlag=false;
        pulseCircleFlag=false;
        colorCircleFlag=false;
    }

    private void moveCircleForever(){
        while (true) {                                      //moving Circle forever
            if (!colorCircleFlag && !soundCirclesFlag) {
                for (int i = 0; i < 200; i++) {
                    moveCircle += 5;
                    mainFrame.repaint();
                    if(colorCircleFlag || soundCirclesFlag) {
                        moveCircle=0;
                        break;}
                    try {
                        Thread.sleep(howLongWait);
                    } catch (Exception ignored) {
                    }
                }
                for (int i = 0; i < 200; i++) {
                    moveCircle -= 5;
                    mainFrame.repaint();
                    if(colorCircleFlag || soundCirclesFlag) {
                        moveCircle=0;
                        break;}
                    try {
                        Thread.sleep(howLongWait);
                    } catch (Exception ignored) {
                    }
                }
            }
            try {
                Thread.sleep(20);
            } catch (Exception ignored) {
            }
        }
    }

    private void makeMusic() {
        try {
            musicSequencer = MidiSystem.getSequencer();
            musicSequencer.open();
            musicSequencer.addControllerEventListener(panelR, new int[]{127});
            Sequence musicSequence = new Sequence(Sequence.PPQ, 4);
            Track musicTrack = musicSequence.createTrack();
            musicTrack.add(event(192, 100, 0, 1));

            for (int i = 5; i < 61; i++) {
                musicTrack.add(event(144, i, 100, i));
                musicTrack.add(event(176, 127, 0, i));
                musicTrack.add(event(128, i, 100, i + 3));
            }
            for (int i = 5; i < 61; i++) {
                musicTrack.add(event(144, 70 - i, 100,  i+61));
                musicTrack.add(event(176, 127, 0,  i+61));
                musicTrack.add(event(128, 70 - i, 100,  i + 3+61));
            }
            musicSequencer.setSequence(musicSequence);
            musicSequencer.setLoopCount(musicSequencer.LOOP_CONTINUOUSLY);
            musicSequencer.setTempoInBPM(120);
            musicSequencer.start();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private static MidiEvent event(int plc, int one, int two, int bar) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(plc, 1, one, two);
            event = new MidiEvent(a, bar);
        } catch (Exception ignored) {}
        return event;
    }

    class CircleStormListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            makeAllFalse();
            circleStormFlag=true;
            musicSequencer.stop();
        }
    }

    class AnimatedCircle implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            makeAllFalse();
            animatedCircleFlag=true;
            musicSequencer.stop();
        }
    }

    class PulseCircle implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            makeAllFalse();
            pulseCircleFlag=true;
            musicSequencer.stop();
        }
    }

    class ColorCircle implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            makeAllFalse();
            colorCircleFlag=true;
            mainFrame.repaint();
            musicSequencer.stop();
        }
    }

    class SoundCircle implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            makeMusic();
            makeAllFalse();
            soundCirclesFlag=true;
        }
    }

    class SlowerCircle implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            howLongWait+=5;
        }
    }

    class FasterCircle implements ActionListener{
        public void actionPerformed(ActionEvent e) {

            if (howLongWait>5){ howLongWait-=5; }
            else {howLongWait=1; }
        }
    }

    class DrawPanel extends JPanel implements ControllerEventListener{
        boolean message = false;

        public void controlChange(ShortMessage event) { //draw when music play
            message = true;
            repaint();
        }
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            if (!soundCirclesFlag) {
                g2d.setPaint(Color.black);
                g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
            }
            int red = (int) (Math.random() * 256);                     //make random color for gradient
            int green = (int) (Math.random() * 256);
            int blue = (int) (Math.random() * 256);
            g.setColor(new Color(red, green, blue));
            Color startColor = new Color(red,green,blue);
            red = (red+128)%256;
            green = (green+128)%256;
            blue = (blue+128)%256;
            Color endColor = new Color(red,green,blue);
            GradientPaint gradient = new GradientPaint(0,0,startColor, 500,1000, endColor);
            g2d.setPaint(gradient);

            if (circleStormFlag) {
                for (int i = 0; i <5 ; i++) {
                    red = (int) (Math.random() * 256);                     //make random color for gradient
                    green = (int) (Math.random() * 256);
                    blue = (int) (Math.random() * 256);
                    g.setColor(new Color(red, green, blue));
                    int x = (int) (Math.random() * 1300);
                    int y = (int) (Math.random() * 600);
                    g2d.fillOval(x + 20, y + 10, 150, 150);
                  
                }

            }
            if(animatedCircleFlag){
                g2d.fillOval(moveCircle,100,500,500);

            }
            if(pulseCircleFlag){
                for (int j = 0; j <2 ; j++) {
                    for (int i = 0; i < 5; i++) {
                        g2d.fillOval((moveCircle / 19) + i * 300, (moveCircle / 6) + 50+ j * 300, (moveCircle / 5) + 20, (moveCircle / 5) + 20);

                    }
                }

            }
            if(colorCircleFlag){
                g2d.fillOval(375,150,500,500);

            }
            if (soundCirclesFlag) {

                if (message) {
                    int x = (int) (Math.random() * 1100);
                    int y = (int) (Math.random() * 400);
                    g2d.fillOval(x + 20, y + 60, 200, 200);

                    message = false;
                }
            }
        }
    }
}