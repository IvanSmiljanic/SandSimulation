import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.security.KeyStore;
import java.util.Random;

public class Main
{
    static int mouseX;
    static int mouseY;
    static boolean mouseDown = false;
    static double dt;
    static int fps;
    static long frameCounter;
    static ParticleType selectedParticle;
    static ParticleType[] selectableParticles;

    public static void main(String[] args)
    {
        JFrame win = new JFrame(Config.TITLE);
        SandPanel panel = new SandPanel();
        Simulation sim = new Simulation();
        boolean running = true;
        double prevTime = (double) System.currentTimeMillis() / 1000;
        selectedParticle = ParticleType.WATER;
        selectableParticles = new ParticleType[]{ParticleType.SAND, ParticleType.WATER};
        frameCounter = 0;
        Random rand = new Random();

        panel.sim = sim;
        MouseAdapter adapter = new MouseDetection();
        panel.addMouseListener(adapter);
        panel.addMouseMotionListener(adapter);
        panel.addMouseWheelListener(adapter);

        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setResizable(false);

        win.add(panel);
        win.pack();

        win.setVisible(true);

        while (running)
        {
            double currentTime = (double) System.currentTimeMillis() / 1000;
            dt = currentTime - prevTime;
            frameCounter += 1;
            if (dt >= (1d/Config.FPS))
            {
                prevTime = currentTime;
                fps = (int) (1d/dt);

                if (mouseDown == true)
                {
                    if (mouseInSelectionBox())
                    {
                        int clickedOption = getOptionClicked();
                        if (clickedOption != -1)
                        {
                            selectedParticle = selectableParticles[clickedOption];
                        }
                    }
                    else
                    {
                        if (sim.getCell(sim.toLinearIndex(mouseX / Config.CELL_SIZE, mouseY / Config.CELL_SIZE)) instanceof Particle.EmptyParticle)
                        {
                            int xOffset;
                            int yOffset;
                            for (int particleNum = 0; particleNum < Config.partsPerClick; particleNum++)
                            {
                                xOffset = (int) ((rand.nextInt(Config.cursorRadius * 2 + 1) - Config.cursorRadius) / 4);
                                yOffset = (int) ((rand.nextInt(Config.cursorRadius * 2 + 1) - Config.cursorRadius) / 4);
                                if (selectedParticle == ParticleType.SAND)
                                {
                                    sim.setCell(sim.toLinearIndex(mouseX / Config.CELL_SIZE + xOffset, mouseY / Config.CELL_SIZE) + yOffset, new Particle.SandParticle());
                                }
                                else if (selectedParticle == ParticleType.WATER)
                                {
                                    sim.setCell(sim.toLinearIndex(mouseX / Config.CELL_SIZE + xOffset, mouseY / Config.CELL_SIZE) + yOffset, new Particle.WaterParticle());
                                }
                            }
                        }
                    }
                }
                sim.simStep();
                panel.repaint();
            }
        }
    }

    public static boolean mouseInSelectionBox()
    {
        return (Config.selectionBoxCentreX - (Config.selectionBoxWidth / 2) <= mouseX
                && mouseX <= Config.selectionBoxCentreX + (Config.selectionBoxWidth / 2)
                && Config.selectionBoxCentreY - (Config.selectionBoxHeight / 2) <= mouseY
                && mouseY <= Config.selectionBoxCentreY + (Config.selectionBoxHeight / 2));
    }

    public static int getOptionClicked()
    {
        int optionCentreX;
        int optionCentreY = Config.selectionBoxCentreY;
        for (int option = 0; option < selectableParticles.length; option++)
        {
            optionCentreX = Config.selectionBoxCentreX - (Config.selectionBoxWidth / 2) + (Config.optionBoxWidth / 2)
                    + Config.optionBorderWidth * 2 + (option + 1) * Config.optionSpacing + option * Config.optionBoxWidth;

            if (optionCentreX - (Config.optionBoxWidth / 2) - Config.optionBorderWidth <= mouseX
                && mouseX <= optionCentreX + (Config.optionBoxWidth / 2) + Config.optionBorderWidth
                && optionCentreY - (Config.optionBoxHeight / 2) - Config.optionBorderWidth <= mouseY
                && mouseY <= optionCentreY + (Config.optionBoxHeight / 2) + Config.optionBorderWidth)
            {
                return option;
            }
        }
        return -1;
    }
}