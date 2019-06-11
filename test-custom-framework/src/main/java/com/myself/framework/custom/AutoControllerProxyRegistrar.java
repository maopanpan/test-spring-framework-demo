package com.myself.framework.custom;

import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * 类名称：AutoControllerProxyRegistrar<br>
 * 类描述：<br>
 * 创建时间：2019年05月27日<br>
 *
 * @author maopanpan
 * @version 1.0.0
 */
public class AutoControllerProxyRegistrar implements ImportBeanDefinitionRegistrar,
        ResourceLoaderAware, BeanFactoryAware, EnvironmentAware {
    private BeanFactory beanFactory;
    private Environment environment;
    private ResourceLoader resourceLoader;
    private static final String DEFAULT_NAME = "Api";
    private static final String PACKAGE_SPLIT = ".";

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        registerApi(annotationMetadata);
    }

    private void registerApi(AnnotationMetadata annotationMetadata) {
        ClassPathScanningCandidateComponentProvider scan = getScanner();
        scan.setResourceLoader(this.resourceLoader);

        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(CustomController.class);
        scan.addIncludeFilter(annotationTypeFilter);

        String basePackage = ClassUtils.getPackageName(annotationMetadata.getClassName());
        Set<BeanDefinition> definitionSet = scan.findCandidateComponents(basePackage);
        for (BeanDefinition definition : definitionSet) {
            if (definition instanceof AnnotatedBeanDefinition) {
                AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) definition;
                AnnotationMetadata metadata = annotatedBeanDefinition.getMetadata();
                Map<String, Object> attributes = metadata.getAnnotationAttributes(CustomController.class.getCanonicalName());
                registerBean(metadata, attributes);
            }
        }
    }

    private void registerBean(AnnotationMetadata metadata, Map<String, Object> attributes) {
        try {
            Class<?> target = Class.forName(metadata.getClassName());
            String className = target.getSimpleName() + DEFAULT_NAME;
            String path = target.getPackage().getName();
            Class<?> aClass = ApiBuilder.builder()
                    .init(target)
                    .makeClass(path.replaceAll("service", "controller") + PACKAGE_SPLIT + className)
                    .addControllerAnnotation(attributes)
                    .autowiredFiled(target)
                    .addMethod()
                    .toClass();
            AbstractBeanDefinition definition = new RootBeanDefinition(aClass, AbstractBeanDefinition.AUTOWIRE_BY_TYPE, true);
            ((DefaultListableBeanFactory) this.beanFactory).registerBeanDefinition(className, definition);
        } catch (ClassNotFoundException | NotFoundException | CannotCompileException | IOException e) {
            throw new RuntimeException("Initialization of controller failed", e);
        }
    }

    private ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(Boolean.FALSE, this.environment) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = Boolean.FALSE;
                if (beanDefinition.getMetadata().isIndependent()) {
                    if (!beanDefinition.getMetadata().isAnnotation()) {
                        isCandidate = Boolean.TRUE;
                    }
                }
                return isCandidate;
            }
        };
    }
}
