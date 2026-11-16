package com.example.userbridge.infrastructure.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

/**
 * Serves the React app built by {@code npm run build} (in {@code frontend/}) from
 * {@code classpath:/static}, so frontend and API share the backend port.
 *
 * <p>React Router handles URLs such as {@code /login} or {@code /reset-password?token=...}
 * in the browser. When such a URL is opened directly (e-mail link, page refresh),
 * there is no matching file, so {@code index.html} is returned and React takes over.
 * Paths under {@code /api/} are never rewritten - unknown API calls still return 404.
 */
@Configuration
public class SpaWebConfig implements WebMvcConfigurer {

    private static final String STATIC_LOCATION = "classpath:/static/";
    private static final String INDEX = "index.html";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/" + INDEX);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(STATIC_LOCATION)
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requested = location.createRelative(resourcePath);
                        if (requested.exists() && requested.isReadable()) {
                            return requested;
                        }
                        if (resourcePath.startsWith("api/") || hasFileExtension(resourcePath)) {
                            return null; // real 404 for API calls and missing assets
                        }
                        Resource index = location.createRelative(INDEX);
                        return index.exists() && index.isReadable() ? index : null;
                    }
                });
    }

    private static boolean hasFileExtension(String path) {
        int slash = path.lastIndexOf('/');
        return path.substring(slash + 1).contains(".");
    }
}
