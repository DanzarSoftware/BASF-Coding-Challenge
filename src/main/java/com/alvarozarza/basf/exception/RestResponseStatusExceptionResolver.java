package com.alvarozarza.basf.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestResponseStatusExceptionResolver extends AbstractHandlerExceptionResolver {

    static final Logger logger = LoggerFactory.getLogger(RestResponseStatusExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            if (ex instanceof XmlDocumentException) {
                return handleXmlDocumentException((XmlDocumentException) ex, response);
            } else if (ex instanceof UnzipException) {
                return handleUnzipException((UnzipException) ex, response);
            } else if (ex instanceof MongoDbException) {
                return handleMongoDbException((MongoDbException) ex, response);
            }
        } catch (Exception handlerException) {
            logger.warn("Handling of [" + ex.getClass().getName() + "]resulted in Exception", handlerException);
        }
        return null;
    }

    private ModelAndView handleMongoDbException(MongoDbException ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return new ModelAndView();
    }


    private ModelAndView handleUnzipException(UnzipException ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        return new ModelAndView();
    }

    private ModelAndView handleXmlDocumentException(XmlDocumentException ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        return new ModelAndView();
    }
}
