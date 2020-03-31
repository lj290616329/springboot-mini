package com.tsingtec.mini.service.impl;

import com.tsingtec.mini.entity.sys.Admin;
import com.tsingtec.mini.entity.sys.Menu;
import com.tsingtec.mini.entity.sys.Role;
import com.tsingtec.mini.exception.BusinessException;
import com.tsingtec.mini.exception.code.BaseExceptionType;
import com.tsingtec.mini.repository.AdminRepository;
import com.tsingtec.mini.repository.MenuRepository;
import com.tsingtec.mini.service.MenuService;
import com.tsingtec.mini.vo.req.sys.menu.MenuAddReqVO;
import com.tsingtec.mini.vo.req.sys.menu.MenuUpdateReqVO;
import com.tsingtec.mini.vo.resp.sys.menu.MenuRespNode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Override
	public Menu findById(Integer id) {
		return menuRepository.findById(id).orElse(null);
	}

	@Override
	public void insert(MenuAddReqVO vo) {
		Menu menu=new Menu();
		BeanUtils.copyProperties(vo,menu);
		menu.setCreateTime(new Date());
		menu.setUpdateTime(new Date());
		menuRepository.save(menu);
	}

	@Override
	public void deleteById(Integer id) {
		Menu menu = menuRepository.getOne(id);
		/**
		 * step1
		 * 判断是否有下级元素
		 */
		List<Menu> childs = menuRepository.findByPid(id);
		if(!childs.isEmpty()){
			throw new BusinessException(BaseExceptionType.USER_ERROR,"该菜单权限存在子集关联，不允许删除");
		}
		/**
		 * step2
		 * 删除role_menu
		 */
		menuRepository.cancelMenuJoin(id);
		/**
		 * step3
		 * 删除该menu
		 */
		menuRepository.deleteById(id);
	}

	@Override
	public void update(MenuUpdateReqVO vo) {
		Menu menu = findById(vo.getId());
		if(null == menu){
			throw new BusinessException(BaseExceptionType.USER_ERROR,"修改对象不存在!");
		}
		/**
		 * 当类型变更或者是父级变更时进行验证
		 */
		if(menu.getType()!=vo.getType()||!menu.getPid().equals(vo.getPid())){
			verify(vo);
		}
		BeanUtils.copyProperties(vo,menu);
		menu.setUpdateTime(new Date());
		menuRepository.save(menu);
	}


	/**
	 * 操作后的菜单类型是目录的时候 父级必须为目录
	 * 操作后的菜单类型是菜单的时候 父类必须为目录类型
	 * 操作后的菜单类型是按钮的时候 父类必须为菜单类型
	 * 操作的不能有
	 */
	private void verify(MenuUpdateReqVO vo){
		Menu parent = findById(vo.getPid());
		switch (vo.getType()){
			case 1:
				if(parent!=null){
					if(parent.getType()!=1){
						throw new BusinessException(BaseExceptionType.USER_ERROR,"父类必须为目录类型");
					}
				}else if(!vo.getPid().equals(0)){
					throw new BusinessException(BaseExceptionType.USER_ERROR,"父类必须为目录类型");
				}
				break;
			case 2:
				if(parent==null||parent.getType()!=1){
					throw new BusinessException(BaseExceptionType.USER_ERROR,"父类必须为目录类型");
				}
				if(StringUtils.isEmpty(vo.getUrl())){
					throw new BusinessException(BaseExceptionType.USER_ERROR,"请添加地址URL");
				}
				break;
			case 3:
				if(parent==null || parent.getType()!=2){
					throw new BusinessException(BaseExceptionType.USER_ERROR,"父类必须为菜单类型");
				}
				if(StringUtils.isEmpty(vo.getPerms())){
					throw new BusinessException(BaseExceptionType.USER_ERROR,"请添加授权码");
				}
				if(StringUtils.isEmpty(vo.getUrl())){
					throw new BusinessException(BaseExceptionType.USER_ERROR,"请添加地址URL");
				}
				break;
		}
		/**
		 * id 不为空表明为修改
		 */
		if(!StringUtils.isEmpty(vo.getId())) {
			List<Menu> list = menuRepository.findByPid(vo.getId());
			if (!list.isEmpty()) {
				throw new BusinessException(BaseExceptionType.USER_ERROR,"子级菜单不为空,请重新修改");
			}
		}

	}

	@Override
	public List<Menu> findAll() {
		List<Menu> menus = menuRepository.findAll();
		for(Menu menu:menus){
			Menu parent = findById(menu.getPid());
			if(null!=parent){
				menu.setPidName(parent.getName());
			}
		}
		return menus;
	}

	private List<Menu> getMenu(Integer aid) {
		Admin admin = adminRepository.getOne(aid);
		Set<Role> roles = admin.getRoles();
		List<Menu> menus = new ArrayList<>();
		for(Role role:roles){
			menus.addAll(role.getMenus());
		}
		System.out.println(menus.size());
		return menus;
	}

	@Override
	public List<MenuRespNode> menuTreeList(Integer aid) {
		List<Menu> list = getMenu(aid);
		return getTree(list,true);
	}

	private List<MenuRespNode> getTree(List<Menu> all, boolean type){

		List<MenuRespNode> list=new ArrayList<>();
		if (all==null||all.isEmpty()){
			return list;
		}
		for(Menu menu:all){
			if(menu.getPid().equals(0)){
				MenuRespNode menuRespNode=new MenuRespNode();
				BeanUtils.copyProperties(menu,menuRespNode);
				menuRespNode.setTitle(menu.getName());

				if(type){
					menuRespNode.setChildren(getChildExcBtn(menu.getId(),all));
				}else {
					menuRespNode.setChildren(getChildAll(menu.getId(),all));
				}
				list.add(menuRespNode);
			}
		}
		return list;
	}

	private List<MenuRespNode>getChildAll(Integer id,List<Menu> all){

		List<MenuRespNode> list=new ArrayList<>();
		for(Menu menu:all){
			if(menu.getPid().equals(id)){
				MenuRespNode menuRespNode=new MenuRespNode();
				BeanUtils.copyProperties(menu,menuRespNode);
				menuRespNode.setTitle(menu.getName());
				menuRespNode.setChildren(getChildAll(menu.getId(),all));
				list.add(menuRespNode);
			}
		}
		return list;
	}

	private List<MenuRespNode> getChildExcBtn(Integer id,List<Menu> all){

		List<MenuRespNode> list = new ArrayList<>();
		for(Menu menu:all){
			if(menu.getPid().equals(id)&&menu.getType()!=3){
				MenuRespNode menuRespNode=new MenuRespNode();
				BeanUtils.copyProperties(menu,menuRespNode);
				menuRespNode.setTitle(menu.getName());
				menuRespNode.setChildren(getChildExcBtn(menu.getId(),all));
				list.add(menuRespNode);
			}
		}
		return list;
	}

	@Override
	public List<MenuRespNode> selectAllByTree() {
		List<Menu> list = findAll();
		return getTree(list,false);
	}

	@Override
	public List<Menu> findAllById(List<Integer> mids) {
		return menuRepository.findAllById(mids);
	}

	@Override
	public List<MenuRespNode> selectAllMenuByTree(Integer menuId) {

		List<Menu> list = findAll();
		if(!list.isEmpty() && !StringUtils.isEmpty(menuId)){
			for (Menu menu:list){
				if (menu.getId().equals(menuId)){
					list.remove(menu);
					break;
				}
			}
		}
		List<MenuRespNode> result=new ArrayList<>();
		//新增顶级目录是为了方便添加一级目录
		MenuRespNode respNode = new MenuRespNode();
		respNode.setId(0);
		respNode.setTitle("默认顶级菜单");
		respNode.setSpread(true);
		respNode.setChildren(getTree(list,true));
		result.add(respNode);
		return result;
	}
}
