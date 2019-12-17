package com.rocky.commonlib.net;

import android.util.Log;
import android.widget.Toast;

import com.rocky.commonlib.base.BaseActivity;
import com.trello.rxlifecycle2.LifecycleTransformer;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Method;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author
 * @date 2019/12/6.
 * description：
 */
public class RequestUtitl {
    private static RequestUtitl ourInstance = null;
    private static String SUCCESS_CODE = "10000";

    public static RequestUtitl getInstance() {
        if (ourInstance == null) {
            synchronized (RequestUtitl.class) {
                if (ourInstance == null) {
                    ourInstance = new RequestUtitl();
                }
            }
        }
        return ourInstance;
    }

    private RequestUtitl() {
    }

    public <S,T> void request(Observable<ResultModel<T>> observable, LifecycleTransformer<ResultModel<T>> transformer, CommonObserver<T> observer, Class<T> tClass) {

        observable.compose(transformer).compose(this.<ResultModel<T>>applySchedulers()).compose(this.<ResultModel<T>, T>handleResult(tClass)).
                subscribe(observer);
    }

    final ObservableTransformer schedulersTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable observable) {
            return observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        }
    };

    @SuppressWarnings("unchecked")
    protected <T> ObservableTransformer<T, T> applySchedulers() {
        return schedulersTransformer;
    }


    @SuppressWarnings("unchecked")
    protected <T extends ResultModel, R> ObservableTransformer<T, R>
    handleResult(final Class<?> c) {
        return new ObservableTransformer<T, R>() {
            @Override
            public ObservableSource<R> apply(Observable<T> upstream) {
                return upstream.flatMap(new Function<T, ObservableSource<R>>() {
                    @Override
                    public ObservableSource<R> apply(final T result) throws Exception {
                        if (SUCCESS_CODE.equals(result.code)) {
                            return Observable.create(new ObservableOnSubscribe<R>() {
                                @Override
                                public void subscribe(ObservableEmitter<R> e) throws Exception {
                                    if (result.result == null) {
                                        Object object = c.newInstance();
                                        e.onNext((R) object);
                                    } else {
                                        e.onNext((R) result.result);
                                    }
                                    e.onComplete();
                                }
                            });
                        } else {

                            //统一处理 其他返回状态
                            return Observable.error(new Exception(result.message));
                        }
                    }
                });
            }
        };
    }
}
