package cabrera.alvaro.gamepad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {

    private int port, red, blue, green;
    private String ip, name, redString, greenString, blueString;
    private EditText editTextIp, editTextPort,editTextName;
    private TextView redTextView, greenTextView, blueTextView;
    private RelativeLayout canvasLayout;
    private ShipView shipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_layout);

        this.red = 128;
        this.green = 128;
        this.blue = 128;

        SharedPreferences prefs = getSharedPreferences("JoystickPreferences", Context.MODE_PRIVATE);
        this.ip = prefs.getString("ip", "IP");
        this.port = prefs.getInt("port", 0);
        this.redString = prefs.getString("red", "128");
        this.greenString = prefs.getString("green", "128");
        this.blueString = prefs.getString("blue", "128");
        this.name = prefs.getString("name", "Nombre");

        try {
            this.red = Integer.parseInt(this.redString);
        } catch (NumberFormatException error) {
            this.red = 128;
            this.redString = "128";
        }

        try {
            this.green = Integer.parseInt(this.greenString);
        } catch (NumberFormatException error) {
            this.green = 128;
            this.greenString = "128";
        }

        try {
            this.blue = Integer.parseInt(this.blueString);
        } catch (NumberFormatException error) {
            this.blue = 128;
            this.blueString = "128";
        }

        this.editTextIp = (EditText) findViewById(R.id.editTextIp);
        this.editTextPort = (EditText) findViewById(R.id.editTextPort);
        this.editTextName = (EditText) findViewById(R.id.editTextName);

        editTextIp.setText(this.ip);
        if (port == 0) { editTextPort.setText("Puerto");} else { editTextPort.setText(Integer.toString(this.port)); }
        editTextName.setText(this.name);

        this.redTextView = (TextView) findViewById(R.id.colorRed);
        this.greenTextView = (TextView) findViewById(R.id.colorGreen);
        this.blueTextView = (TextView) findViewById(R.id.colorBlue);

        redTextView.setText("Red : " + this.redString);
        greenTextView.setText("Green : " + this.greenString);
        blueTextView.setText("Blue : " + this.blueString);

        canvasLayout = (RelativeLayout) findViewById(R.id.canvasLayout);
        this.drawShip();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void connect(View v){

        try {

            ip = this.editTextIp.getText().toString();
            port = Integer.parseInt(this.editTextPort.getText().toString());
            name = this.editTextName.getText().toString();

            SharedPreferences prefs = getSharedPreferences("JoystickPreferences", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("ip", ip);
            editor.putInt("port", port);
            editor.putString("red", redString);
            editor.putString("green", greenString);
            editor.putString("blue", blueString);
            editor.putString("name", name);
            editor.commit();

            Intent intent = new Intent(this, JoyStickActivity.class);
            intent.putExtra("ip", ip);
            intent.putExtra("port", port);
            intent.putExtra("red", redString);
            intent.putExtra("green", greenString);
            intent.putExtra("blue", blueString);
            intent.putExtra("name", name);
            startActivity(intent);

        } catch (Exception e) {

            System.out.println(e);

        }
    }

    public void redPlus1(View v) {
        this.red += 1;
        if (red > 255) {red = 255;}
        this.redString = this.integerToWeftString(this.red);
        redTextView.setText("Red : " + this.redString);
        this.drawShip();
    }

    public void redPlus10(View v) {
        this.red += 10;
        if (red > 255) {red = 255;}
        this.redString = this.integerToWeftString(this.red);
        redTextView.setText("Red: " + this.redString);
        this.drawShip();
    }

    public void greenPlus1(View v) {
        this.green += 1;
        if (green > 255) {green = 255;}
        this.greenString = this.integerToWeftString(this.green);
        greenTextView.setText("Green : " + this.greenString);
        this.drawShip();
    }

    public void greenPlus10(View v) {
        this.green += 10;
        if (green > 255) {green = 255;}
        this.greenString = this.integerToWeftString(this.green);
        greenTextView.setText("Green : " + this.greenString);
        this.drawShip();
    }

    public void bluePlus1(View v) {
        this.blue += 1;
        if (blue > 255) {blue = 255;}
        this.blueString = this.integerToWeftString(this.blue);
        blueTextView.setText("Blue : " + this.blueString);
        this.drawShip();
    }

    public void bluePlus10(View v) {
        this.blue += 10;
        if (blue > 255) {blue = 255;}
        this.blueString = this.integerToWeftString(this.blue);
        blueTextView.setText("Blue : " + this.blueString);
        this.drawShip();
    }

    public void redMinus1(View v) {
        this.red -= 1;
        if (red < 0) {red = 0;}
        this.redString = this.integerToWeftString(this.red);
        redTextView.setText("Red : " + this.redString);
        this.drawShip();
    }

    public void redMinus10(View v) {
        this.red -= 10;
        if (red < 0) {red = 0;}
        this.redString = this.integerToWeftString(this.red);
        redTextView.setText("Red : " + this.redString);
        this.drawShip();
    }

    public void greenMinus1(View v) {
        this.green -= 1;
        if (green < 0) {green = 0;}
        this.greenString = this.integerToWeftString(this.green);
        greenTextView.setText("Green : " + this.greenString);
        this.drawShip();
    }

    public void greenMinus10(View v) {
        this.green -= 10;
        if (green < 0) {green = 0;}
        this.greenString = this.integerToWeftString(this.green);
        greenTextView.setText("Green : " + this.greenString);
        this.drawShip();
    }

    public void blueMinus1(View v) {
        this.blue -= 1;
        if (blue < 0) {blue = 0;}
        this.blueString = this.integerToWeftString(this.blue);
        blueTextView.setText("Blue : " + this.blueString);
        this.drawShip();
    }

    public void blueMinus10(View v) {
        this.blue -= 10;
        if (blue < 0) {blue = 0;}
        this.blueString = this.integerToWeftString(this.blue);
        blueTextView.setText("Blue : " + this.blueString);
        this.drawShip();
    }

    public String integerToWeftString(int integer) {
        String result = "";
        if (integer < 10) {
            result = "00" + Integer.toString(integer);
        } else if (integer > 10 && integer < 100) {
            result = "0" + Integer.toString(integer);
        } else {
            result = Integer.toString(integer);
        }
        return result;
    }

    private void drawShip() {

        this.shipView = new ShipView(this, this.red, this.green, this.blue);
        canvasLayout.addView(shipView);

    }

}
