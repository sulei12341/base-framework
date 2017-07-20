package cn.ydhl.framework.response;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Junpeng.Su
 * @create 2017-07-14 上午 11:01
 * @description
 */
@Order(99)
@ControllerAdvice(annotations = RestController.class)
public class ResultResponseBodyController implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        Class returnType = methodParameter.getMethod().getReturnType();
        if (returnType.isPrimitive() || String.class.isAssignableFrom(returnType) || Number.class.isAssignableFrom(returnType) || Date.class.isAssignableFrom(returnType)) {
            return true;
        } else {
            try {
                return ((Class) returnType.getField("TYPE").get(null)).isPrimitive();
            } catch (Exception e) {
                return false;
            }
        }
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        return new SimpleVo(o);
    }

    private class SimpleVo implements Serializable {
        private static final long serialVersionUID = -4139646494152202139L;

        /**
         * 返回结果
         */
        private Object result;

        public SimpleVo(Object result) {
            this.result = result;
        }

        public Object getResult() {
            return result;
        }

        public void setResult(Object result) {
            this.result = result;
        }
    }
}
