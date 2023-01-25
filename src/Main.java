import javax.swing.*;
import java.awt.*;
import java.security.KeyStore;

public class Main
{
    static int mouseX;
    static int mouseY;
    static boolean mouseDown = false;
    static double dt;
    static int fps;

    public static void main(String[] args)
    {
        JFrame win = new JFrame(Config.TITLE);
        SandPanel panel = new SandPanel();
        Simulation sim = new Simulation();
        boolean running = true;
        double prevTime = (double) System.currentTimeMillis() / 1000;
        ParticleType selectedParticle = ParticleType.WATER;
        byte cycle = 0;

        panel.sim = sim;
        panel.addMouseListener(new MouseDetection());

        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setResizable(false);

        win.add(panel);
        win.pack();

        win.setVisible(true);

        while (running)
        {
            double currentTime = (double) System.currentTimeMillis() / 1000;
            dt = currentTime - prevTime;
            if (dt >= (1d/Config.FPS))
            {
                prevTime = currentTime;
                fps = (int) (1d/dt);
                cycle = (byte) ((cycle + 1) % 10);

                if (mouseDown == true && cycle == 0)
                {
                    if (sim.getCell(sim.toLinearIndex(mouseX / Config.CELL_SIZE, mouseY / Config.CELL_SIZE)) instanceof Particle.EmptyParticle)
                    {
                        if (selectedParticle == ParticleType.SAND)
                        {
                            sim.setCell(sim.toLinearIndex(mouseX / Config.CELL_SIZE, mouseY / Config.CELL_SIZE), new Particle.SandParticle());
                            sim.setCell(sim.toLinearIndex(mouseX / Config.CELL_SIZE - 1, mouseY / Config.CELL_SIZE), new Particle.SandParticle());
                            sim.setCell(sim.toLinearIndex(mouseX / Config.CELL_SIZE + 1, mouseY / Config.CELL_SIZE), new Particle.SandParticle());
                            sim.setCell(sim.toLinearIndex(mouseX / Config.CELL_SIZE, mouseY / Config.CELL_SIZE - 1), new Particle.SandParticle());
                            sim.setCell(sim.toLinearIndex(mouseX / Config.CELL_SIZE, mouseY / Config.CELL_SIZE + 1), new Particle.SandParticle());
                        }
                        else if (selectedParticle == ParticleType.WATER)
                        {
                            sim.setCell(sim.toLinearIndex(mouseX / Config.CELL_SIZE, mouseY / Config.CELL_SIZE), new Particle.WaterParticle());
                            sim.setCell(sim.toLinearIndex(mouseX / Config.CELL_SIZE - 1, mouseY / Config.CELL_SIZE), new Particle.WaterParticle());
                            sim.setCell(sim.toLinearIndex(mouseX / Config.CELL_SIZE + 1, mouseY / Config.CELL_SIZE), new Particle.WaterParticle());
                            sim.setCell(sim.toLinearIndex(mouseX / Config.CELL_SIZE, mouseY / Config.CELL_SIZE - 1), new Particle.WaterParticle());
                            sim.setCell(sim.toLinearIndex(mouseX / Config.CELL_SIZE, mouseY / Config.CELL_SIZE + 1), new Particle.WaterParticle());
                        }
                    }
                }
                sim.simStep();
                panel.repaint();
            }
        }
    }
}