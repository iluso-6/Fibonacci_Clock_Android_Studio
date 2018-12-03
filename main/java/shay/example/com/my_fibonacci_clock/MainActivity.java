package shay.example.com.my_fibonacci_clock;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    String PACKAGE;
    int CHANGE_UP_FREQUENCY = 8000;
    int[][] hours;
    int[][] mins;

    int[][] hours1 = {{1, 1, 1, 1, 1}, {1, 0, 0, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 0, 1, 0}, {1, 1, 1, 0, 0}, {0, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {0, 0, 1, 0, 1}, {0, 0, 0, 1, 1}, {1, 0, 0, 1, 1}, {0, 0, 1, 1, 1}, {1, 0, 1, 1, 1}};
    int[][] hours2 = {{1, 1, 1, 1, 1}, {0, 1, 0, 0, 0}, {1, 1, 0, 0, 0}, {1, 0, 1, 0, 0}, {0, 1, 0, 1, 0}, {0, 0, 1, 1, 0}, {1, 0, 1, 1, 0}, {1, 1, 1, 1, 0}, {1, 0, 1, 0, 1}, {1, 1, 1, 0, 1}, {1, 1, 0, 1, 1}, {0, 1, 1, 1, 1}};

    int[][] mins1 = {{0, 0, 0, 0, 0}, {1, 0, 0, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 0, 1, 0}, {1, 0, 0, 1, 0}, {0, 0, 0, 0, 1}, {1, 0, 1, 1, 0}, {0, 0, 1, 0, 1}, {0, 0, 0, 1, 1}, {1, 1, 1, 0, 1}, {0, 0, 1, 1, 1}, {1, 0, 1, 1, 1}};
    int[][] mins2 = {{0, 0, 0, 0, 0}, {0, 1, 0, 0, 0}, {1, 1, 0, 0, 0}, {1, 0, 1, 0, 0}, {1, 1, 1, 0, 0}, {0, 0, 1, 1, 0}, {0, 1, 0, 0, 1}, {1, 1, 1, 1, 0}, {1, 0, 1, 0, 1}, {1, 0, 0, 1, 1}, {1, 1, 0, 1, 1}, {0, 1, 1, 1, 1}};
    Animation animation;
    private Button panel[];
    private TextView redHr;
    private TextView greenMin;
    private TextView blueCommon;
    private TextView blueCommonMin;
    private CountDownTimer count_timer;
    private TextView hr_result;
    private TextView min_result, min_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        panel = new Button[5];
        setContentView(R.layout.activity_main);

        PACKAGE = this.getPackageName();

        panel[0] = findViewById(R.id.panel_0);
        panel[1] = findViewById(R.id.panel_1);
        panel[2] = findViewById(R.id.panel_2);
        panel[3] = findViewById(R.id.panel_3);
        panel[4] = findViewById(R.id.panel_4);


        redHr = findViewById(R.id.redHour);
        blueCommon = findViewById(R.id.blueCommon);
        blueCommonMin = findViewById(R.id.blueCommonMin);
        greenMin = findViewById(R.id.greenMin);
        hr_result = findViewById(R.id.hr_result);
        min_result = findViewById(R.id.min_result);
        min_total = findViewById(R.id.min_total);

        animation = AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.scale);
        changeUpDisplay();
        generateClock();
        setTextDisplay();
        startClockRefresh();

    }


    private void startClockRefresh() {
        count_timer = new CountDownTimer(CHANGE_UP_FREQUENCY, 1000) {


            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                changeUpDisplay();
                generateClock();
                setTextDisplay();
                count_timer.start();
            }
        };
        count_timer.start();


    }

    private void generateClock() {

        Calendar cal = Calendar.getInstance();
        int curMinute = (cal.get(Calendar.MINUTE) / 5);
        int curHour = (cal.get(Calendar.HOUR) % 12);

        Log.e(TAG, "generate  Clock: HR: " + curHour + " MIN: " + curMinute);
        setHourColors(curHour);
        setMinuteColors(curMinute);

    }

    private void animateDrawableChange(View view, int drawable) {
        Drawable[] layers = new Drawable[2];
        layers[0] = view.getBackground();
        layers[1] = getResources().getDrawable(drawable);

        TransitionDrawable transitionDrawable = new TransitionDrawable(layers);
        view.setBackground(transitionDrawable);
        transitionDrawable.setCrossFadeEnabled(true);
        transitionDrawable.startTransition(1200);

    }

    private void setTextDisplay() {
        int red = 0;
        int blue = 0;
        int green = 0;
        for (int i = 0; i < panel.length; i++) {
            int panel_val = getPanelValue(panel[i]);
            String color = (String) panel[i].getTag();
            if (color.equals("RED")) {

                red += panel_val;
            } else if (color.equals("GREEN")) {
                green += panel_val;
            } else if (color.equals("BLUE")) {
                blue += panel_val;
                Log.e(TAG, "must be blue ");
            }
        }
        redHr.setText(red + "");
        greenMin.setText(green + "");
        blueCommon.setText(blue + "");
        blueCommonMin.setText(blue + "");

        redHr.startAnimation(animation);
        greenMin.startAnimation(animation);
        blueCommon.startAnimation(animation);
        blueCommonMin.startAnimation(animation);

        hr_result.setText(red + blue + "");
        min_result.setText(green + blue + "");
        min_total.setText((green + blue) * 5 + "");
    }

    private int getPanelValue(View view) {
        int viewResource = view.getId();
        switch (viewResource) {
            case (R.id.panel_0):
                return 1;
            case (R.id.panel_1):
                return 1;
            case (R.id.panel_2):
                return 2;
            case (R.id.panel_3):
                return 3;
            case (R.id.panel_4):
                return 5;
        }
        return 0;
    }

    private void setHourColors(int hr) {
        Button view;
        for (int i = 0; i < hours[hr].length; i++) {
            if (hours[hr][i] == 1) {
                view = panel[i];
                animateDrawableChange(view, R.drawable.red);
                view.setTag("RED");
            } else {
                view = panel[i];
                animateDrawableChange(view, R.drawable.white);
                view.setTag("WHITE");
            }
        }
    }

    private void setMinuteColors(int mn) {

        for (int i = 0; i < mins[mn].length; i++) {
            if (mins[mn][i] == 1) {
                Button view = panel[i];
                boolean red_Background = view.getTag() == "RED";
                if (red_Background) {
                    animateDrawableChange(view, R.drawable.blue);
                    view.setTag("BLUE");// or anything other than red
                } else {
                    view.setTag("GREEN");
                    animateDrawableChange(view, R.drawable.green);
                }
            }

        }
    }


    private void changeUpDisplay() {

        Random rand = new Random();
        int selectHour = rand.nextInt(2);

        if (selectHour == 1) {
            hours = hours1;
        } else {
            hours = hours2;
        }
        int selectMin = rand.nextInt(2);

        if (selectMin == 1) {
            mins = mins1;
        } else {
            mins = mins2;
        }

    }
}
