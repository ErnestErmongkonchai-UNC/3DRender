package Render;

import Render.entity.EntityManager;
import Render.input.ClickType;
import Render.input.Mouse;
import Render.point.MyPoint;
import Render.point.PointConverter;
import Render.shapes.MyPolygon;
import Render.shapes.Tetrahedron;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Display extends Canvas implements Runnable { //Runnable enables use of Threads
    private Thread thread; //Threads help initiate multiple tasks
    private JFrame frame;
    private static String title = "3D Render";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private static boolean running = false;

    //private MyPolygon poly;
    //private Tetrahedron tetra;
    private EntityManager entityManager;

    private Mouse mouse;

    public Display(){ //constructor
        this.frame = new JFrame();

        Dimension size = new Dimension(WIDTH, HEIGHT);
        this.setPreferredSize(size);

        this.entityManager = new EntityManager();

        this.mouse = new Mouse();
        this.addMouseListener(this.mouse); //from the Canvas library
        this.addMouseMotionListener(this.mouse);
        this.addMouseWheelListener(this.mouse);
    }

    public static void main(String[] args){
        Display display = new Display();
        display.frame.setTitle(title);
        display.frame.add(display);
        display.frame.pack(); //??????
        display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//close window = program stops running
        display.frame.setLocationRelativeTo(null); //centers frame on screen
        display.frame.setResizable(false); //fixed size no need to resize
        display.frame.setVisible(true);

        display.start();
    }

    public synchronized void start(){ //sync helps threads use method separately
        running = true;
        this.thread = new Thread(this, "Render.Display");
        this.thread.start();
    }

    public synchronized void stop(){
        running = false;
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() { //from the Runnable interface, instantiated when start is called
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 144; //billion ns is one second, divided by 60 bc update 60 time per sec
        double delta = 0; //progress to the next update in %
        int frames = 0;

        //init();
        this.entityManager.init();

        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){ //If an update is skipped this while loop will keep it under 1
                update();
                delta--;
                render();
                frames++;
            }
            /*render(); //unlimited frames
            frames++;*/

            if(System.currentTimeMillis() - timer > 1000){ //display title and fps
                timer += 1000;
                this.frame.setTitle(title + " | " + frames + " fps");
                frames = 0;
            }
        }
        stop();
    }

    /*public void init(){
        this.poly = new MyPolygon( //Polygon
                Color.BLUE,
                new MyPoint(0,100,0),
                new MyPoint(100,0,0),
                new MyPoint(0,0,100));

        int s = 100; //size of object

        MyPoint p1 = new MyPoint(s/2,-s/2,-s/2); //Cube
        MyPoint p2 = new MyPoint(s/2,s/2,-s/2);
        MyPoint p3 = new MyPoint(s/2,s/2,s/2);
        MyPoint p4 = new MyPoint(s/2,-s/2,s/2);
        MyPoint p5 = new MyPoint(-s/2,-s/2,-s/2);
        MyPoint p6 = new MyPoint(-s/2,s/2,-s/2);
        MyPoint p7 = new MyPoint(-s/2,s/2,s/2);
        MyPoint p8 = new MyPoint(-s/2,-s/2,s/2);
        this.tetra = new Tetrahedron(
                new MyPolygon(Color.BLUE,p5,p6,p7,p8),
                new MyPolygon(Color.YELLOW,p1,p2,p6,p5),
                new MyPolygon(Color.WHITE,p1,p5,p8,p4),
                new MyPolygon(Color.GREEN,p2,p6,p7,p3),
                new MyPolygon(Color.ORANGE,p4,p3,p7,p8),
                new MyPolygon(Color.RED,p1,p2,p3,p4));

        MyPoint p1 = new MyPoint(0,0,s/2); //Pyramid
        MyPoint p2 = new MyPoint(s/2,-s/2,-s/2);
        MyPoint p3 = new MyPoint(s/2,s/2,-s/2);
        MyPoint p4 = new MyPoint(-s/2,-s/2,-s/2);
        MyPoint p5 = new MyPoint(-s/2,s/2,-s/2);
        this.tetra = new Tetrahedron(
                new MyPolygon(Color.BLUE,p1,p2,p3),
                new MyPolygon(Color.RED,p1,p3,p5),
                new MyPolygon(Color.GREEN,p1,p5,p4),
                new MyPolygon(Color.WHITE,p1,p4,p2),
                new MyPolygon(Color.YELLOW,p2,p4,p5,p3));
    }*/

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();//creates buffer, graphic points to buffer to minimize tearing
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK); //new Color(0,0,0) for custom
        g.fillRect(0,0,WIDTH,HEIGHT);
        /*g.setColor(Color.RED);
        g.fillRect(50,100,200,200);*/

        //tetra.render(g);
        this.entityManager.render(g);

        g.dispose(); //Disposes of this graphics context and releases any system resources that it is using.
        bs.show(); //Makes the next available buffer visible
    }

    /*ClickType prevMouse = ClickType.Unknown;
    int initialX, initialY;
    double mouseSensitivity = 2.5;*/
    private void update(){
        /*int x = this.mouse.getX(); //takes current mouse position
        int y = this.mouse.getY();
        if(this.mouse.getButton() == ClickType.LeftClick){ //checks if click left
            int xDif = x - initialX; //finds difference from the previous mouse position
            int yDif = y - initialY;
            this.tetra.rotate(true,0,-yDif/mouseSensitivity,-xDif/mouseSensitivity); //rotate according to difference
        }
        else if(this.mouse.getButton() == ClickType.RightCLick){ //rotate in x direction
            int xDif = x - initialX;
            this.tetra.rotate(true,-xDif/mouseSensitivity,0,0);
        }

        if(this.mouse.isScrollingUp()){
            PointConverter.zoomIn();
        } else if(this.mouse.isScrollingDown()){
            PointConverter.zoomOut();
        }
        this.mouse.resetScroll();

        initialX = x;
        initialY = y;
        //this.tetra.rotate(true,.3,.3,.5);
        //System.out.println(this.mouse.getX() + " " + this.mouse.getY()); //printout mouse position
        //System.out.println(this.mouse.getButton());*/
        this.entityManager.update(this.mouse); //entity manager does the logic above
        //Display is more about displaying not logic
        //Can add a World Manager that manages entities
    }
}
