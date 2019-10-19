package au.edu.curtin.mad_assignment;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the list of possible structures. This has a static get() method for retrieving an
 * instance, rather than calling the constructor directly.
 *
 * The remaining methods -- get(int), size(), add(Structure) and remove(int) -- provide
 * minimalistic list functionality.
 *
 * There is a static int array called DRAWABLES, which stores all the drawable integer references,
 * some of which are not actually used (yet) in a Structure object.
 * REFERENCE: MAD prac3 resource. slightly modified
 */
public class StructureData
{
    private List<Structure> structureList;

    private static StructureData instance = null;
    private Residential[] residential;
    private Commercial[] commercial;
    private Road[] roads;

    public static StructureData get()
    {
        if(instance == null)
        {
            instance = new StructureData();
        }
        return instance;
    }

    protected StructureData()
    {
        this.structureList = new ArrayList<>();

        residential = new Residential[] {new Residential(R.drawable.ic_building1),
                                         new Residential(R.drawable.ic_building2),
                                         new Residential(R.drawable.ic_building3),
                                         new Residential(R.drawable.ic_building4)};

        commercial = new Commercial[] {new Commercial(R.drawable.ic_building5),
                                        new Commercial(R.drawable.ic_building6),
                                        new Commercial(R.drawable.ic_building7),
                                        new Commercial(R.drawable.ic_building8)};

        roads = new Road[] {new Road(R.drawable.ic_road_ns), new Road(R.drawable.ic_road_ew), new Road(R.drawable.ic_road_nsew),
                            new Road(R.drawable.ic_road_ne), new Road(R.drawable.ic_road_nw), new Road(R.drawable.ic_road_se), new Road(R.drawable.ic_road_sw),
                            new Road(R.drawable.ic_road_n), new Road(R.drawable.ic_road_e), new Road(R.drawable.ic_road_s), new Road(R.drawable.ic_road_w),
                            new Road(R.drawable.ic_road_nse), new Road(R.drawable.ic_road_nsw), new Road(R.drawable.ic_road_new), new Road(R.drawable.ic_road_sew)};

        structureList.add(null);//this adds none to list, to allow user to not build when clicking on screen if they want
        arrayToList(residential);
        arrayToList(commercial);
        arrayToList(roads);
    }

    public Structure get(int i)
    {
        return structureList.get(i);
    }

    public int size()
    {
        return structureList.size();
    }

    public void add(Structure s)
    {
        structureList.add(0, s);
    }

    public void remove(int i)
    {
        structureList.remove(i);
    }

    public void arrayToList(Structure[] structs)
    {
        for(int i = 0; i < structs.length; i++)
        {
            structureList.add(structs[i]);
        }
    }
}
