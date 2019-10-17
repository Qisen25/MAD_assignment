package au.edu.curtin.mad_assignment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity
{
    private GameData gd;
    private EditText height, width, money;
    private TextView viewMoney, viewHeight, viewWidth;
    private Button save;
    private int hT, wD, mN; //temp variables for height, width , money

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        gd = GameData.get();
//        gd.load(getApplicationContext());//do not load again or money will be set to 1000

        Settings settings = gd.getSettings();

        wD = settings.getMapWidth();
        hT = settings.getMapHeight();
        mN = settings.getInitialMoney();

        save = (Button) findViewById(R.id.saveButton);

        width = (EditText) findViewById(R.id.widthInput);
        height = (EditText) findViewById(R.id.heightInput);
        money = (EditText) findViewById(R.id.moneyInput);

        viewHeight = (TextView) findViewById(R.id.mapIdH);
        viewWidth = (TextView) findViewById(R.id.mapIdW);
        viewMoney = (TextView) findViewById(R.id.money);

        viewCurrentSettings(gd.getSettings());


        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!height.getText().toString().isEmpty())
                {
                    hT = Integer.parseInt(height.getText().toString());
                }

                if(!width.getText().toString().isEmpty())
                {
                    wD = Integer.parseInt(width.getText().toString());
                }

                if(!money.getText().toString().isEmpty())
                {
                    mN = Integer.parseInt(money.getText().toString());
                }

                gd.editSetting(new Settings(wD, hT, mN));

                viewCurrentSettings(gd.getSettings());
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
        super.onBackPressed();
    }

    private void viewCurrentSettings(Settings settings)
    {
        viewHeight.setText("Map Height: " + settings.getMapHeight());
        viewWidth.setText("Map Width: " + settings.getMapWidth());
        viewMoney.setText("Start Money: " + settings.getInitialMoney());
    }
}
