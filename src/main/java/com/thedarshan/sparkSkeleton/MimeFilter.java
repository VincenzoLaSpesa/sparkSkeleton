package com.thedarshan.sparkSkeleton;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.servlet.SparkFilter;

/**
 *
 * @author Darshan
 * 08/04/2017
 * Based on https://github.com/perwendel/spark/issues/373
 */
public class MimeFilter extends SparkFilter implements Filter
{
    public static final Map<String, String> mimeMapping;
    
    static{
        mimeMapping = new HashMap<>();
        mimeMapping.put("css","text/css");
        mimeMapping.put("js","text/javascript");
        mimeMapping.put("json","text/javascript");
    }
    
    public static String getMime(String requestPath){
        if(requestPath.length()<2) return "text/html";
        System.out.printf("%s is %s \n" , requestPath, mimeMapping.getOrDefault(Helper.getExtension(requestPath), "text/html"));
        return mimeMapping.getOrDefault(Helper.getExtension(requestPath), "text/html");
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        String requestUrl = ((HttpServletRequest) request).getRequestURI();
        ((HttpServletResponse) response).setHeader("Content-Type",getMime(requestUrl));
        super.doFilter(request, response, chain);
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        String requestUrl = request.uri();
        response.header("Content-Type", getMime(requestUrl));
    }
}
