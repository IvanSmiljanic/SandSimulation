public class Simulation
{
    Particle[] cells;
    Particle[] tempCells;
    int size;
    int width;
    int height;

    public Simulation()
    {
        size = calculateSize();
        width = calculateWidth();
        height = calculateHeight();
        initCells();
    }

    private void initCells()
    {
        cells = new Particle[size];
        for (int i = 0; i < size; i++)
        {
            cells[i] = new Particle.EmptyParticle();
        }
    }

    public Particle[] getCells()
    {
        return cells;
    }

    public Particle getCell(int index)
    {
        return cells[index];
    }

    public void setCell(int index, Particle p)
    {
        cells[index] = p;
    }

    public int toLinearIndex(int x, int y)
    {
        return (x + width * y);
    }

    private int calculateSize()
    {
        return (Config.SIZE[0] * Config.SIZE[1]) / (Config.CELL_SIZE * Config.CELL_SIZE);
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
        tempCells = new Particle[size];

        for (int i = 0; i < size; i++)
        {
            tempCells[i] = new Particle.EmptyParticle();
        }

        for (int i = 0; i < size; i++)
        {
            ParticleType id = cells[i].getId();

            switch (id) {
                case EMPTY:
                    break;

                case SAND:
                    updateSand(i);
                    break;

                case WATER:
                    updateWater(i);
                    break;
            }
        }

        System.arraycopy(tempCells, 0, cells, 0, cells.length);
    }

    private void updateSand(int index)
    {
        if (isValidIndex(bottomNeighbourIndex(index)))
        {
            if (isEmpty(bottomNeighbourIndex(index)))
            {
                tempCells[bottomNeighbourIndex(index)] = cells[index];
            }
            else if (isSand(bottomNeighbourIndex(index)))
            {
                if (isEmpty(bottomLeftNeighbourIndex(index)) && inSameRow(bottomNeighbourIndex(index), bottomLeftNeighbourIndex(index)))
                {
                    tempCells[bottomLeftNeighbourIndex(index)] = cells[index];
                }
                else if (isValidIndex(bottomRightNeighbourIndex(index)))
                {
                    if (isEmpty(bottomRightNeighbourIndex(index)) && inSameRow(bottomNeighbourIndex(index), bottomRightNeighbourIndex(index)))
                    {
                        tempCells[bottomRightNeighbourIndex(index)] = cells[index];
                    }
                    else
                    {
                        tempCells[index] = cells[index];
                    }
                }
                else
                {
                    tempCells[index] = cells[index];
                }
            }
        }
        else
        {
            tempCells[index] = cells[index];
        }
    }

    private void updateWater(int index)
    {
        System.out.println("id: " + cells[index].getId());
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
