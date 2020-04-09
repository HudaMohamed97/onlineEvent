package com.OnlineEvent.umangburman.event;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class DefaultFragment extends Fragment {

    private View root;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //((MainActivity) getActivity()).setDrawerLocked(true);
        //  ((MainActivity) getActivity()).hideToolbar(true);
        root = inflater.inflate(R.layout.default_fragment, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebView webView;

        webView = root.findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://event-on.cat-sw.com/meeting/2");

        // ((MainActivity) getActivity()).hideItem("second");

       /* button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).showDrwer(true);

                //Navigation.findNavController(view).navigate(R.id.action_login_to_home);
            }
        });*/
    }

    @Override
    public void onDestroy() {
       /* ((MainActivity) getActivity()).showDrwer(true);
        ((MainActivity) getActivity()).setDrawerLocked(false);
        ((MainActivity) getActivity()).showItem("second");*/
        // ((MainActivity) getActivity()).hideToolbar(false);

        super.onDestroy();
    }
}
