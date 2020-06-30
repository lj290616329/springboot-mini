package com.tsingtec.mini.entity.mini;

import com.google.common.collect.Lists;
import com.tsingtec.mini.entity.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author lj
 * @Date 2020/6/19 15:18
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "mini_question")
public class Question extends BaseEntity {
    private Integer groupSort;//父级排序
    private String groupName;//父级名称..
    private String title;//题目 (性别)..
    private String showTitle;//显示题目(您的性别?)..
    private String type;//类型 暂时支持 radio checkbox input picker textarea 这5类..
    private String inputType="text";//如果是输入框 默认类型text number idcard digit
    private String pickerMode="selector";//selector,time,date,region
    private Boolean required=false;//是否必填
    private String options;//选项 逗号分隔
    private Boolean ifNeed=false;//是否关联
    private Boolean basic=true;//基本测试信息
    private String need;//关联的内容
    private Integer sort;//排序

    @Transient
    private List<String> optionList;

    public List<String> getOptionList(){
        List<String> list = new ArrayList();
        if(StringUtils.isNotBlank(options)){
            String[] option = options.split(",");
            list = Arrays.asList(option);
        }
        return list;
    };

    @Transient
    private List<String> needs;

    public List<String> getNeeds(){
        String[] option = {"",""};
        List<String> list = Lists.newArrayList(option);
        if(StringUtils.isNotBlank(need)){
            option = need.split(",");
            list = Arrays.asList(option);
        }
        return list;
    };
}
