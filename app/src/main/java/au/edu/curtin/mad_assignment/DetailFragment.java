package au.edu.curtin.mad_assignment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
/*
    fragment that handles map elements details screen
 */
public class DetailFragment extends Fragment
{
    private MapElement currElement;
    private TextView row, col,structTitle;
    private EditText ownName;
    private ImageView image;
    private TextWatcher tw;

    private static final int REQUEST_THUMBNAIL = 1;
    private Intent photoIntent;

    public DetailFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        this.setupView(view);

        return view;
    }

    public MapElement getCurrElement()
    {
        return currElement;
    }

    public void setCurrElement(MapElement currElement)
    {
        this.currElement = currElement;
    }

    //handle all view setting
    private void setupView(View view)
    {
        this.row = (TextView) view.findViewById(R.id.detailRow);
        this.col = (TextView) view.findViewById(R.id.detailCol);
        this.structTitle = (TextView) view.findViewById(R.id.structTitle);
        this.ownName = (EditText) view.findViewById(R.id.name);
        this.image = (ImageView) view.findViewById(R.id.thumbnail);

        tw = new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                currElement.setOwnerName(ownName.getText().toString());
                GameData.get().updateMapElement(currElement);
            }
        };


        this.row.setText("row: " + currElement.getRow());
        this.col.setText("col: " + currElement.getCol());

        this.structTitle.setText(currElement.getStructure().getType());

        ownName.addTextChangedListener(tw);//listen to changes in edit tex
        if(currElement.getOwnerName() != null)
        {
            ownName.setText(currElement.getOwnerName());
        }
        else//use structure type as default name
        {
            ownName.setText(currElement.getStructure().getType());
        }

        //display if img available
        if(currElement.getImage() != null)
        {
            image.setImageBitmap(currElement.getImage());
        }

        //set photo
        image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photoIntent, REQUEST_THUMBNAIL);
            }
        });

    }

    //when camera activity is finished, retrieve photo and set the image view
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultIntent)
    {
        if(resultCode == MainActivity.RESULT_OK && requestCode == REQUEST_THUMBNAIL)
        {
            Bitmap photo = (Bitmap) resultIntent.getExtras().get("data");

            image.setImageBitmap(photo);
            this.currElement.setImage(photo);
            GameData.get().updateMapElement(this.currElement);//add img to database
        }
    }
}
