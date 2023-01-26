import java.awt.*;

public class Particle
{
    protected ParticleType id;
    protected float lifeTime = 0;
    protected float[] velocity = new float[]{0, 0};
    protected Color color;
    protected boolean updated = false;

    public static Color getColorFromId(ParticleType id)
    {
        switch (id)
        {
            case EMPTY:
                return new Color(51, 51, 51);

            case SAND:
                return new Color(150, 145, 70);

            case WATER:
                return Color.blue;
        }
        return null;
    }

    public void setId(ParticleType id) {this.id = id;}

    public void setLifeTime(float lifeTime) {this.lifeTime = lifeTime;}

    public void setVelocity(float[] velocity) {this.velocity = velocity;}

    public void setColor(Color color) {this.color = color;}

    public void setUpdated(boolean updated) {this.updated = updated;}

    public ParticleType getId() {return id;}

    public float getLifeTime() {return lifeTime;}

    public float[] getVelocity() {return velocity;}

    public Color getColor() {return color;}

    public boolean getUpdated() {return updated;}

    public static class EmptyParticle extends Particle
    {
        EmptyParticle()
        {
            id = ParticleType.EMPTY;
            color = getColorFromId(id);
        }
    }

    public static class SandParticle extends Particle
    {
        SandParticle()
        {
            id = ParticleType.SAND;
            color = getColorFromId(id);
        }
    }

    public static class WaterParticle extends Particle
    {
        WaterParticle()
        {
            id=ParticleType.WATER;
            color = getColorFromId(id);
        }
    }
}
