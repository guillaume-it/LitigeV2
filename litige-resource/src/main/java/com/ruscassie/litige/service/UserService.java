package com.ruscassie.litige.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ruscassie.litige.dao.UserDao;
import com.ruscassie.litige.entity.Users;


@Service(value = "userService")
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;

	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Users user = userDao.findByUsername(userId);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	public List<Users> findAll() {
		List<Users> list = new ArrayList<>();
		userDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	public Users findOne(long id) {
		return userDao.findById(id).get();
	}

	public void delete(long id) {
		userDao.deleteById(id);
	}

    public Users save(Users user) {
        return userDao.save(user);
    }
}
