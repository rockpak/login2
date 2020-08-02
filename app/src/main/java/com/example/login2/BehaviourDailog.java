package com.example.login2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Map;

public class BehaviourDailog extends DialogFragment implements View.OnClickListener {

    private final String TAG = Behaviour.class.getSimpleName();
    private Map<String,Map<String, String>> be;
    private String behaviourSelected;
    private double avg, daysRecorded;

    public BehaviourDailog(Map<String, Map<String, String>> be, String behaviourSelected){
        this.be = be;
        this.behaviourSelected = behaviourSelected;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.record_dialog, container, false);
        v.findViewById(R.id.closeButton).setOnClickListener(this);
        extractDays(v);
        return v;
    }

    private void extractDays(View v){
            for (String key : be.keySet()) {
                if (be.get(key) != null) {
                    if (be.get(key).containsKey(behaviourSelected)) {
                        setText(v, key, be.get(key).get(behaviourSelected));
                        incAvg(be.get(key).get(behaviourSelected));
                        Log.i(TAG, be.get(key).get(behaviourSelected));
                        daysRecorded++;
                    }
                }
            }

            Log.i(TAG, "Average " + avg + " days recoreder " + daysRecorded);

            setText(v, "Average", rateBeh((daysRecorded*3),(daysRecorded > 1 ? ((new BigDecimal(avg/daysRecorded).setScale(0,BigDecimal.ROUND_UP).doubleValue())) : avg), be.get(new ArrayList<String>(be.keySet()).get(0)).get(behaviourSelected)));

    }

    private void incAvg(String res){
        if(res.contains("Excellent")){
            avg += 4;
        }else if(res.contains("Very Good")){
            avg += 2;
        }else if(res.contains("Good")){
            avg += 1;
        }
    }

    private String rateBeh(double max, double res, String resString){
        Log.i(TAG, "Average " + max + " days recoreder " + res + " " + (res > 0 && res <= (max*.25)) + " " + (max*.5) + " " + (max*.25));

        return daysRecorded == 0 ? "No Records" : (daysRecorded == 1 ? resString : ((res > 0 && res <= (max*.20)) ? "Good" : (((res > (max*.20) && res <= (max*.40) ? "Very Good" : "Excellent")))));
    }

    private void setText(View v, String id, String text){
        ((TextView)v.findViewWithTag(id)).setText(text);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.closeButton) {
            this.dismiss();
        }
    }
}
