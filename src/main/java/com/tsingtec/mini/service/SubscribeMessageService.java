package com.tsingtec.mini.service;

/**
 * @Author lj
 * @Date 2020/6/29 16:57
 * @Version 1.0
 */
public interface SubscribeMessageService {
    void save(Integer uid,String tmplIds);
    void deleteByUid(Integer uid);
    String getTmpIds(Integer uid);
}
