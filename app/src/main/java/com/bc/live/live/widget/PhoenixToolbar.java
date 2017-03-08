package com.bc.live.live.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * Created by Administrator on 2017/2/10.
 */
public class PhoenixToolbar extends Toolbar {
    private LayoutInflater layoutInflater;
    private View mView;
    private EditText mSerachView;
    private TextView mTexttitle;
    private ImageButton mRightButton;

    public PhoenixToolbar(Context context) {
        this(context,null);
    }

    public PhoenixToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PhoenixToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initview();
        //相对边距的设定
        setContentInsetsRelative(10,10);
        if(attrs!=null){
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(),attrs,
                    R.styleable.PhoenixToolbar, defStyleAttr, 0);

//            final Drawable leftIcon = a.getDrawable(R.styleable.PhoenixToolbar_LeftButtonIcon);
//            if (leftIcon != null) {
//                setRightButtonIcon(leftIcon);
//            }

            final Drawable rightIcon = a.getDrawable(R.styleable.PhoenixToolbar_rightButtonIcon);
            if (rightIcon != null) {
                setRightButtonIcon(rightIcon);
            }

//            CharSequence rightButtonText = a.getText(R.styleable.PhoenixToolbar_rightButtonText);
//            if(rightButtonText!=null){
//                setRightButtonText(rightButtonText);
//            }

            Boolean isShowSearchView =
                    a.getBoolean(R.styleable.PhoenixToolbar_isShowSearchView,true);
            if(isShowSearchView){
                showSearchView();
                hideTextTitle();
            }else {
                hideSearchView();
                hideTextTitle();
            }
            a.recycle();
        }
    }

    public void setRightButtonText(CharSequence rightButtonText) {

    }

    private void setRightButtonIcon(Drawable rightIcon) {
        if(mRightButton!=null){
            mRightButton.setImageDrawable(rightIcon);
            mRightButton.setVisibility(VISIBLE);
        }

    }


    private void initview() {
        if(mView == null){
        layoutInflater = LayoutInflater.from(getContext());
        mView = layoutInflater.inflate(R.layout.toolbar,null);
        mTexttitle = (TextView) mView.findViewById(R.id.toolbar_title);
        mSerachView = (EditText) mView.findViewById(R.id.toolbar_searchview);
        mRightButton = (ImageButton) mView.findViewById(R.id.toolbar_rightButton);

        LayoutParams lp = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER_HORIZONTAL);

        addView(mView,lp);
        }
    }

    @Override
    public void setTitle(@StringRes int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        initview();
        if(mTexttitle != null){
           mTexttitle.setText(title);
            showTextTitle();
        }
    }

    //显示搜索框
    public void showSearchView(){
        if(mSerachView!=null){
            mSerachView.setVisibility(VISIBLE);
        }
    }
    //隐藏搜索框
    public void hideSearchView(){
        if(mSerachView!=null){
            mSerachView.setVisibility(GONE);
        }
    }

    //显示中间标题
    public void showTextTitle(){
        if(mTexttitle!=null){
            mTexttitle.setVisibility(VISIBLE);
        }
    }
    //隐藏中间标题
    public void hideTextTitle(){
        if(mTexttitle!=null){
            mTexttitle.setVisibility(GONE);
            mRightButton.setVisibility(GONE);
        }
    }

    //设置右侧按钮点击监听
    public void setRightButtonClickListener(OnClickListener listener){
        mRightButton.setOnClickListener(listener);
    }
}
