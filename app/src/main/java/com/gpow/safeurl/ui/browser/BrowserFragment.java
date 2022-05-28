package com.gpow.safeurl.ui.browser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
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

//        browserViewModel.getURL().observe(getViewLifecycleOwner(), webView::loadUrl);

        return root;
    }

    private void setupWebView() {

        WebView webView = binding.webview;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageFinished(WebView view, final String url) {
//                progDailog.dismiss();
            }
        });

        webView.loadUrl("http://www.google.com");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}