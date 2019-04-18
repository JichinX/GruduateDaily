package me.djc.base.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.djc.base.factory.IView;

/**
 * Des:GruduateDaily - me.djc.base.fragment
 *
 * @author xujichang
 * @date 2019-04-15 - 20:47
 * <p>
 * modify:
 */
public abstract class BaseFragment extends BaseLazyFreagment implements IView {
    private String TAG = this.getClass().getSimpleName();
    private IFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            onGetArguments(getArguments());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return initView(inflater.inflate(onCreateViewRes(), container, false));
    }

    protected abstract View initView(View inflate);

    public void interaction(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(onGetInteractionTag(), uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IFragmentInteractionListener) {
            mListener = (IFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private String onGetInteractionTag() {
        return TAG;
    }

    protected abstract void onGetArguments(Bundle arguments);

    protected abstract int onCreateViewRes();
}
