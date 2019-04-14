package com.qujie.mintwo.ustils;

public class ResultDataUtils {

    public static Object successResult(String message,Object data){
        ResultData resultData = new ResultData();
        resultData.setStatus(true);
        resultData.setMessage(message);
        resultData.setData(data);
        return resultData;
    }

    public static Object failResult(String message){
        ResultData resultData = new ResultData();
        resultData.setStatus(false);
        resultData.setMessage(message);
        return resultData;
    }
    public static class ResultData{
        private boolean status;
        private String message;
        private Object data;

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}
