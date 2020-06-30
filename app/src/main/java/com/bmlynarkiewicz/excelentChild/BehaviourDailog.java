package com.bmlynarkiewicz.excelentChild;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import java.util.ArrayList;
import java.util.Map;

public class BehaviourDailog extends DialogFragment implements View.OnClickListener {

    private final String TAG = Behaviour.class.getSimpleName();
    private Map<String,Map<String, String>> be;
    private String behaviourSelected;
    private int avg, daysRecorded;

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
                        daysRecorded++;
                    }
                }
            }

            setText(v, "Average", rateBeh(be.get(new ArrayList<String>(be.keySet()).get(0)).get(behaviourSelected)));

    }

    private void incAvg(String res){
        if(res.contains("Excellent")){
            avg += 3;
        }else if(res.contains("Very Good")){
            avg += 2;
        }else if(res.contains("Good")){
            avg += 1;
        }
    }

    private String rateBeh(String resString){
        int max = daysRecorded * 3;
        int res = daysRecorded > 1 ? (avg/daysRecorded) : avg;
        return daysRecorded == 0 ? "No Records" : (daysRecorded == 1 ? resString : ((res > 0 && res <= (max*.25)) ? "Good" : (((res > (max*.25) && res <= (max*.5) ? "Very Good" : "Excellent")))));
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
