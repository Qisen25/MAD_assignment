package au.edu.curtin.mad_assignment.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import au.edu.curtin.mad_assignment.Model.Structure;
import au.edu.curtin.mad_assignment.Model.StructureData;
import au.edu.curtin.mad_assignment.R;

/*
    fragment that handles structure selection
    REFERENCE: From MAD prac 3, modified to suit assignment
 */
public class SelectorFragment extends Fragment
{
    private StructureData structures;
    private Structure current;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.structures = StructureData.get();
    }

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup ui, Bundle bundle)
    {
        View view = li.inflate(R.layout.fragment_selector, ui, false);
        RecyclerView recycView = (RecyclerView)view.findViewById(R.id.selectRecyclerView);
        recycView.setLayoutManager(new LinearLayoutManager(getActivity(),
                                            LinearLayoutManager.HORIZONTAL, false));

        StructureAdapter adapter = new StructureAdapter();

        recycView.setAdapter(adapter);

        return view;
    }

    private class StructureViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView structs;
        private TextView desc;
        private Structure bindData;


        public StructureViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.list_selection, parent, false));

            structs = (ImageView)itemView.findViewById(R.id.ui_elements);
            desc = (TextView)itemView.findViewById(R.id.ui_desc);

            structs.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    current = bindData;
                }
            });
        }


        public void bind(Structure struct)
        {
            bindData = struct;
            if(struct != null)
            {
                structs.setImageResource(struct.getImageId());
                desc.setText(struct.getType());
            }
            else
            {
                desc.setText("NO BUILD");//null structure in structure list represents no build option
            }
        }
    }

    private class StructureAdapter extends RecyclerView.Adapter<StructureViewHolder>
    {
        @Override
        public StructureViewHolder onCreateViewHolder(ViewGroup parent, int i)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());

            return new StructureViewHolder(li, parent);
        }

        @Override
        public void onBindViewHolder(StructureViewHolder vh, int i)
        {
            vh.bind(structures.get(i));
        }

        @Override
        public int getItemCount()
        {
            return structures.size();
        }
    }

    public Structure getSelected()
    {
        return this.current;
    }
}
