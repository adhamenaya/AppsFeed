package uk.co.ribot.androidboilerplate.ui.details;

import uk.co.ribot.androidboilerplate.data.model.Application;
import uk.co.ribot.androidboilerplate.ui.base.MvpView;

/**
 * Created by adhamenaya on 4/9/2016.
 */
public interface DetailsMvpView extends MvpView {

    public void showDetails(Application application);
}
