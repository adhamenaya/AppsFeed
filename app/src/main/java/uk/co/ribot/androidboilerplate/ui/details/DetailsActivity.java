package uk.co.ribot.androidboilerplate.ui.details;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Application;
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity;
import uk.co.ribot.androidboilerplate.ui.category.CategoryActivity;
import uk.co.ribot.androidboilerplate.ui.main.MainActivity;
import uk.co.ribot.androidboilerplate.util.CommonUtils;

/**
 * Created by adhamenaya on 4/9/2016.
 */
public class DetailsActivity extends BaseActivity implements DetailsMvpView {

    @Inject DetailsPresenter mDetailsPresenter;

    @Bind(R.id.relative_layout_top)
    RelativeLayout mRelativeLayoutTop;

    @Bind(R.id.relative_layout_bottom)
    LinearLayout mLinearLayout;

    @Bind(R.id.text_view_name)
    TextView textViewName;

    @Bind(R.id.text_view_summary)
    TextView textViewSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        mDetailsPresenter.attachView(this);

        int categoryPosition = getIntent().getExtras().getInt(CategoryActivity.CATEGORY_POSITION);
        int applicationId = getIntent().getExtras().getInt(MainActivity.APPLICATION_ID);

        mRelativeLayoutTop.setBackgroundColor(
                Color.parseColor(CommonUtils.getBackgroundColor(getApplicationContext(), categoryPosition)));

        int nextColorPosition = categoryPosition + 1;
        if(nextColorPosition ==15) nextColorPosition = 13;

        mLinearLayout.setBackgroundColor(
                Color.parseColor(CommonUtils.getBackgroundColor(getApplicationContext(), nextColorPosition)));

        mDetailsPresenter.loadApplication("-1",applicationId);

    }

    @Override
    public void showDetails(Application application) {
        textViewName.setText(application.name.label);
        textViewSummary.setText(application.summary.label);
    }
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, DetailsActivity.class);
        return intent;
    }
}
