package com.xpf.rxjavaretrofit2demo;

import com.xpf.rxjavaretrofit2demo.annotation.LifecyclePublisher;

import org.reactivestreams.Publisher;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.processors.BehaviorProcessor;

/**
 * Created by x-sir on 2019-06-02 :)
 * Function:
 */
public class LifecycleTransformer<T> implements ObservableTransformer<T, T>,
        FlowableTransformer<T, T>,
        SingleTransformer<T, T>,
        MaybeTransformer<T, T>,
        CompletableTransformer {

    private final BehaviorProcessor<Integer> lifecycleBehavior;

    private LifecycleTransformer() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public LifecycleTransformer(@NonNull BehaviorProcessor<Integer> lifecycleBehavior) {
        this.lifecycleBehavior = lifecycleBehavior;
    }

    @Override
    public CompletableSource apply(Completable upstream) {
        return upstream.ambWith(
                lifecycleBehavior
                        .filter(event -> event == LifecyclePublisher.DESTROY_VIEW || event == LifecyclePublisher.DESTROY || event == LifecyclePublisher.DETACH)
                        .take(1)
                        .flatMapCompletable((Function<Integer, Completable>) flowable -> Completable.complete())
        );
    }

    @Override
    public Publisher<T> apply(Flowable<T> upstream) {
        return upstream.takeUntil(
                lifecycleBehavior
                        .skipWhile(event -> event != LifecyclePublisher.DESTROY_VIEW && event != LifecyclePublisher.DESTROY && event != LifecyclePublisher.DETACH)
        );
    }

    @Override
    public MaybeSource<T> apply(Maybe<T> upstream) {
        return upstream.takeUntil(
                lifecycleBehavior
                        .skipWhile(event -> event != LifecyclePublisher.DESTROY_VIEW && event != LifecyclePublisher.DESTROY && event != LifecyclePublisher.DETACH)
        );
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream.takeUntil(
                lifecycleBehavior
                        .skipWhile(event -> event != LifecyclePublisher.DESTROY_VIEW && event != LifecyclePublisher.DESTROY && event != LifecyclePublisher.DETACH).toObservable()
        );
    }

    @Override
    public SingleSource<T> apply(Single<T> upstream) {
        return upstream.takeUntil(
                lifecycleBehavior.skipWhile(event -> event != LifecyclePublisher.DESTROY_VIEW && event != LifecyclePublisher.DESTROY && event != LifecyclePublisher.DETACH)
        );
    }
}
