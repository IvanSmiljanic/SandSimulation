import javax.swing.*;
import java.awt.*;

public class SandPanel extends JPanel
{
    Simulation sim;

    public SandPanel()
    {

    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(Config.SIZE[0], Config.SIZE[1]);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        int x;
        int y;

        for (int cell = 0; cell < sim.getCells().length; cell++)
        {
            g.setColor(sim.getCells()[cell].getColor());
            x = (cell % sim.width) * Config.CELL_SIZE;
            y = (cell / sim.width) * Config.CELL_SIZE;
            g.fillRect(x, y, x + Config.CELL_SIZE, y + Config.CELL_SIZE);
        }
        g.setColor(Color.green);
        g.setFont(new Font("CourierNew", Font.PLAIN, 30));
        g.drawString(""+ Main.fps, 1830, 60);
    }
}
