package Render.shapes;

import Render.point.MyPoint;
import Render.point.MyVector;
import javafx.scene.PointLight;

import java.awt.*;


public class Tetrahedron { //array of polygons
    private MyPolygon[] polygons;
    private Color color;

    public Tetrahedron(Color color, boolean decayColor, MyPolygon... polygons){
        this.color = color;
        this.polygons = polygons;

        if(decayColor){
            this.setDecayPolygonColor();
        } else{
            this.setPolygonColor();
        }
        this.sortPolygons();
    }

    public Tetrahedron(MyPolygon... polygons){
        this.color = Color.WHITE;
        this.polygons = polygons;
    }

    public void render(Graphics g){ //use polygon methods to draw
        for(MyPolygon poly : this.polygons){
            poly.render(g);
        }
    }

    public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees, MyVector lightVector){
        for(MyPolygon p : this.polygons){
            p.rotate(CW,xDegrees,yDegrees,zDegrees,lightVector);
        }
        this.sortPolygons();
    }

    public void setLighting(MyVector lightVector){
        for(MyPolygon p : this.polygons){
            p.setLighting(lightVector);
        }
    }

    public MyPolygon[] getPolygons(){
        return this.polygons;
    }

    private void sortPolygons(){
        MyPolygon.sortPolygons(this.polygons);
    }

    private void setPolygonColor(){
        for(MyPolygon poly: this.polygons){
            poly.setColor(this.color);
        }
    }

    private void setDecayPolygonColor(){
        double decay = 0.98;
        for(MyPolygon poly : this.polygons){
            poly.setColor(this.color);
            int r = (int)(this.color.getRed() * decay);
            int g = (int)(this.color.getGreen() * decay);
            int b = (int)(this.color.getBlue() * decay);
            this.color = new Color(r,g,b);
        }
    }

}
