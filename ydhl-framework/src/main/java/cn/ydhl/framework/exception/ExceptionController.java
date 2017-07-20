package cn.ydhl.framework.exception;


import cn.ydhl.framework.exception.model.BusinessException;
import cn.ydhl.framework.exception.model.MaxSessionException;
import cn.ydhl.framework.exception.model.PageNotFoundException;
import cn.ydhl.framework.exception.model.RequestLimitException;
import cn.ydhl.framework.exception.model.SessionTimeoutException;
import cn.ydhl.framework.exception.model.ValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.net.BindException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Set;

/**
 * @author Junpeng.Su
 * @create 2017-07-13 上午 11:49
 * @description 统一异常处理器
 */
public abstract class ExceptionController {

    public static final String PATH_ERROR_MAX_SESSION = "/error/max_sessions";
    public static final String PATH_TIME_OUT = "/error/time_out";
    private static final String PATH_ERROR_ALL = "/error/all";

    private static final String MAX_SESSION_LIMIT = "max_session_limit";
    private static final String TIME_OUT = "time_out";
    private static final String UNAUTHORIZED = "unauthorized";
    private static final String INVALID_PARAM = "invalid_param";
    private static final String PAGE_NOT_FOUND = "page_not_found";
    private static final String METHOD_NOT_ALLOWED = "method_not_allowed";
    private static final String INTERFACE_DEPRECATED = "interface_deprecated";
    private static final String GATEWAY_TIMEOUT = "gateway_timeout";
    private static final String TOO_MANY_REQUESTS = "too_many_requests";
    private static final String INTERNAL_SERVER_ERROR = "internal_server_error";

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

    private boolean showRealError = false;

    public void setShowRealError(boolean showRealError) {
        this.showRealError = showRealError;
    }

    /**
     * session超过最大限制异常
     *
     * @return 异常页面
     */
    @RequestMapping(PATH_ERROR_MAX_SESSION)
    public String maxSessions() throws MaxSessionException {
        throw new MaxSessionException();
    }

    /**
     * 超时异常
     *
     * @return 异常页面
     */
    @RequestMapping(PATH_TIME_OUT)
    public String timeout() throws SessionTimeoutException {
        throw new SessionTimeoutException();
    }

