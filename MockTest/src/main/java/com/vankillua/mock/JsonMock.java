package com.vankillua.mock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vankillua.enums.MockNumberEnum;
import com.vankillua.enums.MockStringEnum;
import com.vankillua.utils.CommonUtils;
import com.vankillua.utils.RandomUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @Author KILLUA
 * @Date 2020/7/14 12:40
 * @Description
 */
@Slf4j
public class JsonMock {
    private int mockNumberIndex = 0;
    private int mockStringIndex = 0;
    private int mockArrayIndex = 0;

    private int[] mockArraySize = {0, 1, 10, 100};

    private List<String> keys = new LinkedList<>();

    public JsonMock() {
    }

    public JsonMock(int[] mockArraySize) {
        this.mockArraySize = mockArraySize;
    }

    public int[] getMockArraySize() {
        return mockArraySize;
    }

    public void setMockArraySize(int[] mockArraySize) {
        this.mockArraySize = Arrays.stream(mockArraySize).filter(i -> i >= 0).toArray();
    }

    /**
     * 当接口数据是json的数字的时候，自动mock为对应的等价类，如： -10, -0.5, -1, 0, 0.5, 1, 10
     * 遍历json，mock里面全部数字字段
     * @param json: JSONArray or JSONObject
     */
    public void mockJsonNumber(JSON json) {
        mockJsonNumber(json, Collections.emptyList(), MockNumberEnum.BEGIN.getCode(), new LinkedList<>());
    }

    /**
     * 当接口数据是json的数字的时候，自动mock为对应的等价类，如： -10, -0.5, -1, 0, 0.5, 1, 10
     * 遍历json，找到数字字段且为目标key的字段进行mock
     * @param json: JSONArray or JSONObject
     * @param targetKey: 目标Key
     */
    public void mockJsonNumber(JSON json, String targetKey) {
        targetKey = Optional.ofNullable(targetKey).isPresent() ? targetKey : "";
        mockJsonNumber(json, targetKey.isEmpty() ? Collections.emptyList() : Collections.singletonList(targetKey), MockNumberEnum.BEGIN.getCode(), new LinkedList<>());
    }

    /**
     * 当接口数据是json的数字的时候，自动mock为对应的等价类，如： -10, -0.5, -1, 0, 0.5, 1, 10
     * 遍历json，找到数字字段且为目标key的字段进行mock
     * @param json: JSONArray or JSONObject
     * @param targetKeys: 目标Key列表
     */
    public void mockJsonNumber(JSON json, List<String> targetKeys) {
        mockJsonNumber(json, targetKeys, MockNumberEnum.BEGIN.getCode(), new LinkedList<>());
    }

    /**
     * 当接口数据是json的数字的时候，自动mock为对应的等价类，如： -10, -0.5, -1, 0, 0.5, 1, 10
     * 遍历json，找到数字字段进行对应的mock操作
     * @param json: JSONArray or JSONObject
     * @param mockType: Json数字字段mock类型（负整数，负浮点数，零，正浮点数，正整数）
     */
    public void mockJsonNumber(JSON json, int mockType) {
        mockJsonNumber(json, Collections.emptyList(), mockType, new LinkedList<>());
    }

    /**
     * 当接口数据是json的数字的时候，自动mock为对应的等价类，如： -10, -0.5, -1, 0, 0.5, 1, 10
     * 遍历json，找到数字字段且为目标key的字段进行对应的mock操作
     * @param json: JSONArray or JSONObject
     * @param targetKey: 目标Key
     * @param mockType: Json数字字段mock类型
     */
    public void mockJsonNumber(JSON json, String targetKey, int mockType) {
        targetKey = Optional.ofNullable(targetKey).isPresent() ? targetKey : "";
        mockJsonNumber(json, targetKey.isEmpty() ? Collections.emptyList() : Collections.singletonList(targetKey), mockType, new LinkedList<>());
    }

