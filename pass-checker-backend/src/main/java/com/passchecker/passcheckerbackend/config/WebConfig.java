package com.passchecker.passcheckerbackend.config;

import com.passchecker.passcheckerbackend.interceptor.LoggingInterceptor;
import com.passchecker.passcheckerbackend.service.HistoryService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    HistoryService historyService;

    public WebConfig(HistoryService historyService) {
        this.historyService = historyService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor(historyService))
                .addPathPatterns("/api/**");
    }
}
