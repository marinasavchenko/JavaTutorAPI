package pro.abacus.javatutor.security;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import pro.abacus.javatutor.domain.User;
import pro.abacus.javatutor.repository.UserRepository;

public class UserDetailsServiceImplTest {
	
private static final String ANY_USER_NAME = "Brigette";
	
	private UserRepository userRepository = mock(UserRepository.class);
	
	private User user = mock(User.class);
	
	UserDetailsService userDetailService = new UserDetailsServiceImpl(userRepository);
	
	@Test (expected = UsernameNotFoundException.class)
	public void shouldThrowUsernameNotFoundException(){
		when(userRepository.findByUsername(ANY_USER_NAME)).thenReturn(null);
	
		userDetailService.loadUserByUsername(ANY_USER_NAME);
	}
	
	@Test
	public void shouldReturnInstanceWebUserDetails(){
		when(userRepository.findByUsername(ANY_USER_NAME)).thenReturn(user);
		UserDetails userDetails =userDetailService.loadUserByUsername(ANY_USER_NAME);
		
		assertNotNull(userDetails);
		verify(userRepository).findByUsername(ANY_USER_NAME);
	}

}
