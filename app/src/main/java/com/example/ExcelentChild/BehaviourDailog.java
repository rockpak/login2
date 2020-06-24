package com.example.ExcelentChild;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Map;

public class BehaviourDailog extends DialogFragment implements View.OnClickListener {

    private final String TAG = Behaviour.class.getSimpleName();

    Map<String, String> be;

    public BehaviourDailog(Map<String, String> be){
        this.be = be;
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

        for(String key : be.keySet()){
            switch (key){
                case "Eating Habit": {
                    setText(v, R.id.eating, be.get("Eating Habit"));
                    break;
                }
                case "Homework":{
                    setText(v, R.id.homeWork, be.get("Homework"));
                    break;
                }
                case "Excessive Playing":{
                    setText(v, R.id.excessive_playing, be.get("Excessive Playing"));
                    break;
                }
                case "Excessive Phone Uses":{
                    setText(v, R.id.excessive_phone_use, be.get("Excessive Phone Uses"));
                    break;
                }


            }
        }

        return v;
    }

    private void setText(View v, int id, String text){
        ((TextView)v.findViewById(id)).setText(text);
        Log.i(TAG, text);
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
        switch (view.getId()){
            case R.id.closeButton:{
                this.dismiss();
                break;
            }
        }

    }
}
