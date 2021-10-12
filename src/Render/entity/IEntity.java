package Render.entity;

import Render.point.MyVector;

import java.awt.*;

public interface IEntity {

    void render(Graphics g);

    void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees, MyVector lightVector);

    void setLighting(MyVector lightVector);
}
