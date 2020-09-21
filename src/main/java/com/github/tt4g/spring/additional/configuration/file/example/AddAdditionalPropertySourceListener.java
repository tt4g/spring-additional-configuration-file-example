package com.github.tt4g.spring.additional.configuration.file.example;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.ResourcePropertySource;

/**
 * Add custom configuration file <code>spring-additional-configuration-file-example.yml</code>
 *
 * Can specify config file path command line argument <code>--additional.config.path</code>
 * or JVM argument <code>-Dadditional.config.path</code>.
 * Example for command line argument: <code>java ... --additional.config.path=${PWD}/spring-additional-configuration-file-example.yml</code>
 * Example for JVM argument: <code>java -Dadditional.config.path=${PWD}/spring-additional-configuration-file-example.yml ... </code>
 */
public class AddAdditionalPropertySourceListener
    implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddAdditionalPropertySourceListener.class);

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment configurableEnvironment = event.getEnvironment();

        Optional<Path> additionalPropertyPathOptional =
            Optional.ofNullable(configurableEnvironment.getProperty("additional.config.path"))
                .map(Paths::get);
        PropertiesPropertySource additionalPropertySource =
            loadAdditionalPropertySource(additionalPropertyPathOptional);

        MutablePropertySources mutablePropertySources = configurableEnvironment.getPropertySources();
        // NOTE: It is added to the properties that Spring Boot can refer to in
        //  the order of priority after OS environment variables.
        //  See official document: https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/html/spring-boot-features.html#boot-features-external-config
        mutablePropertySources.addAfter(
            StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME,
            additionalPropertySource);
    }

    /**
     * Load additional {@link PropertiesPropertySource}.
     *
     * Load <code>spring-additional-configuration-file-example.yml</code> in
     * working directory, if <code>additionalPropertyPathOptional</code> is empty.
     *
     * @param additionalPropertyPathOptional The path of additional config file.
     * @return
     */
    private PropertiesPropertySource loadAdditionalPropertySource(
        Optional<Path> additionalPropertyPathOptional) {

        Path additionalPropertyPath =
            additionalPropertyPathOptional
                .orElse(Paths.get("spring-additional-configuration-file-example.yml"));

        LOGGER.debug("Load additional property from {}", additionalPropertyPath);

        FileSystemResource additionalPropertyResource = new FileSystemResource(additionalPropertyPath);

        try {
            return new ResourcePropertySource(additionalPropertyResource);
        } catch (IOException ex) {
            LOGGER.warn("Failed to load additional property", ex);

            throw new RuntimeException("Failed to load additional property.", ex);
        }
    }

}