    /**
     * 其他异常
     *
     * @param request HttpServletRequest
     * @return 异常页面
     * @throws Throwable
     */
    @RequestMapping(PATH_ERROR_ALL)
    public String error(HttpServletRequest request) throws Throwable {
        Integer code = (Integer) request.getAttribute(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE);
        Throwable exception = (Throwable) request.getAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE);
        String simpleMsg = (String) request.getAttribute(WebUtils.ERROR_MESSAGE_ATTRIBUTE);
        LOGGER.error("*****Spring can't catch this error code[{}], message[{}], currUserId[{}]*****", code, simpleMsg, 1, exception);
        if (exception != null) {
            throw getRootCause(exception);
        } else {
            switch (code) {
                case 404:
                    throw new PageNotFoundException();
                case 403:
                    throw new AccessDeniedException(simpleMsg);
                default:
                    throw new Exception(simpleMsg);
            }
        }
    }


    /**
     * 同时登陆数量超过最大异常 401
     *
     * @param request 页面请求
     * @return ExceptionDto 异常JSON
     */
    @ExceptionHandler(value = MaxSessionException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView maxSessionException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        LOGGER.error("Error catch MaxSessionException: errorUrl[{}],errorMessage[{}], currUserId[{}]", request.getRequestURI(), exception.getMessage(), 1);
        return prepareExceptionInfo(request, response, HttpStatus.UNAUTHORIZED, MAX_SESSION_LIMIT, "同时登陆的会话已达最大阀值", exception);
    }

    /**
     * 会话超时异常 401
     *
     * @param request 页面请求
     * @return ExceptionDto 异常JSON
     */
    @ExceptionHandler(value = SessionTimeoutException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView sessionTimeoutException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        LOGGER.error("Error catch SessionTimeoutException: errorUrl[{}],errorMessage[{}], currUserId[{}]", request.getRequestURI(), exception.getMessage(), 1);
        return prepareExceptionInfo(request, response, HttpStatus.UNAUTHORIZED, TIME_OUT, "会话超时", exception);
    }

    /**
     * 无权访问指定页面异常 401
     *
     * @param request 页面请求
     * @return ExceptionDto 异常JSON
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView accessDeniedException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        LOGGER.error("Error catch AccessDeniedException: errorUrl[{}],errorMessage[{}], currUserId[{}]", request.getRequestURI(), exception.getMessage(), 1);
        return prepareExceptionInfo(request, response, HttpStatus.UNAUTHORIZED, UNAUTHORIZED, "无权访问", exception);
    }

    /**
     * 系统实体校验，丢失参数，参数类型转换异常 400
     *
     * @param request   页面请求
     * @param exception 异常
     * @return ExceptionDto 异常JSON
     */
    @ExceptionHandler(value = {ValidateException.class, ServletRequestBindingException.class, TypeMismatchException.class, BindException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView validateException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        String errorMessage = null;
        LOGGER.error("Error catch ValidateException: errorUrl[{}],errorMessage[{}], currUserId[{}]", request.getRequestURI(), exception.getMessage(), 11);
        if (exception instanceof ConstraintViolationException) {
            StringBuilder sb = new StringBuilder();
            Set<ConstraintViolation<?>> constraintViolationSet = ((ConstraintViolationException) exception).getConstraintViolations();
            for (ConstraintViolation<?> constraintViolation : constraintViolationSet) {
                sb.append("[");
                sb.append(constraintViolation.getPropertyPath());
                sb.append(constraintViolation.getMessage());
                sb.append("]");
            }
            errorMessage = sb.toString();
        } else if (exception instanceof ValidateException) {
            StringBuilder sb = new StringBuilder();
            StringBuilder me = new StringBuilder();
            List<String> codeList = ((ValidateException) exception).getCodeList();
//            List<String> messageList = ((ValidateException) exception).getMessageList();
            for (String code : codeList) {
                sb.append("[");
                sb.append(code);
                sb.append("]");
            }
/*            for (String message : messageList) {
                me.append("[");
                me.append(message);
                me.append("]");
            }*/
            errorMessage = exception.getMessage();
        } else {
            errorMessage = exception.getMessage();
        }
        return prepareExceptionInfo(request, response, HttpStatus.BAD_REQUEST, INVALID_PARAM, errorMessage, exception);
    }

    /**
     * 系统业务异常 403
     *
     * @param request   页面请求
     * @param exception 异常
     * @return ExceptionDto 异常JSON
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView businessException(HttpServletRequest request, HttpServletResponse response, BusinessException exception) {
        LOGGER.error("Error catch BusinessException: errorUrl[{}],errorMessage[{}], currUserId[{}]", request.getRequestURI(), exception.getMessage(), 1);
        return prepareExceptionInfo(request, response, HttpStatus.FORBIDDEN, exception.getCode(), exception.getMessage(), exception);
    }

    /**
     * 找不到指定页面异常 404
     *
     * @param request 页面请求
     * @return ExceptionDto 异常JSON
     */
    @ExceptionHandler(value = PageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView pageNotFoundException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        LOGGER.error("Error catch PageNotFoundException: errorUrl[{}],errorMessage[{}], currUserId[{}]", request.getRequestURI(), exception.getMessage(),1);
        return prepareExceptionInfo(request, response, HttpStatus.NOT_FOUND, PAGE_NOT_FOUND, "找不到指定页面", exception);
    }

    /**
     * 请求方法错误 405
     *
     * @param request 页面请求
     * @return ExceptionDto 异常JSON
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ModelAndView httpRequestMethodNotSupportedException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        String requestMethod = request.getMethod();
        LOGGER.error("Error catch HttpRequestMethodNotSupportedException: errorUrl[{}], errorMessage[{}], currUserId[{}]", request.getRequestURI(), exception.getMessage(),1);
        return prepareExceptionInfo(request, response, HttpStatus.METHOD_NOT_ALLOWED, METHOD_NOT_ALLOWED, "该接口不支持" + requestMethod + "请求", exception);
    }

    /**
     * 太多请求 429
     *
     * @param request 页面请求
     * @return ExceptionDto 异常JSON
     */
    @ExceptionHandler(value = RequestLimitException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ModelAndView requestLimitException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        LOGGER.error("Error catch RequestLimitException: errorUrl[{}], errorMessage[{}], currUserId[{}]", request.getRequestURI(), exception.getMessage(), 1);
        return prepareExceptionInfo(request, response, HttpStatus.TOO_MANY_REQUESTS, TOO_MANY_REQUESTS, "接口繁忙，请稍后再试", exception);
    }


    /**
     * 其他异常 500
     *
     * @param request   页面请求
     * @param exception 异常
     * @return ExceptionDto 异常JSON
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView springErrorHand(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        String simpleMsg = (String) request.getAttribute(WebUtils.ERROR_MESSAGE_ATTRIBUTE);
        String errorCode = exception == null ? simpleMsg : exception.getClass().getSimpleName();
        String errorMessage = exception == null ? simpleMsg : exception.getMessage();

        LOGGER.error("Error catch: statusCode[{}], errorCode[{}], errorMessage[{}], errorUrl[{}], currUserId[{}]"
                , HttpStatus.INTERNAL_SERVER_ERROR.value()
                , errorCode
                , errorMessage
                , request.getRequestURI()
                , 1
                , exception);
//        String realMessage = messageSource.getMessage(INTERNAL_SERVER_ERROR, null, errorMessage, LocaleContextHolder.getLocale());
//        if (showRealError) {
//            realMessage = errorMessage;
//        }
        return prepareExceptionInfo(request, response, HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR, errorMessage, exception);
    }

    abstract protected ModelAndView prepareExceptionInfo(HttpServletRequest request, HttpServletResponse response, HttpStatus httpStatus, String errorCode, String errorMessage, Exception exception);

    private Throwable getRootCause(Throwable exception) {
        if (exception.getCause() != null) {
            return getRootCause(exception.getCause());
        }
        return exception;
    }
}
