package au.edu.curtin.mad_assignment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MapActivity extends AppCompatActivity {

    private Button reset;
    private MapFragment mapFrag;
    private SelectorFragment selFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        FragmentManager fm = getSupportFragmentManager();
        mapFrag = (MapFragment)fm.findFragmentById(R.id.map);
        selFrag = (SelectorFragment) fm.findFragmentById(R.id.selector);

        if(selFrag == null)
        {
            selFrag = new SelectorFragment();
            fm.beginTransaction().add(R.id.selector, selFrag).commit();
        }

        if(mapFrag == null)
        {
            mapFrag = new MapFragment();
            mapFrag.setSelector(selFrag);
            fm.beginTransaction().add(R.id.map, mapFrag).commit();
        }

        reset = (Button)findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mapFrag.resetMap();
            }
        });

    }
}
