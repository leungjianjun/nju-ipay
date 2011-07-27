package com.ipay.server.security;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.ipay.server.dao.IDao;
import com.ipay.server.entity.Authority;
import com.ipay.server.entity.User;

@Service("userDetailsService")
@Transactional(readOnly=true)
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private IDao<User> dao;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		User user = dao.findUniqueBy("from User as user where user.account=?", username);
		if (user == null) {
			throw new UsernameNotFoundException("user " + username + " not exited ");
		}

		//-- miniweb示例中无以下属性, 暂时全部设为true. --//
		//boolean enabled = true;
		//boolean accountNonExpired = true;
		//boolean credentialsNonExpired = true;
		//boolean accountNonLocked = true;
		
		return new org.springframework.security.core.userdetails.User(user.getAccount(), user
				.getPassword(), true, true, true, true, obtainGrantedAuthorities(user));
	}
	
	/**
	 * 获得用户所有角色的权限集合.
	 */
	private Set<GrantedAuthority> obtainGrantedAuthorities(User user) {
		Set<GrantedAuthority> authSet = Sets.newHashSet();
			for (Authority authority : user.getAuthorityList()) {
				authSet.add(new GrantedAuthorityImpl(authority.getPrefixedName()));
			}
		return authSet;
	}

	@Autowired
	public void setDao(IDao<User> dao) {
		this.dao = dao;
	}

	public IDao<User> getDao() {
		return dao;
	}

}
