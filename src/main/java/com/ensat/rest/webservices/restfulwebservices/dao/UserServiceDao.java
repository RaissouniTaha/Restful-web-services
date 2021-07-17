package com.ensat.rest.webservices.restfulwebservices.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ensat.rest.webservices.restfulwebservices.entity.Post;
import com.ensat.rest.webservices.restfulwebservices.entity.User;

@Component
public class UserServiceDao {

	private static List<User> users = new ArrayList();
	private static int userCount = 3;
	private static int postCount = 0;
	static {
		users.add(new User(1, "Taha", new Date()));
		users.add(new User(2, "Yassmina", new Date()));
		users.add(new User(3, "Omar", new Date()));
		users.add(new User(4, "Mohamed", new Date()));
	}
	
	public List<User> findAll(){
		return users;
	}
	
	public User save(User user) {
		if(user.getId()==null) {
			user.setId(++userCount);
		}
		users.add(user);
		return user;
	}
	
	public User findOne(int id) {
		for(User user : users) {
			if(user.getId()==id) {
				return user;
			}
		}
		return null;
	}
	public User deleteById(int id) {
		Iterator<User> iterator = users.iterator();
		while(iterator.hasNext()) {
			User user = iterator.next();
			if(user.getId()==id) {
				iterator.remove();
				return user;
			}
		}
		return null;
	}
	
}
