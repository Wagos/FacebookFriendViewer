/*******************************************************************************
 * Copyright 2013 Witkowsky Dmitry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package com.example.facebookfriendviewer.tasks;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import com.example.facebookfriendviewer.BaseActivity;
import com.example.facebookfriendviewer.database.DbService;
import roboguice.util.RoboAsyncTask;

import javax.inject.Inject;

/**
 * Base AsyncTask to implementation data loading
 * Displaying ProgressDialog while working
 * Call onLoadComplete() after loading
 */
public abstract class AbstractLoadTask extends RoboAsyncTask<Void> {

    private ProgressDialog progressDialog;

    @Inject
    protected DbService dbService;

    private final LoadCompleteListener callback;

    public AbstractLoadTask(BaseActivity context,
            LoadCompleteListener callback) {
        super(context);
        if (callback ==null) { //if callback is null set empty method
            callback = new LoadCompleteListener() {
                @Override
                public void onLoadComplete() {
                }
            };
        }
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {

        progressDialog = ProgressDialog.show(context,"", "Loading...");
        progressDialog.setCancelable(true);
        progressDialog
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dlg) {
                        AbstractLoadTask.this.cancel(true);
                    }
                });
    }

    @Override
    protected void onFinally() throws RuntimeException {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        callback.onLoadComplete();
    }

}
