import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseDetection extends MouseAdapter
{
    public void mousePressed(MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            Main.mouseDown = true;
            Main.mouseX = e.getX();
            Main.mouseY = e.getY();
        }
    }

    public void mouseDragged(MouseEvent e)
    {
        System.out.println("DRAGGED");
        Main.mouseX = e.getX();
        Main.mouseY = e.getY();
    }

    public void mouseWheelMoved(MouseEvent e)
    {
        System.out.println("GOD DAMN IT");
    }

    public void mouseReleased(MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            Main.mouseDown = false;
        }
    }
}
