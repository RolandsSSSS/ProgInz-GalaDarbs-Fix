package lv.venta.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.Course;
import lv.venta.repos.ICourseRepo;
import lv.venta.services.impl.ICourseCRUDService;

@Service
public class CourseCRUDService implements ICourseCRUDService{

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

	@Override
	public Course selectCourseById(long idc) {
		for(Course course : selectAllCourses()) {
			if(course.getIdc() == idc) {
				return course;
			}
		}
		return null;
	}
	
}
