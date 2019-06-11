package com.myself.framework.custom;

import javassist.*;
import javassist.bytecode.*;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.EnumMemberValue;
import javassist.bytecode.annotation.StringMemberValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名称：ApiBuilder<br>
 * 类描述：<br>
 * 创建时间：2019年05月27日<br>
 *
 * @author maopanpan
 * @version 1.0.0
 */
public class ApiBuilder {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private static final String RETURN_TYPE = "com.myself.framework.custom.JsonResult";
        private ClassPool classPool;
        private CtClass ctClass;
        private ClassFile classFile;
        private ConstPool constPool;
        private Class<?> target;

        public Builder init(Class<?> clazz) {
            this.classPool = ClassPool.getDefault();
            this.classPool.insertClassPath(new ClassClassPath(clazz));
            return this;
        }

        public Builder makeClass(String className) {
            if (StringUtils.isEmpty(className)) {
                throw new RuntimeException("className must not be null！");
            }
            this.ctClass = this.classPool.makeClass(className);
            this.classFile = this.ctClass.getClassFile();
            this.constPool = this.classFile.getConstPool();
            return this;
        }

        public Builder addControllerAnnotation(Map<String, Object> param) {
            AnnotationsAttribute attribute = addAnnotation(RestController.class);
            attribute.addAnnotation(addRequestMapping(param));
            this.classFile.addAttribute(attribute);
            return this;
        }

        public Builder autowiredFiled(Class<?> target) throws NotFoundException, CannotCompileException {
            this.target = target;
            CtClass filed = this.classPool.get(target.getCanonicalName());
            String filedName = target.getSimpleName();

            CtField ctField = new CtField(filed, filedName, this.ctClass);
            ctField.setModifiers(Modifier.PRIVATE);
            FieldInfo fieldInfo = ctField.getFieldInfo();

            AnnotationsAttribute annotationsAttribute = addAnnotation(Autowired.class);
            fieldInfo.addAttribute(annotationsAttribute);
            this.ctClass.addField(ctField);
            return this;
        }

        public Builder addMethod() throws NotFoundException, CannotCompileException {
            Method[] declaredMethods = this.target.getDeclaredMethods();
            for (Method method : declaredMethods) {
                boolean isGetRequest = isGetRequest(method);
                Map<String, Object> annotationValueMap;
                if (!method.isAnnotationPresent(CustomMapping.class)) {
                    annotationValueMap = defaultMakeMethodValue(method, isGetRequest);
                } else {
                    annotationValueMap = makeMethodValue(method);
                }

                CtMethod ctMethod = makeMethodBody(method, this.target.getSimpleName());
                AnnotationsAttribute annotationsAttribute = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
                annotationsAttribute.addAnnotation(addRequestMapping(annotationValueMap));
                MethodInfo methodInfo = ctMethod.getMethodInfo();
                addParameterAnnotation(methodInfo, method, isGetRequest);
                methodInfo.addAttribute(annotationsAttribute);
                this.ctClass.addMethod(ctMethod);
            }

            return this;

        }

        private void addParameterAnnotation(MethodInfo info, Method method, boolean isGetRequest) {
            Parameter[] parameters = method.getParameters();
            Annotation[][] paramAnnotanArr = new Annotation[parameters.length][1];
            for (int i = 0; i < parameters.length; i++) {
                Annotation paramAnno;
                if (isGetRequest) {
                    paramAnno = new Annotation(RequestParam.class.getCanonicalName(), this.constPool);
                    paramAnno.addMemberValue("value", new StringMemberValue(parameters[i].getName(), this.constPool));
                } else {
                    paramAnno = new Annotation(RequestBody.class.getCanonicalName(), this.constPool);
                }
                paramAnnotanArr[i][0] = paramAnno;

            }
            ParameterAnnotationsAttribute paramAnnoTan = new ParameterAnnotationsAttribute(this.constPool, ParameterAnnotationsAttribute.visibleTag);
            paramAnnoTan.setAnnotations(paramAnnotanArr);
            info.addAttribute(paramAnnoTan);
        }

