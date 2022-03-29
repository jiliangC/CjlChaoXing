package com.cjl.cjlchaoxing.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cjl.cjlchaoxing.Informationn;
import com.cjl.cjlchaoxing.MainActivity;
import com.cjl.cjlchaoxing.R;
import com.cjl.cjlchaoxing.cookies.GetCookies;

import java.util.HashMap;
import java.util.Map;

public class ExamFragment extends Fragment implements Informationn {
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root==null){
            root = inflater.inflate(R.layout.fragment_exam,container,false);
            WebView webView = root.findViewById(R.id.wv_exam);
            Map<String,String> map = new HashMap<>();
            map.put("Cookie", new GetCookies().getCookies());
            map.put("User-Agent",user_agent);

            WebSettings webSettings = webView.getSettings();
            //webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


            webSettings.setAllowFileAccess(true); //设置可以访问文件
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口


            String url = "https://mooc1-api.chaoxing.com/exam/phone/examcode";
            System.out.println(map.get("Cookie"));

            webView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.startsWith("http://")||url.startsWith("https://")) {
                        //使用WebView加载显示url
                        view.loadUrl(url);
                        //返回true
                        return false;
                    }
                        return true;

                }
            });
            webView.loadUrl(url,map);
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}