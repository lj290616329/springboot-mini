package com.tsingtec.mini.service;

import com.tsingtec.mini.entity.sys.Menu;
import com.tsingtec.mini.vo.req.sys.menu.MenuAddReqVO;
import com.tsingtec.mini.vo.req.sys.menu.MenuUpdateReqVO;
import com.tsingtec.mini.vo.resp.sys.menu.MenuRespNode;

import java.util.List;
import java.util.Set;

public interface MenuService {

    Menu findById(Integer id);

    void insert(MenuAddReqVO vo);

    void deleteById(Integer id);

    void update(MenuUpdateReqVO menu);

    List<Menu> findAll();

    List<MenuRespNode> menuTreeList(Integer userId);

    List<MenuRespNode> selectAllMenuByTree(Integer menuId);

    List<MenuRespNode> selectAllByTree();

    List<Menu> findAllById(List<Integer> mids);
}
