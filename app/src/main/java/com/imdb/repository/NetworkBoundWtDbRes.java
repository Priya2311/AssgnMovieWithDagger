/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.imdb.repository;


import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.imdb.api.ApiResponse;
import com.imdb.di.AppExecutors;
import com.imdb.utility.AbsentLiveData;
import com.imdb.utility.ApiUtil;
import com.imdb.utility.AppConstants;
import com.imdb.utility.Lg;
import com.imdb.vo.Resource;

/**
 * A generic class that can provide a resource backed by network only.
 *
 * @param <ResultType>
 * @param <RequestType>
 */
public abstract class NetworkBoundWtDbRes<ResultType, RequestType> {
    private final AppExecutors appExecutors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    protected NetworkBoundWtDbRes(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        result.setValue((Resource<ResultType>) Resource.loading(null, AppConstants.ZERO_STATUS_CODE));
        final LiveData<ResultType> dbSource = AbsentLiveData.create();

        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType data) {
                result.removeSource(dbSource);
                Lg.e("", "");
                fetchFromNetwork(dbSource);
            }
        });

    }


    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        final LiveData<ApiResponse<ResultType>> apiResponse = createCall();

        result.addSource(apiResponse, new Observer<ApiResponse<ResultType>>() {
            @Override
            public void onChanged(@Nullable final ApiResponse<ResultType> resultTypeApiResponse) {
                result.removeSource(apiResponse);
                result.removeSource(dbSource);
                //noinspection ConstantConditions
                if (resultTypeApiResponse.isSuccessful()) {
                    appExecutors.diskIO().execute(new Runnable() {
                        @Override
                        public void run() {

                            appExecutors.mainThread().execute(new Runnable() {
                                                                  @Override
                                                                  public void run() {
                                                                      result.addSource(ApiUtil.successCall(resultTypeApiResponse.body), new Observer<ResultType>() {
                                                                                  @Override
                                                                                  public void onChanged(@Nullable ResultType resultType) {
                                                                                      result.setValue(Resource.success(resultType, resultTypeApiResponse.code));
                                                                                  }
                                                                              }
                                                                      );
                                                                  }
                                                              }
                            );
                        }
                    });
                } else {
                    onFetchFailed();
                    result.addSource(dbSource,
                            new Observer<ResultType>() {
                                @Override
                                public void onChanged(@Nullable ResultType newData) {
                                    result.setValue(Resource.error(resultTypeApiResponse.errorMessage, newData, resultTypeApiResponse.code));
                                }
                            });
                }
            }
        });
    }

    protected void onFetchFailed() {
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }


    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<ResultType>> createCall();
}
