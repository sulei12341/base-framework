package cn.ydhl.merchant.controller.exception;

import cn.ydhl.framework.exception.ExceptionController;
import cn.ydhl.framework.exception.model.ValidateException;
import cn.ydhl.framework.utils.IOUtilPlus;
import cn.ydhl.framework.utils.StringUtilPlus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Junpeng.Su
 * @create 2017-07-13 上午 11:49
 * @description 后台异常处理器
 */
@ControllerAdvice
@Controller
public class BackendExceptionController extends ExceptionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BackendExceptionController.class);

    private static final MappingJackson2JsonView DEFAULT_JSON_VIEW = new MappingJackson2JsonView();

    @Override
    protected ModelAndView prepareExceptionInfo(HttpServletRequest request, HttpServletResponse response, HttpStatus httpStatus, String errorCode, String errorMessage, Exception exception) {
        LOGGER.error("client : [{}]", request.getHeader("clientType"));
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            LOGGER.error("request param : [{}]=[{}]", entry.getKey(), StringUtilPlus.join(entry.getValue()));
        }
        if (!(exception instanceof ValidateException)) {
            try {
                String result = IOUtilPlus.toString(request.getInputStream(), "UTF-8");
                LOGGER.error("request body : [{}]", result);
            } catch (IOException e) {
                LOGGER.error("request body parse error", e);
            }
        }

        Map<String, Object> models = new LinkedHashMap<>();
        Map<String, Object> modelsResult = new LinkedHashMap<>();
        modelsResult.put("errorCode", errorCode);
        modelsResult.put("errorMessage", errorMessage);
        models.put("error", modelsResult);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(DEFAULT_JSON_VIEW);
        modelAndView.addAllObjects(models);
        return modelAndView;
    }

}
