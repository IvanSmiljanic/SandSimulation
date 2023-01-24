public class Simulation
{
    Particle[] cells;
    int size;
    int width;
    int height;

    public Simulation()
    {
        size = calculateSize();
        width = calculateWidth();
        height = calculateHeight();
        cells = new Particle[size];
    }

    private int calculateSize()
    {
        return (Config.SIZE[0] * Config.SIZE[1]) / Config.CELL_SIZE;
    }

    private int calculateWidth()
    {
        return Config.SIZE[0] / Config.CELL_SIZE;
    }

    private int calculateHeight()
    {
        return Config.SIZE[1] / Config.CELL_SIZE;
    }

    public void simStep()
    {
        Particle[] newArray = new Particle[size];

        for (int i = 0; i < size; i++) {
            ParticleType id = cells[i].getId();
            switch (id) {
                case EMPTY:
                    break;

                case SAND:
                    newArray = updateSand(newArray, i);

                case WATER:
                    updateWater(newArray, i);
            }
        }
    }

    private Particle[] updateSand(Particle[] array, int index)
    {
        if (isValidIndex(bottomNeighbourIndex(index)))
        {
            if (isEmpty(bottomNeighbourIndex(index)))
            {

            }
        }
        return array;
    }

    private Particle[] updateWater(Particle[] array, int index)
    {
        return array;
    }

    boolean isValidIndex(int index)
    {
        if (0 <= index && index < size)
        {
            return true;
        }
        return false;
    }

    boolean isEmpty(int index)
    {
        return (cells[index].getId() == ParticleType.EMPTY);
    }

    boolean isSand(int index)
    {
        return (cells[index].getId() == ParticleType.SAND);
    }

    boolean isWater(int index)
    {
        return (cells[index].getId() == ParticleType.WATER);
    }

    boolean inSameRow(int index1, int index2)
    {
        return (index1 / width == index2 / width);
    }

    boolean inSameCol(int index1, int index2)
    {
        return (index1 % width == index2 % width);
    }

    int bottomNeighbourIndex(int index)
    {
        return index + width;
    }

    int topNeighbourIndex(int index)
    {
        return index - width;
    }

    int leftNeighbourIndex(int index)
    {
        return index - 1;
    }

    int rightNeighbourIndex(int index)
    {
        return index + 1;
    }

    int topLeftNeighbourIndex(int index)
    {
        return index - width - 1;
    }

    int topRightNeighbourIndex(int index)
    {
        return index - width + 1;
    }

    int bottomLeftNeighbourIndex(int index)
    {
        return index + width - 1;
    }

    int bottomRightNeighbourIndex(int index)
    {
        return index + width + 1;
    }
}
