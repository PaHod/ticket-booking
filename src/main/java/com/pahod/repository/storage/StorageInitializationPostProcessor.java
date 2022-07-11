package com.pahod.repository.storage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;


public class StorageInitializationPostProcessor implements BeanPostProcessor {

    private JsonNode jsonTree;
    private ObjectMapper jsonMapper;

    @Value("${storage.initial.data.file}")
    private String filePath;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        checkAndParseData();

        Field[] declaredFields = bean.getClass().getDeclaredFields();

        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(AutoInitWithDemoData.class)) {
                try {
                    fillTheMap(bean, field);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return bean;
    }

    // TODO: is this the best approach?

    /**
     * as the @Value annotation is processed be BeanPostProcessor filePath is not available on constructor phase.
     */
    private void checkAndParseData() {
        if (jsonTree == null) {
            try {
                String json = Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
                jsonMapper = new ObjectMapper();
                jsonTree = jsonMapper.readTree(json);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void fillTheMap(Object bean, Field field) throws NoSuchFieldException, ClassNotFoundException, JsonProcessingException, IllegalAccessException {
        field.setAccessible(true);
        String fieldName = field.getName();
        JsonNode tableNode = jsonTree.get(fieldName);
        JsonNode entityList = tableNode.get("entityList");

        Class<?> entityClass = Class.forName(tableNode.get("entityClassName").textValue());

        int maxId = 0;
        for (JsonNode entityNode : entityList) {
            Object entity = jsonMapper.treeToValue(entityNode, entityClass);
            int id = entityNode.get("id").intValue();
            ((Map<Object, Object>) field.get(bean)).put(id, entity);
            maxId = Math.max(maxId, id);
        }

        // TODO: think how update this values better
        Field declaredField = bean.getClass().getDeclaredField("lastIdForClass");
        Map<Class<?>, Integer> lastIdForClass = (Map<Class<?>, Integer>) declaredField.get(bean);
        lastIdForClass.put(entityClass, maxId);
    }
}
