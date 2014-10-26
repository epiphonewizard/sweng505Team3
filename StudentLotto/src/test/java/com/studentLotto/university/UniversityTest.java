package com.studentLotto.university;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import com.studentLotto.university.University;
import com.studentLotto.university.UniversityRepository;

@RunWith(MockitoJUnitRunner.class)
public class UniversityTest {

	@Mock
	private UniversityRepository universityRepositoryMock;
	
	@Mock
	private List<University> universityListMock;
	
	@Test
	public void shouldInitializeWithTwoDemoUniversities() {
		universityRepositoryMock.save(new University());
		universityRepositoryMock.save(new University());
		// assert
		verify(universityRepositoryMock, times(2)).save(any(University.class));
	}
	
	@Test
	public void shouldRemoveDemoUniversity() {
		universityRepositoryMock.remove(new University());
		// assert
		verify(universityRepositoryMock, times(1)).remove(any(University.class));
	}
	
	@Test
	public void shouldReturnUniversityDetails() {
		
		//arrange
		University demoUniversity = new University("testAddress1", null, "testCity", "testUniversity", "NA", "11111");
		when(universityRepositoryMock.findByName("testUniversity")).thenReturn(demoUniversity);

		// act
		University universityDetails = universityRepositoryMock.findByName("testUniversity");

		// assert
		assertThat(demoUniversity.getName()).isEqualTo(universityDetails.getName());
		assertThat(demoUniversity.getAddressLine1()).isEqualTo(universityDetails.getAddressLine1());
		assertThat(demoUniversity.getAddressLine2()).isEqualTo(universityDetails.getAddressLine2());
		assertThat(demoUniversity.getCity()).isEqualTo(universityDetails.getCity());
		assertThat(demoUniversity.getState()).isEqualTo(universityDetails.getState());
		assertThat(demoUniversity.getZip()).isEqualTo(universityDetails.getZip());
	}
	
	@Test
	public void shouldReturnUniversityList() {
		
		//arrange
		University demoUniversity = new University((long) 1, "testAddress1", null, "testCity", "testUniversity", "NA", "11111");
		University demoUniversity2 = new University((long) 2, "testAddress1", null, "testCity", "testUniversity", "NA", "11111");
		universityListMock.add(demoUniversity);
		universityListMock.add(demoUniversity2);
		when(universityRepositoryMock.getUniversityList()).thenReturn(universityListMock);

		// act
		List<University> demoUniversityList = universityRepositoryMock.getUniversityList();

		// assert
		assertThat(demoUniversityList.contains(demoUniversity));
	}
	
	@Test
	public void universityFormTest() {
		
		//arrange
		University demoUniversity = new University((long) 1, "testAddress1", null, "testCity", "testUniversity", "NA", "11111");
		UniversityForm universityForm = new UniversityForm(demoUniversity);
		
		// act
		University universityDetails = universityForm.createUniversity();

		// assert
		assertThat(universityForm.getName()).isEqualTo(universityDetails.getName());
		assertThat(universityForm.getAddressLine1()).isEqualTo(universityDetails.getAddressLine1());
		assertThat(universityForm.getAddressLine2()).isEqualTo(universityDetails.getAddressLine2());
		assertThat(universityForm.getAddressCity()).isEqualTo(universityDetails.getCity());
		assertThat(universityForm.getAddressState()).isEqualTo(universityDetails.getState());
		assertThat(universityForm.getAddressZip()).isEqualTo(universityDetails.getZip());
	}

}
