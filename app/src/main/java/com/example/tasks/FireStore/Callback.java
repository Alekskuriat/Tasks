package com.example.tasks.FireStore;

public interface Callback<T> {

    void onSuccess(T value);
    void onError(Throwable error);
}
