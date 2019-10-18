package au.edu.curtin.mad_assignment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MapFragment extends Fragment
{
    private GameData gameData;
    private MapAdapter adapter;
    private SelectorFragment selector;
    private Settings settings;

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        gameData = GameData.get();
        settings = gameData.getSettings();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle)
    {
        View view = inflater.inflate(R.layout.fragment_map, ui, false);
        RecyclerView recycView = (RecyclerView)view.findViewById(R.id.mapRecyclerView);
        recycView.setLayoutManager(new GridLayoutManager(getActivity(),
                                        settings.getMapHeight(),
                                        GridLayoutManager.HORIZONTAL,
                                false));
        adapter = new MapAdapter();
        recycView.setAdapter(adapter);

        return view;
    }

    //View holder
    private class MapViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView img1, img2, img3, img4, img5;
        private MapElement currentPlace;
        private MapActivity currActivity = (MapActivity) getActivity();

        public MapViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.grid_cell, parent, false));

            int size = parent.getMeasuredHeight() / settings.getMapHeight() + 1;
            ViewGroup.LayoutParams lp = itemView.getLayoutParams();
            lp.width = size;
            lp.height = size;

            //get image attribute from grid_cell
            img1 = (ImageView)itemView.findViewById(R.id.img1);
            img2 = (ImageView)itemView.findViewById(R.id.img2);
            img3 = (ImageView)itemView.findViewById(R.id.img3);
            img4 = (ImageView)itemView.findViewById(R.id.img4);
            img5 = (ImageView)itemView.findViewById(R.id.img5);

            //adding structures
            img5.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(currentPlace.isBuildable() && currentPlace.getStructure() == null && selector.getSelected() != null)
                    {
                        currentPlace.setStructure(selector.getSelected());
                        adapter.notifyItemChanged(getAdapterPosition());
                        gameData.addStructure(currentPlace);//put in database as soon structure binded
                        currActivity.updateHud();
                    }
                    else if(currentPlace.getStructure() != null)
                    {
                        DetailFragment df = new DetailFragment();
                        df.setCurrElement(currentPlace);
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.map, df);
                        ft.addToBackStack(null);
                        ft.hide(fm.findFragmentById(R.id.selector));

                        ft.commit();
                    }
                }
            });

            //remove when holding click
            img5.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View view)
                {
                    if(currentPlace.getStructure() != null)
                    {
                        currentPlace.setStructure(null);
                        currentPlace.setBuildable(true);
                        adapter.notifyItemChanged(getAdapterPosition());
                        gameData.removeStructure(currentPlace);
                        currActivity.updateHud();
                    }

                    return true;
                }
            });
        }

        public void bind(MapElement element)
        {
            currentPlace = element;
            img1.setImageResource(element.getNorthWest());
            img2.setImageResource(element.getNorthEast());
            img3.setImageResource(element.getSouthWest());
            img4.setImageResource(element.getSouthEast());
            if(element.getStructure() == null)
            {
                img5.setImageResource(0);
            }
            else
            {
                img5.setImageResource(element.getStructure().getImageId());
            }
        }
    }

    //adapter
    private class MapAdapter extends RecyclerView.Adapter<MapViewHolder>
    {
        @Override
        public MapViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new MapViewHolder(li, parent);
        }

        @Override
        public void onBindViewHolder(MapViewHolder vh, int index)
        {
            int row = index % settings.getMapHeight();
            int col =  index / settings.getMapHeight();
            vh.bind(gameData.get(row, col));
        }

        @Override
        public int getItemCount()
        {
            return settings.getMapHeight() * settings.getMapWidth();
        }
    }

    public void setSelector(SelectorFragment sel)
    {
        this.selector = sel;
    }

    public void resetMap()
    {
        gameData.reset();
        adapter.notifyItemRangeChanged(0, settings.getMapHeight() * settings.getMapWidth());
    }
}
