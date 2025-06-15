package com.passchecker.passcheckerbackend.interceptor;

import com.passchecker.passcheckerbackend.model.dto.HistoryDTO;
import com.passchecker.passcheckerbackend.service.HistoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Enumeration;

@Component
public class LoggingInterceptor implements HandlerInterceptor {
    HistoryService historyService;

    public LoggingInterceptor(HistoryService historyService) {
        this.historyService = historyService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Date date = new Date(System.currentTimeMillis());
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        
String hostHeader = request.getHeader("Host");
String[] hostParts = hostHeader != null ? hostHeader.split(":") : new String[0];

Integer portNumber = hostParts.length > 1 
    ? Integer.parseInt(hostParts[1])
    : request.getServerPort();


        HistoryDTO newHistory = new HistoryDTO(
                request.getRemoteAddr(),
                portNumber,
                request.getRequestURI(),
                request.getMethod(),
                request.getQueryString(),
                localDateTime,
                request.getLocalAddr(),
                request.getHeader("User-Agent")
        );

        historyService.saveHistory(newHistory);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("PostHandle: Executed handler for: " + request.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("AfterCompletion: Request completed for: " + request.getRequestURI());
    }
}
