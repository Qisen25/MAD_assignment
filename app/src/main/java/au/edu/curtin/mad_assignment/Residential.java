package au.edu.curtin.mad_assignment;

class Residential extends Structure
{

    public Residential(int imageId)
    {
        super(imageId);
    }

    @Override
    public String getType()
    {
        return "Residential";
    }

    @Override
    public int getCost()
    {
        return GameData.get().getSettings().getHouseBuildingCost();
    }
}
