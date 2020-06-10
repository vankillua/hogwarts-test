package com.vankillua.common;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @Author KILLUA
 * @Date 2020/6/7 13:16
 * @Description
 */
public class CommonPropertyResourceFactory implements PropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) throws IOException {
        String resourceName = Optional.ofNullable(name).orElse(resource.getResource().getFilename());
        YamlPropertySourceLoader loader = new YamlPropertySourceLoader();

        if (Arrays.stream(loader.getFileExtensions()).anyMatch(Objects.requireNonNull(resourceName)::endsWith)) {
            return loader.load(resourceName, resource.getResource()).get(0);
        } else {
            return new DefaultPropertySourceFactory().createPropertySource(name, resource);
        }
    }
}
