package au.edu.curtin.mad_assignment;

class Commercial extends Structure
{
    public Commercial(int imageId)
    {
        super(imageId);
    }

    @Override
    public String getType()
    {
        return "Commercial";
    }

    @Override
    public int getCost()
    {
        return GameData.get().getSettings().getCommBuildingCost();
    }
}