    /**
     * 当接口数据是json的数字的时候，自动mock为对应的等价类，如： -10, -0.5, -1, 0, 0.5, 1, 10
     * 遍历json，找到数字字段且为目标key的字段进行对应的mock操作
     * @param json: JSONArray or JSONObject
     * @param targetKeys: 目标Key列表
     * @param mockType: Json数字字段mock类型
     */
    public void mockJsonNumber(JSON json, List<String> targetKeys, int mockType) {
        mockJsonNumber(json, targetKeys, mockType, new LinkedList<>());
    }

    /**
     * 当接口数据是json的数字的时候，自动mock为对应的等价类，如： -10, -0.5, -1, 0, 0.5, 1, 10
     * @param json: JSONArray or JSONObject
     * @param targetKeys: 目标Key列表
     * @param mockType: Json数字字段mock类型
     * @param keys: 键值列表，用于日志中显示被mock的Json数字字段
     */
    private void mockJsonNumber(JSON json, List<String> targetKeys, int mockType, List<String> keys) {
        if (null != json) {
            if (json instanceof JSONObject) {
                for (Map.Entry<String, Object> entry : ((JSONObject) json).entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    keys.add(key);
                    if (value instanceof Number) {
                        if (targetKeys.isEmpty() || targetKeys.contains(key)) {
                            Object mockValue = mockNumber(mockType);
                            entry.setValue(mockValue);
                            log.info("键值【{}】对应的数字【{}】会Mock为【{}】", String.join(" -> ", keys), value, mockValue);
                        }
                    } else if (value instanceof JSONArray || value instanceof JSONObject) {
                        mockJsonNumber((JSON) value, targetKeys, mockType, keys);
                    }
                    keys.remove(key);
                }
            } else if (json instanceof JSONArray) {
                for (int i = 0; i < ((JSONArray) json).size(); i++) {
                    JSON object = ((JSONArray) json).getJSONObject(i);
                    String index = String.valueOf(i);
                    keys.add(index);
                    mockJsonNumber(object, targetKeys, mockType, keys);
                    keys.remove(index);
                }
            }
        }
    }

    /**
     * 当接口数据是json的string的时候，自动mock为对应的等价类，如： "", 中文, 英文, 特殊字符, null
     * 遍历json，mock里面全部字符串字段
     * @param json: JSONArray or JSONObject
     */
    public void mockJsonString(JSON json) {
        mockJsonString(json, Collections.emptyList(), MockStringEnum.BEGIN.getCode(), new LinkedList<>());
    }

    /**
     * 当接口数据是json的string的时候，自动mock为对应的等价类，如： "", 中文, 英文, 特殊字符, null
     * 遍历json，找到字符串字段且为目标key的字段进行mock
     * @param json: JSONArray or JSONObject
     * @param targetKey: 目标Key
     */
    public void mockJsonString(JSON json, String targetKey) {
        targetKey = Optional.ofNullable(targetKey).isPresent() ? targetKey : "";
        mockJsonString(json, targetKey.isEmpty() ? Collections.emptyList() : Collections.singletonList(targetKey), MockStringEnum.BEGIN.getCode(), new LinkedList<>());
    }

    /**
     * 当接口数据是json的string的时候，自动mock为对应的等价类，如： "", 中文, 英文, 特殊字符, null
     * 遍历json，找到字符串字段且为目标key的字段进行mock
     * @param json: JSONArray or JSONObject
     * @param targetKeys: 目标Key列表
     */
    public void mockJsonString(JSON json, List<String> targetKeys) {
        mockJsonString(json, targetKeys, MockStringEnum.BEGIN.getCode(), new LinkedList<>());
    }

