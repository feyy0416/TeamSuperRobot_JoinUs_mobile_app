package edu.neu.madcourse.joinus.util;

import android.view.View;
import android.widget.EditText;

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
