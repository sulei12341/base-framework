package cn.ydhl.merchant.helper.constant;

/**
 * @author Junpeng.Su
 * @create 2017-07-14 上午 10:47
 * @description 订单常量模块
 */
public enum ProductConstantModule {

    NOT_FOUND("product.notFound", "未找到指定商品!");

    private String code;

    private String message;

    ProductConstantModule(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
