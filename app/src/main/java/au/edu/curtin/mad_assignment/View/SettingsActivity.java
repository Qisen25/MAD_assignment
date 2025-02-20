package au.edu.curtin.mad_assignment.View;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import au.edu.curtin.mad_assignment.Database.GameData;
import au.edu.curtin.mad_assignment.Model.Settings;
import au.edu.curtin.mad_assignment.R;

public class SettingsActivity extends AppCompatActivity
{
    private GameData gd;
    private EditText height, width, money, tax;
    private TextView viewMoney, viewHeight, viewWidth, viewTax;
    private Button save;
    private int hT, wD, mN; //temp variables for height, width , money
    private double taxIn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        gd = GameData.get();

        Settings settings = gd.getSettings();

        //get current values
        wD = settings.getMapWidth();
        hT = settings.getMapHeight();
        mN = settings.getInitialMoney();
        taxIn = settings.getTaxRate();

        save = (Button) findViewById(R.id.saveButton);

        width = (EditText) findViewById(R.id.widthInput);
        height = (EditText) findViewById(R.id.heightInput);
        money = (EditText) findViewById(R.id.moneyInput);
        tax = (EditText) findViewById(R.id.taxInput);

        viewHeight = (TextView) findViewById(R.id.mapIdH);
        viewWidth = (TextView) findViewById(R.id.mapIdW);
        viewMoney = (TextView) findViewById(R.id.money);
        viewTax = (TextView) findViewById(R.id.tax);

        viewCurrentSettings(gd.getSettings());


        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //statements successful if valid text is found
                //other wise store current values again
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

                if(!tax.getText().toString().isEmpty())
                {
                    taxIn = Double.parseDouble(tax.getText().toString());
                }

                ///update settings
                gd.editSetting(new Settings(wD, hT, mN, taxIn));

                viewCurrentSettings(gd.getSettings());
            }
        });

    }

    //make sure activity is finished on back press
    @Override
    public void onBackPressed()
    {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
        super.onBackPressed();
    }

    //display results/stats to xml views
    private void viewCurrentSettings(Settings settings)
    {
        viewHeight.setText("Map Height: " + settings.getMapHeight());
        viewWidth.setText("Map Width: " + settings.getMapWidth());
        viewMoney.setText("Start Money: " + settings.getInitialMoney());
        viewTax.setText("Tax Rate: " + settings.getTaxRate());
    }
}
