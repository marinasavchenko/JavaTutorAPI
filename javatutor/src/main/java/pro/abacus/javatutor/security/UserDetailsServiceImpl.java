package pro.abacus.javatutor.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pro.abacus.javatutor.domain.User;
import pro.abacus.javatutor.repository.UserRepository;

/**
 * Authenticate a user from the database.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepository accountRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOG.trace("Load user by username" + username);
		User user = accountRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new UserDetailsImpl(user);
	}

}
