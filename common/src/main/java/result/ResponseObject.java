package result;

/**
 * @author ：zty
 * @date ：Created in 2021/4/8 14:36
 * @description ： 封装响应数据的对象
 */
public class ResponseObject {

    private static  final String SUCCESS_CODE="0";
    private static  final String SUCCESS="SUCCESS";

    /**
     * 结果编码，0-固定代表处理成功
     */
    private String code;
    /**
     * 响应消息
     */
    private String message;
    /**
     * 响应附加数据（可选）
     */
    private Object data;

    public ResponseObject() {
        this.code = SUCCESS_CODE;
        this.message = SUCCESS;
    }

    public ResponseObject(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    @Override
    public String toString() {
        return "ResponseObject{}";
    }
}