    /**
     * 当接口数据是json的string的时候，自动mock为对应的等价类，如： "", 中文, 英文, 特殊字符, null
     * 遍历json，找到字符串字段进行对应的mock操作
     * @param json: JSONArray or JSONObject
     * @param mockType: Json字符串字段mock类型（空字符串，中文字符串，英文字符串，特殊字符字符串，null）
     */
    public void mockJsonString(JSON json, int mockType) {
        mockJsonString(json, Collections.emptyList(), mockType, new LinkedList<>());
    }

    /**
     * 当接口数据是json的string的时候，自动mock为对应的等价类，如： "", 中文, 英文, 特殊字符, null
     * 遍历json，找到字符串字段且为目标key的字段进行对应的mock操作
     * @param json: JSONArray or JSONObject
     * @param targetKey: 目标Key
     * @param mockType: Json字符串字段mock类型
     */
    public void mockJsonString(JSON json, String targetKey, int mockType) {
        targetKey = Optional.ofNullable(targetKey).isPresent() ? targetKey : "";
        mockJsonString(json, targetKey.isEmpty() ? Collections.emptyList() : Collections.singletonList(targetKey), mockType, new LinkedList<>());
    }

    /**
     * 当接口数据是json的string的时候，自动mock为对应的等价类，如： "", 中文, 英文, 特殊字符, null
     * 遍历json，找到字符串字段且为目标key的字段进行对应的mock操作
     * @param json: JSONArray or JSONObject
     * @param targetKeys: 目标Key列表
     * @param mockType: Json字符串字段mock类型
     */
    public void mockJsonString(JSON json, List<String> targetKeys, int mockType) {
        mockJsonString(json, targetKeys, mockType, new LinkedList<>());
    }

    /**
     * 当接口数据是json的string的时候，自动mock为对应的等价类，如： "", 中文, 英文, 特殊字符, null
     * @param json: JSONArray or JSONObject
     * @param targetKeys: 目标Key列表
     * @param mockType: Json数字字段mock类型
     * @param keys: 键值列表，用于日志中显示被mock的Json字符串字段
     */
    private void mockJsonString(JSON json, List<String> targetKeys, int mockType, List<String> keys) {
        if (null != json) {
            if (json instanceof JSONObject) {
                for (Map.Entry<String, Object> entry : ((JSONObject) json).entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    keys.add(key);
                    if (value instanceof String) {
                        if (targetKeys.isEmpty() || targetKeys.contains(key)) {
                            Object mockValue = mockString(mockType);
                            entry.setValue(mockValue);
                            log.info("键值【{}】对应的字符串【{}】会Mock为【{}】", String.join(" -> ", keys), value, mockValue);
                        }
                    } else if (value instanceof JSONArray || value instanceof JSONObject) {
                        mockJsonString((JSON) value, targetKeys, mockType, keys);
                    }
                    keys.remove(key);
                }
            } else if (json instanceof JSONArray) {
                for (int i = 0; i < ((JSONArray) json).size(); i++) {
                    JSON object = ((JSONArray) json).getJSONObject(i);
                    String index = String.valueOf(i);
                    keys.add(index);
                    mockJsonString(object, targetKeys, mockType, keys);
                    keys.remove(index);
                }
            }
        }
    }

    /**
     * 当接口数据是json的array的时候，自动mock为对应的等价类： 0, 1, 10, 100
     * @param json: JSONArray or JSONObject
     * @param targetLevel: 需要进行mock的Json数组的层级，如：
     *                   {"data": {"items": [{"fields": [{}, {}, {}]}, {}, {}]}}
     *                   对 data -> items 数组进行mock，targetLevel为 1
     *                   对 data -> items -> x -> fields 数组进行mock，targetLevel为 2
     *                   如此类推
     */
    public void mockJsonArray(JSON json, int targetLevel) {
        mockJsonArray(json, targetLevel, 0, -1, new LinkedList<>());
    }

