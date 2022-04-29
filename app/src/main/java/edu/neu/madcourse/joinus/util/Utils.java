package edu.neu.madcourse.joinus.util;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.neu.madcourse.joinus.EventListActivity;
import edu.neu.madcourse.joinus.MainActivity;
import edu.neu.madcourse.joinus.R;

public class Utils {

    public static void setInputReset(View view){
        if (view instanceof EditText){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((EditText) view).getText().clear();
                }
            });

        }
    }

}
