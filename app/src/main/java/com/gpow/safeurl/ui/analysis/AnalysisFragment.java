package com.gpow.safeurl.ui.analysis;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gpow.safeurl.databinding.FragmentAnalysisBinding;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class AnalysisFragment extends Fragment {

    private FragmentAnalysisBinding binding;

    private String final_url;
    private String final_verdict = "Suspicious";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        AnalysisViewModel analysisViewModel =
//                new ViewModelProvider(this).get(AnalysisViewModel.class);

        binding = FragmentAnalysisBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textDashboard;
//        analysisViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        viewSetup();
        return root;
    }

    private void viewSetup() {

        binding.urlField.setText("https://google.de");
        binding.goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = String.valueOf(binding.urlField.getText());
                if (!url.contains("http")) {
                    url = "http://" + url;
                    binding.urlField.setText(url);
                }
                checkForUrlRedirection(url);
            }
        });
    }

    private void checkForUrlRedirection(String url_str) {

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    String response_text = getURLContent(url_str);
                    handler.post(new Runnable(){
                        public void run() {
                            binding.textDashboard.setText("URL redirection check\n==================" +
                                    binding.textDashboard.getText() + response_text +
                                    "\n\nSummary\n=======\n:::Actual URL:::\n" + final_url + "\n\n:::Verdict:::\n" + final_verdict);
                        }
                    });
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(runnable).start();
    }

    public String getURLContent(String url_str) {

        final_url = url_str;
        String response_text = "";
        try {
            HttpURLConnection con = (HttpURLConnection)(new URL(url_str).openConnection());
            con.setInstanceFollowRedirects( false );
            con.connect();
            int responseCode = con.getResponseCode();
            System.out.println( responseCode );
            String location = con.getHeaderField( "Location" );
            System.out.println( location );

            response_text = "\n\n Response code: " + responseCode + "\n Redirected URL: " + url_str;
            if (responseCode == 301 || responseCode == 302) {
                String new_response = getURLContent(location);
                return response_text + new_response;
            }
            else {
                if (responseCode == 200) {
                    final_verdict = "Safe";
                }
                return response_text;
            }
        } catch (IOException e) {
            e.printStackTrace();
            String error_msg = String.valueOf(e.getCause());
            if (error_msg.contains("CertificateException")) {
                error_msg = "Unknown/ Invalid certificate";
                final_verdict = "Not secure";
            }
            return "\n\n Response code: " + e.getCause() + "\n Redirected URL: " + url_str;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}