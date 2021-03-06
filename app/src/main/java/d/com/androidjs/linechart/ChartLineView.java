package d.com.androidjs.linechart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import org.json.JSONObject;

import d.com.androidjs.DisplayUtil;
import d.com.androidjs.LineActivity;
import d.com.androidjs.R;

/**
 * 折线图View
 */

public class ChartLineView extends LinearLayout {
    private WebView mWebview;
    private String jsLine;
    private Context mContext;

    public ChartLineView(Context context) {
        this(context, null);
    }

    public ChartLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.layout_chart_container, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChartsWebView, 0, 0);
        int height = typedArray.getInteger(R.styleable.ChartsWebView_viewHeight, 260);
        typedArray.recycle();
        initWebView();
        setWebViewHeight(height);
    }


    private void initWebView() {
        mWebview = findViewById(R.id.chartshow_wb);
        mWebview.getSettings().setJavaScriptEnabled(true);
        WebSettings webSettings = mWebview.getSettings();
        // User settings
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);//设置可以访问文件
        mWebview.setHorizontalScrollBarEnabled(false);//水平不显示
        mWebview.setVerticalScrollBarEnabled(false); //垂直不显示
    }


    public void createLineChart(final JSONObject lineJson) {
        jsLine = "javascript:createChart(" + lineJson + ")";
        mWebview.loadUrl("file:///android_asset/echart/lineChart.html");
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Log.w("webview", "shouldOverrideUrlLoading");
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.w("webview", "onPageFinished");
                mWebview.loadUrl(jsLine);
            }
        });
    }

    public void setWebViewHeight(int height) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                DisplayUtil.dip2px(mContext, height));
        mWebview.setLayoutParams(params);
    }

}

