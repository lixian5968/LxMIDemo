package com.zongbutech.ntfinance.common.infra;

public interface DefaultTaskCallback {
    public void onFinish(String key, int result, Object attachment);
}