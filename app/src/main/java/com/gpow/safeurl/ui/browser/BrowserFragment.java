package com.gpow.safeurl.ui.browser;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gpow.safeurl.databinding.FragmentBrowserBinding;

public class BrowserFragment extends Fragment {

    private FragmentBrowserBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        BrowserViewModel browserViewModel =
//                new ViewModelProvider(this).get(BrowserViewModel.class);
        binding = FragmentBrowserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupWebView();
        setupGoBtn();

//        browserViewModel.getURL().observe(getViewLifecycleOwner(), webView::loadUrl);

        return root;
    }

    private void setupWebView() {

        WebView webView = binding.webview;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            webView.getSettings().setSafeBrowsingEnabled(true);
        }

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setAllowFileAccess(false);
        webView.getSettings().setAllowFileAccessFromFileURLs(false);
        webView.getSettings().setAllowContentAccess(false);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                loadWebUrl(url);
                return false;
            }
            @Override
            public void onPageFinished(WebView view, final String url) {
//                progDailog.dismiss();
            }
        });

        webView.loadUrl("http://www.google.com");
    }


    private void setupGoBtn() {

        Button goBtn = binding.goBtn;
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadWebUrl(String.valueOf(binding.urlTv.getText()));
            }
        });
    }


    private void loadWebUrl(String url) {
        if (!url.contains("http")) {
            url = "http://" + url;
            binding.urlTv.setText(url);
        }
        binding.webview.loadUrl(url);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}