package Render.input;

import java.awt.event.*;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {
    private int mouseX = -1; //x coord of mouse
    private int mouseY = -1; //y coord of mouse
    private int mouseB = -1; //mouse buttons input
    private int scroll = 0;

    public int getX(){
        return this.mouseX;
    }

    public int getY(){
        return this.mouseY;
    }

    public boolean isScrollingUp(){
        return this.scroll == -1;
    }

    public boolean isScrollingDown(){
        return this.scroll == 1;
    }

    public void resetScroll(){
        this.scroll = 0;
    }

    public ClickType getButton(){
        switch(this.mouseB){
            case 1:
                return ClickType.LeftClick;
            case 2:
                return ClickType.ScrollClick;
            case 3:
                return ClickType.RightCLick;
            case 4:
                return ClickType.BackPage;
            case 5:
                return ClickType.ForwardPage;
            default:
                return ClickType.Unknown;

        }
    }

    public void resetButton(){ //changes button clicked back to not clicked
        this.mouseB = -1;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.mouseB = e.getButton();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.mouseB = -1;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.mouseX = e.getX();
        this.mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.mouseX = e.getX();
        this.mouseY = e.getY();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll = e.getWheelRotation();
    }
}
