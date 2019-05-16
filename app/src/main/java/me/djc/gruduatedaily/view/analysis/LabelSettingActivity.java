package me.djc.gruduatedaily.view.analysis;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Guideline;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.afollestad.materialdialogs.MaterialDialog;
import me.djc.base.activity.BaseActivity;
import me.djc.common.util.ColorUtils;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.room.entity.Label;
import me.djc.gruduatedaily.view.analysis.adapter.LabelAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xujichang
 * @date 2019/05/15
 **/
public class LabelSettingActivity extends BaseActivity {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_ADD = 1;
    public static final int TYPE_DELETE = 2;

    private TextView mTvAddLabel;
    private TextView mTvDeleteLabel;
    private Guideline mGuide;
    private RecyclerView mRvLabels;
    private AnalysisViewModel mViewModel;

    private List<Label> mLabels;
    private LabelAdapter mLabelAdapter;
    private TextView mTvComplete;

    private int type = TYPE_NORMAL;

    @Override
    protected void onIntentData(Intent intent) {

    }

    @Override
    protected void onDataInit() {
        mLabels = new ArrayList<>();
        mLabelAdapter = new LabelAdapter(mLabels);

        mViewModel = ViewModelProviders.of(this).get(AnalysisViewModel.class);
        mViewModel.getLabels().observe(this, new Observer<List<Label>>() {
            @Override
            public void onChanged(List<Label> eLabels) {
                mLabels.clear();
                mLabels.addAll(eLabels);
                mLabelAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onViewInit() {
        mTvAddLabel = findViewById(R.id.tv_add_label);
        mTvDeleteLabel = findViewById(R.id.tv_delete_label);
        mGuide = findViewById(R.id.guide);
        mRvLabels = findViewById(R.id.rv_labels);
        mTvComplete = findViewById(R.id.tv_complete);
        mRvLabels.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvLabels.setAdapter(mLabelAdapter);

        mTvAddLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加
                showLabelAddDialog();
            }
        });
        mTvDeleteLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除
                if (type == TYPE_DELETE) {
                    return;
                }
                type = TYPE_DELETE;
                mLabelAdapter.enableDelete(true);
                showComplete(true);
            }
        });
        mTvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComplete(false);
                mLabelAdapter.enableDelete(false);
                type = TYPE_NORMAL;
            }
        });
        mLabelAdapter.setEditListener(new LabelAdapter.OnLabelEditListener() {
            @Override
            public void onDelete(Label eLabel) {
                mViewModel.deleteLabel(eLabel);
            }

            @Override
            public void onEnable(Label eLabel, boolean enable) {
                eLabel.setEnable(enable);
                mViewModel.updateLabel(eLabel);
            }
        });
    }

    private void showComplete(boolean complete) {
        mTvComplete.setVisibility(complete ? View.VISIBLE : View.GONE);
    }

    private void showLabelAddDialog() {
        MaterialDialog.Builder vBuilder = new MaterialDialog.Builder(getContext());
        vBuilder.title("添加标签").positiveText("添加").input("请输入标签名称", null, false, new MaterialDialog.InputCallback() {
            @Override
            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                Label vLabel = new Label();
                vLabel.setClolor(ColorUtils.createRandomColor());
                vLabel.setContent(input.toString());
                vLabel.setEnable(true);
                mViewModel.addLabel(vLabel);
            }
        }).show();
    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.activity_label_setting;
    }
}
