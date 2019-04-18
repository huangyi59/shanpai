package sp.base;

import android.support.v4.app.FragmentActivity;

public interface BaseContract {

    abstract class BasePresenter<V,M extends BaseModel>{
        public V mView;
        public M mMode;
        public FragmentActivity mActivity;

        public void attachVM(V view,M modle,FragmentActivity activity){
            this.mView = view;
            this.mMode = modle;
            this.mActivity = activity;
            this.mMode.onStart(activity,this);
            this.onCreate();
        }

        public void detachVM(){
            mView = null;
            mMode = null;
            this.onDestroy();
        }

        public abstract void onCreate();
        public abstract void onDestroy();
        public abstract void showMessage(String message);
    }

    interface BaseView{
        void showMessage(String msg);
    }

    interface BaseModel<P>{
        void onStart(FragmentActivity activity,P presenter);
    }

}
