package com.cjl.cjlchaoxing.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cjl.cjlchaoxing.R;

public class CourseFragment extends Fragment {
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root==null){
            root=inflater.inflate(R.layout.fragment_course,container,false);
            TextView textView = root.findViewById(R.id.text_course);
            textView.setText("这是设置后的文本");
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}