        private CtMethod makeMethodBody(Method method, String filedName) throws NotFoundException, CannotCompileException {
            String methodName = method.getName();
            Class<?>[] parameters = method.getParameterTypes();
            CtClass[] ctClasses = new CtClass[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                ctClasses[i] = classPool.get(parameters[i].getCanonicalName());
            }
            Class<?> returnType = method.getReturnType();
            CtClass response = classPool.get(RETURN_TYPE);
            StringBuilder methodBody = new StringBuilder();
            CtMethod ctMethod = new CtMethod(response, methodName, ctClasses, this.ctClass);
            ctMethod.setModifiers(Modifier.PUBLIC);
            methodBody.append("{return ").append(RETURN_TYPE).append(".ok(").append(filedName).append(".").append(methodName).append("(");
            StringBuilder returnMethod = new StringBuilder();
            for (int i = 1; i <= parameters.length; i++) {
                returnMethod.append("$").append(i).append(",");
            }
            methodBody.append(returnMethod.toString(), 0, returnMethod.length() - 1);
            methodBody.append("));}");
            ctMethod.setBody(methodBody.toString());
            return ctMethod;
        }

        private Map<String, Object> makeMethodValue(Method method) {
            Map<String, Object> param = new HashMap<>(2);
            CustomMapping customMapping = method.getAnnotation(CustomMapping.class);
            param.put("value", customMapping.path());
            param.put("method", customMapping.method());
            return param;
        }

        private Map<String, Object> defaultMakeMethodValue(Method method, boolean isGetRequest) {
            Map<String, Object> param = new HashMap<>(2);
            param.put("value", method.getName());
            param.put("method", isGetRequest ? RequestMethod.GET : RequestMethod.POST);
            return param;
        }

        private boolean isGetRequest(Method method) {
            if (method.isAnnotationPresent(CustomMapping.class)) {
                return method.getAnnotation(CustomMapping.class).method().equals(RequestMethod.GET);
            } else {
                return Arrays.stream(method.getParameterTypes()).allMatch(BasicTypeEnum::containThis);
            }
        }

        private Annotation addRequestMapping(Map<String, Object> param) {
            Annotation annotation = new Annotation(RequestMapping.class.getCanonicalName(), this.constPool);
            if (!StringUtils.isEmpty(param.get("value"))) {
                ArrayMemberValue arrayMemberValue = new ArrayMemberValue(this.constPool);
                arrayMemberValue.setValue(new StringMemberValue[]{new StringMemberValue((String) param.get("value"), this.constPool)});
                annotation.addMemberValue("value", arrayMemberValue);
            }
            if (!StringUtils.isEmpty(param.get("method"))) {
                ArrayMemberValue arrayMemberValue = new ArrayMemberValue(this.constPool);
                EnumMemberValue enumMemberValue = new EnumMemberValue(this.constPool);
                enumMemberValue.setType(RequestMethod.class.getCanonicalName());
                enumMemberValue.setValue(String.valueOf(param.get("method")));
                arrayMemberValue.setValue(new EnumMemberValue[]{enumMemberValue});
                annotation.addMemberValue("method", arrayMemberValue);
            }
            return annotation;
        }

        private AnnotationsAttribute addAnnotation(Class<?> restControllerClass) {
            AnnotationsAttribute annotationsAttribute = new AnnotationsAttribute(this.constPool, AnnotationsAttribute.visibleTag);
            Annotation annotation = new Annotation(restControllerClass.getCanonicalName(), this.constPool);
            annotationsAttribute.addAnnotation(annotation);
            return annotationsAttribute;
        }

        public Class<?> toClass() throws CannotCompileException, IOException {
            try {
                return this.ctClass.toClass();
            } finally {
                // 将该class从ClassPool中删除
                this.ctClass.detach();
            }
        }

    }
}
