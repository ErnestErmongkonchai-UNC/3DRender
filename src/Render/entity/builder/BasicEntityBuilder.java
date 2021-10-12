package Render.entity.builder;

import Render.entity.Entity;
import Render.entity.IEntity;
import Render.point.MyPoint;
import Render.shapes.MyPolygon;
import Render.shapes.Tetrahedron;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BasicEntityBuilder {

    public static IEntity createCube(double s, double centerX, double centerY, double centerZ){
        MyPoint p1 = new MyPoint(centerX + s/2,centerY + -s/2,centerZ + -s/2); //Cube
        MyPoint p2 = new MyPoint(centerX + s/2,centerY + s/2,centerZ + -s/2);
        MyPoint p3 = new MyPoint(centerX + s/2,centerY + s/2,centerZ + s/2);
        MyPoint p4 = new MyPoint(centerX + s/2,centerY + -s/2,centerZ + s/2);
        MyPoint p5 = new MyPoint(centerX + -s/2,centerY + -s/2,centerZ + -s/2);
        MyPoint p6 = new MyPoint(centerX + -s/2,centerY + s/2,centerZ + -s/2);
        MyPoint p7 = new MyPoint(centerX + -s/2,centerY + s/2,centerZ + s/2);
        MyPoint p8 = new MyPoint(centerX + -s/2,centerY + -s/2,centerZ + s/2);

        Tetrahedron tetra = new Tetrahedron(
                new MyPolygon(Color.BLUE,p5,p6,p7,p8),
                new MyPolygon(Color.YELLOW,p1,p2,p6,p5),
                new MyPolygon(Color.WHITE,p1,p5,p8,p4),
                new MyPolygon(Color.GREEN,p2,p6,p7,p3),
                new MyPolygon(Color.ORANGE,p4,p3,p7,p8),
                new MyPolygon(Color.RED,p1,p2,p3,p4));

        List<Tetrahedron> tetras = new ArrayList<Tetrahedron>();
        tetras.add(tetra);

        return new Entity(tetras);
    }

    public static IEntity createDiamond(Color color, double size, double centerX, double centerY, double centerZ){
        List<Tetrahedron> tetras = new ArrayList<Tetrahedron>();

        int edges = 20; //shape on top
        double inFactor = 0.8; //ratio between top shape and outer ring
        MyPoint bottom = new MyPoint(centerX,centerY,centerZ - size/2);
        MyPoint[] outerPoints = new MyPoint[edges];
        MyPoint[] innerPoints = new MyPoint[edges];

        for(int i=0; i<edges; i++){
            double theta = 2 * Math.PI / edges * i;
            double xPos = -Math.sin(theta) * size/2;
            double yPos = Math.cos(theta) * size/2;
            double zPos = size/2;
            outerPoints[i] = new MyPoint(centerX+xPos, centerY+yPos, centerZ+zPos*inFactor);
            innerPoints[i] = new MyPoint(centerX+xPos*inFactor, centerY+yPos*inFactor, centerZ+zPos);
        }

        MyPolygon polygons[] = new MyPolygon[2*edges+1];
        for(int i=0;i<edges;i++){
            polygons[i] = new MyPolygon(outerPoints[i], bottom, outerPoints[(i+1)%edges]);
        }
        for(int i=0;i<edges;i++){
            polygons[i+edges] = new MyPolygon(outerPoints[i], outerPoints[(i+1)%edges], innerPoints[(i+1)%edges], innerPoints[i]);
        }
        polygons[edges*2] = new MyPolygon(innerPoints);

        Tetrahedron tetra = new Tetrahedron(color, false, polygons);
        tetras.add(tetra);

        return new Entity(tetras);
    }

    public static IEntity createRing(Color color, double size, double centerX, double centerY, double centerZ){
        List<Tetrahedron> tetras = new ArrayList<Tetrahedron>();

        int edges = 20;
        double inFactor = 0.8; //ratio between top shape and outer ring
        double thick = 10;
        MyPoint[] outerPoints = new MyPoint[edges];
        MyPoint[] innerPoints = new MyPoint[edges];
        MyPoint[] outerPoints2 = new MyPoint[edges];
        MyPoint[] innerPoints2 = new MyPoint[edges];

        for(int i=0; i<edges; i++){
            double theta = 2 * Math.PI / edges * i;
            double yPos = Math.sin(theta) * size/2;
            double zPos = Math.cos(theta) * size/2;
            double xPos = 10;
            outerPoints[i] = new MyPoint(centerX+xPos, centerY+yPos, centerZ+zPos);
            innerPoints[i] = new MyPoint(centerX+xPos, centerY+yPos*inFactor, centerZ+zPos*inFactor);
            outerPoints2[i] = new MyPoint(centerX-xPos, centerY+yPos, centerZ+zPos);
            innerPoints2[i] = new MyPoint(centerX-xPos, centerY+yPos*inFactor, centerZ+zPos*inFactor);
        }

        MyPolygon polygons[] = new MyPolygon[4*edges];
        for(int i=0;i<edges;i++){
            polygons[i] = new MyPolygon(outerPoints[i], outerPoints[(i+1)%edges], innerPoints[(i+1)%edges], innerPoints[i]);
        }
        for(int i=0;i<edges;i++){
            polygons[i+edges] = new MyPolygon(outerPoints[i], outerPoints[(i+1)%edges], outerPoints2[(i+1)%edges], outerPoints2[i]);
        }
        for(int i=0;i<edges;i++){
            polygons[i+(2*edges)] = new MyPolygon(outerPoints2[i], outerPoints2[(i+1)%edges], innerPoints2[(i+1)%edges], innerPoints2[i]);
        }
        for(int i=0;i<edges;i++){
            polygons[i+(3*edges)] = new MyPolygon(innerPoints2[i], innerPoints2[(i+1)%edges], innerPoints[(i+1)%edges], innerPoints[i]);
        }

        Tetrahedron tetra = new Tetrahedron(color, false, polygons);
        tetras.add(tetra);

        return new Entity(tetras);
    }
}
