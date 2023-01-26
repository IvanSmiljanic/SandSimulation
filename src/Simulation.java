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
            cells[i].setUpdated(false);
        }

        int index;

        if (Main.frameCounter % 2 == 0)
        {
            for (int i = height - 1; i >= 0; i--)
            {
                for (int j = 0; j < width; j++)
                {
                    index = toLinearIndex(j, i);

                    if (!cells[index].getUpdated())
                    {
                        cells[index].setUpdated(true);
                        ParticleType id = cells[index].getId();

                        switch (id) {
                            case EMPTY:
                                break;

                            case SAND:
                                updateSand(index);
                                break;

                            case WATER:
                                updateWater(index);
                                break;

                        }
                    }
                }
            }
        }
        else
        {
            for (int i = height - 1; i >= 0; i--)
            {
                for (int j = width - 1; j >= 0; j--)
                {
                    index = toLinearIndex(j, i);
                    cells[index].setUpdated(true);
                    ParticleType id = cells[index].getId();

                    switch (id)
                    {
                        case EMPTY:
                            break;

                        case SAND:
                            updateSand(index);
                            break;

                        case WATER:
                            updateWater(index);
                            break;

                    }
                }
            }
        }

        System.arraycopy(tempCells, 0, cells, 0, cells.length);
    }

    private void updateSand(int index)
    {
        if (isValidIndex(bottomNeighbourIndex(index)) && isEmpty(bottomNeighbourIndex(index)) && isTempEmpty(index))
        {
            tempCells[bottomNeighbourIndex(index)] = cells[index];
        }
        else if (isValidIndex(bottomNeighbourIndex(index)) && isEmpty(bottomLeftNeighbourIndex(index)) && isTempEmpty(bottomLeftNeighbourIndex(index)) && inSameRow(bottomNeighbourIndex(index), bottomLeftNeighbourIndex(index)))
        {
            tempCells[bottomLeftNeighbourIndex(index)] = cells[index];
        }
        else if (isValidIndex(bottomRightNeighbourIndex(index)) && isEmpty(bottomRightNeighbourIndex(index)) && isTempEmpty(bottomRightNeighbourIndex(index)) && inSameRow(bottomNeighbourIndex(index), bottomRightNeighbourIndex(index)))
        {
            tempCells[bottomRightNeighbourIndex(index)] = cells[index];
        }
        else
        {
            tempCells[index] = cells[index];
        }
    }

    private void updateWater(int index)
    {
        if (isValidIndex(bottomNeighbourIndex(index)) && isTempEmpty(bottomNeighbourIndex(index))
                && (isEmpty(bottomNeighbourIndex(index))
                || (!isEmpty(bottomNeighbourIndex(index)) && isUpdated(bottomNeighbourIndex(index)))))
        {
            tempCells[bottomNeighbourIndex(index)] = cells[index];
        }
        else if (isValidIndex(bottomNeighbourIndex(index)) && isTempEmpty(bottomLeftNeighbourIndex(index)) && inSameRow(bottomNeighbourIndex(index), bottomLeftNeighbourIndex(index))
                && (isEmpty(bottomLeftNeighbourIndex(index))
                || (!isEmpty(bottomLeftNeighbourIndex(index)) && isUpdated(bottomLeftNeighbourIndex(index)))))
        {
            tempCells[bottomLeftNeighbourIndex(index)] = cells[index];
        }
        else if (isValidIndex(bottomRightNeighbourIndex(index)) && isTempEmpty(bottomRightNeighbourIndex(index)) && inSameRow(bottomNeighbourIndex(index), bottomRightNeighbourIndex(index))
                && (isEmpty(bottomRightNeighbourIndex(index))
                || (!isEmpty(bottomLeftNeighbourIndex(index)) && isUpdated(bottomLeftNeighbourIndex(index)))))
        {
            tempCells[bottomRightNeighbourIndex(index)] = cells[index];
        }
        else if (isValidIndex(leftNeighbourIndex(index)) && isTempEmpty(leftNeighbourIndex(index)) && inSameRow(index, leftNeighbourIndex(index)) && isEmpty(leftNeighbourIndex(index)))
        {
            tempCells[leftNeighbourIndex(index)] = cells[index];
        }
        else if (isValidIndex(rightNeighbourIndex(index)) && isTempEmpty(rightNeighbourIndex(index)) && inSameRow(index, rightNeighbourIndex(index)) && isEmpty(rightNeighbourIndex(index)))
        {
            tempCells[rightNeighbourIndex(index)] = cells[index];
        }
        else
        {
            tempCells[index] = cells[index];
        }
    }

    boolean isValidIndex(int index)
    {
        if (0 <= index && index < size)
        {
            return true;
        }
        return false;
    }

    boolean isUpdated(int index)
    {
        if (cells[index].getUpdated() == true)
        {
            return true;
        }
        return false;
    }

    boolean isEmpty(int index)
    {
        return (cells[index].getId() == ParticleType.EMPTY);
    }

    boolean isTempEmpty(int index)
    {
        return (tempCells[index].getId() == ParticleType.EMPTY);
    }

    boolean isSand(int index)
    {
        return (cells[index].getId() == ParticleType.SAND);
    }

    boolean isTempSand(int index)
    {
        return (tempCells[index].getId() == ParticleType.SAND);
    }

    boolean isWater(int index)
    {
        return (cells[index].getId() == ParticleType.WATER);
    }

    boolean isTempWater(int index)
    {
        return (tempCells[index].getId() == ParticleType.WATER);
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
