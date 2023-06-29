package lv.venta.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.Course;
import lv.venta.repos.ICourseRepo;

@Service
public class CourseCRUDService implements ICourseCRUDService {

	@Autowired
	private ICourseRepo courseRepo;
	
	@Autowired
	public CourseCRUDService(ICourseRepo courseRepo) {
		this.courseRepo = courseRepo;
	}

	@Override
	public List<Course> selectAllCourses() {
		return (List<Course>)courseRepo.findAll();
	}
	
	
}
