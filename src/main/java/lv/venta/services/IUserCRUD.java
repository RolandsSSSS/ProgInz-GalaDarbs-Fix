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
	private final SystemLogger systemLogger;
	
	@Autowired
	private IUserRepo userRepo;
	

	@Autowired
	public IUserCRUD(IUserRepo userRepo, SystemLogger systemLogger) {
		this.userRepo = userRepo;
		this.systemLogger = systemLogger;
	}
	
	
	@Override
	public List<User> selectAllUsers() {
		systemLogger.logInfo("Atlasīti visi lietotāji.");
		return (List<User>) userRepo.findAll();
	}

	@Override
	public User selectUserById(long idu) {
		for (User user : selectAllUsers()) {
			if (user.getIdu() == idu) {
				systemLogger.logInfo("Atlasīts lietotājs ar ID: " + idu);
				return user;
			}
		}
		systemLogger.logWarning("Lietotājs ar ID " + idu + " netika atrasts.");
		return null;
	}

	@Override
	public void deleteUserById(long idc) {
		User user = selectUserById(idc);
		if (user != null) {
			systemLogger.logInfo("Izdzēsts lietotājs ar ID: " + idc);
			
			userRepo.delete(user);
		} else {
	        systemLogger.logWarning("Mēģināts izdzēst neesošu lietotāju ar ID " + idc);
		}
	}
		
	

	@Override
	public void updateUserById(long idu, User updatedUser) {
		User user = selectUserById(idu);
		if (user != null) {
			user.setEmail(updatedUser.getEmail());
			user.setPassword(updatedUser.getPassword());
			userRepo.save(user);
			
			systemLogger.logInfo("Lietotājs atjaunināts ar ID: " + idu);
		} else {
	        systemLogger.logWarning("Mēģināts atjaunināt neesošu lietotāju ar ID " + idu);
		}
	}
	

	@Override
	public void insertNewUser(User user) {
		for(User User1 : selectAllUsers()) {
			if(User1.getEmail().equals(user.getEmail()) && User1.getPassword() == (user.getPassword())) {
				return;
			}
		}
		selectAllUsers().add(user);
		userRepo.save(user);
		
		systemLogger.logInfo("Ievietots jauns lietotājs: " + user.getEmail());
	}
		
	}


