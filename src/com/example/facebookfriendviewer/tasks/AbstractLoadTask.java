package com.example.facebookfriendviewer.tasks;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import com.example.facebookfriendviewer.BaseActivity;

/**
 * Base AsyncTask to implementation data loading
 * Displaying ProgressDialog while working
 * Call onLoadComplete() after loading
 *
 * @author Witkowsky Dmitry
 */
public abstract class AbstractLoadTask extends AsyncTask<Void, Void, Void> {

    private ProgressDialog progressDialog;
    private final LoadCompleteListener callback;
    protected BaseActivity context;

    public AbstractLoadTask(BaseActivity context, LoadCompleteListener callback) {
        super();
        this.context = context;
        if (callback == null) {                     //if callback is null set empty method
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
        super.onPreExecute();

        progressDialog = ProgressDialog.show(context,
                "", "Loading...");
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dlg) {
                AbstractLoadTask.this.cancel(true);
            }
        });
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        callback.onLoadComplete();
    }


}
