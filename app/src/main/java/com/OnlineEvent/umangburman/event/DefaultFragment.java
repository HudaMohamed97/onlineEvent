package com.OnlineEvent.umangburman.event;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        ((MainActivity) getActivity()).setDrawerLocked(true);
        //  ((MainActivity) getActivity()).hideToolbar(true);
        root = inflater.inflate(R.layout.default_fragment, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button2 = root.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_login_to_home);
            }
        });
    }

    @Override
    public void onDestroy() {
        ((MainActivity) getActivity()).setDrawerLocked(false);
        // ((MainActivity) getActivity()).hideToolbar(false);

        super.onDestroy();
    }
}
