package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("health_data")
public class HealthData {
    /** id */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id ;
    private Integer index;
    /** 用户id */
    private Integer userId ;
    private String username ;
    /** 血压 */
    private String bloodPressure ;
    /** 血糖 */
    private String bloodSugar ;
    /** 胆固醇 */
    private String cholesterol ;
    /** 体重指数（BMI） */
    private String bmi ;
    /** 视力 */
    private String vision ;
    /** 听力 */
    private String hearing ;
    /** 骨密度 */
    private String boneMineralDensity ;
    /** 心率 */
    private String heartRate ;
    /** 肺功能 */
    private String pulmonaryFunction ;
    /** 肌肉强度 */
    private String strength ;
    /** 甲状腺激素水平 */
    private String thyroid ;
    /** 尿素氮 */
    private String ureaNitrogen ;
    /** 血红蛋白 */
    private String hemoglobin ;
    /** C-反应蛋白（CRP） */
    private String reactiveProtein ;
    /** 白细胞计数 */
    private String leukocyte ;
    /** 淋巴细胞计数 */
    private String lymphocyte ;
    /** 脑电图 */
    private String electroencephalogram ;
}
