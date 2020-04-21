package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author fengxuechao
 * @date 2020/4/21
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class MyErrorController extends AbstractErrorController {

    @Autowired
    private ServerProperties serverProperties;

    public MyErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @Autowired
    public MyErrorController(ErrorAttributes errorAttributes, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorViewResolvers);
    }

    @Override
    public String getErrorPath() {
        return this.serverProperties.getError().getPath();
    }

    @RequestMapping(produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        Map<String, Object> model = Collections
                .unmodifiableMap(getErrorAttributes(request, isIncludeStackTrace(request, MediaType.TEXT_HTML)));
        response.setStatus(status.value());
        ModelAndView modelAndView = resolveErrorView(request, response, status, model);
        return (modelAndView != null) ? modelAndView : new ModelAndView("error", model);
    }

    @RequestMapping
    @ResponseBody
    public ResultBean<Object> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);
        return ResultBean.error(status.value(), String.valueOf(body.get("message")));
//        return new ResponseEntity<Map<String, Object>>(body, status);
    }

    /**
     * Determine if the stacktrace attribute should be included.
     *
     * @param request  the source request
     * @param produces the media type produced (or {@code MediaType.ALL})
     * @return if the stacktrace attribute should be included
     */
    protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
        ErrorProperties.IncludeStacktrace include = getErrorProperties().getIncludeStacktrace();
        if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
            return true;
        }
        if (include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM) {
            return getTraceParameter(request);
        }
        return false;
    }

    /**
     * Provide access to the error properties.
     *
     * @return the error properties
     */
    protected ErrorProperties getErrorProperties() {
        return this.serverProperties.getError();
    }


}
