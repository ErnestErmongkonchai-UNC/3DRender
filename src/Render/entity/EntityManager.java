package Render.entity;

import Render.entity.builder.BasicEntityBuilder;
import Render.entity.builder.ComplexEntityBuilder;
import Render.input.ClickType;
import Render.input.Mouse;
import Render.point.MyPoint;
import Render.point.MyVector;
import Render.point.PointConverter;
import Render.shapes.MyPolygon;
import Render.shapes.Tetrahedron;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    private List<IEntity> entities;
    private int initialX, initialY;
    private ClickType prevMouse = ClickType.Unknown;
    private double mouseSensitivity = 2.5;
    private MyVector lightVector = MyVector.normalize(new MyVector(.3,-1,-1));

    public EntityManager(){
        this.entities = new ArrayList<IEntity>();
    }

    public void init(){
        //this.entities.add(BasicEntityBuilder.createCube(100,0,0,0));
        this.entities.add(BasicEntityBuilder.createRing(Color.gray,100,0,0,0));
        this.entities.add(BasicEntityBuilder.createDiamond(Color.cyan,50,0,0,75));

        //this.entities.add(ComplexEntityBuilder.createRubiksCube(100,0,0,0));
        this.setLighting(lightVector);
    }

    public void update(Mouse mouse){
        int x = mouse.getX(); //takes current mouse position
        int y = mouse.getY();
        if(mouse.getButton() == ClickType.LeftClick){ //checks if click left
            int xDif = x - initialX; //finds difference from the previous mouse position
            int yDif = y - initialY;
            this.rotate(true,0,-yDif/mouseSensitivity,-xDif/mouseSensitivity); //rotate according to difference
        }
        else if(mouse.getButton() == ClickType.RightCLick){ //rotate in x direction
            int xDif = x - initialX;
            this.rotate(true,-xDif/mouseSensitivity,0,0);
        }

        if(mouse.isScrollingUp()){
            PointConverter.zoomIn();
        } else if(mouse.isScrollingDown()){
            PointConverter.zoomOut();
        }
        mouse.resetScroll();

        initialX = x;
        initialY = y;
    }

    public void render(Graphics g){
        for(IEntity entity : this.entities){
            entity.render(g);
        }
    }

    private void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees){
        for(IEntity entity : this.entities){
            entity.rotate(CW,xDegrees,yDegrees,zDegrees,this.lightVector);
        }
    }

    private void setLighting(MyVector lightVector){
        for(IEntity entity: this.entities){
            entity.setLighting(lightVector);
        }
    }
}