    /**
     * 当接口数据是json的array的时候，自动mock为对应的等价类，如： 0, 1, 10, 100
     * @param json: JSONArray or JSONObject
     * @param targetLevel: 需要进行mock的Json数组的层级
     * @param mockType: mock修改Json数组元素个数，对应 MOCK_ARRAY_SIZE 的下标
     */
    public void mockJsonArray(JSON json, int targetLevel, int mockType) {
        mockJsonArray(json, targetLevel, 0, mockType, new LinkedList<>());
    }

    /**
     * 当接口数据是json的array的时候，自动mock为对应的等价类，如： 0, 1, 10, 100
     * @param json: JSONArray or JSONObject
     * @param targetLevel: 需要进行mock的Json数组的层级
     * @param currentLevel: 当前递归中的Json数组的层级，即递归过程中遇到了多少层JSONArray
     * @param mockType: mock修改Json数组元素个数，对应 MOCK_ARRAY_SIZE 的下标
     * @param keys: 键值列表，用于日志中显示被mock的Json数组字段
     */
    private void mockJsonArray(JSON json, int targetLevel, int currentLevel, int mockType, List<String> keys) {
        if (null != json) {
            if (json instanceof JSONObject) {
                for (Map.Entry<String, Object> entry : ((JSONObject) json).entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    keys.add(key);
                    if (value instanceof JSONArray || value instanceof JSONObject) {
                        mockJsonArray((JSON) value, targetLevel, currentLevel, mockType, keys);
                    }
                    keys.remove(key);
                }
            } else if (json instanceof JSONArray) {
                if (targetLevel == ++currentLevel) {
                    int beforeSize = ((JSONArray) json).size();
                    mockJsonArray((JSONArray) json, mockType);
                    log.info("键值【{}】对应的Json列表的大小【{}】会Mock为【{}】", String.join(" -> ", keys), beforeSize, ((JSONArray) json).size());
                } else {
                    for (int i = 0; i < ((JSONArray) json).size(); i++) {
                        JSON object = ((JSONArray) json).getJSONObject(i);
                        String index = String.valueOf(i);
                        keys.add(index);
                        mockJsonArray(object, targetLevel, currentLevel, mockType, keys);
                        keys.remove(index);
                    }
                }
            }
        }
    }

    /**
     * 当接口数据是json的对象的时候，自动mock为对应的异常数据为 null
     * @param json: JSONArray or JSONObject
     * @param targetLevel: 需要进行mock的Json对象的层级
     */
    public void mockJsonObject(JSON json, int targetLevel) {
        mockJsonObject(json, targetLevel, 0, new LinkedList<>());
    }

    private JSON mockJsonObject(JSON json, int targetLevel, int currentLevel, List<String> keys) {
        if (null != json) {
            if (json instanceof JSONObject) {
                if (targetLevel == ++currentLevel) {
                    json = null;
                    log.info("键值【{}】对应的Json对象会Mock为【null】", String.join(" -> ", keys));
                } else {
                    for (Map.Entry<String, Object> entry : ((JSONObject) json).entrySet()) {
                        String key = entry.getKey();
                        Object value = entry.getValue();
                        keys.add(key);
                        if (value instanceof JSONArray || value instanceof JSONObject) {
                            entry.setValue(mockJsonObject((JSON) value, targetLevel, currentLevel, keys));
                        }
                        keys.remove(key);
                    }
                }
            } else if (json instanceof JSONArray) {
                for (int i = 0; i < ((JSONArray) json).size(); i ++) {
                    JSON object = ((JSONArray) json).getJSONObject(i);
                    String index = String.valueOf(i);
                    keys.add(index);
                    ((JSONArray) json).set(i, mockJsonObject(object, targetLevel, currentLevel, keys));
                    keys.remove(index);
                }
            }
        }
        return json;
    }

    /**
     * 当接口数据是json的对象的时候，自动mock为对应的异常数据为 null
     * @param json: JSONArray or JSONObject
     * @param targetKey: 需要进行mock的JSON对象的键值
     */
    public void mockJsonObject(JSON json, String targetKey) {
        mockJsonObject(json, targetKey, false, new LinkedList<>());
    }

