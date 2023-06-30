package lv.venta.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.Course;
import lv.venta.models.users.Student;
import lv.venta.repos.ICourseRepo;
import lv.venta.services.impl.ICourseCRUDService;

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
		return (List<Course>) courseRepo.findAll();
	}

	@Override
	public Course selectCourseById(long idc) {
		for (Course course : selectAllCourses()) {
			if (course.getIdc() == idc) {
				return course;
			}
		}
		return null;
	}

	@Override
	public void deleteCourseById(long idc) {
		Course course = selectCourseById(idc);
		if (course != null) {
			for (Student student : course.getDebtStudents()) {
				student.getDebtCourses().remove(course);
			}
			course.getDebtStudents().clear();
			courseRepo.delete(course);
		}
	}

	@Override
	public void updateCourseById(long idc, Course updatedCourse) {
		Course course = selectCourseById(idc);
		if (course != null) {
			course.setTitle(updatedCourse.getTitle());
			course.setCreditPoints(updatedCourse.getCreditPoints());
			courseRepo.save(course);
		}
	}
	
	@Override
	public void insertNewCourse(Course course) {
		for(Course course1 : selectAllCourses()) {
			if(course1.getTitle().equals(course.getTitle()) && course1.getCreditPoints() == (course.getCreditPoints())) {
				return;
			}
		}
		selectAllCourses().add(course);
		courseRepo.save(course);
	}

}
