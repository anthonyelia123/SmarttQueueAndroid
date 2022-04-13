package me.queue.utils;

import static com.google.common.math.IntMath.factorial;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import es.dmoral.toasty.Toasty;
import me.queue.smartqueue.Model.QueueAndServiceTime;
import me.queue.smartqueue.main.data.models.QueueModel;

public class LocalFunctions {
    public LocalFunctions() {
    }

    public static String getTrimmedText(EditText field){
        return field.getText().toString().trim();
    }

    public static void errorToast(Context context, String error){
        Toasty.error(context,error,Toasty.LENGTH_SHORT).show();
    }
    public static void successToast(Context context, String success){
        Toasty.error(context,success,Toasty.LENGTH_SHORT).show();
    }
    public static QueueAndServiceTime getQueueTime(QueueModel queueModel){
        int s = Integer.parseInt(queueModel.getCounter());
        double lambda = Double.parseDouble(queueModel.getLambda());
        double mue = Double.parseDouble(queueModel.getMue());

        double p = lambda / (s * mue);
        double temp1 = 0;
        for(int i = 0; i < s; i++) {
            double v = Math.pow((lambda / mue), i) / factorial(i);
            temp1 += v;
        }
        double temp2 = Math.pow((lambda / mue), s) / factorial(s);
        double temp3 = (s * mue) / ((s * mue) - lambda);
        double P0 = 1 / (temp1 + (temp2 * temp3));

        double term1 = (P0 * lambda * mue * Math.pow((lambda / mue), s));
        double term2 = factorial(s - 1) * Math.pow(((s * mue) - lambda), 2);

        double W1 = (P0 * mue * Math.pow((lambda / mue) , s));
        double Ws = (W1 / term2) + (1/mue);
        double Wq = Ws - (1 / mue);

        Log.d("WS", String.valueOf(Ws * 60));
        Log.d("WQ", String.valueOf(Wq * 60));

        QueueAndServiceTime queueAndServiceTime = new QueueAndServiceTime((Ws * 60) -(Wq * 60), Wq *60);
        return queueAndServiceTime;
    }

}
