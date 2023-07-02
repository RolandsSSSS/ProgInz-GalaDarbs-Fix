package lv.venta.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.users.User;
import lv.venta.repos.users.IUserRepo;
import lv.venta.services.impl.IUserCRUDService;

@Service
public class IUserCRUD implements IUserCRUDService{

	
	@Autowired
	private IUserRepo userRepo;
	

	@Autowired
	public IUserCRUD(IUserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	
	@Override
	public List<User> selectAllUsers() {
		return (List<User>) userRepo.findAll();
	}

	@Override
	public User selectUserById(long idu) {
		for (User user : selectAllUsers()) {
			if (user.getIdu() == idu) {
				return user;
			}
		}
		return null;
	}

	@Override
	public void deleteUserById(long idc) {
		User user = selectUserById(idc);
		if (user != null) {
			
			userRepo.delete(user);
		}
	}
		
	

	@Override
	public void updateUserById(long idu, User updatedUser) {
		User user = selectUserById(idu);
		if (user != null) {
			user.setEmail(updatedUser.getEmail());
			user.setPassword(updatedUser.getPassword());
			userRepo.save(user);
		}
	}
	

	@Override
	public void insertNewUser(User User) {
		for(User User1 : selectAllUsers()) {
			if(User1.getEmail().equals(User.getEmail()) && User1.getPassword() == (User.getPassword())) {
				return;
			}
		}
		selectAllUsers().add(User);
		userRepo.save(User);
	}
		
	}


