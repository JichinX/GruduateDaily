package me.djc.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;
import me.djc.common.R;

/**
 * Des:GruduateDaily - me.djc.common.widget
 * 带有头部和底部View的Rv
 *
 * @author xujichang
 * @date 2019-04-19 - 02:34
 * <p>
 * modify:
 */
public class FootAndHeaderRv extends LinearLayoutCompat {
    private ViewStub mFooter;
    private RecyclerView mRv;
    private ViewStub mHeader;

    public FootAndHeaderRv(Context context) {
        this(context, null);
    }

    public FootAndHeaderRv(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FootAndHeaderRv(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context eContext, AttributeSet eAttrs, int eDefStyleAttr) {
        setOrientation(VERTICAL);
        LayoutInflater.from(eContext).inflate(R.layout.common_view_foot_header_rv, this, true);

        mHeader = findViewById(R.id.common_header_header_footer_rv);
        mRv = findViewById(R.id.common_rv_header_footer_rv);
        mFooter = findViewById(R.id.common_footer_header_footer_rv);


        requestLayout();
    }

    public void initData(int headerRes, int footerRes) {
        initData(headerRes, footerRes, null, null);
    }

    public void initData(int headerRes, int footerRes, RecyclerView.Adapter eAdapter, RecyclerView.LayoutManager eLayoutManager) {
        if (0 != headerRes) {
            View headView = mHeader.inflate();
            onHeaderInflate(headView);
            requestLayout();
        }
        if (0 != footerRes) {
            View footView = mFooter.inflate();
            onFooterInflate(footView);
            requestLayout();
        }
        if (null != eAdapter) {
            mRv.setAdapter(eAdapter);
        }
        if (null != eLayoutManager) {
            mRv.setLayoutManager(eLayoutManager);
        }
    }

    private void onFooterInflate(View eView) {
        if (null != mInflateListener) {
            mInflateListener.onFooterInfalte(eView);
        }
    }

    private void onHeaderInflate(View eView) {
        if (null != mInflateListener) {
            mInflateListener.onHeaderInfalte(eView);
        }
    }

    public RecyclerView getRv() {
        return mRv;
    }

    public interface OnViewInflateListener {

        void onHeaderInfalte(View eView);

        void onFooterInfalte(View eView);

    }

    private OnViewInflateListener mInflateListener;

    public void setInflateListener(OnViewInflateListener eInflateListener) {
        mInflateListener = eInflateListener;
    }
}
