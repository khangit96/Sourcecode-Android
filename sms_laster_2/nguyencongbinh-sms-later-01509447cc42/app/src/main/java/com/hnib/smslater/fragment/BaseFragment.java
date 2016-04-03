package com.hnib.smslater.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.hnib.smslater.activity.MainActivity;

/**
 * Created by caucukien on 22/12/2015.
 */
public class BaseFragment extends Fragment {

    public Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Log.d("binh", "onAttach");
        mActivity = (Activity) context;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideSoftKeyboard();
    }

    public void startFragment(int rootId, Fragment fragment) {
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.replace(rootId, fragment);
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        trans.addToBackStack(null);
        trans.commit();
    }
    public void startMainActivity(){
        if(getActivity()!=null){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }
    public void popBackStack() {
        if (getActivity() != null) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.popBackStackImmediate();
        }
    }

    public void hideSoftKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
