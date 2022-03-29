package com.cjl.cjlchaoxing.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cjl.cjlchaoxing.R;


public class SignFragment extends Fragment {

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root==null){
            root = inflater.inflate(R.layout.fragment_sgin,container,false);
            TextView textView = root.findViewById(R.id.text_sign);
            textView.setText("签到");
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
