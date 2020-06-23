package com.tsingtec.mini.vo.req.sort;

import lombok.Data;

/**
 * @Author lj
 * @Date 2020/6/12 15:57
 * @Version 1.0
 */
@Data
public class SortReqVO {
    private Integer id;//id
    private Integer toSort;//新排序
    private Integer oldSort;//老的排序
}
