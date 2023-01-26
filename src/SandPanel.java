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

        // Draw Each Particle
        for (int cell = 0; cell < sim.getCells().length; cell++)
        {
            g.setColor(sim.getCells()[cell].getColor());
            x = (cell % sim.width) * Config.CELL_SIZE;
            y = (cell / sim.width) * Config.CELL_SIZE;
            g.fillRect(x, y, x + Config.CELL_SIZE, y + Config.CELL_SIZE);
        }

        // Draw Selection Box
        g.setColor(Config.selectionBoxColor);
        g.fillRect(Config.selectionBoxCentreX - (Config.selectionBoxWidth / 2), Config.selectionBoxCentreY - (Config.selectionBoxHeight / 2), Config.selectionBoxWidth, Config.selectionBoxHeight);

        //Draw Each Option
        int optionCentreX;
        int optionCentreY = Config.selectionBoxCentreY;
        for (int option = 0; option < Main.selectableParticles.length; option++)
        {
            optionCentreX = Config.selectionBoxCentreX - (Config.selectionBoxWidth / 2) + (Config.optionBoxWidth / 2)
                            + Config.optionBorderWidth * 2 + (option + 1) * Config.optionSpacing + option * Config.optionBoxWidth;

            if (Main.selectableParticles[option] == Main.selectedParticle)
            {
                g.setColor(Config.activeOptionColor);
            }
            else
            {
                g.setColor(Config.inactiveOptionColor);
            }
            g.fillRect(optionCentreX - (Config.optionBoxWidth / 2) - Config.optionBorderWidth, optionCentreY - (Config.optionBoxHeight / 2) - Config.optionBorderWidth, Config.optionBoxWidth + (Config.optionBorderWidth * 2), Config.optionBoxHeight + (Config.optionBorderWidth * 2));

            g.setColor(Particle.getColorFromId(Main.selectableParticles[option]));
            g.fillRect(optionCentreX - (Config.optionBoxWidth / 2), optionCentreY - (Config.optionBoxHeight / 2), Config.optionBoxWidth, Config.optionBoxHeight);
        }

        // Draw Oval Cursor
        if (Main.mouseDown && !Main.mouseInSelectionBox())
        {
            g.setColor(Color.white);
            g.drawOval(Main.mouseX - Config.cursorRadius, Main.mouseY - Config.cursorRadius, Config.cursorRadius * 2, Config.cursorRadius * 2);
        }

        // Draw FPS
        g.setColor(Color.green);
        g.setFont(new Font("CourierNew", Font.PLAIN, 30));
        g.drawString(""+ Main.fps, 1830, 60);
    }
}