    private JSON mockJsonObject(JSON json, String targetKey, boolean isFound, List<String> keys) {
        if (null != json) {
            if (json instanceof JSONObject) {
                if (isFound) {
                    json = null;
                    log.info("键值【{}】对应的Json对象会Mock为【null】", String.join(" -> ", keys));
                } else {
                    for (Map.Entry<String, Object> entry : ((JSONObject) json).entrySet()) {
                        String key = entry.getKey();
                        Object value = entry.getValue();
                        keys.add(key);
                        if (value instanceof JSONArray || value instanceof JSONObject) {
                            entry.setValue(mockJsonObject((JSON) value, targetKey, targetKey.equals(key), keys));
                        }
                        keys.remove(key);
                    }
                }

            } else if (json instanceof JSONArray) {
                for (int i = 0; i < ((JSONArray) json).size(); i ++) {
                    JSON object = ((JSONArray) json).getJSONObject(i);
                    String index = String.valueOf(i);
                    keys.add(index);
                    ((JSONArray) json).set(i, mockJsonObject(object, targetKey, isFound, keys));
                    keys.remove(index);
                }
            }
        }
        return json;
    }

    private Number mockNumber(int mockType) {
        int mod = (mockType <= MockNumberEnum.BEGIN.getCode() || mockType >= MockNumberEnum.END.getCode())
                ? mockNumberIndex++ % MockNumberEnum.END.getCode() : mockType % MockNumberEnum.END.getCode();
        if (MockNumberEnum.NEGATIVE_INTEGER.getCode() == mod) {
            return RandomUtils.randomNegativeInteger(2);
        } else if (MockNumberEnum.NEGATIVE_FLOAT.getCode() == mod) {
            return RandomUtils.randomFloat(-10, -1);
        } else if (MockNumberEnum.ZERO.getCode() == mod) {
            return 0;
        } else if (MockNumberEnum.POSITIVE_FLOAT.getCode() == mod) {
            return RandomUtils.randomFloat(1, 10);
        } else {
            return RandomUtils.randomPositiveInteger(2);
        }
    }

    private String mockString(int mockType) {
        int mod = (mockType <= MockStringEnum.BEGIN.getCode() || mockType >= MockStringEnum.END.getCode())
                ? mockStringIndex++ % MockStringEnum.END.getCode() : mockType % MockStringEnum.END.getCode();
        if (MockStringEnum.EMPTY_STRING.getCode() == mod) {
            return "";
        } else if (MockStringEnum.CHINESE_STRING.getCode() == mod) {
            return RandomUtils.randomSimplifiedChineseString(5);
        } else if (MockStringEnum.ENGLISH_STRING.getCode() == mod) {
            return RandomUtils.randomEnglishString(6);
        } else if (MockStringEnum.SPECIAL_STRING.getCode() == mod) {
            return RandomUtils.randomSpecialString(7);
        } else {
            return null;
        }
    }

    private void mockJsonArray(JSONArray array, int mockType) {
        int mod = (0 > mockType) ? mockArrayIndex++ % mockArraySize.length : mockType % mockArraySize.length;
        int arraySize = array.size();
        if (0 >= arraySize) {
            mod = 0;
        }
        int currentSize = arraySize;
        if (arraySize > mockArraySize[mod]) {
            while (currentSize > mockArraySize[mod]) {
                array.remove(--currentSize);
            }
        } else if (arraySize < mockArraySize[mod]) {
            currentSize = 0;
            while (currentSize < mockArraySize[mod]) {
                int copySize = Math.min(mockArraySize[mod] - currentSize, arraySize);
                Collections.addAll(array, Objects.requireNonNull(CommonUtils.deepClone(Arrays.copyOf(array.toArray(), copySize))));
                currentSize = array.size();
            }
        }
    }
}
