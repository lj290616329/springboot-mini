package com.tsingtec.mini.service.impl;

import com.tsingtec.mini.entity.sys.Menu;
import com.tsingtec.mini.entity.sys.Role;
import com.tsingtec.mini.exception.BusinessException;
import com.tsingtec.mini.exception.code.BaseExceptionType;
import com.tsingtec.mini.repository.RoleRepository;
import com.tsingtec.mini.service.MenuService;
import com.tsingtec.mini.service.RoleService;
import com.tsingtec.mini.vo.req.sys.role.RoleAddReqVO;
import com.tsingtec.mini.vo.req.sys.role.RolePageReqVO;
import com.tsingtec.mini.vo.req.sys.role.RoleUpdateReqVO;
import com.tsingtec.mini.vo.resp.sys.menu.MenuRespNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private MenuService menuService;

	@Override
	@Transactional
	public Role addRole(RoleAddReqVO vo) {
		Role role = new Role();
		role.setDescription(vo.getDescription());
		role.setName(vo.getName());
		role.setStatus(vo.getStatus());
		role.setCreateTime(new Date());
		role.setUpdateTime(new Date());
		return roleRepository.save(role);
	}

	@Override
	@Transactional
	public void updateRole(RoleUpdateReqVO vo) {
		Role role = roleRepository.getOne(vo.getId());
		if (null==role){
			log.error("传入 的 id:{}不合法",vo.getId());
			throw new BusinessException(BaseExceptionType.USER_ERROR,"id不合法");
		}

		BeanUtils.copyProperties(vo,role);
		role.setUpdateTime(new Date());

		List<Menu> menus = menuService.findAllById(vo.getMids());
		role.setMenus(new HashSet<>(menus));
		roleRepository.save(role);
	}

	@Override
	@Transactional
	public Role findById(Integer id) {
		Role role = roleRepository.findById(id).get();
		if(null==role){
			return null;
		}
		Set<Menu> menus = role.getMenus();
		Set<Integer> checkList = menus.stream().map(menu -> menu.getId()).collect(Collectors.toSet());
		List<MenuRespNode> menuRespNodes = menuService.selectAllByTree();
		setheckced(menuRespNodes,checkList);
		role.setChilids(menuRespNodes);
		return roleRepository.findById(id).get();
	}

	private void setheckced(List<MenuRespNode> menuRespNodes, Set<Integer> checkList){
		for(MenuRespNode node:menuRespNodes){
			if(checkList.contains(node.getId()) && (node.getChildren()==null || node.getChildren().isEmpty())){
				node.setChecked(true);
			}
			setheckced((List<MenuRespNode>) node.getChildren(),checkList);
		}
	}


	@Override
	public Page<Role> pageInfo(RolePageReqVO vo) {
		Pageable pageable = PageRequest.of(vo.getPageNum()-1, vo.getPageSize(), Sort.Direction.DESC,"id");
		return roleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<Predicate>();

			if (!StringUtils.isEmpty(vo.getRoleName())){
				predicates.add(criteriaBuilder.like(root.get("name"),"%"+vo.getRoleName()+"%"));
			}

			if (null != vo.getStartTime()){
				predicates.add(criteriaBuilder.greaterThan(root.get("createTime"),vo.getStartTime()));
			}

			if (null != vo.getEndTime()){
				predicates.add(criteriaBuilder.lessThan(root.get("createTime"),vo.getEndTime()));
			}
			return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
		},pageable);
	}

	@Override
	@Transactional
	public void deleteBatch(List<Integer> rids) {

		roleRepository.deleteBatch(rids);

		roleRepository.cancelMenuJoin(rids);

		roleRepository.cancelMenuJoin(rids);
	}

}
